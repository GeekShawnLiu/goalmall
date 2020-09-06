package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.SysOperateLogs;
import www.tonghao.service.common.service.SysOperateLogsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: SysOperateLogsContrller</p>  

* <p>Description: 系统操作日志管理控制类</p>  

* @author Yml  

* @date 2018年10月29日  

*/  
@Api(value="sysOperateLogsContrller",description="系统操作日志管理api")
@RestController
@RequestMapping("/sysOperateLogsContrller")
public class SysOperateLogsContrller {

	@Autowired
	private SysOperateLogsService sysOperateLogsService;
	
	/**  
	 * <p>Title: getPageforOperate</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param operateLogs
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="用户操作日志-分页查询列表",notes="获取用户操作日志数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPageforOperate",method=RequestMethod.GET)
	public PageInfo<SysOperateLogs> getPageforOperate(@ModelAttribute SysOperateLogs operateLogs, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<>();
		map.put("loginName", operateLogs.getLoginName());
		map.put("ip", operateLogs.getIp());
		map.put("logType", 1);
		map.put("startTime", operateLogs.getStartTime());
		map.put("endTime", operateLogs.getEndTime());
		map.put("realName", operateLogs.getRealName());
		map.put("department", operateLogs.getDepartment());
		List<SysOperateLogs> list = sysOperateLogsService.selectLoginOperate(map);
		return new PageInfo<SysOperateLogs>(list);
	}
	
	/**  
	 * <p>Title: getPageforLogin</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param operateLogs
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="用户登录日志-分页查询列表",notes="获取用户登录日志数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPageforLogin",method=RequestMethod.GET)
	public PageInfo<SysOperateLogs> getPageforLogin(@ModelAttribute SysOperateLogs operateLogs, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<>();
		map.put("loginName", operateLogs.getLoginName());
		map.put("ip", operateLogs.getIp());
		map.put("logType", 2);
		map.put("startTime", operateLogs.getStartTime());
		map.put("endTime", operateLogs.getEndTime());
		map.put("realName", operateLogs.getRealName());
		map.put("department", operateLogs.getDepartment());
		List<SysOperateLogs> list = sysOperateLogsService.selectLoginOperate(map);
		return new PageInfo<SysOperateLogs>(list);
	}
	
	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public SysOperateLogs getById(Long id){
		SysOperateLogs sysOperateLogs = sysOperateLogsService.selectByKey(id);
		return sysOperateLogs;
	}
}
