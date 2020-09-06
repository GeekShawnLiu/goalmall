package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Integral;
import www.tonghao.service.common.entity.Organization;

/**
 * @Description:积分
 * @date 2019年4月26日
 */
public interface IntegralService extends BaseService<Integral>{
	
	/**
	 * @Description:登记积分
	 * @date 2019年4月26日
	 */
	public Map<String, Object> registrationIntegral(Integral integral);
	
	/**
	 * @Description:积分修改
	 * @date 2019年4月27日
	 */
	public Map<String, Object> updateIntegral(Integral integral);
	
	/**
	 * @Description:积分登记列表
	 * @date 2019年4月30日
	 */
	PageInfo<Integral> selectIntegralList(PageBean page, Integral integral);
	
	/**
	 * @Description:获取机构树
	 * @date 2019年4月28日
	 */
	List<Organization> getOrgTree(Long integralId);
	
	/**
	 * @Description:获取积分关联可用的活动
	 * @date 2019年8月7日
	 */
	List<Integral> selectByUsableActivity();

}
