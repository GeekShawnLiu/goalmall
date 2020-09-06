package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Integral;

public interface IntegralMapper extends BaseMapper<Integral> {
	
	/**
	 * @Description:积分登记列表
	 * @date 2019年4月30日
	 */
	List<Integral> selectList(Integral integral);
	
	/**
	 * @Description:获取积分关联可用的活动
	 * @date 2019年8月7日
	 */
	List<Integral> selectByUsableActivity();
}