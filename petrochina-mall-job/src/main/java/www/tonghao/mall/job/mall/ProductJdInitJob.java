package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ProductJdInitService;

public class ProductJdInitJob extends BaseJob {
    private ProductJdInitService productJdInitService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		productJdInitService=SpringUtil.getBean(ProductJdInitService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		productJdInitService.executeProductJdinitJob();
	}

}
