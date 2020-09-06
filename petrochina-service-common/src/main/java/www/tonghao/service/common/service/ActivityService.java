package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.ActivityProduct;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;

import com.github.pagehelper.PageInfo;

/**
 * @Description:活动
 * @date 2019年4月25日
 */
public interface ActivityService extends BaseService<Activity>{
	
	/**
	 * @Description:添加活动
	 * @date 2019年4月25日
	 */
	public Map<String, Object> addActivity(Activity activity);
	
	/**
	 * @Description:更新活动
	 * @date 2019年4月25日
	 */
	public Map<String, Object> updateActivity(Activity activity);

	
	/**
	 * @Description:id查询
	 * @date 2019年4月25日
	 */
	public Activity selectById(Long id);
	
	/**
	 * @Description:活动列表
	 * @date 2019年4月25日
	 */
	public PageInfo<Activity> list(PageBean page, Map<String, Object> map);
	
	/**
	 * @Description:活动商品池
	 * @date 2019年4月29日
	 */
	public PageInfo<ActivityProduct> selectActivityProduct(PageBean page, ActivityProduct activityProduct);
	
	/**
	 * @Description:按商品添加
	 * @date 2019年4月29日
	 */
	public String addProduct(ActivityProduct activityProduct);
	
	/**
	 * @Description:按品目添加
	 * @date 2019年4月29日
	 */
	public String addCatalogs(ActivityProduct activityProduct);
	
	/**
	 * @Description:活动添加商品列表  
	 * @date 2019年4月29日
	 */
	public PageInfo<Products> getProductsList(ActivityProduct activityProduct);
	
	/**
	 * @Description:活动中商品设置排序
	 * @date 2019年4月30日
	 */
	public String setRank(Long id, Long rank, Long activityId);
	
	/**
	 * @Description:删除活动商品
	 * @date 2019年4月30日
	 */
	public Map<String, Object> deleteActivityProduct(Long id);
	
	/**
	 * @Description:查询机构下的活动
	 * @date 2019年4月30日
	 */
	List<Activity> selectActivityByOrg(Long[] orgIds);
	
	/**
	 * 
	 * Description: 查询已上线活动列表 
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	List<Activity> getActivityFloor(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 根据活动查询所有符合条件的商品
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	List<Products> selectActivityProducts(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 判断用户是否有当前活动
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	Map<String, Object> vaUserActivity(Users user, Long activityId);
	
	/**
	 * @Description:获取机构树
	 * @date 2019年4月28日
	 */
	List<Organization> getOrgTree(Long activityId);
	
	/**
	 * @Description:按商品条件全部添加
	 * @date 2019年4月29日
	 */
	public Map<String, Object> addAllProduct(ActivityProduct activityProduct);
	
	/**
	 * 
	 * Description: 获取活动列表页查询价格区间
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	List<PriceRangeModel> getActivityProductsPriceRanges(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 获取活动列表页查询供应商列表
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	List<Suppliers> getActivityProductsSuppliers(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 获取活动列表页查询品牌列表
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	List<Brand> getActivityProductsBrands(Map<String, Object> map);
	
	List<ActivityProduct> getActivityProductByUser(Map<String, Object> map);

	/**
	 * @Description:发送活动短信
	 * @date 2019年8月1日
	 */
	Map<String, Object> sendActivityMessage(Long activityId,boolean isEnd);
}
