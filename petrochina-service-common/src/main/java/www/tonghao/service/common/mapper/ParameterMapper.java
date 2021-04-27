package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Parameter;

public interface ParameterMapper extends BaseMapper<Parameter> {
	
	public List<Parameter> getParamterByCatalogsId(@Param("catalogsId") Long catalogsId);

	/**  
	 * <p>Title: getParameterJoinByCatalogId</p>  
	 * <p>Description: </p>  
	 * @author Yml
	 * @param catalogId
	 * @return  
	 */  
	public List<Parameter> getParameterJoinByCatalogId(@Param("catalogId") Long catalogId);

    int deleteParameterBycatalogsId(@Param("catalogsId") Long catalogId );
    
    String getNameByStand(@Param("standParamId")String standParamId);
    List<Parameter> selectBycatalogID(@Param("catalogID")Long catalogID);

    
    /**
     * 根据平台参数ID获取一个参数
     * @param standParamId
     * @return
     */
    Parameter getOneByStandParamId(@Param("standParamId")String standParamId);

    long getCountByName(@Param("name") String name);

	List<Parameter> getByCatalogId(@Param("catalogId") Long catalogId);

	List<Parameter> selectAllByOrder(@Param("paramName") String paramName);

}