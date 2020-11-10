package www.tonghao.platform.web;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.service.ProductQuotationService;
import www.tonghao.service.common.service.ProductsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/protocol_product")
@Api(value = "ProtocolProductController", description = "协议商品Api")
public class ProtocolProductController {

    @Autowired
    private ProductQuotationService productQuotationService;

    @Autowired
    private ProductsService productsService;

    @ApiOperation(value = "查询协议关联的商品列表", notes = "获取商品列表api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "商品名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sku", value = "sku", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 3上架  4下架", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "catalogId", value = "品目id", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "brandName", value = "品牌信息", required = false, dataType = "string", paramType = "query"),
    })
    @RequestMapping(value = "/selectProtocolProductList", method = RequestMethod.GET)
    public PageInfo<ProductQuotation> selectProtocolProductList(@ModelAttribute PageBean page, String productName, String sku,
                                                                Long protocolId, Integer status, Long catalogId, String brandName) {
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productName);
        map.put("sku", sku);
        map.put("protocolId", protocolId);
        map.put("status", status);
        map.put("catalogId", catalogId);
        map.put("brandName", brandName);
        return productQuotationService.selectProtocolProductList(page, map);
    }

    @ApiOperation(value = "查询协议商品详情信息", notes = "查询协议商品详情信息")
    @RequestMapping(value = "/selectProtocolProductInfo", method = RequestMethod.GET)
    public ProductQuotation selectProtocolProductInfo(Long id) {
        ProductQuotation productQuotation = productQuotationService.selectByKey(id);
        if (productQuotation != null) {
            Products products = productsService.selectByKey(productQuotation.getProductId());
            productQuotation.setProducts(products);
        }
        return productQuotation;
    }

    @ApiOperation(value = "修改协议商品状态", notes = "修改协议商品状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Map<String, Object> updateStatus(@RequestBody ProductQuotation productQuotation) {
        return productQuotationService.updateStatus(productQuotation);
    }

    @ApiOperation(value = "修改协议商品库存信息", notes = "修改协议商品库存信息")
    @RequestMapping(value = "/updateStock", method = RequestMethod.POST)
    public Map<String, Object> updateStock(@RequestBody ProductQuotation productQuotation) {
        return productQuotationService.updateStock(productQuotation);
    }

    @ApiOperation(value = "批量上架", notes = "批量上架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/batchShelves", method = RequestMethod.POST)
    public Map<String, Object> batchShelves(String ids) {
        return productQuotationService.batchUpdateStatus(ids, 3);
    }

    @ApiOperation(value = "批量下架", notes = "批量下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/batchShelvesDown", method = RequestMethod.POST)
    public Map<String, Object> batchShelvesDown(String ids) {
        return productQuotationService.batchUpdateStatus(ids, 4);
    }

    @ApiOperation(value = "修改协议商品价格", notes = "修改协议商品价格")
    @RequestMapping(value = "/updateProtocolPrice", method = RequestMethod.POST)
    public Map<String, Object> updateProtocolPrice(@RequestBody ProductQuotation productQuotation) {
        return productQuotationService.updateProtocolPrice(productQuotation);
    }

    @ApiOperation(value = "移除协议商品", notes = "移除协议商品")
    @RequestMapping(value = "/deleteProtocolProduct", method = RequestMethod.POST)
    public Map<String, Object> deleteProtocolProduct(Long id) {
        return productQuotationService.deleteProtocolProduct(id);
    }

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "商品名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sku", value = "sku", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "catalogId", value = "品目id", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "brandName", value = "品牌信息", required = false, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/getProductPage", method = RequestMethod.GET)
    public PageInfo<Products> getProductPage(@ModelAttribute PageBean page, String productName, String sku, Long protocolId, Long catalogId, String brandName) {
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productName);
        map.put("sku", sku);
        map.put("protocolId", protocolId);
        map.put("catalogId", catalogId);
        map.put("brandName", brandName);
        return productQuotationService.getProductPage(page, map);
    }

    @ApiOperation(value = "添加协议商品关联信息", notes = "添加协议商品关联信息api")
    @RequestMapping(value = "/addProtocolProduct", method = RequestMethod.POST)
    public Map<String, Object> addProtocolProduct(@RequestBody Protocol protocol) {
        return productQuotationService.addProtocolProduct(protocol);
    }
}
