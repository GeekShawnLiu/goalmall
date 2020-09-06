package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Commodities;


public interface CommoditiesService extends BaseService<Commodities> {

	/**  
	 * <p>Title: find</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param map
	 * @return 
	 */
	List<Commodities> findCond(Map<String, Object> map);


}
