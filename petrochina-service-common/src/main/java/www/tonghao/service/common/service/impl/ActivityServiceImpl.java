package www.tonghao.service.common.service.impl;

import io.jsonwebtoken.lang.Collections;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.constant.SmSConstant;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.HttpClient;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.PriceUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.ActivityOrg;
import www.tonghao.service.common.entity.ActivityProduct;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.ActivityMapper;
import www.tonghao.service.common.mapper.ActivityOrgMapper;
import www.tonghao.service.common.mapper.ActivityProductMapper;
import www.tonghao.service.common.mapper.IntegralUserMapper;
import www.tonghao.service.common.mapper.OrganizationMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ProductsMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.service.common.service.ActivityService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("activityService")
@Transactional
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService{

	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private ActivityOrgMapper activityOrgMapper;
	
	@Autowired
	private ActivityProductMapper activityProductMapper;
	
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	@Autowired
	private ProductsMapper productsMapper; 
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private OrganizationMapper organizationMapper;
	
	@Autowired
	private IntegralUserMapper integralUserMapper;
	
	/**
	 * @Description:添加活动
	 * @date 2019年4月25日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> addActivity(Activity activity) {
		Map<String, Object> checkInfo = checkInfo(activity);
		if(!ResultUtil.isSuccess(checkInfo)){
			return checkInfo;
		}
		
		activity.setStartAt(activity.getStartAt());
		activity.setEndAt(activity.getEndAt());
		activity.setOnlineAt(activity.getOnlineAt());
		activity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		activity.setIsDelete(0);
		activityMapper.insertSelective(activity);
		Long activityId = activity.getId();
		
		List<ActivityOrg> activityOrgList = activity.getActivityOrgList();
		if(activityOrgList.size() >0){
			for(ActivityOrg ao : activityOrgList){
				ao.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				ao.setActivityId(activityId);
				
				ActivityOrg activityOrg  = new ActivityOrg();
				activityOrg.setOrgId(ao.getOrgId());
				activityOrg.setActivityId(activityId);
				List<ActivityOrg> select = activityOrgMapper.select(ao);
				if(CollectionUtils.isEmpty(select)){
					activityOrgMapper.insert(ao);
				}
			}
		}
		return ResultUtil.success("");
	}
	
	/**
	 * @Description:更新活动
	 * @date 2019年4月25日
	 */
	@Override
	public Map<String, Object> updateActivity(Activity activity) {
		Map<String, Object> checkInfo = checkInfo(activity);
		if(!ResultUtil.isSuccess(checkInfo)){
			return checkInfo;
		}
		
		/**
		 * 更新活动
		 */
		activity.setStartAt(activity.getStartAt());
		activity.setEndAt(activity.getEndAt());
		activity.setOnlineAt(activity.getOnlineAt());
		activity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		activityMapper.updateByPrimaryKeySelective(activity);
		Long activityId = activity.getId();
		
		/**
		 * 更新活动关联的机构信息
		 */
		ActivityOrg activityOrg = new ActivityOrg();
		activityOrg.setActivityId(activityId);
		activityOrgMapper.delete(activityOrg);
		
		List<ActivityOrg> activityOrgList = activity.getActivityOrgList();
		if(activityOrgList.size() >0){
			for(ActivityOrg ao : activityOrgList){
				ao.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				ao.setActivityId(activityId);
				activityOrgMapper.insert(ao);
			}
		}
		return ResultUtil.success("");
	}
	
	/**
	 * @Description:校验信息
	 * @date 2019年4月25日
	 */
	public Map<String, Object> checkInfo(Activity activity){
		if(StringUtils.isNotBlank(activity.getName())){
			Example example = new Example(Activity.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("isDelete", 0);
			createCriteria.andEqualTo("name", activity.getName());
			createCriteria.andNotEqualTo("id", activity.getId());
			List<Activity> select = activityMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(select)){
				return ResultUtil.error("名称重复");
			}
		}else{
			return ResultUtil.error("请填写活动名称");
		}
		if(StringUtils.isBlank(activity.getStartAt())){
			return ResultUtil.error("请选择开始时间");
		}
		if(StringUtils.isBlank(activity.getEndAt())){
			return ResultUtil.error("请选择结束时间");
		}
		if(StringUtils.isBlank(activity.getOnlineAt())){
			return ResultUtil.error("请选择上线时间");
		}
		if(activity.getKind() == null){
			return ResultUtil.error("请选活动类型");
		}
		if(activity.getType() == null){
			return ResultUtil.error("请选择活动属性");
		}else{
			if(activity.getType() == 1){
				//积分活动
				if(CollectionUtils.isEmpty(activity.getActivityOrgList())){
					return ResultUtil.error("请选活动可见范围");
				}
			}else{
				//促销活动
				//校验是否存在爱心超市或者积分乐园
				List<Activity> selectByKind = activityMapper.selectByKind(2, activity.getKind(), activity.getId());
				if(CollectionUtils.isNotEmpty(selectByKind)){
					String activityKind = "";
					if(activity.getKind() == 1){
						activityKind = "爱心超市";
					}else if(activity.getKind() == 2){
						activityKind = "积分乐园";
					}
					return ResultUtil.error(activityKind + "已存在");
				}
			}
		}
		return ResultUtil.success("");
	}

	@Override
	public Activity selectById(Long id) {
		Activity activity = activityMapper.selectById(id);
		if(activity !=null){
			Map<String, Object> map = new HashMap<>();
			map.put("activityId", id);
			List<ActivityOrg> activityOrgList = activityOrgMapper.selectOrgByActivityId(map);
			if(CollectionUtils.isNotEmpty(activityOrgList)){
				StringBuilder sb = new StringBuilder();
				activityOrgList.forEach(ao ->{
					sb.append(ao.getOrgName() + "，");
				});
				activity.setOrgName(sb.substring(0, sb.length()-1));
			}
		}
		return activity;
	}

	/**
	 * @Description:活动列表
	 * @date 2019年4月25日
	 */
	@Override
	public PageInfo<Activity> list(PageBean page, Map<String, Object> map) {
		/*Example example = new Example(Activity.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		CriteriaLikeUtil.criteriaLike(createCriteria, map);
		List<Activity> activityList = activityMapper.selectByExample(example);*/
		
		Long orgId = (Long) map.get("orgId");
		if(orgId !=null){
			ActivityOrg activityOrg = new ActivityOrg();
			activityOrg.setOrgId(orgId);
			List<ActivityOrg> select = activityOrgMapper.select(activityOrg);
			List<Long> ids = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(select)){
				for(ActivityOrg ao : select){
					Long activityId = ao.getActivityId();
					ids.add(activityId);
				}
				map.put("ids", ids);
			}else{
				ids.add(0L);
				map.put("ids", ids);
			}
		}
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Activity> activityList = activityMapper.selectAvtivityList(map);
		for(Activity a : activityList){
			if(a.getOnlineAt() !=null && a.getIsOffline() == 0){
				int compareDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), a.getOnlineAt(), "yyyy-MM-dd HH:mm:ss");
				if(compareDate == 1){
					a.setOnlineState("已上线");
				}else{
					a.setOnlineState("未上线");
				}
			}else{
				
				a.setOnlineState("已下线");
			}
			
			if(a.getEndAt() !=null){
				int compareDate1 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), a.getStartAt(), "yyyy-MM-dd HH:mm:ss");
				int compareDate2 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), a.getEndAt(), "yyyy-MM-dd HH:mm:ss");
				
				if(compareDate1 == 1 && compareDate2 == -1){
					a.setActivityState("进行中");
				}else if(compareDate1 == -1){
					a.setActivityState("未开始");
				}else if(compareDate2 == 1){
					a.setActivityState("已结束");
				}
			}
			
			StringBuilder sb = new StringBuilder();
			if(a.getActivityOrgList().size() > 0){
				for(ActivityOrg activityOrg : a.getActivityOrgList()){
					sb.append(activityOrg.getOrgName() + ",");
				}
				String orgName = sb.substring(0, sb.length()-1);
				a.setOrgName(orgName);
			}
		}
		
		return new PageInfo<Activity>(activityList);
	}

	/**
	 * @Description:活动商品池
	 * @date 2019年4月29日
	 */
	@Override
	public PageInfo<ActivityProduct> selectActivityProduct(PageBean page, ActivityProduct activityProduct) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<ActivityProduct> selectActivityProductList = activityProductMapper.selectActivityProductList(activityProduct);
		return new PageInfo<ActivityProduct>(selectActivityProductList);
	}

	/**
	 * @Description:按商品添加
	 * @date 2019年4月29日
	 */
	@Override
	public String addProduct(ActivityProduct activityProduct) {
		if(activityProduct ==null || activityProduct.getActivityId() ==null){
			return ResultUtil.ERROR;
		}
		
		if(activityProduct.getProductIds()!=null && activityProduct.getProductIds().length >0){
			for(Long productId :  activityProduct.getProductIds()){
				ActivityProduct a = new ActivityProduct();
				a.setProductId(productId);
				a.setActivityId(activityProduct.getActivityId());
				a.setIsDelete(0);
				List<ActivityProduct> select = activityProductMapper.select(a);
				if(select.isEmpty()){
					ActivityProduct ap = new ActivityProduct();
					ap.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					ap.setIsDelete(0);
					ap.setProductId(productId);
					ap.setActivityId(activityProduct.getActivityId());
					activityProductMapper.insertSelective(ap);
				}
			}
			return ResultUtil.SUCCESS;
		}
		return ResultUtil.ERROR;
	}

	/**
	 * @Description:按品目添加
	 * @date 2019年4月29日
	 */
	@Override
	public String addCatalogs(ActivityProduct activityProduct) {
		if(activityProduct ==null || activityProduct.getActivityId() ==null){
			return ResultUtil.ERROR;
		}
		
		if(activityProduct.getCatalogsIds() !=null && activityProduct.getCatalogsIds().length > 0){
			for(Long catalogsId :  activityProduct.getCatalogsIds()){
				PlatformCatalogs platformCatalogs = platformCatalogsMapper.selectByPrimaryKey(catalogsId);
				if("false".equals(platformCatalogs.getIsParent())){
					insertProduct(activityProduct.getActivityId(), activityProduct.getSupplierId(), catalogsId);
				}
			}
			return ResultUtil.SUCCESS;
		}else if(activityProduct.getSupplierId() !=null){
			insertProduct(activityProduct.getActivityId(), activityProduct.getSupplierId(), null);
			return ResultUtil.SUCCESS;
		}
		return ResultUtil.ERROR;
	}
	
	/**
	 * @Description:插入商品
	 * @date 2019年5月5日
	 */
	private void insertProduct(Long activityId, Long supplierId, Long catalogsId){
		Map<String, Object> map = new HashMap<>();
		map.put("status", 3);
		map.put("supplierId", supplierId);
		map.put("catalogId", catalogsId);
		if(activityId != null){
			Activity activity = activityMapper.selectByPrimaryKey(activityId);
			if (activity != null 
					&& activity.getType() != null && activity.getType() == 2 
					&& activity.getKind() != null && activity.getKind() == 1) {
				map.put("labelType", 2);
			}
			map.put("activityId", activityId);
			map.put("activityType", activity.getType());
		}
		List<Products> productsList = productsMapper.selectByMap(map);
		if(productsList !=null && productsList.size() > 0){
			for(Products p :productsList){
				ActivityProduct activityProduct = new ActivityProduct();
				activityProduct.setProductId(p.getId());
				activityProduct.setIsDelete(0);
				activityProduct.setActivityId(activityId);
				List<ActivityProduct> select = activityProductMapper.select(activityProduct);
				if(select.isEmpty()){
					ActivityProduct ap = new ActivityProduct();
					ap.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					ap.setIsDelete(0);
					ap.setProductId(p.getId());
					ap.setActivityId(activityId);
					activityProductMapper.insertSelective(ap);
				}
			}
		}
	}

	/**
	 * @Description:活动添加商品列表  
	 * @date 2019年4月29日
	 */
	@Override
	public PageInfo<Products> getProductsList(ActivityProduct activityProduct) {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("catalogIds", activityProduct.getCatalogsIds());
		map.put("supplierName", activityProduct.getSupplierName());
		map.put("sku", activityProduct.getSku());
		map.put("name", activityProduct.getProductsName());
		map.put("startPrice", activityProduct.getStartPrice());
		map.put("endPrice", activityProduct.getEndPrice());
		map.put("activityId", activityProduct.getActivityId());
		if(activityProduct.getActivityId() != null){
			Activity activity = activityMapper.selectByPrimaryKey(activityProduct.getActivityId());
			if (activity != null 
					&& activity.getType() != null && activity.getType() == 2 
					&& activity.getKind() != null && activity.getKind() == 1) {
				map.put("labelType", 2);
			}
			map.put("activityType", activity.getType());
		}
		Integer page = activityProduct.getPage();
		Integer rows = activityProduct.getRows();
		PageHelper.startPage(page, rows);
		List<Products> activityProductsList = productsMapper.getActivityProductsList(map);
		return new PageInfo<Products>(activityProductsList);
	}
	
	/**
	 * @Description:活动中商品设置排序
	 * @date 2019年4月30日
	 */
	@Override
	public String setRank(Long id, Long rank, Long activityId) {
		ActivityProduct record = new ActivityProduct();
		record.setRank(rank);
		record.setIsDelete(0);
		record.setActivityId(activityId);
		List<ActivityProduct> select = activityProductMapper.select(record);
		
		if(select !=null && select.size() > 0 ){
			Long selectMaxRank = activityProductMapper.selectMaxRank();
			String maxRank = null;
			if(selectMaxRank !=null){
				maxRank  = selectMaxRank.toString();
			}
			return "排序已存在，最大排序到：" + maxRank;
		}
		record.setId(id);
		int result = activityProductMapper.updateByPrimaryKeySelective(record);
		if(result > 0){
			return "success";
		}else{
			return "error";
		}
	}

	/**
	 * @Description:删除活动商品
	 * @date 2019年4月30日
	 */
	@Override
	public Map<String, Object> deleteActivityProduct(Long id) {
		ActivityProduct record = new ActivityProduct();
		record.setId(id);
		record.setIsDelete(1);
		record.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = activityProductMapper.updateByPrimaryKeySelective(record);
		return ResultUtil.resultMessage(result, "");
	}

	/**
	 * @Description:查询机构下的活动
	 * @date 2019年4月30日
	 */
	@Override
	public List<Activity> selectActivityByOrg(Long[] orgIds) {
		return activityMapper.selectActivityByOrg(orgIds);
	}

	@Override
	public List<Activity> getActivityFloor(Map<String, Object> map) {
		List<Activity> list = activityMapper.selectByUserId(map);
		if(!Collections.isEmpty(list)){
			Map<String,Object> productMap = new HashMap<String, Object>();
			list.forEach(activity -> {
				productMap.clear();
				productMap.put("activityId", activity.getId());
				//-ap.rank DESC
				productMap.put("floorOrderBy", "p.price DESC");
				productMap.put("count", map.get("count"));
				productMap.put("isDelete", 0);
				productMap.put("orgId", map.get("orgId"));
				activity.setProductsList(productsMapper.selectActivityProducts(productMap));
			});
		}
		return list;
	}

	@Override
	public List<Products> selectActivityProducts(Map<String, Object> map){
		return productsMapper.selectActivityProducts(map);
	}

	@Override
	public Map<String, Object> vaUserActivity(Users user, Long activityId) {
		Activity activity = activityMapper.selectByPrimaryKey(activityId);
		if(activity != null && activity.getType() != null && activity.getIsDelete() == 0){
			if(activity.getIsOffline() != 0){
				return ResultUtil.resultMessage(HttpResponseCode.FORBIDDEN, "抱歉，该活动已下线", null);
			}
			if(activity.getType() == 1){
				//积分活动
				if(user == null){
					return ResultUtil.resultMessage(HttpResponseCode.UNAUTHORIZED, "没有登录", null);
				}
				user = usersMapper.selectByPrimaryKey(user.getId());
				if(user.getDepId() != null){
					Example example=new Example(ActivityOrg.class);
					Criteria createCriteria = example.createCriteria();
					createCriteria.andEqualTo("orgId", user.getDepId());
					createCriteria.andEqualTo("activityId", activityId);
					List<ActivityOrg> selectByExample = activityOrgMapper.selectByExample(example);
					if(CollectionUtils.isNotEmpty(selectByExample)){
						return ResultUtil.resultMessage(HttpResponseCode.OK, "", null);
					}
				}
			}else if(activity.getType() == 2){
				//促销活动
				return ResultUtil.resultMessage(HttpResponseCode.OK, "", null);
			}
		}else{
			return ResultUtil.resultMessage(HttpResponseCode.FORBIDDEN, "抱歉，未查询到该活动", null);
		}
		return ResultUtil.resultMessage(HttpResponseCode.FORBIDDEN, "抱歉，您无法参与该活动", null);
	}

	/**
	 * @Description:获取机构树
	 * @date 2019年4月28日
	 */
	@Override
	public List<Organization> getOrgTree(Long activityId) {
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		List<Organization> orgList = organizationMapper.selectByExample(example);
		
		if(activityId !=null){
			for(Organization org : orgList){
				//if("false".equals(org.getIsParent())){
					Long orgId = org.getId();
					ActivityOrg activityOrg = new ActivityOrg();
					activityOrg.setActivityId(activityId);
					activityOrg.setOrgId(orgId);
					List<ActivityOrg> activityOrgList = activityOrgMapper.select(activityOrg);
					if(activityOrgList !=null && activityOrgList.size() > 0){
						org.setChecked(true);
					}
				//}
			}
		}
		return orgList;
	}

	@Override
	public Map<String, Object> addAllProduct(ActivityProduct activityProduct) {
		if(activityProduct ==null || activityProduct.getActivityId() ==null){
			return ResultUtil.error("");
		}
		if(activityProduct.getCatalogsIds().length == 0 && StringUtils.isBlank(activityProduct.getSupplierName())
				&& StringUtils.isBlank(activityProduct.getSku()) && StringUtils.isBlank(activityProduct.getProductsName()) 
				&& activityProduct.getStartPrice() ==null && activityProduct.getEndPrice() ==null){
			return ResultUtil.error("请选择条件");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("catalogIds", activityProduct.getCatalogsIds());
		map.put("supplierName", activityProduct.getSupplierName());
		map.put("sku", activityProduct.getSku());
		map.put("name", activityProduct.getProductsName());
		map.put("startPrice", activityProduct.getStartPrice());
		map.put("endPrice", activityProduct.getEndPrice());
		
		if(activityProduct.getActivityId() != null){
			Activity activity = activityMapper.selectByPrimaryKey(activityProduct.getActivityId());
			if (activity != null 
					&& activity.getType() != null && activity.getType() == 2 
					&& activity.getKind() != null && activity.getKind() == 1) {
				map.put("labelType", 2);
			}
			map.put("activityType", activity.getType());
		}
		List<Products> activityProductsList = productsMapper.getActivityProductsList(map);
		
		int result = 0;
		if(activityProductsList !=null && activityProductsList.size() >0){
			for(Products products : activityProductsList){
				ActivityProduct a = new ActivityProduct();
				a.setProductId(products.getId());
				a.setActivityId(activityProduct.getActivityId());
				a.setIsDelete(0);
				List<ActivityProduct> select = activityProductMapper.select(a);
				if(select.isEmpty()){
					ActivityProduct ap = new ActivityProduct();
					ap.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					ap.setIsDelete(0);
					ap.setProductId(products.getId());
					ap.setActivityId(activityProduct.getActivityId());
					int insertSelective = activityProductMapper.insertSelective(ap);
					if(insertSelective >0){
						result ++;
					}
				}
			}
			return ResultUtil.success("成功添加" + result + "个");
		}
		return ResultUtil.error("");
	}

	@Override
	public List<PriceRangeModel> getActivityProductsPriceRanges(Map<String, Object> map) {
		BigDecimal maxPrice = activityProductMapper.getMaxActivityProductsPrice(map);
		return PriceUtil.getRanges(maxPrice);
	}

	@Override
	public List<Suppliers> getActivityProductsSuppliers(Map<String, Object> map) {
		return activityProductMapper.getActivityProductsSuppliers(map);
	}

	@Override
	public List<Brand> getActivityProductsBrands(Map<String, Object> map) {
		return activityProductMapper.getActivityProductsBrands(map);
	}

	@Override
	public List<ActivityProduct> getActivityProductByUser(
			Map<String, Object> map) {
		return activityProductMapper.getActivityProductByUser(map);
	}
	
	public Map<String, Object> sendActivityMessage(Long activityId,boolean isEnd) {
		Activity activity = activityMapper.selectByPrimaryKey(activityId);
		if(activity ==null){
			return ResultUtil.error("未找到活动信息");
		}
		int endDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), activity.getEndAt(), "yyyy-MM-dd HH:mm:ss");
		int onlineDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), activity.getOnlineAt(), "yyyy-MM-dd HH:mm:ss");
		int startDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), activity.getStartAt(), "yyyy-MM-dd HH:mm:ss");
		if(activity.getIsOffline() == 0 && endDate == -1 && onlineDate == 1 && startDate == 1){
			IntegralUser integralUser = new IntegralUser();
			integralUser.setActivityId(activityId);
			List<IntegralUser> integralUserList = integralUserMapper.select(integralUser);
			if(CollectionUtils.isNotEmpty(integralUserList)){
				ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
				newCachedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
							Integer year = DateUtilEx.getIndexYear(new Date());
							for(IntegralUser iu : integralUserList){
								Users user = usersMapper.selectByPrimaryKey(iu.getUserId());
								if(isEnd){//结束
									if(iu.getBalance().compareTo(new BigDecimal(99)) > -1){
										String params = year + "," + activity.getName() + "," + DateUtilEx.dateFormat(DateUtilEx.timeToDate(activity.getEndAt()));
										sendSms(user.getMobile(),params,SmSConstant.END_ACTIVITY_TEMPLATE_ID);
									}
								}else{
									String params=year + "," + activity.getName() + ","+DateUtilEx.dateFormat(DateUtilEx.timeToDate(activity.getStartAt()))+","+DateUtilEx.dateFormat(DateUtilEx.timeToDate(activity.getEndAt()));
									sendSms(user.getMobile(),params,SmSConstant.BEGIN_ACTIVITY_TEMPLATE_ID);
								}
							}
						
					}
				 });
				return ResultUtil.success("短信发送成功");
			}else{
				return ResultUtil.success("短信发送失败,该活动下暂无分配用户");
			}
		}
		return ResultUtil.error("该活动还不能使用");
	}
	
	private void sendSms(String mobile, String params,String templateId){
		Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", mobile);
		map.put("params", params);
		map.put("template", templateId);
		System.out.println(map);
		String doGet = HttpClient.doGet(SmSConstant.SMSURL, map);
		System.out.println(doGet);
	}
}
