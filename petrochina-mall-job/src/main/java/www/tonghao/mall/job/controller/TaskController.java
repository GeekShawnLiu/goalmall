package www.tonghao.mall.job.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.job.init.QuartzSchedulerMall;
import www.tonghao.service.common.entity.SysTask;
import www.tonghao.service.common.service.SysTaskService;
@Api(value="sysTaskController",description="定时器Api")
@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private SysTaskService sysTaskService;
	
	@Autowired
	private QuartzSchedulerMall quartzSchedulerMall;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<SysTask> test(@ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(SysTask.class);
		List<SysTask> list = sysTaskService.selectByExample(example);
		return new PageInfo<SysTask>(list);
	}
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public SysTask getById(Long id){
		SysTask task = sysTaskService.selectByKey(id);
		return task;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		SysTask task = sysTaskService.selectByKey(id);
		int delete = sysTaskService.delete(id);
		if(delete>0) {
			try {
				if (task.getIsEnable() == 1) {
					quartzSchedulerMall.deleteJob(task.getJobClass());
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody SysTask task) {
		int saveOrUpdate = sysTaskService.saveOrUpdate(task);
		if (saveOrUpdate > 0) {
			try {
				if (task.getId() != null) {
					if (task.getIsEnable() == 1) {
						String jobInfo = quartzSchedulerMall.getJobInfo(task.getJobClass());
						if (StringUtils.isEmpty(jobInfo)) {
							quartzSchedulerMall.addJob(task.getJobClass(), task.getCronExpression());
						} else {
							quartzSchedulerMall.modifyJob(task.getJobClass(), task.getCronExpression());
						}
					} else {
						quartzSchedulerMall.deleteJob(task.getJobClass());
					}
				} else {
					if (task.getIsEnable() == 1) {
						quartzSchedulerMall.addJob(task.getJobClass(), task.getCronExpression());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultUtil.result(saveOrUpdate, 0);
	}
}
