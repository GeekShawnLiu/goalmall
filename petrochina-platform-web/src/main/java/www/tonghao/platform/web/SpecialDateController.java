package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.SpecialDate;
import www.tonghao.platform.service.SpecialDateService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="specialDateController",description="特殊日期Api")
@RestController
@RequestMapping("/specialDate")
public class SpecialDateController {

	@Autowired
	private SpecialDateService specialDateService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="startTime",value="开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="endTime",value="结束日期",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<SpecialDate> test(@ModelAttribute PageBean page,String startTime,String endTime,Integer status){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(SpecialDate.class);
		Criteria createCriteria = example.createCriteria();
		if(startTime!=null&&!"".equals(startTime)&&endTime!=null&&!"".equals(endTime)){
			createCriteria.andBetween("dateTime", startTime, endTime);
		}
		if(status!=null){
			createCriteria.andEqualTo("status", status);
		}
		List<SpecialDate> list = specialDateService.selectByExample(example);
		return new PageInfo<SpecialDate>(list);
	}
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public SpecialDate getById(Long id){
		SpecialDate specialDate = specialDateService.selectByKey(id);
		return specialDate;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = specialDateService.delete(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody SpecialDate specialDate){
		int saveOrUpdate = specialDateService.saveOrUpdate(specialDate);
		return ResultUtil.result(saveOrUpdate, 0);
	}
}
