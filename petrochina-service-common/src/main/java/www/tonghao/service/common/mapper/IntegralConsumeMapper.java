package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.IntegralConsume;

public interface IntegralConsumeMapper extends BaseMapper<IntegralConsume> {
	
	public List<IntegralConsume> selectIntegralConsume(Map<String, Object> map);
}