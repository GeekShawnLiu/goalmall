package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.EmallCatalogsMapping;


public interface EmallCatalogsMappingMapper extends BaseMapper<EmallCatalogsMapping> {
	public List<EmallCatalogsMapping> getEmallCatalogs(Map<String, Object> map);
}