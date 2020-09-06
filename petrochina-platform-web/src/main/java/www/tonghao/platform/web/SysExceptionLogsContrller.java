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

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.platform.entity.SysExceptionLogs;
import www.tonghao.platform.service.SysExceptionLogsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: SysExceptionLogsContrller</p>  

* <p>Description: 系统异常日志管理控制类</p>  

* @author Yml  

* @date 2018年10月29日  

*/  
@Api(value="sysExceptionLogsContrller",description="系统异常日志管理api")
@RestController
@RequestMapping("/sysExceptionLogsContrller")
public class SysExceptionLogsContrller {

	@Autowired
	private SysExceptionLogsService sysExceptionLogsService;
	
	/**  
	 * <p>Title: getPage</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param sysExceptionLogs
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="系统异常日志-分页查询列表",notes="系统异常日志数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<SysExceptionLogs> getPage(@ModelAttribute SysExceptionLogs sysExceptionLogs, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(SysExceptionLogs.class);
		Criteria criteria = example.createCriteria();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", sysExceptionLogs.getName());
		CriteriaLikeUtil.criteriaLike(criteria, map);
		CriteriaLikeUtil.criteriaBetwwen(criteria, "createdAt", sysExceptionLogs.getStartTime(), sysExceptionLogs.getEndTime());
		example.setOrderByClause("created_at desc");
		List<SysExceptionLogs> list = sysExceptionLogsService.selectByExample(example);
		return new PageInfo<SysExceptionLogs>(list);
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
	public SysExceptionLogs getById(Long id){
		SysExceptionLogs sysExceptionLogs = sysExceptionLogsService.selectByKey(id);
		return sysExceptionLogs;
	}
}
