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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.platform.entity.QuickLink;
import www.tonghao.platform.service.QuickLinkService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="quickLinkController",description="快速链接Api")
@RestController
@RequestMapping("/quickLink")
public class QuickLinkController {

	@Autowired
	private QuickLinkService quickLinkService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="string",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<QuickLink> test(@ModelAttribute PageBean page,String name,String status){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(QuickLink.class);
		Map<String, Object> maplike=new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		maplike.put("name", name);
		maplike.put("status", status);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<QuickLink> list = quickLinkService.selectByExample(example);
		return new PageInfo<QuickLink>(list);
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public QuickLink getById(Long id){
		QuickLink quickLink = quickLinkService.selectByKey(id);
		return quickLink;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = quickLinkService.delete(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody QuickLink quickLink){
		int saveOrUpdate = quickLinkService.saveOrUpdate(quickLink);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public List<QuickLink> All(){
		List<QuickLink> selectByExample = quickLinkService.selectByExample(null);
		return selectByExample;
	}
	
}
