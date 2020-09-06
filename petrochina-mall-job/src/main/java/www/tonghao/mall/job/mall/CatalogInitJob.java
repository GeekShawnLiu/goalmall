package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.CatalogInitService;
import www.tonghao.mall.job.service.ProductStandardInitService;

public class CatalogInitJob extends BaseJob {

    private CatalogInitService catalogInitService;
	
	protected void before(JobExecutionContext jobexecutioncontext) {
		catalogInitService=SpringUtil.getBean(CatalogInitService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		catalogInitService.catalogInitJob();
	}

	
	
}
