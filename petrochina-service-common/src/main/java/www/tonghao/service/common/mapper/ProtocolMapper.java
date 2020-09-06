package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Protocol;

public interface ProtocolMapper extends BaseMapper<Protocol> {
	
	public Protocol getProtocolById(@Param("id")Long id);
	
	
	public List<Protocol> getProtocolBySupplier(Map<String, Object> map);
	
	public List<Integer> selectList();
	int selectFix();
}