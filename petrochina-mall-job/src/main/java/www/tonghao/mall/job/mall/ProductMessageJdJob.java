package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ProductMessageJdService;

public class ProductMessageJdJob extends BaseJob{
private ProductMessageJdService productMessageJdService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		productMessageJdService=SpringUtil.getBean(ProductMessageJdService.class);
	}
	
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		productMessageJdService.executeProductJdMessageJob();
	}

}
