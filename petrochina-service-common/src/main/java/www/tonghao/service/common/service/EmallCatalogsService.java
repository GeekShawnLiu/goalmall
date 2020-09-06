package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.EmallCatalogs;

public interface EmallCatalogsService extends BaseService<EmallCatalogs> {
	public List<EmallCatalogs> getEmallCatalogs(Map<String, Object> map);
	public List<EmallCatalogs> getEmallCatalogsList(Map<String, Object> map);
}
