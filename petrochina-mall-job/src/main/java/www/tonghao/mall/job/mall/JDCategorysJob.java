package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.JDCategorysService;

public class JDCategorysJob extends BaseJob{
	private JDCategorysService jdCategorysService;
	protected void before(JobExecutionContext jobexecutioncontext) {
		jdCategorysService=SpringUtil.getBean(JDCategorysService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		jdCategorysService.executeJDCategorysJob();
	}
    
}
