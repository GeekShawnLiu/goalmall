package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaEqualsUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.service.common.entity.*;
import www.tonghao.service.common.service.*;
import www.tonghao.service.common.service.impl.PlatformInfoServiceImpl;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/protocol")
@Api(value = "protocolController", description = "协议Api")
public class ProtocolController {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private PlatformInfoService platformInfoService;

    @ApiOperation(value = "分页查询", notes = "分页查询api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "协议名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "协议编号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "失效日期开始", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "失效日期结束", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public PageInfo<Protocol> getPage(@ModelAttribute PageBean page, String name, String code, Integer status, String startTime, String endTime) {
        PageHelper.startPage(page.getPage(), page.getRows());
        Example example = new Example(Protocol.class);
        Criteria createCriteria = example.createCriteria();
        Map<String, Object> maplike = new HashMap<String, Object>();
        maplike.put("name", name);
        maplike.put("code", code);
        CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
        Map<String, Object> mapEq = new HashMap<String, Object>();
        mapEq.put("status", status);
        mapEq.put("isDelete", 0);
        CriteriaEqualsUtil.criteriaEquals(createCriteria, mapEq);
        CriteriaLikeUtil.criteriaBetwwen(createCriteria, "endTime", startTime, endTime);
        List<Protocol> list = protocolService.selectByExample(example);
        return new PageInfo<Protocol>(list);
    }

    @ApiOperation(value = "获取所有的平台信息", notes = "获取所有的平台信息api")
    @RequestMapping(value = "/getAllPlatformInfo", method = RequestMethod.GET)
    public List<PlatformInfo> getAllPlatformInfo(Long id) {
        List<PlatformInfo> platformInfos = platformInfoService.selectAllPlatformInfo();
        return platformInfos;
    }

    @ApiOperation(value = "添加", notes = "添加api")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody Protocol protocol) {
        return protocolService.saveEntity(protocol);
    }

    @ApiOperation(value = "删除协议", notes = "删除协议api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map<String, Object> delete(Long id) {
        return protocolService.deleteEntity(id);
    }

    @ApiOperation(value = "根据id查询", notes = "查询单条api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query"),
    })
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Protocol getById(@RequestParam("id") Long id) {
        return protocolService.selectByKey(id);
    }

    @ApiOperation(value = "修改", notes = "修改api")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(@RequestBody Protocol protocol) {
        return protocolService.updateEntity(protocol);
    }

    @ApiOperation(value = "修改协议状态", notes = "修改协议状态api")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Map<String, Object> updateStatus(@RequestBody Protocol protocol) {
        return protocolService.updateStatus(protocol);
    }

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "商品名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sku", value = "sku", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议id", required = true, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/getProductPage", method = RequestMethod.GET)
    public PageInfo<Products> getProductPage(@ModelAttribute PageBean page, String productName, String sku, Long protocolId) {
        return protocolService.getProductPage(page, productName, sku, protocolId);
    }


    @ApiOperation(value = "添加协议商品关联信息", notes = "添加协议商品关联信息api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "协议id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productIds", value = "商品id集合", required = true, dataType = "Array", paramType = "query"),
    })
    @RequestMapping(value = "/addProtocolProduct", method = RequestMethod.POST)
    public Map<String, Object> addProtocolProduct(@RequestBody Protocol protocol) {
        return protocolService.addProtocolProduct(protocol);
    }

    @ApiOperation(value = "查询协议关联的商品列表", notes = "获取商品列表api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "商品名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sku", value = "sku", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "protocolId", value = "协议id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 3上架  4下架", required = false, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/selectProtocolProductList", method = RequestMethod.GET)
    public PageInfo<ProductQuotation> selectProtocolProductList(@ModelAttribute PageBean page, String productName, String sku, Long protocolId, Integer status) {
        return protocolService.selectProtocolProductList(page, productName, sku, protocolId, status);
    }

    @ApiOperation(value = "修改协议商品信息", notes = "修改协议商品信息api")
    @RequestMapping(value = "/updateProtocolProductInfo", method = RequestMethod.POST)
    public Map<String, Object> updateProtocolProductInfo(@RequestBody ProductQuotation productQuotation) {
        return protocolService.updateProtocolProductInfo(productQuotation);
    }
}
