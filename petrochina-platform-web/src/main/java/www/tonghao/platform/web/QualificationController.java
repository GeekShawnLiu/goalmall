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
import www.tonghao.platform.entity.Qualification;
import www.tonghao.platform.service.QualificationService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="qualification",description="资质管理Api")
@RestController
@RequestMapping("/qualification")
public class QualificationController {

	@Autowired
	private QualificationService qualificationService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="catalogsId",value="产品目录id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Qualification> test(@ModelAttribute PageBean page,Long  catalogsId){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(Qualification.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("catalogsId", catalogsId);
		List<Qualification> list = qualificationService.selectByExample(example);
		return new PageInfo<Qualification>(list);
	}
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Qualification getById(Long id){
		Qualification qualification = qualificationService.selectByKey(id);
		return qualification;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = qualificationService.delete(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Qualification qualification){
		int saveOrUpdate = qualificationService.saveOrUpdate(qualification);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	
}
