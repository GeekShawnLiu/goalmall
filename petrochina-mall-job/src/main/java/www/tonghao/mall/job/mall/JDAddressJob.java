package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.JDAddressService;

public class JDAddressJob extends BaseJob  {

	private JDAddressService jdAddressService;
	protected void before(JobExecutionContext jobexecutioncontext) {
		jdAddressService=SpringUtil.getBean(JDAddressService.class);
	}
	
	@Override
	public void doService(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		
		jdAddressService.executeJDAddressJob();
		
	}

}
