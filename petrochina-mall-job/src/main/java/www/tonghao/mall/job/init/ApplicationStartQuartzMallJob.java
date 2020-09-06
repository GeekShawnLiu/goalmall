package www.tonghao.mall.job.init;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.service.common.entity.SysTask;
import www.tonghao.service.common.service.SysTaskService;

@Configuration
public class ApplicationStartQuartzMallJob implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private SysTaskService sysTaskService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Example example=new Example(SysTask.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isEnable", 1);
		createCriteria.andEqualTo("type", 2);
		List<SysTask> list = sysTaskService.selectByExample(example);
		Scheduler scheduler=null;
		try {
			scheduler = scheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		if(!CollectionUtils.isEmpty(list)){
			Class classs=null;
			for (SysTask sysTask : list) {
				try {
					classs=Class.forName(sysTask.getJobClass());
					JobDetail jobDetail=JobBuilder.newJob(classs).withIdentity(sysTask.getJobClass(), Scheduler.DEFAULT_GROUP).build();
					CronScheduleBuilder builder=CronScheduleBuilder.cronSchedule(sysTask.getCronExpression());
					CronTrigger cronTrigger=TriggerBuilder.newTrigger().withIdentity(sysTask.getJobClass(), Scheduler.DEFAULT_GROUP).withSchedule(builder).build();
					scheduler.scheduleJob(jobDetail, cronTrigger);
					scheduler.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
	}
	 /**
     * 初始注入scheduler
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException{
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler(); 
    }
}
