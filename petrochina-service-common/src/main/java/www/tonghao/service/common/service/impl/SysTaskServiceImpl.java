package www.tonghao.service.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.SysTask;
import www.tonghao.service.common.mapper.SysTaskMapper;
import www.tonghao.service.common.service.SysTaskService;

@Service("sysTaskService")
public class SysTaskServiceImpl extends BaseServiceImpl<SysTask> implements SysTaskService {

	@Autowired
	private SysTaskMapper sysTaskMapper;
	
	@Override
	public int saveOrUpdate(SysTask sysTask) {
		int result_default=0;
		if(sysTask.getId()!=null){
			sysTask.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=sysTaskMapper.updateByPrimaryKeySelective(sysTask);
		}else{
			sysTask.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=sysTaskMapper.insert(sysTask);
		}
		return result_default;
	}

	
	
}
