package www.tonghao.mall.job.mall;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.tonghao.common.utils.SpringUtil;
import www.tonghao.mall.job.init.BaseJob;
import www.tonghao.mall.job.service.ImageStbPicService;

public class ImageStbPicJob extends BaseJob {
	private ImageStbPicService imageStbPicService;
	protected void before(JobExecutionContext jobexecutioncontext) {
		imageStbPicService=SpringUtil.getBean(ImageStbPicService.class);
	}
	@Override
	public void doService(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		imageStbPicService.executeImageStbPicJob();
	}

}
