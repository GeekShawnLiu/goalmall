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
import www.tonghao.platform.entity.SupplierScale;
import www.tonghao.platform.service.SupplierScaleService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Api(value="supplierScaleController",description="供应商规模Api")
@RestController
@RequestMapping("/supplierScale")
public class SupplierScaleController {

	@Autowired
	private SupplierScaleService supplierScaleService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<SupplierScale> test(@ModelAttribute PageBean page,String name,Integer status){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(SupplierScale.class);
		Map<String, Object> maplike=new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		maplike.put("name", name);
		maplike.put("status", status);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<SupplierScale> list = supplierScaleService.selectByExample(example);
		return new PageInfo<SupplierScale>(list);
	}
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public SupplierScale getById(Long id){
		SupplierScale supplierScale = supplierScaleService.selectByKey(id);
		return supplierScale;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = supplierScaleService.delete(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody SupplierScale supplierScale){
		int saveOrUpdate = supplierScaleService.saveOrUpdate(supplierScale);
		return ResultUtil.result(saveOrUpdate, 0);
	}
}
