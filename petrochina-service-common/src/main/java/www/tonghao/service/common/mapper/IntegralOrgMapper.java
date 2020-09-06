package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.IntegralOrg;

/**
 * @Description:积分关联的机构
 * @date 2019年5月1日
 */
public interface IntegralOrgMapper extends BaseMapper<IntegralOrg> {
	
	/**
	 * @Description:积分id获取机构
	 * @date 2019年5月1日
	 */
	List<IntegralOrg> selectIntegralOrg(Long integralId);
}