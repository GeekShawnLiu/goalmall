package www.tonghao.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.MallFeedback;
import www.tonghao.mall.mapper.MallFeedbackMapper;
import www.tonghao.mall.service.MallFeedbackService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("mallFeedbackService")
public class MallFeedbackServiceImpl extends BaseServiceImpl<MallFeedback> implements MallFeedbackService {

	@Autowired
	private MallFeedbackMapper mallFeedbackMapper;
	
	@Override
	public MallFeedback findById(Long id) {
		return mallFeedbackMapper.findById(id);
	}

	@Override
	public List<MallFeedback> findListByEntity(MallFeedback entity) {
		return mallFeedbackMapper.findListByEntity(entity);
	}

}
