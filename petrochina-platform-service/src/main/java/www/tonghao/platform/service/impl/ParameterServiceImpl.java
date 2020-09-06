package www.tonghao.platform.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.service.ParameterItemService;
import www.tonghao.platform.service.ParameterService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.mapper.ParameterMapper;
@Service("parameterService")
public class ParameterServiceImpl extends BaseServiceImpl<Parameter> implements ParameterService {

	@Autowired
	private ParameterMapper parameterMapper;
	@Autowired
	private ParameterItemService parameterItemService;
	
	
	@Override
	public List<Parameter> getParamterByCatalogsId(Long catalogsId) {
		return parameterMapper.getParamterByCatalogsId(catalogsId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveInitParam(String code,Long catalogsId) {
		int flgs=0;
		/*
		Parameter entity=new Parameter();
		entity.setCatalogsId(catalogsId);
		List<Parameter> p = this.select(entity);
		GetParamsCatalogById byId=new GetParamsCatalogById(code,catalogsId+"");
		ParamsCatalogs result = byId.getResult();
		if(!CollectionUtils.isEmpty(p)){
			for (Parameter parameter : p) {
				parameterItemService.deleteParameterItemByParameterId(parameter.getId());
			}
			parameterMapper.deleteParameterBycatalogsId(catalogsId);
		}else{
			if(result.getSuccess()){
				List<ParamsCatalogAttr> paramsCatalogAttr = result.getParamsCatalogAttr();
				if(!CollectionUtils.isEmpty(paramsCatalogAttr)){
					Parameter parameter=null;
					for (ParamsCatalogAttr pca : paramsCatalogAttr) {
						parameter=new Parameter();
						parameter.setCatalogsId(catalogsId);
						parameter.setIsKey(pca.getIsKey()+"");
						parameter.setIsSellable(pca.getIsSellable()+"");
						parameter.setName(pca.getName());
						parameter.setPosition(pca.getPosition());
						parameter.setStandParamId(pca.getStandParamId());
						parameter.setTtype(pca.getTtype());
						flgs+=this.save(parameter);
						List<StandValue> standValues = pca.getStandValues();
						if(!CollectionUtils.isEmpty(standValues)){
							ParameterItem item=null;
							for (StandValue standValue : standValues) {
								item=new ParameterItem();
								item.setName(standValue.getName());
								item.setParameterId(parameter.getId());
								item.setPosition(standValue.getPosition());
								item.setStandParamId(standValue.getStandParamId());
								item.setStandValueId(standValue.getStandValueId());
								flgs+=parameterItemService.save(item);
							}
						}
					}
				}
			}
		}*/
		return flgs;
	}

	@Override
	public List<Parameter> getParameterJoinByCatalogId(Long catalogId) {
		return parameterMapper.getParameterJoinByCatalogId(catalogId);
	}

	/**
	 * 根据平台参数ID获取一个参数
	 */
	public Parameter getOneByStandParamId(@Param("standParamId")String standParamId) {
		return parameterMapper.getOneByStandParamId(standParamId);
	}

}
