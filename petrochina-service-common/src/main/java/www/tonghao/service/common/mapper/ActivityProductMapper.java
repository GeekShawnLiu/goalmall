package www.tonghao.service.common.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.ActivityProduct;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Suppliers;

public interface ActivityProductMapper extends BaseMapper<ActivityProduct> {
	
	/**
	 * @Description:查询活动商品池列表
	 * @date 2019年4月30日
	 */
	public List<ActivityProduct> selectActivityProductList(ActivityProduct activityProduct);
	
	/**
	 * @Description:查询最大的排序
	 * @date 2019年4月30日
	 */
	public Long selectMaxRank();
	
	public List<Activity> selActivityIdByProduct(@Param("productId")Long productId);
	
	/**
	 * 
	 * Description: 获取活动列表页查询最大价格
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	public BigDecimal getMaxActivityProductsPrice(Map<String, Object> map);

	/**
	 * 
	 * Description: 获取活动列表页查询供应商列表
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	public List<Suppliers> getActivityProductsSuppliers(Map<String, Object> map);

	/**
	 * 
	 * Description: 获取活动列表页查询品牌列表
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	public List<Brand> getActivityProductsBrands(Map<String, Object> map);
	
	/**
	 * 获取当前用户所选择的商品所在的活动
	 * @param map
	 * @return
	 */
	public List<ActivityProduct> getActivityProductByUser(Map<String, Object> map);
}