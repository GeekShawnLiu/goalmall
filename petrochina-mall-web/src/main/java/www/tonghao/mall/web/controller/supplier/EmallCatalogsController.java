package www.tonghao.mall.web.controller.supplier;

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

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.service.EmallCatalogsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/supplier/emallCatalogs")
@Api(value="emallCatalogsController",description="供应商商品池映射")
public class EmallCatalogsController {

	@Autowired
	private EmallCatalogsService emallCatalogsService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",dataType="string",paramType="query"),
		@ApiImplicitParam(name="catalogsName",value="品目名称",dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<EmallCatalogs> getPage(@ModelAttribute PageBean page,String supplierName,String catalogsName){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supplierName", supplierName);
		map.put("catalogsName", catalogsName);
		List<EmallCatalogs> emallCatalogs = emallCatalogsService.getEmallCatalogs(map);
		return new PageInfo<EmallCatalogs>(emallCatalogs);
	}
	@ApiOperation(value="查询单个",notes="查询单个api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="ID",required=true,dataType="long"),
	})
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public EmallCatalogs get(Long id){
		return emallCatalogsService.selectByKey(id);
	}
	
	@ApiOperation(value="修改",notes="修改api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String, Object> get(@RequestBody EmallCatalogs emallCatalogs){
		int updateNotNull = emallCatalogsService.updateNotNull(emallCatalogs);
		return ResultUtil.result(updateNotNull, 0); 
	}
}
