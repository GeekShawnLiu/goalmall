package www.tonghao.platform.service;

import java.util.List;

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
	
	
}
