package www.tonghao.service.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.mapper.MappingAreaMapper;
import www.tonghao.service.common.service.MappingAreaService;

@Service("mappingAreaService")
public class MappingAreaServiceImpl extends BaseServiceImpl<MappingArea> implements MappingAreaService {

	@Autowired
	private MappingAreaMapper mappingAreaMapper;
	
	@Override
	public MappingArea getEmallMappingArea(Long areaId, String emallCode) {
		Example example = new Example(MappingArea.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("areaId", areaId);
		criteria.andEqualTo("emallCode", emallCode);
		List<MappingArea> list = selectByExample(example);
		if(list.size()<1){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<MappingArea> selectByMappingArea(Map<String, Object> map) {
		return mappingAreaMapper.selectByMappingArea(map);
	}
	
}
