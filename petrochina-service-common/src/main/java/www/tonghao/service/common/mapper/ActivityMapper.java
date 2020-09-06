package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Activity;

/**
 * @Description:活动
 * @date 2019年4月25日
 */
public interface ActivityMapper extends BaseMapper<Activity> {
	
	/**
	 * @Description:id查询
	 * @date 2019年4月25日
	 */
	public Activity selectById(Long id);
	
	/**
	 * @Description:查询机构下的活动
	 * @date 2019年4月30日
	 */
	List<Activity> selectActivityByOrg(@Param("orgIds") Long[] orgIds);
	
  	/**
	 * 
	 * Description: 根据用户查询正在进行的活动
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	List<Activity> selectByUserId(Map<String, Object> map);
	
	/**
	 * @Description:积分id获取活动
	 * @date 2019年5月1日
	 */
	Activity selectActivityByIntegralId(@Param("integralId") Long integralId);
	
	/**
	 * 
	 * Description: 判断当前活动是否正在进行中
	 * 
	 * @data 2019年5月3日
	 * @param 
	 * @return
	 */
	Integer vaIsStart(@Param("id")Long id, @Param("userId")Long userId);

	/**
	 * @Description:活动列表
	 * @date 2019年5月7日
	 */
	List<Activity> selectAvtivityList(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 根据用户查询活动信息
	 * 
	 * @data 2019年8月22日
	 * @param 
	 * @return
	 */
	List<Activity> selectActivityByUser(@Param("userId")Long userId);
	
	/**
	 * 
	 * Description: 爱心超市或积分乐园重复查询
	 * 
	 * @data 2019年8月24日
	 * @param 
	 * @return
	 */
	List<Activity> selectByKind(@Param("type")Integer type, @Param("kind")Integer kind, @Param("id")Long id);
	
	/**
	 * 
	 * Description: 查询订单关联的活动信息
	 * 
	 * @data 2019年8月29日
	 * @param 
	 * @return
	 */
	List<Activity> selectByOrder(@Param("orderId") Long orderId);
}