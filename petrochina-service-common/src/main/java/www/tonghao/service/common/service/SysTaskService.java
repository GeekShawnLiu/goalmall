package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.SysTask;

public interface SysTaskService extends BaseService<SysTask> {

	/**
	 * 修改或添加定时任务
	 * @param sysTask
	 * @return
	 */
	public int saveOrUpdate(SysTask sysTask);
	
	
}
