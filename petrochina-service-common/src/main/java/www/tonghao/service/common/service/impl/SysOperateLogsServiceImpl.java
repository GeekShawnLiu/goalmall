package www.tonghao.service.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.SysOperateLogs;
import www.tonghao.service.common.mapper.SysOperateLogsMapper;
import www.tonghao.service.common.service.SysOperateLogsService;

@Service("sysOperateLogsService")
@Transactional
public class SysOperateLogsServiceImpl extends BaseServiceImpl<SysOperateLogs> implements SysOperateLogsService {

	@Autowired
	private SysOperateLogsMapper sysOperateLogsMapper;
	
	@Override
	public void logsInsert(SysOperateLogs log) {
		sysOperateLogsMapper.add(log);
	}

	@Override
	public List<SysOperateLogs> selectLoginOperate(Map<String, Object> map) {
		return sysOperateLogsMapper.selectLoginOperate(map);
	}
}
