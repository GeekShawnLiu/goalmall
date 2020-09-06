package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ProductMessageStbService;

public class ProductMessageStbJob extends BaseJob {

    private ProductMessageStbService productMessageStbService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		productMessageStbService=SpringUtil.getBean(ProductMessageStbService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		productMessageStbService.executeProductMessageJob();
	}

}
