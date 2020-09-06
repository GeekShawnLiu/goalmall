package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Commodities;


public interface CommoditiesMapper extends BaseMapper<Commodities> {

	/**  
	 * <p>Title: find</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param record
	 * @return 
	 */
	List<Commodities> find(Commodities record);

	List<Commodities> findCond(Map<String, Object> map);
}