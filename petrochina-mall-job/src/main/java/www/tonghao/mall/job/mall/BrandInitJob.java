package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.BrandInitService;

public class BrandInitJob extends BaseJob {
private BrandInitService brandInitService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		brandInitService=SpringUtil.getBean(BrandInitService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		brandInitService.brandInitServiceJob();
	}
}
