package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.AfterSaleMessageJdService;

public class AfterSaleMessageJdJob extends BaseJob{

	private AfterSaleMessageJdService afterSaleMessageJdService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		afterSaleMessageJdService = SpringUtil.getBean(AfterSaleMessageJdService.class);
	}

	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		afterSaleMessageJdService.executeAfterSaleMessageJdJob();
	}

}
