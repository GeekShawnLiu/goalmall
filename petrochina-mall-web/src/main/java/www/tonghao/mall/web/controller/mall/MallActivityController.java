package www.tonghao.mall.web.controller.mall;

import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.PriceUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.mall.warpper.GoodsResponse;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.ActivityService;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="mallActivityController",description="商城活动api")
@RestController(value="mallActivityController")
@RequestMapping(value="/m_activity")
public class MallActivityController {

	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private MallProductService mallProductService;
	
	@Autowired
	private MallCatalogsService mallCatalogsService;
	
	@Autowired
	private IntegralUserService integralUserService;
	
	/**
	 * 
	 * Description: 获取活动列表页楼层数据
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="活动列表页楼层数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name="count",value="楼层展示商品数量 默认10",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="kind",value="活动分类标识  0福瑞商城 1爱心超市 2积分乐园  默认0",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="type",value="活动类型标识  1积分活动，2促销活动  默认1",required=false,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/getActivityFloor")
	public List<Activity> getActivityFloor(HttpServletRequest request, Integer count, Integer kind, Integer type){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("count", count == null ? 10 : count);
			map.put("kind", kind == null ? 0 : kind);
			map.put("type", type == null ? 1 : type);
			//根据机构判断商品展示
			if(user != null && user.getType() != null && user.getType() == 1){
				map.put("orgId", user.getDepId());
			}
			List<Activity> activityList = activityService.getActivityFloor(map);
			activityList.forEach(activity -> {
				List<Products> list = activity.getProductsList();
				if(!Collections.isEmpty(list)){
					list.forEach(product -> {
						MallProducts mallProduct = new MallProducts();
						BeanUtils.copyProperties(product, mallProduct);
						product.setAttributeModel(mallProductService.getProductAttributeModel(mallProduct));
					});
				}
			});
			return activityList;
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 活动详情页商品列表
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="活动详情页商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="activityId",value="活动id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="productName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderby",value="排序 销量sales 折扣discount 价格price",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="isAsc",value="是否升序",required=false,dataType="boolean",paramType="query"),
		@ApiImplicitParam(name="mallCatalogsIds",value="品目Id",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="keywords",value="pc搜索关键字",required=false,dataType="String",paramType="query"),
	})
	@GetMapping(value = "/activityProducts")
	public Map<String, Object> activityProducts(HttpServletRequest request, @ModelAttribute PageBean page, Long activityId, String orderby, Boolean isAsc
			, String productName
			, @RequestParam(value="mallCatalogsIds[]",required=false) Long[] mallCatalogsIds
			, @RequestParam(value="brands[]",required=false) Long[] brands
    		, @RequestParam(value="suppliers[]",required=false)Long[] suppliers
    		, @RequestParam(value="priceRanges[]",required=false)String[] priceRanges, String keywords){
		Users user = UserUtil.getUser(request);
		Map<String, Object> result = activityService.vaUserActivity(user, activityId);
		if("success".equals(result.get("status"))){
			PageHelper.startPage(page.getPage(), page.getRows());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("activityId", activityId);
			if(StringUtils.isNotEmpty(orderby) && isAsc != null){
				if("discount".equals(orderby)){
					//按折扣排序
					map.put("orderBy", "p.price/p.market_price "+ (isAsc ? "asc" : "desc"));
				}else{
					map.put("orderBy", "p." + orderby +" "+ (isAsc ? "asc" : "desc"));
				}
			}else{
				map.put("orderBy", "p.price DESC");
			}
			if(StringUtils.isNotEmpty(productName)){
				map.put("productName", productName.trim());
			}
			if(mallCatalogsIds != null){
				map.put("mallCatalogsIds", mallCatalogsIds);
			}
			if(brands!=null){
				map.put("inBrandIds", brands);
			}
			if(suppliers!=null){
				map.put("inSupplierIds", suppliers);
			}
			if(priceRanges!=null&&priceRanges.length>0){
				PriceRangeModel priceRangeModel = PriceUtil.calRange(priceRanges);
				if(priceRangeModel.getBegin()>0){
					map.put("priceBegin", priceRangeModel.getBegin());
				}
				if(priceRangeModel.getEnd()>0){
					map.put("priceEnd", priceRangeModel.getEnd());
				}
			}
			map.put("isDelete", 0);
			if(keywords != null && !"".equals(keywords.trim())){
				map.put("keywords", keywords);
			}
			//根据机构判断商品展示
			if(user != null && user.getType() != null && user.getType() == 1){
				map.put("orgId", user.getDepId());
			}
			List<Products> list = activityService.selectActivityProducts(map);
			if(!Collections.isEmpty(list)){
				list.forEach(product -> {
					MallProducts mallProduct = new MallProducts();
					BeanUtils.copyProperties(product, mallProduct);
					product.setAttributeModel(mallProductService.getProductAttributeModel(mallProduct));
				});
			}
			result.put("data", new PageInfo<Products>(list));
		}
		return result;
	}
	
	@ApiOperation(value="活动详情页")
	@ApiImplicitParams({
		@ApiImplicitParam(name="activityId",value="活动id",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/activityView")
	public Map<String, Object> activityView(HttpServletRequest request, Long activityId){
		Users user = UserUtil.getUser(request);
		Map<String, Object> result = activityService.vaUserActivity(user, activityId);
		if("success".equals(result.get("status"))){
			Activity activity = activityService.selectByKey(activityId);
			result.put("data", activity);
		}
		return result;
	}
	
	@ApiOperation(value="活动详情页商品列表筛选参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name="activityId",value="活动id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="productName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mallCatalogsId",value="品目Id",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value = "/getActivityProductsParam")
	public Map<String, Object> getActivityProductsParam(HttpServletRequest request, Long activityId
			, String productName
			, @RequestParam(value="mallCatalogsIds[]",required=false) Long[] mallCatalogsIds
			, @RequestParam(value="brands[]",required=false) Long[] brands
    		, @RequestParam(value="suppliers[]",required=false)Long[] suppliers
    		, @RequestParam(value="priceRanges[]",required=false)String[] priceRanges){
		Users user = UserUtil.getUser(request);
		Map<String, Object> result = activityService.vaUserActivity(user, activityId);
		if("success".equals(result.get("status"))){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("activityId", activityId);
			if(StringUtils.isNotEmpty(productName)){
				map.put("productName", productName.trim());
			}
			if(mallCatalogsIds != null){
				map.put("mallCatalogsIds", mallCatalogsIds);
			}
			//根据机构判断商品展示
			if(user != null && user.getType() != null && user.getType() == 1){
				map.put("orgId", user.getDepId());
			}
			GoodsResponse goodsResponse = new GoodsResponse();
			//品牌
			goodsResponse.setBrands(activityService.getActivityProductsBrands(map));
			//供应商
			goodsResponse.setSuppliers(activityService.getActivityProductsSuppliers(map));
			//价格
			goodsResponse.setPriceRanges(activityService.getActivityProductsPriceRanges(map));
			//品目
			goodsResponse.setMallCatalogsList(mallCatalogsService.getMallCatalogsByActivity(map));
			result.put("data", goodsResponse);
		}
		return result;
	}
	
	
	@ApiOperation(value="活动积分到账提醒")
	@GetMapping(value = "/integralPrompt")
	public List<IntegralUser> integralPrompt(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			return integralUserService.findIntegralPrompt(user.getId());
		}
		return null;
	}
	
	@ApiOperation(value="修改活动积分到账提醒标识")
	@ApiImplicitParams({
		@ApiImplicitParam(name="activityId",value="活动id",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/updateIntegralPrompt")
	public Map<String, Object> updateIntegralPrompt(HttpServletRequest request, Long activityId){
		Users user = UserUtil.getUser(request);
		if(user != null && activityId != null){
			int updateIntegralPrompt = integralUserService.updateIntegralPrompt(user.getId(), activityId);
			if(updateIntegralPrompt > 0){
				return ResultUtil.success("修改成功");
			}
		}
		return ResultUtil.error("未查询到该活动");
	}
	

	@ApiOperation(value="获取爱心超市或者积分乐园活动信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="kind",value="类别id 1爱心超市 2积分乐园",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/getActivityByKind")
	public Activity getActivityByKind(Integer kind){
		if(kind != null){
			Example example = new Example(Activity.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("isDelete", 0);
			createCriteria.andEqualTo("kind", kind);
			example.setOrderByClause(" created_at DESC");
			List<Activity> list = activityService.selectByExample(example);
			if(CollectionUtils.isNotEmpty(list)){
				return list.get(0);
			}
		}
		return null;
	}
}
