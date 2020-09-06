package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.UpperLowerHistory;


public interface UpperLowerHistoryMapper extends BaseMapper<UpperLowerHistory> {
	
	List<UpperLowerHistory> selectProductIdGroupBy();
	
}