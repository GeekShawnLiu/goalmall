package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ProductStbInitService;

public class ProductStbInitJob extends BaseJob {

	private ProductStbInitService productStbInitService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		productStbInitService=SpringUtil.getBean(ProductStbInitService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		productStbInitService.executeStbInitJob();
	}

}
