package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.StandardAreaInitService;

public class StandardAreaInitJob  extends BaseJob {

private StandardAreaInitService standardAreaInitService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		standardAreaInitService=SpringUtil.getBean(StandardAreaInitService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		standardAreaInitService.StandardAreaInitJob();
	}
    
}
