package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.IntegralGrant;

public interface IntegralGrantMapper extends BaseMapper<IntegralGrant> {
	
	/**
	 * @Description:查询今天的编号
	 * @date 2019年5月13日
	 */
	IntegralGrant selectBatchByToday();
	
	/**
	 * @Description:获取列表（积分发放和审核）
	 * @date 2019年8月8日
	 */
	List<IntegralGrant> selectList(Map<String, Object> map);
}