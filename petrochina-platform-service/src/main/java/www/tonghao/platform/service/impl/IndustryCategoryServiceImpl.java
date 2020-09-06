package www.tonghao.platform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.IndustryCategory;
import www.tonghao.platform.mapper.IndustryCategoryMapper;
import www.tonghao.platform.service.IndustryCategoryService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("industryCategoryService")
public class IndustryCategoryServiceImpl extends BaseServiceImpl<IndustryCategory> implements IndustryCategoryService {

	@Autowired
	private IndustryCategoryMapper industryCategoryMapper;

	@Override
	public int saveOrUpdate(IndustryCategory industryCategory) {
		int result_default=0;
		if(industryCategory.getId()!=null){
			industryCategory.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=industryCategoryMapper.updateByPrimaryKeySelective(industryCategory);
		}else{
			industryCategory.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=industryCategoryMapper.insert(industryCategory);
		}
		return result_default;
	}
	
}
