package www.tonghao.mall.web.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.SysLog;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.ActivityProduct;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.service.ActivityService;
import www.tonghao.service.common.service.OrgSupplierService;
import www.tonghao.service.common.service.OrganizationService;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.SuppliersService;

import com.github.pagehelper.PageInfo;

/**
 * @Description:活动
 * @date 2019年4月25日
 */
@Api(value="activityController", description="活动")
@RestController
@RequestMapping("/activity")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@Autowired
	private MallCatalogsService MallCatalogsService;
	
	@Autowired
	private OrgSupplierService orgSupplierService;
	
	/**
	 * @Description:活动列表
	 * @date 2019年4月25日
	 */
	@SysLog(opt = "查看活动列表")
	@ApiOperation(value="活动列表",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="type",value="活动类型：1积分活动，2促销活动",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="orgId",value="活动可见范围",required=false,dataType="long",paramType="query"),
		@ApiImplicitParam(name="activityState",value="活动进行状态:1 未开始 2 进行中 3 已结束",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="onlineState",value="上线状态:1 未上线 2 已上线 3已下线",required=false,dataType="int",paramType="query")
	})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public PageInfo<Activity> list(@ModelAttribute PageBean page, String name, Integer type, Long orgId, Integer activityState, Integer onlineState){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNoneBlank(name)){
			map.put("name", name);
		}
		if(type !=null){
			map.put("type", type);
		}
		if(orgId !=null){
			map.put("orgId", orgId);
		}
		if(activityState !=null){
			map.put("activityState", activityState);
		}
		if(onlineState !=null){
			map.put("onlineState", onlineState);
		}
		return activityService.list(page, map);
	}
	
	/**
	 * @Description:添加活动
	 * @date 2019年4月25日
	 */
	@SysLog(opt = "添加活动")
	@ApiOperation(value="添加活动")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@RequestBody Activity activity){
		Map<String, Object> result = activityService.addActivity(activity);
		return result;
	}
	
	/**
	 * @Description:更新活动
	 * @date 2019年4月25日
	 */
	@ApiOperation(value="更新活动")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> updateActivity(@RequestBody Activity activity){
		Map<String, Object> result = activityService.updateActivity(activity);
		return result;
	}
	
	/**
	 * @Description:删除活动
	 * @date 2019年4月25日
	 */
	@ApiOperation(value="删除活动")
	@RequestMapping(value="/delActivity", method=RequestMethod.DELETE)
	public Map<String, Object> delActivity(Long id){
		Activity activity = new Activity();
		activity.setId(id);
		activity.setIsDelete(1);
		int result = activityService.updateNotNull(activity);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:id查询
	 * @date 2019年4月25日
	 */
	@ApiOperation(value="活动id查询")
	@RequestMapping(value="/getActivity", method=RequestMethod.GET)
	public Activity getActivity(Long id){
		Activity selectById = activityService.selectById(id);
		return selectById;
	}
	
	/**
	 * @Description:获取所有活动
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取所有活动")
	@RequestMapping(value="/getActivityList", method=RequestMethod.GET)
	public List<Activity> getActivityList(){
		Example example=new Example(Activity.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("isOffline", 0);
		createCriteria.andGreaterThan("endAt", DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		List<Activity> activityList = activityService.selectByExample(example);
		return activityList;
	}
	
	/**
	 * @Description:活动商品池
	 * @date 2019年4月29日
	 */
	@SysLog(opt = "分页查询活动商品池列表")
	@ApiOperation(value="分页查询活动商品池列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sku",value="sku",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="productsName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityId",value="活动id",required=true,dataType="string",paramType="query")
	})
	@RequestMapping(value="/getActivityProductList", method=RequestMethod.GET)
	public PageInfo<ActivityProduct> getActivityProductList(@ModelAttribute PageBean page, String supplierName, String sku, String productsName, Long activityId){
		PageInfo<ActivityProduct> selectActivityProduct = null;
		if(activityId !=null){
			ActivityProduct activityProduct = new ActivityProduct();
			activityProduct.setSupplierName(supplierName);
			activityProduct.setSku(sku);
			activityProduct.setProductsName(productsName);
			activityProduct.setActivityId(activityId);
			selectActivityProduct = activityService.selectActivityProduct(page, activityProduct);
		}
		return selectActivityProduct;
	}
	
	/**
	 * @Description:按商品添加
	 * @date 2019年4月29日
	 */
	@ApiOperation(value="按商品添加")
	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
	public String addProduct(@RequestBody ActivityProduct activityProduct){
		return activityService.addProduct(activityProduct);
	}
	
	/**
	 * @Description:活动添加商品列表  
	 * @date 2019年4月30日
	 */
	@ApiOperation(value="活动添加商品列表 ",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="catalogIds",value="商品品目",required=false,allowMultiple=true, dataType="Long",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sku",value="sku",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="name",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="startPrice",value="开始价格",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endPrice",value="结束价格",required=false,dataType="BigDecimal",paramType="query")
	})
	@RequestMapping(value="/getProductsList", method=RequestMethod.POST)
	public PageInfo<Products> getProductsList(@RequestBody ActivityProduct activityProduct, HttpServletRequest request){
		if(activityProduct == null || activityProduct.getActivityId() == null){
			return null;
		}
		return activityService.getProductsList(activityProduct);
	}
	
	/**
	 * @Description:按商品添加
	 * @date 2019年4月29日
	 */
	@ApiOperation(value="按品目添加")
	@RequestMapping(value="/addCatalogs", method=RequestMethod.POST)
	public String addCatalogs(@RequestBody ActivityProduct activityProduct){
		return activityService.addCatalogs(activityProduct);
	}
	
	/**
	 * @Description:设置排序
	 * @date 2019年4月30日
	 */
	@ApiOperation(value="设置排序")
	@RequestMapping(value="/setRank", method=RequestMethod.GET)
	public String setRank(Long id, Long rank, Long activityId){
		return activityService.setRank(id, rank, activityId);
	}
	
	/**
	 * @Description:删除活动商品
	 * @date 2019年4月30日
	 */
	@ApiOperation(value="删除活动商品")
	@RequestMapping(value="/deleteActivityProduct", method=RequestMethod.DELETE)
	public Map<String, Object> deleteActivityProduct(Long id){
		return activityService.deleteActivityProduct(id);
	}
	
	/**
	 * @Description:获取机构树
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取机构树")
	@RequestMapping(value="/getOrgTree", method=RequestMethod.GET)
	public List<Organization> getOrgTree(Long activityId){
		List<Organization> organizationList = activityService.getOrgTree(activityId);
		return organizationList;
	}
	
	/**
	 * @Description:获取所有供应商
	 * @date 2019年5月5日
	 */
	@ApiOperation(value="获取所有供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name="activityId",value="活动id",required=false,dataType="Long",paramType="query"),
	})
	@RequestMapping(value="/getAllSupplier", method=RequestMethod.GET)
	public List<Suppliers> getAllSupplier(Long activityId){
		
		Map<String, Object> map = new HashMap<>();
		if (activityId != null) {
			Activity activity = activityService.selectByKey(activityId);
			if (activity != null && activity.getType() != null
					&& activity.getType() == 2 && activity.getKind() != null
					&& activity.getKind() == 1) {
				map.put("labelType", 2);
			}
			if(activity != null && activity.getType() != null && activity.getType() == 1){
				map.put("activityId", activityId);
				map.put("activityType", activity.getType());
			}
		}
		List<Suppliers> supplierList = suppliersService.selectByActivity(map);
		return supplierList;
	}
	
	/**
	 * @Description:按商品条件全部添加
	 * @date 2019年5月6日
	 */
	@ApiOperation(value="按商品条件全部添加",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="activityId",value="活动id",required=false,dataType="Long",paramType="query"),
		@ApiImplicitParam(name="catalogIds",value="商品品目",required=false,allowMultiple=true,dataType="Long",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sku",value="sku",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="name",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="startPrice",value="开始价格",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endPrice",value="结束价格",required=false,dataType="BigDecimal",paramType="query")
	})
	@RequestMapping(value="/addAllProduct", method=RequestMethod.POST)
	public Map<String, Object> addAllProduct(@RequestBody ActivityProduct activityProduct){
		return activityService.addAllProduct(activityProduct);
	}
	
	/**
	 * @Description:获取货物所有品目
	 * @date 2019年4月29日
	 */
	@ApiOperation(value="获取货物所有品目")
	@RequestMapping(value="/getGoodsPlatformCatalogsAll",method={RequestMethod.GET})
	public List<PlatformCatalogs> getGoodsPlatformCatalogsAll(){
		List<PlatformCatalogs> selectByTreeNames = platformCatalogsService.selectByTreeNamesOrCode(null,null);
		return selectByTreeNames;
	}
	
	/**
	 * @Description:修改活动结束时间
	 * @date 2019年5月13日
	 */
	@ApiOperation(value="修改活动结束时间")
	@ApiImplicitParams({
		@ApiImplicitParam(name="activityId",value="活动id",required=false,dataType="Long",paramType="query"),
		@ApiImplicitParam(name="endAt",value="结束时间",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value="/updateEndAt", method=RequestMethod.GET)
	public Map<String, Object> updateActivity(Long activityId, String endAt){
		Activity activity = new Activity();
		activity.setId(activityId);
		activity.setEndAt(endAt + " 23:59:59");
		activity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = activityService.updateNotNull(activity);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:立即下线活动
	 * @date 2019年5月31日
	 */
	@ApiOperation(value="立即下线活动")
	@GetMapping("/offlineImmediately")
	public Map<String, Object> offlineImmediately(Long id){
		Activity selectByKey = activityService.selectByKey(id);
		if(selectByKey !=null){
			int compareDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), selectByKey.getOnlineAt(), "yyyy-MM-dd HH:mm:ss");
			if(compareDate == 1){
				Activity activity = new Activity();
				activity.setIsOffline(1);
				activity.setId(id);
				activity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				int result = activityService.updateNotNull(activity);
				return ResultUtil.resultMessage(result, "");
			}else{
				return ResultUtil.error("请选择已上线的活动");
			}
		}
		return ResultUtil.error("");
	}
	
	/**
	 * @Description:发送活动短信
	 * @date 2019年8月1日
	 */
	@ApiOperation(value="结束发送活动短信")
	@GetMapping("/endSendActivityMessage")
	public Map<String, Object> sendActivityMessage(Long activityId){
		Map<String, Object> result = activityService.sendActivityMessage(activityId,true);
		return result;
	}
	

	/**
	 * @Description:发送活动短信
	 * @date 2019年8月1日
	 */
	@ApiOperation(value="开始发送活动短信")
	@GetMapping("/beginSendActivityMessage")
	public Map<String, Object> beginSendActivityMessage(Long activityId){
		Map<String, Object> result = activityService.sendActivityMessage(activityId,false);
		return result;
	}
	
	/**
	 * @Description:手动上线
	 * @date 2019年8月14日
	 */
	@ApiOperation(value="手动上线")
	@GetMapping("/manualOnline")
	public Map<String, Object> manualOnline(Long id){
		if(id ==null){
			return ResultUtil.error("操作失败");
		}
		Activity selectByKey = activityService.selectByKey(id);
		if(selectByKey !=null){
			if(selectByKey.getIsOffline() == 1){
				Activity activity = new Activity();
				activity.setIsOffline(0);
				activity.setId(id);
				activity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				int result = activityService.updateNotNull(activity);
				return ResultUtil.resultMessage(result, "操作成功");
			}else{
				return ResultUtil.error("请选择手动下线的活动");
			}
		}
		return ResultUtil.error("操作失败");
	}
}
