package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.JdProductDetailService;

public class JdProductDetailJob extends BaseJob{
	private JdProductDetailService jdProductDetailService;

	protected void before(JobExecutionContext jobexecutioncontext) {
		jdProductDetailService = SpringUtil.getBean(JdProductDetailService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		jdProductDetailService.executedProductDetailjob();
	}
    
}
