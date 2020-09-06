package www.tonghao.service.common.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.IntegralConsume;
import www.tonghao.service.common.entity.IntegralUser;

import com.github.pagehelper.PageInfo;

/**
 * @Description:用户积分
 * @date 2019年5月2日
 */
public interface IntegralUserService extends BaseService<IntegralUser>{
	
	/**
	 * @Description:积分支付，减余额
	 * @date 2019年5月2日
	 */
	public String subtractBalance(Long userId, Long activityId, Long orderId, BigDecimal number);
	
	/**
	 * @Description:我的积分
	 * @date 2019年5月5日
	 */
	public PageInfo<IntegralUser> selectIntegralUser(PageBean page, Long userId);
	
	/**
	 * @Description:查询全部用户积分
	 * @date 2019年5月5日
	 */
	public PageInfo<IntegralUser> selectAllIntegralUser(PageBean page, IntegralUser IntegralUser);
	
	/**
	 * @Description:导出excel
	 * @date 2019年5月7日
	 */
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, IntegralUser integralUser);
	
	/**
	 * @Description:我的积分
	 * @date 2019年5月5日
	 */
	public List<IntegralUser> selectIntegralUser(Long userId);
	
	/**
	 * @Description:查询个人积分信息
	 * @date 2019年5月15日
	 */
	public IntegralUser getUserIntegralInfo(Long id);
	
	/**
	 * @Description:查询积分消费
	 * @date 2019年5月29日
	 */
	public PageInfo<IntegralConsume> selectIntegralConsume(PageBean page, Map<String, Object> map);
	
	/**
	 * @Description:积分退款，加余额
	 * @date 2019年5月29日
	 */
	public String addBalance(Long userId, Long activityId, Long orderId, BigDecimal number, String reason);
	
	/**
	 * @Description:导出明细excel
	 * @date 2019年6月3日
	 */
	public void exportConsumeInfoExcel(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map);
	
	/**
	 * 
	 * Description: 积分到账提醒查询
	 * 
	 * @data 2019年8月8日
	 * @param 
	 * @return
	 */
	List<IntegralUser> findIntegralPrompt(Long userId);
	
	/**
	 * 
	 * Description: 修改活动积分到账提醒标识
	 * 
	 * @data 2019年8月8日
	 * @param 
	 * @return
	 */
	int updateIntegralPrompt(Long userId, Long activityId);
}
