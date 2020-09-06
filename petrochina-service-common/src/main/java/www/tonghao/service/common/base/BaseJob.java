package www.tonghao.service.common.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class BaseJob implements Job {

	
    protected void before(JobExecutionContext jobexecutioncontext){
    	
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		before(context);
		doService(context);
		
	}
	/**
	 * 执行业务代码
	 * @param jobexecutioncontext
	 * @throws JobExecutionException
	 */
	public abstract void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException;
}
