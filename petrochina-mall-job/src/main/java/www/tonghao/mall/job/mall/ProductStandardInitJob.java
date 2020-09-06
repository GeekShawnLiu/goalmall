package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ProductStandardInitService;

public class ProductStandardInitJob extends BaseJob {

private ProductStandardInitService productStandardInitService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		productStandardInitService=SpringUtil.getBean(ProductStandardInitService.class);
	}
	
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		productStandardInitService.productStandardInitJob();
	}

}
