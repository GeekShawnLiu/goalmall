package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.DictionaryType;

public interface DictionaryTypeService extends BaseService<DictionaryType>{

	List<DictionaryType> findList(DictionaryType entity);
	
	Map<String, Object> saveEntity(DictionaryType entity);
	
	Map<String, Object> updateEntity(DictionaryType entity);
}
