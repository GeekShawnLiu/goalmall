package www.tonghao.platform.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.IntegrityConfiguration;
import www.tonghao.platform.service.IntegrityConfigurationService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@RestController
@RequestMapping("/integrityConfiguration")
public class IntegrityConfigurationController {

	@Autowired
	private IntegrityConfigurationService integrityConfigurationService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="title",value="扣减信用因素",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="isStint",value="是否限制",required=false,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<IntegrityConfiguration> test(@ModelAttribute PageBean page,String title,String status,String isStint){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(IntegrityConfiguration.class);
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isBlank(title)){
			createCriteria.andLike("title", "%"+title+"%");
		}
		if(!StringUtils.isBlank(isStint)){
			createCriteria.andEqualTo("isStint", Integer.parseInt(isStint));
		}
		if(!StringUtils.isBlank(status)){
			createCriteria.andEqualTo("status", Integer.parseInt(status));
		}
		createCriteria.andEqualTo("isDelete", 0);
		List<IntegrityConfiguration> list = integrityConfigurationService.selectByExample(example);
		return new PageInfo<IntegrityConfiguration>(list);
	}
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public IntegrityConfiguration getById(Long id){
		IntegrityConfiguration qualification = integrityConfigurationService.selectByKey(id);
		return qualification;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		IntegrityConfiguration qualification = integrityConfigurationService.selectByKey(id);
		qualification.setIsDelete(1);
		qualification.setUpdateAt(DateUtilEx.timeFormat(new Date()));
		int delete = integrityConfigurationService.updateNotNull(qualification);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="添加",notes="添加api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody IntegrityConfiguration qualification){
		qualification.setIsDelete(0);
		qualification.setCreateAt(DateUtilEx.timeFormat(new Date()));
		qualification.setUpdateAt(DateUtilEx.timeFormat(new Date()));
		int saveOrUpdate = integrityConfigurationService.save(qualification);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	@ApiOperation(value="修改",notes="修改api")
	@RequestMapping(value="/upadte",method=RequestMethod.POST)
	public Map<String, Object> update(@RequestBody IntegrityConfiguration qualification){
		qualification.setUpdateAt(DateUtilEx.timeFormat(new Date()));
		int saveOrUpdate = integrityConfigurationService.updateNotNull(qualification);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public List<IntegrityConfiguration> getAll(){
		Example example=new Example(IntegrityConfiguration.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isStint", 0);
		createCriteria.andEqualTo("status", 0);
		createCriteria.andEqualTo("isDelete", 0);
		List<IntegrityConfiguration> list = integrityConfigurationService.selectByExample(example);
		return list;
	}
	
	
}
