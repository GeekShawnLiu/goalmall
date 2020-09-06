package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.EmallCatalogs;

public interface EmallCatalogsMapper extends BaseMapper<EmallCatalogs> {
	public List<EmallCatalogs> getEmallCatalogs(Map<String, Object> map);
	
	
	public List<EmallCatalogs> getEmallCatalogsList(Map<String, Object> map);
}