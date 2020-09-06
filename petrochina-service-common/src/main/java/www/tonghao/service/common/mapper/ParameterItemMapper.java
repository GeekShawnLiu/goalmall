package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ParameterItem;

public interface ParameterItemMapper extends BaseMapper<ParameterItem> {
	
	/**  
	 * <p>Title: getByStandParamId</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param standParamId
	 * @return  
	 */  
	List<ParameterItem> getByStandParamId(@Param("standParamId") String standParamId);
	
	int deleteParameterItemByParameterId(@Param("parameterId") Long parameterId);
	
	/**
	 * 根据标准参数值查询
	 * @param standValueIds
	 * @return
	 */
	List<ParameterItem> getByStandValueIds(@Param("standValueIds")String[] standValueIds);
	
	/**
	 * 根据参数ids查询参数项的ids
	 * @param parameterIds
	 * @return
	 */
	Set<Long> getByParameterIds(@Param("parameterIds") Set<Long> parameterIds);
}