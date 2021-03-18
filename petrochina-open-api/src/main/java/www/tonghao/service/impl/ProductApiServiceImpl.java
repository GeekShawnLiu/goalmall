package www.tonghao.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.dto.ImageDto;
import www.tonghao.dto.MessageDto;
import www.tonghao.dto.ProductDto;
import www.tonghao.dto.ProductImageDto;
import www.tonghao.service.ProductApiService;
import www.tonghao.service.common.entity.*;
import www.tonghao.service.common.mapper.*;
import www.tonghao.util.ApiResultUtil;

import java.math.BigDecimal;
import java.util.*;

@Service("productApiService")
public class ProductApiServiceImpl implements ProductApiService {

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ProductPicsMapper productPicsMapper;

    @Autowired
    private MessagePoolMapper messagePoolMapper;

    @Autowired
    private PlatformInfoMapper platformInfoMapper;

    @Autowired
    private PlatformCatalogMappingMapper platformCatalogMappingMapper;

    @Autowired
    private ProductQuotationMapper productQuotationMapper;

    @Autowired
    private ThirdPlatformCatalogsMapper thirdPlatformCatalogsMapper;

    @Autowired
    private ProductParameterMapper productParameterMapper;

    @Value("${product-view-url-prefix}")
    private String productViewUrlPrefix;

    @Override
    public String getPools(String platformCode) {
        try {
            PlatformInfo platformInfo = platformInfoMapper.selectByPlatformCode(platformCode);
            if (platformInfo == null) {
                return ApiResultUtil.error("platformCode有误");
            }
            List<String> strings = thirdPlatformCatalogsMapper.selectByPlatformInfoCode(platformCode);
            return ApiResultUtil.success("", strings);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String skus(String catalogId, String platformCode) {
        try {
            if (StringUtils.isNotBlank(catalogId)) {
                List<String> skuList = productQuotationMapper.selectSkuPool(catalogId, platformCode);
                if (CollectionUtils.isNotEmpty(skuList)) {
                    return ApiResultUtil.success("操作成功", skuList);
                }
            }
            return ApiResultUtil.success("", new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String detail(String sku, String productExtendAttributes, String platformCode) {
        try {
            ProductQuotation productQuotation = productQuotationMapper.selectBySku(sku, platformCode);
            if (productQuotation == null) {
                return ApiResultUtil.error("商品不存在");
            }
            Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
            if (products == null) {
                return ApiResultUtil.error("商品不存在");
            }
            ProductDto productDto = new ProductDto();
            productDto.setSku(products.getSku());
            productDto.setUrl(productViewUrlPrefix + products.getId());
            productDto.setModel(products.getModel());
            productDto.setWeight(products.getWeight());
            productDto.setImage_path(products.getCoverUrl());
            productDto.setState((productQuotation.getStatus() != null && productQuotation.getStatus() == 3) ? "1" : "0");
            productDto.setBrand_name(products.getBrandName());
            productDto.setName(products.getName());
            productDto.setProduct_area(products.getProductArea());
            productDto.setUpc(products.getUpc());
            productDto.setUnit(products.getUnit());
            Long catalogId = productQuotation.getCatalogId();
            PlatformCatalogMapping platformCatalogMapping = platformCatalogMappingMapper.selectByCatalogId(catalogId, platformCode);
            if (platformCatalogMapping != null) {
                ThirdPlatformCatalogs thirdPlatformCatalogs = thirdPlatformCatalogsMapper.selectByPrimaryKey(platformCatalogMapping.getThirdPlatformCatalogId());
                if (thirdPlatformCatalogs != null) {
                    productDto.setCategory(thirdPlatformCatalogs.getCatalogId());
                }
            }
            productDto.setService(products.getAfterSaleService());
            productDto.setCode_69("");
            productDto.setIntroduction(products.getDetail());
            productDto.setWare(products.getWare());
            productDto.setSale_actives(0);
            // 查询商品参数
            productDto.setParam(getParamTable(products.getBrandName(), products.getName(), products.getModel(), products.getUnit()));
            List<Map<String, Object>> attributes = new ArrayList<>();
            Map<String, Object> map = null;
            List<ProductParameter> productParameters = productParameterMapper.getByProductId(products.getId());
            if (CollectionUtils.isNotEmpty(productParameters)) {
                for (ProductParameter productParameter : productParameters) {
                    map = new HashMap<>();
                    map.put("attributeID", productParameter.getParentParamId());
                    map.put("attributeName", productParameter.getParentParamValue());
                    map.put("valueID", productParameter.getParamId());
                    map.put("value", productParameter.getParamValue());
                    attributes.add(map);
                }
            }
            productDto.setAttributes(JsonUtil.toJson(attributes));
            return ApiResultUtil.success("操作成功", productDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String shelfStates(String sku, String platformCode) {
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            List<ProductQuotation> productQuotations = productQuotationMapper.selectBySkus(sku.split(","), platformCode);
            Map<String, Object> map = null;
            if (CollectionUtils.isNotEmpty(productQuotations)) {
                for (ProductQuotation productQuotation : productQuotations) {
                    map = new HashMap<>();
                    map.put("sku", productQuotation.getSku());
                    map.put("state", (productQuotation.getStatus() != null && productQuotation.getStatus() == 3) ? 1 : 0);
                    result.add(map);
                }
            }
            return ApiResultUtil.success("操作成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String images(String sku, String platformCode) {
        try {
            List<ProductImageDto> result = new ArrayList<>();
            List<ProductQuotation> productQuotations = productQuotationMapper.selectBySkus(sku.split(","), platformCode);
            if (CollectionUtils.isNotEmpty(productQuotations)) {
                ProductImageDto productImageDto = null;
                for (ProductQuotation productQuotation : productQuotations) {
                    productImageDto = new ProductImageDto();
                    productImageDto.setSku(productQuotation.getSku());
                    List<ImageDto> imageDtos = new ArrayList<>();
                    List<ProductPics> productPics = productPicsMapper.getByProductId(productQuotation.getProductId());
                    if (CollectionUtils.isNotEmpty(productPics)) {
                        ImageDto imageDto = null;
                        for (int i = 0; i < productPics.size(); i++) {
                            imageDto = new ImageDto();
                            imageDto.setOrder(i);
                            imageDto.setPath(productPics.get(i).getSource());
                            imageDtos.add(imageDto);
                        }
                    }
                    productImageDto.setImages(imageDtos);
                    result.add(productImageDto);
                }
            }
            return ApiResultUtil.success("操作成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String prices(String sku, String platformCode) {
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            List<ProductQuotation> productQuotations = productQuotationMapper.selectBySkus(sku.split(","), platformCode);
            Map<String, Object> map = null;
            if (CollectionUtils.isNotEmpty(productQuotations)) {
                for (ProductQuotation productQuotation : productQuotations) {
                    map = new HashMap<>();
                    map.put("sku", productQuotation.getSku());
                    map.put("price", productQuotation.getProtocolPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
                    map.put("mall_price", productQuotation.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
                    result.add(map);
                }
            }
            return ApiResultUtil.success("操作成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String stocks(String sku, String area, String platformCode) {
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            List<ProductQuotation> productQuotations = productQuotationMapper.selectBySkus(sku.split(","), platformCode);
            Map<String, Object> map = null;
            if (CollectionUtils.isNotEmpty(productQuotations)) {
                for (ProductQuotation productQuotation : productQuotations) {
                    map = new HashMap<>();
                    map.put("area", area);
                    map.put("desc", productQuotation.getStock() > 0 ? "有货" : "无货");
                    map.put("sku", productQuotation.getSku());
                    map.put("num", productQuotation.getStock());
                    result.add(map);
                }
            }
            return ApiResultUtil.success("操作成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String getMessagePool(String type, String platformCode) {
        Example example = new Example(MessagePool.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", type);
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("platformCode", platformCode);
        example.orderBy("id");
        List<MessagePool> messagePools = messagePoolMapper.selectByExample(example);
        List<MessageDto> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(messagePools)) {
            MessageDto messageDto = null;
            for (MessagePool messagePool : messagePools) {
                messageDto = new MessageDto();
                messageDto.setId(messagePool.getId() + "");
                messageDto.setTime(messagePool.getCreatedAt());
                messageDto.setType(Integer.parseInt(type));
                Map<String, Object> map = new HashMap<>();
                switch (type) {
                    case "2": // 商品价格变更
                        map.put("skuId", messagePool.getSku());
                        map.put("state", messagePool.getState());
                        map.put("price", messagePool.getPrice());
                        map.put("market_price", messagePool.getMarketPrice());
                        break;
                    case "4": // 代表商品上下架变更消息
                    case "6": // 代表添加、删除商品池内的商品
                    case "16": // 商品介绍及规格参数变更消息
                        map.put("skuId", messagePool.getSku());
                        map.put("state", messagePool.getState());
                        break;
                    case "5": // 订单已完成
                        map.put("orderId", messagePool.getOrderSn());
                        map.put("state", 1);
                        break;
                    case "10": // 代表订单取消
                        map.put("orderId", messagePool.getOrderSn());
                        map.put("state", messagePool.getState());
                        break;
                    case "12": // 配送单生成成功消息
                        map.put("orderId", messagePool.getOrderSn());
                        break;

                }
                messageDto.setResult(map);
                result.add(messageDto);
            }
        }
        return ApiResultUtil.success("操作成功", result);
    }

    @Override
    public String delMessagePool(Long messageId, String platformCode) {
        MessagePool messagePool = messagePoolMapper.selectByPrimaryKey(messageId);
        if (messagePool != null && messagePool.getPlatformCode() != null && messagePool.getPlatformCode().equals(platformCode)) {
            messagePool.setIsDelete(1);
            messagePool.setUpdateAt(DateUtilEx.timeFormat(new Date()));
            int i = messagePoolMapper.updateByPrimaryKeySelective(messagePool);
            if (i > 0) {
                return ApiResultUtil.success("消息" + messageId + "删除成功");
            }
        }
        return ApiResultUtil.error("消息" + messageId + "删除失败");
    }

    @Override
    public String certificates(String skus, String platformCode) {
        List<ProductQuotation> productQuotations = productQuotationMapper.selectBySkus(skus.split(","), platformCode);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        if (CollectionUtils.isNotEmpty(productQuotations)) {
            for (ProductQuotation productQuotation : productQuotations) {
                map = new HashMap<>();
                map.put("sku", productQuotation.getSku());
                Long productId = productQuotation.getProductId();
                Products products = productsMapper.selectByPrimaryKey(productId);
                if (products != null) {
                    map.put("save_energy_cert_no", products.getEnergySaveCertNo());
                    map.put("save_energy_cert_image", products.getEnergySaveCertImg());
                    if (StringUtils.isNotBlank(products.getEnergySaveCertIndate())) {
                        Date energySaveCertIndate = DateUtilEx.strToDate(products.getEnergySaveCertIndate(), DateUtilEx.DATE_PATTERN);
                        map.put("save_energy_cert_indate", energySaveCertIndate);
                    }
                    map.put("environment_protect_cert_no", products.getEnvironmentCertNo());
                    map.put("environment_protect_cert_image", products.getEnvironmentCertImg());
                    if (StringUtils.isNotBlank(products.getEnvironmentCertIndate())) {
                        Date environmentCertIndate = DateUtilEx.strToDate(products.getEnvironmentCertIndate(), DateUtilEx.DATE_PATTERN);
                        map.put("environment_protect_cert_indate", environmentCertIndate);
                    }
                }
                result.add(map);
            }
        }
        return ApiResultUtil.success("操作成功", result);
    }


    /**
     * 获取规格参数列表
     *
     * @param brandName
     * @param productName
     * @param model
     * @param unit
     * @return
     */
    public String getParamTable(String brandName, String productName, String model, String unit) {
        brandName = brandName == null ? "" : brandName;
        productName = productName == null ? "" : productName;
        model = model == null ? "" : model;
        unit = unit == null ? "" : unit;
        String paramTable = "<table class='Ptable'>" +
                "<tr>" +
                    "<td class='tdTitle'>品牌</td>" +
                    "<td>" + brandName + "</td>" +
                "</tr>" +
                "<tr>" +
                    "<td class='tdTitle'>商品名称</td>" +
                    "<td>" + productName + "</td>" +
                "</tr>" +
                "<tr>" +
                    "<td class='tdTitle'>型号</td>" +
                    "<td>" + model + "</td>" +
                "</tr>" +
                "<tr>" +
                    "<td class='tdTitle'>单位</td>" +
                    "<td>" + unit + "</td>" +
                "</tr>" +
                "</table>";
        return paramTable;
    }
}
