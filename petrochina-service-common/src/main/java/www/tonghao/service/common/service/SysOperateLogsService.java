package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.SysOperateLogs;

public interface SysOperateLogsService extends BaseService<SysOperateLogs> {

	/**  
	 * <p>Title: logsInsert</p>  
	 * <p>Description: 保存日志，为防止不被保存日志切点切中，用logsInsert名字</p>  
	 * @author Yml  
	 * @param log  
	 */  
	void logsInsert(SysOperateLogs log);
	
	/**
	 * 
	 * Description: 登录日志查询
	 * 
	 * @data 2019年8月8日
	 * @param 
	 * @return
	 */
	List<SysOperateLogs> selectLoginOperate(Map<String, Object> map);
}
