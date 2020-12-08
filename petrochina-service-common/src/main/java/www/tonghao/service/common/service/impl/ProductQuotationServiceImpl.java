package www.tonghao.service.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.mapper.ProductQuotationMapper;
import www.tonghao.service.common.mapper.ProductsMapper;
import www.tonghao.service.common.mapper.ProtocolMapper;
import www.tonghao.service.common.service.MessagePoolService;
import www.tonghao.service.common.service.ProductQuotationService;

import java.util.*;

@Service("productQuotationService")
public class ProductQuotationServiceImpl extends BaseServiceImpl<ProductQuotation> implements ProductQuotationService {

    @Autowired
    private ProductQuotationMapper productQuotationMapper;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private MessagePoolService messagePoolService;

    @Override
    public Map<String, Object> updateStatus(ProductQuotation productQuotation) {
        if (productQuotation.getStatus() != null && productQuotation.getStatus() == 3) {
            Map<String, Object> check = productCheck(productQuotation.getId());
            if (check.get(ResultUtil.STATUS).toString().equals(ResultUtil.ERROR)) {
                return check;
            }
        }
        ProductQuotation entity = new ProductQuotation();
        entity.setId(productQuotation.getId());
        entity.setStatus(productQuotation.getStatus());
        int i = productQuotationMapper.updateByPrimaryKeySelective(entity);
        if (i > 0) {
            // 保存商品消息
            if(productQuotation.getStatus() == 3){
                messagePoolService.addProductMessage(entity.getId(), 6L, 1);
            }
            messagePoolService.addProductMessage(entity.getId(), 4L, productQuotation.getStatus() == 3 ? 1 : 2);
        }
        return ResultUtil.resultMessage(i, "操作成功", "操作失败");
    }

    @Override
    public Map<String, Object> updateStock(ProductQuotation productQuotation) {
        ProductQuotation entity = new ProductQuotation();
        entity.setId(productQuotation.getId());
        entity.setStock(productQuotation.getStock());
        int i = productQuotationMapper.updateByPrimaryKeySelective(entity);
        return ResultUtil.resultMessage(i, "操作成功", "操作失败");
    }

    @Override
    public Map<String, Object> batchUpdateStatus(String ids, Integer status) {
        int result = 0;
        if (ids != null && status != null) {
            if (status == 3) {
                // 上架
                String[] split = ids.split(",");
                List<ProductQuotation> list = new ArrayList<>();
                ProductQuotation productQuotation = null;
                for (String str : split) {
                    Map<String, Object> check = productCheck(Long.parseLong(str));
                    if (check.get(ResultUtil.STATUS).toString().equals(ResultUtil.ERROR)) {
                        return check;
                    }
                    productQuotation = new ProductQuotation();
                    productQuotation.setId(Long.parseLong(str));
                    productQuotation.setStatus(status);
                    list.add(productQuotation);
                }
                if (CollectionUtils.isNotEmpty(list)) {
                    for (ProductQuotation entity : list) {
                        result = productQuotationMapper.updateByPrimaryKeySelective(entity);
                        if (result > 0) {
                            // 保存商品消息
                            messagePoolService.addProductMessage(entity.getId(), 6L, 1);
                            messagePoolService.addProductMessage(entity.getId(), 4L, 1);
                        }
                    }
                }
            } else {
                // 下架
                String[] split = ids.split(",");
                for (String str : split) {
                    ProductQuotation productQuotation = new ProductQuotation();
                    productQuotation.setId(Long.parseLong(str));
                    productQuotation.setStatus(status);
                    result = productQuotationMapper.updateByPrimaryKeySelective(productQuotation);
                    if (result > 0) {
                        // 保存商品消息
                        messagePoolService.addProductMessage(Long.parseLong(str), 4L, 2);
                    }
                }
            }
        }
        return ResultUtil.resultMessage(result, "操作成功", "操作失败");
    }

    public Map<String, Object> productCheck(Long id) {
        ProductQuotation productQuotation = productQuotationMapper.selectByPrimaryKey(id);
        if (productQuotation == null) {
            return ResultUtil.error("商品不存在");
        }
        Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
        if (products == null) {
            return ResultUtil.error(productQuotation.getSku() + ":商品不存在");
        }
        if (products.getStatus() == null || products.getStatus() != 3) {
            return ResultUtil.error(productQuotation.getSku() + ":商品未上架");
        }
        if (productQuotation.getPrice() == null || productQuotation.getProtocolPrice() == null) {
            return ResultUtil.error(productQuotation.getSku() + ":商品价格不能为空");
        }
        if (productQuotation.getStock() == null || productQuotation.getStock() < 0) {
            return ResultUtil.error(productQuotation.getSku() + ":商品库存异常");
        }
        return ResultUtil.success("");
    }

    @Override
    public Map<String, Object> updateProtocolPrice(ProductQuotation productQuotation) {
        if (productQuotation.getProtocolPrice() == null) {
            return ResultUtil.error("请输入商品协议价");
        }
        ProductQuotation entity = new ProductQuotation();
        entity.setId(productQuotation.getId());
        entity.setProtocolPrice(productQuotation.getProtocolPrice());
        int i = productQuotationMapper.updateByPrimaryKeySelective(entity);
        if (i > 0) {
            // 添加商品价格消息
            messagePoolService.addProductMessage(productQuotation.getId(), 2L, 1);
        }
        return ResultUtil.resultMessage(i, "操作成功", "操作失败");
    }

    @Override
    public Map<String, Object> deleteProtocolProduct(Long id) {
        ProductQuotation productQuotation = new ProductQuotation();
        productQuotation.setId(id);
        productQuotation.setIsDelete(1);
        int i = productQuotationMapper.updateByPrimaryKeySelective(productQuotation);
        if (i > 0) {
            // 添加商品删除消息
            messagePoolService.addProductMessage(productQuotation.getId(), 6L, 2);
        }
        return ResultUtil.resultMessage(i, "操作成功", "操作失败");
    }

    @Override
    public PageInfo<ProductQuotation> selectProtocolProductList(PageBean page, Map<String, Object> map) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<ProductQuotation> productQuotationList = productQuotationMapper.selectByProtocolId(map);
        if (CollectionUtils.isNotEmpty(productQuotationList)) {
            for (ProductQuotation productQuotation : productQuotationList) {
                Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
                productQuotation.setProducts(products);
            }
        }
        return new PageInfo<>(productQuotationList);
    }

    @Override
    public PageInfo<Products> getProductPage(PageBean page, Map<String, Object> map) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Products> productsList = productQuotationMapper.selectAddProtocolProductList(map);
        return new PageInfo<>(productsList);
    }

    @Override
    public Map<String, Object> addProtocolProduct(Protocol protocol) {
        List<ProductQuotation> productQuotations = protocol.getProductQuotations();
        if (CollectionUtils.isEmpty(productQuotations)) {
            return ResultUtil.error("请选择商品");
        }
        Protocol entity = protocolMapper.selectByPrimaryKey(protocol.getId());
        if (entity == null) {
            return ResultUtil.error("未查询到协议信息");
        }
        for (ProductQuotation productQuotation : productQuotations) {
            Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
            if (products == null) {
                return ResultUtil.error("未查询到商品信息");
            }
            productQuotation.setCatalogId(products.getCatalogId());
            productQuotation.setSku(products.getSku());
            productQuotation.setIsDelete(0);
            productQuotation.setPlatformInfoCode(entity.getPlatformInfoCode());
            productQuotation.setPlatformInfoId(entity.getPlatformInfoId());
            productQuotation.setProtocolId(entity.getId());
            productQuotation.setCreatedAt(DateUtilEx.timeFormat(new Date()));
            productQuotation.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
            productQuotation.setPrice(products.getPrice());
            productQuotation.setStatus(0);
            productQuotation.setSales(0);
            // 查询是否存在
            int i = 0;
            Example example = new Example(ProductQuotation.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("productId", productQuotation.getProductId());
            criteria.andEqualTo("platformInfoId", entity.getPlatformInfoId());
            List<ProductQuotation> productQuotationList = productQuotationMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(productQuotationList)){
                productQuotation.setId(productQuotationList.get(0).getId());
                i = productQuotationMapper.updateByPrimaryKey(productQuotation);
            }else{
                productQuotation.setId(null);
                i = productQuotationMapper.insertSelective(productQuotation);
            }
        }
        return ResultUtil.success("");
    }
}

