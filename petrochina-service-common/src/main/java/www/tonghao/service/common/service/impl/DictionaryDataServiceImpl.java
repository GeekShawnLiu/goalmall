package www.tonghao.service.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.DictionaryData;
import www.tonghao.service.common.entity.DictionaryType;
import www.tonghao.service.common.mapper.DictionaryDataMapper;
import www.tonghao.service.common.mapper.DictionaryTypeMapper;
import www.tonghao.service.common.service.DictionaryDataService;


@Service("dictionaryDataService")
public class DictionaryDataServiceImpl extends BaseServiceImpl<DictionaryData> implements DictionaryDataService{

	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	
	@Autowired
	private DictionaryTypeMapper dictionaryTypeMapper;

	@Override
	public List<DictionaryData> findList(DictionaryData entity) {
		entity.setIsDelete(0);
		return dictionaryDataMapper.select(entity);
	}

	@Override
	public Map<String, Object> saveEntity(DictionaryData entity) {
		Map<String, Object> vaDictionaryData = vaDictionaryData(entity);
		if(ResultUtil.isSuccess(vaDictionaryData)){
			entity.setIsDelete(0);
			entity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			int insert = dictionaryDataMapper.insertSelective(entity);
			return ResultUtil.resultMessage(insert, "");
		}
		return vaDictionaryData;
	}

	@Override
	public Map<String, Object> updateEntity(DictionaryData entity) {
		Map<String, Object> vaDictionaryData = vaDictionaryData(entity);
		if(ResultUtil.isSuccess(vaDictionaryData)){
			entity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int update = dictionaryDataMapper.updateByPrimaryKeySelective(entity);
			return ResultUtil.resultMessage(update, "");
		}
		return vaDictionaryData;
	}
	
	public Map<String, Object> vaDictionaryData(DictionaryData entity){
		if(entity == null){
			return ResultUtil.error("信息不能为空");
		}
		if(entity.getCode() == null){
			return ResultUtil.error("code不能为空");
		}else{
			Example example = new Example(DictionaryData.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("code", entity.getCode());
			if(entity.getId() != null){
				createCriteria.andNotEqualTo("id", entity.getId());
			}
			List<DictionaryData> selectByExample = dictionaryDataMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(selectByExample)){
				return ResultUtil.error("code不能重复");
			}
		}
		if(entity.getName() == null){
			return ResultUtil.error("名称不能为空");
		}
		if(entity.getType() == null){
			return ResultUtil.error("类型不能为空");
		}
		return ResultUtil.success("");
	}

	@Override
	public Map<String, Object> deleteEntity(Long id) {
		DictionaryData dictionaryData = new DictionaryData();
		dictionaryData.setId(id);
		dictionaryData.setIsDelete(1);
		int update = dictionaryDataMapper.updateByPrimaryKeySelective(dictionaryData);
		return ResultUtil.resultMessage(update, "");
	}

	@Override
	public List<DictionaryType> getDictionaryTypeTree() {
		List<DictionaryType> list = new ArrayList<>();
		DictionaryType parent = new DictionaryType();
		parent.setId(0L);
		parent.setName("数据字典类型");
		list.add(parent);
		Example example = new Example(DictionaryType.class);
		example.orderBy("id");
		List<DictionaryType> selectByExample = dictionaryTypeMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(selectByExample)){
			selectByExample.forEach(d -> {
				d.setParentId(0L);
				list.add(d);
			});
		}
		return list;
	}

	@Override
	public List<DictionaryData> getDictionaryDataByType(String code) {
		Example typeExample = new Example(DictionaryType.class);
		Criteria typeCriteria = typeExample.createCriteria();
		typeCriteria.andEqualTo("code", code);
		List<DictionaryType> typeList = dictionaryTypeMapper.selectByExample(typeExample);
		if(CollectionUtils.isNotEmpty(typeList)){
			DictionaryType dictionaryType = typeList.get(0);
			Example dataExample = new Example(DictionaryData.class);
			Criteria dataCriteria = dataExample.createCriteria();
			dataCriteria.andEqualTo("type", dictionaryType.getId());
			dataCriteria.andEqualTo("isDelete", 0);
			List<DictionaryData> dataList = dictionaryDataMapper.selectByExample(dataExample);
			return dataList;
		}
		return new ArrayList<DictionaryData>();
	}
}
