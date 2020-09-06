package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.DeleteProductJdService;

public class JdProductDeleteJob extends BaseJob  {
	private DeleteProductJdService deleteProductJdService;

	protected void before(JobExecutionContext jobexecutioncontext) {
		deleteProductJdService = SpringUtil.getBean(DeleteProductJdService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		deleteProductJdService.executeDeleteProductJdJob();
	}

}
