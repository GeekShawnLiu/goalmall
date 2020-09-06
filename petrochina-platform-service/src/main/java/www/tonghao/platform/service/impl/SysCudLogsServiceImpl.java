package www.tonghao.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.entity.SysCudLogs;
import www.tonghao.platform.service.SysCudLogsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("sysCudLogsService")
@Transactional
public class SysCudLogsServiceImpl extends BaseServiceImpl<SysCudLogs> implements SysCudLogsService {

}
