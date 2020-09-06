package www.tonghao.platform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.QuickLink;
import www.tonghao.platform.mapper.QuickLinkMapper;
import www.tonghao.platform.service.QuickLinkService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("quickLinkService")
public class QuickLinkServiceImpl extends BaseServiceImpl<QuickLink> implements QuickLinkService {

	@Autowired
	private QuickLinkMapper quickLinkMapper;
	
	@Override
	public int saveOrUpdate(QuickLink link) {
		int result_default=0;
		if(link.getId()!=null){
			link.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=quickLinkMapper.updateByPrimaryKeySelective(link);
		}else{
			link.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=quickLinkMapper.insert(link);
		}
		return result_default;
	}

}
