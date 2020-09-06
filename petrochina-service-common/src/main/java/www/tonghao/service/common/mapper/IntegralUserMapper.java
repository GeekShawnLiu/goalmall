package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.IntegralUser;

/**
 * @Description:积分用户表
 * @date 2019年5月2日
 */
public interface IntegralUserMapper extends BaseMapper<IntegralUser> {
	
	/**
	 * @Description:我的积分
	 * @date 2019年5月5日
	 */
	public List<IntegralUser> selectIntegralUser(@Param("userId") Long userId);
	
	/**
	 * @Description:查询全部用户积分
	 * @date 2019年5月5日
	 */
	public List<IntegralUser> selectAllIntegralUser(IntegralUser IntegralUser);
	
	/**
	 * @Description:查询个人积分信息
	 * @date 2019年5月15日
	 */
	public IntegralUser selectAllIntegralUser(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 根据活动id和用户id查询积分余额 
	 * 
	 * @data 2019年7月10日
	 * @param 
	 * @return
	 */
	IntegralUser findbalanceByUIdAndAId(@Param("userId") Long userId, @Param("activityId") Long activityId);
	
	/**
	 * 
	 * Description: 查询用户积分到账提醒
	 * 
	 * @data 2019年8月8日
	 * @param 
	 * @return
	 */
	List<IntegralUser> findIntegralPrompt(@Param("userId") Long userId);
	
	/**
	 * 
	 * Description: 修改活动积分到账提醒标识
	 * 
	 * @data 2019年8月8日
	 * @param 
	 * @return
	 */
	int updateIntegralPrompt(@Param("userId") Long userId, @Param("activityId") Long activityId);
}