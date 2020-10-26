package www.tonghao.service.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PlatformInfo;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.mapper.PlatformInfoMapper;
import www.tonghao.service.common.mapper.ProductQuotationMapper;
import www.tonghao.service.common.mapper.ProductsMapper;
import www.tonghao.service.common.mapper.ProtocolMapper;
import www.tonghao.service.common.service.ProductQuotationService;
import www.tonghao.service.common.service.ProtocolService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("protocolService")
public class ProtocolServiceImpl extends BaseServiceImpl<Protocol> implements ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private PlatformInfoMapper platformInfoMapper;

    @Autowired
    private ProductQuotationMapper productQuotationMapper;

    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public Map<String, Object> saveEntity(Protocol protocol) {
        if (protocol.getPlatformInfoId() == null) {
            return ResultUtil.error("请选择平台信息");
        }
        PlatformInfo platformInfo = platformInfoMapper.selectByPrimaryKey(protocol.getPlatformInfoId());
        if (platformInfo == null) {
            return ResultUtil.error("平台信息不存在");
        }
        protocol.setIsDelete(0);
        protocol.setCreatedAt(DateUtilEx.timeFormat(new Date()));
        protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        protocol.setPlatformInfoCode(platformInfo.getCode());
        int i = protocolMapper.insertSelective(protocol);
        return ResultUtil.resultMessage(i, "");
    }

    @Override
    public Map<String, Object> updateEntity(Protocol protocol) {
        protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        int i = protocolMapper.updateByPrimaryKeySelective(protocol);
        return ResultUtil.resultMessage(i, "");
    }

    @Override
    public Map<String, Object> deleteEntity(Long id) {
        int result = 0;
        Protocol protocol = protocolMapper.selectByPrimaryKey(id);
        if (protocol != null) {
            protocol.setIsDelete(1);
            protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
            result = protocolMapper.updateByPrimaryKeySelective(protocol);
        }
        return ResultUtil.resultMessage(result, "");
    }

    @Override
    public Map<String, Object> updateStatus(Protocol protocol) {
        int result = 0;
        Protocol p = protocolMapper.selectByPrimaryKey(protocol.getId());
        if (p != null) {
            protocol.setStatus(protocol.getStatus());
            protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
            result = protocolMapper.updateByPrimaryKeySelective(protocol);
        }
        return ResultUtil.resultMessage(result, "");
    }

    @Override
    public PageInfo<Products> getProductPage(PageBean page, String productName, String sku, Long protocolId) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Products> productsList = productQuotationMapper.selectAddProtocolProductList(protocolId, productName, sku);
        return new PageInfo<Products>(productsList);
    }

    @Override
    public Map<String, Object> addProtocolProduct(Protocol protocol) {
        List<Long> productIds = protocol.getProductIds();
        if (CollectionUtils.isEmpty(productIds)) {
            return ResultUtil.error("请选择商品");
        }
        Protocol entity = protocolMapper.selectByPrimaryKey(protocol.getId());
        if (entity == null) {
            return ResultUtil.error("未查询到协议信息");
        }
        ProductQuotation productQuotation = null;
        for (Long productId : productIds) {
            productQuotation = new ProductQuotation();
            productQuotation.setProductId(productId);
            Products products = productsMapper.selectByPrimaryKey(productId);
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
            productQuotationMapper.insertSelective(productQuotation);
        }
        return ResultUtil.success("");
    }

    @Override
    public PageInfo<ProductQuotation> selectProtocolProductList(PageBean page, String productName, String sku, Long protocolId, Integer status) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<ProductQuotation> productQuotationList = productQuotationMapper.selectByProtocolId(protocolId, productName, sku, status);
        if (CollectionUtils.isNotEmpty(productQuotationList)) {
            for (ProductQuotation productQuotation : productQuotationList) {
                Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
                productQuotation.setProducts(products);
            }
        }
        return new PageInfo<ProductQuotation>(productQuotationList);
    }

    @Override
    public Map<String, Object> updateProtocolProductInfo(ProductQuotation productQuotation) {
        productQuotation.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        int i = productQuotationMapper.updateByPrimaryKeySelective(productQuotation);
        return ResultUtil.resultMessage(i, "");
    }
}
