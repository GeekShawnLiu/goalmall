package www.tonghao.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.entity.SysExceptionLogs;
import www.tonghao.platform.service.SysExceptionLogsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("sysExceptionLogsService")
@Transactional
public class SysExceptionLogsServiceImpl extends BaseServiceImpl<SysExceptionLogs> implements SysExceptionLogsService {

	
}
