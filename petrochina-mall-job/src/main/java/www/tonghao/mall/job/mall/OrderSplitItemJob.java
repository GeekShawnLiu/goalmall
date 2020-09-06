package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.OrderSplitItemJobService;

public class OrderSplitItemJob extends BaseJob {
	private OrderSplitItemJobService orderSplitItemJobService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		orderSplitItemJobService = SpringUtil.getBean(OrderSplitItemJobService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		orderSplitItemJobService.orderSplitItemJob();
	}

}
