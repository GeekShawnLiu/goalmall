package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.mapper.AreasMapper;
import www.tonghao.service.common.service.AreasService;

@Service("areasService")
@Transactional
public class AreasServiceImpl extends BaseServiceImpl<Areas> implements AreasService {

	@Autowired
	private AreasMapper areasMapper;

	@Override
	public int saveAreas(Areas areas) {
		String nowDate = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
		areas.setCreatedAt(nowDate);
		areas.setUpdatedAt(nowDate);
		int result = areasMapper.insertSelective(areas);
		return result;
	}

	@Override
	public List<Areas> getChildrenById(long id) {
		return areasMapper.getChildrenById(id);
	}

	@Override
	public List<Areas> findListByEntity(Areas areas) {
		return areasMapper.findListByEntity(areas);
	}

	
}
