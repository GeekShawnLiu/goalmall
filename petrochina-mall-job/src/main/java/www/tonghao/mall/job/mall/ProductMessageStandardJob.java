package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ProductMessageStandardService;

public class ProductMessageStandardJob extends BaseJob {

    private ProductMessageStandardService productMessageStandardService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		productMessageStandardService=SpringUtil.getBean(ProductMessageStandardService.class);
	}
	
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		productMessageStandardService.executeProductMessageStandardJob();
	}

}
