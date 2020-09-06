package www.tonghao.platform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.Qualification;
import www.tonghao.platform.mapper.QualificationMapper;
import www.tonghao.platform.service.QualificationService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("qualificationService")
public class QualificationServiceImpl extends BaseServiceImpl<Qualification> implements QualificationService {

	@Autowired
	public QualificationMapper qualificationMapper;
	
	
	@Override
	public int saveOrUpdate(Qualification qualification) {
		int result_default=0;
		if(qualification.getId()!=null){
			qualification.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=qualificationMapper.updateByPrimaryKeySelective(qualification);
		}else{
			qualification.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=qualificationMapper.insert(qualification);
		}
		return result_default;
	}

	
	
}
