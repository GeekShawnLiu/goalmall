package www.tonghao.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.service.CommoditiesService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Commodities;
import www.tonghao.service.common.mapper.CommoditiesMapper;

@Service("commoditiesService")
@Transactional
public class CommoditiesServiceImpl extends BaseServiceImpl<Commodities> implements CommoditiesService {

	@Autowired
	private CommoditiesMapper commoditiesMapper;
	
	@Override
	public List<Commodities> findCond(Map<String, Object> map) {
		return commoditiesMapper.findCond(map);
	}

}
