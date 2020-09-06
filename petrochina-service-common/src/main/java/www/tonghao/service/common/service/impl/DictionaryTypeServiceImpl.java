package www.tonghao.service.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.DictionaryType;
import www.tonghao.service.common.mapper.DictionaryTypeMapper;
import www.tonghao.service.common.service.DictionaryTypeService;

@Service("dictionaryTypeService")
public class DictionaryTypeServiceImpl extends BaseServiceImpl<DictionaryType> implements DictionaryTypeService {

	@Autowired
	private DictionaryTypeMapper dictionaryTypeMapper;

	@Override
	public List<DictionaryType> findList(DictionaryType entity) {
		return dictionaryTypeMapper.select(entity);
	}

	@Override
	public Map<String, Object> saveEntity(DictionaryType entity) {
		Map<String, Object> vaDictionaryType = vaDictionaryType(entity);
		if(ResultUtil.isSuccess(vaDictionaryType)){
			int insert = dictionaryTypeMapper.insertSelective(entity);
			return ResultUtil.resultMessage(insert, "");
		}
		return vaDictionaryType;
	}

	@Override
	public Map<String, Object> updateEntity(DictionaryType entity) {
		Map<String, Object> vaDictionaryType = vaDictionaryType(entity);
		if(ResultUtil.isSuccess(vaDictionaryType)){
			int update = dictionaryTypeMapper.updateByPrimaryKeySelective(entity);
			return ResultUtil.resultMessage(update, "");
		}
		return vaDictionaryType;
	}
	
	public Map<String, Object> vaDictionaryType(DictionaryType entity){
		if(entity == null){
			return ResultUtil.error("信息不能为空");
		}
		if(entity.getCode() == null){
			return ResultUtil.error("code不能为空");
		}else{
			Example example = new Example(DictionaryType.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("code", entity.getCode());
			if(entity.getId() != null){
				createCriteria.andNotEqualTo("id", entity.getId());
			}
			List<DictionaryType> selectByExample = dictionaryTypeMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(selectByExample)){
				return ResultUtil.error("code不能重复");
			}
		}
		if(entity.getName() == null){
			return ResultUtil.error("名称不能为空");
		}
		return ResultUtil.success("");
	}
}
