package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;

import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.IntegralGrant;
import www.tonghao.service.common.entity.IntegralOrg;
import www.tonghao.service.common.entity.Users;

/**
 * @Description:积分发放
 * @date 2019年4月28日
 */
public interface IntegralGrantService extends BaseService<IntegralGrant>{
	
	/**
	 * @Description:积分发放
	 * @date 2019年4月28日
	 */
	public Map<String, Object> saveInfo(IntegralGrant integralGrant);
	
	/**
	 * @Description:修改积分发放
	 * @date 2019年4月28日
	 */
	public Map<String, Object> updateInfo(IntegralGrant integralGrant);
	
	/**
	 * @Description:获取机构下的用户
	 * @date 2019年4月30日
	 */
	public List<Users> getUserList(Long orgId, String realName);
	
	/**
	 * @Description:积分id获取机构
	 * @date 2019年5月1日
	 */
	List<IntegralOrg> selectIntegralOrg(Long integralId);
	
	/**
	 * @Description:积分id获取活动
	 * @date 2019年5月1日
	 */
	Activity selectActivityByIntegralId(Long integralId);
	
	/**
	 * @Description:积分发放审核通过
	 * @date 2019年5月2日
	 */
	Map<String, Object> audit(Long[] integralGrantIds, Users user);
	
	/**
	 * @Description:积分发放审核不通过
	 * @date 2019年5月2日
	 */
	Map<String, Object> auditNoPass(Long[] integralGrantIds, Users user);
	
	/**
	 * @Description:删除积分发放
	 * @date 2019年5月3日
	 */
	Map<String, Object> deleteIntegralGrant(Long integralGrantId);
	
	/**
	 * @Description:Excel批量导入积分发放
	 * @date 2019年5月8日
	 */
	Map<String, Object> importIntegralGrantExcle(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * @Description:获取列表（积分发放和审核）
	 * @date 2019年8月8日
	 */
	PageInfo<IntegralGrant> selectList(PageBean page, Map<String, Object> map);
	
	/**
	 * @Description:搜索条件提交审核
	 * @date 2019年8月14日
	 */
	Map<String, Object> conditionSubmitAudit(Map<String, Object> map);
}
