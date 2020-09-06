package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.MappingArea;

public interface MappingAreaMapper extends BaseMapper<MappingArea> {
	
	
	public List<MappingArea>  selectByMappingArea(Map<String, Object> map);
}