package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Parameter;

public interface ParameterService extends BaseService<Parameter> {
	public List<Parameter> getParamterByCatalogsId(Long catalogsId);
	
	/**
	 * 根据品目id和服务网站code初始化或者修改参数
	 * @param code
	 * @param catalogsId
	 * @return
	 */
	public int saveInitParam(String code,Long catalogsId);

	/**  
	 * <p>Title: getParameterJoinByCatalogId</p>  
	 * <p>Description: 根据财政品目id获取对应品目参数及对应参数值</p>  
	 * @author Yml  
	 * @param catalogId
	 * @return  
	 */  
	public List<Parameter> getParameterJoinByCatalogId(Long catalogId);
	
	/**
	 * 根据平台参数ID获取一个参数
	 * @param standParamId
	 * @return
	 */
	public Parameter getOneByStandParamId(@Param("standParamId")String standParamId);

	/**
	 * 校验参数名是否重复
	 * @param id
	 * @param name
	 * @return
	 */
	long validateName(Long id,String name);

	/**
	 * 新增或修改
	 * @param parameter
	 * @return
	 */
	Map<String, Object> saveOrUpdate(Parameter parameter);

	/**
	 * 根据平台品目查询
	 * @param catalogId
	 * @return
	 */
	List<Parameter> getByCatalogId(Long catalogId);

	/**
	 * 查询所有的参数
	 * @return
	 */
	List<Parameter> selectAllByOrder();


	
	
}
