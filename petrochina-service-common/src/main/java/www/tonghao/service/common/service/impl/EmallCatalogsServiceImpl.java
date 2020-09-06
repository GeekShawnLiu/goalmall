package www.tonghao.service.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.mapper.EmallCatalogsMapper;
import www.tonghao.service.common.service.EmallCatalogsService;

@Service("emallCatalogsService")
public class EmallCatalogsServiceImpl extends BaseServiceImpl<EmallCatalogs> implements EmallCatalogsService {

	@Autowired
	private EmallCatalogsMapper emallCatalogsMapper;
	@Override
	public List<EmallCatalogs> getEmallCatalogs(Map<String, Object> map) {
		return emallCatalogsMapper.getEmallCatalogs(map);
	}
	@Override
	public List<EmallCatalogs> getEmallCatalogsList(Map<String, Object> map) {
		return emallCatalogsMapper.getEmallCatalogsList(map);
	}

}
