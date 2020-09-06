package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.DictionaryData;
import www.tonghao.service.common.entity.DictionaryType;

public interface DictionaryDataService extends BaseService<DictionaryData> {

	List<DictionaryData> findList(DictionaryData entity);

	Map<String, Object> saveEntity(DictionaryData entity);

	Map<String, Object> updateEntity(DictionaryData entity);
	
	Map<String, Object> deleteEntity(Long id);
	
	List<DictionaryType> getDictionaryTypeTree();
	
	/**
	 * 
	 * Description: 根据字典类型code获取数据
	 * 
	 * @data 2019年9月5日
	 * @param 
	 * @return
	 */
	List<DictionaryData> getDictionaryDataByType(String code);
}