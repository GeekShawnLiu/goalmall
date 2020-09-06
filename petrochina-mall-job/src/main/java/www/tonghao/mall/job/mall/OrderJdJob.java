package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.OrderJdService;

public class OrderJdJob extends BaseJob {

	private OrderJdService orderJdService;

	protected void before(JobExecutionContext jobexecutioncontext) {
		orderJdService = SpringUtil.getBean(OrderJdService.class);
	}

	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		orderJdService.executeOrderJdJob();
	}

}
