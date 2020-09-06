package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.OrderStbService;

public class OrderStbJob extends BaseJob {
	private OrderStbService orderStbService;
	protected void before(JobExecutionContext jobexecutioncontext) {
		orderStbService=SpringUtil.getBean(OrderStbService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		orderStbService.executeOrderStbJob();
	}

}
