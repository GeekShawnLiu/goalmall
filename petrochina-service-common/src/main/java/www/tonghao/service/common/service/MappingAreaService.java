package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.MappingArea;

public interface MappingAreaService extends BaseService<MappingArea> {
	 
	/**
	 * 得到电商映射地址
	 * @param areaId
	 * @param emallCode
	 * @return
	 */
	MappingArea getEmallMappingArea(Long areaId,String emallCode);
	
	public List<MappingArea>  selectByMappingArea(Map<String, Object> map);
}
