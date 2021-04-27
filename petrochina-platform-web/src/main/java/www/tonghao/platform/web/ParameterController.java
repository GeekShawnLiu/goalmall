package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.service.ParameterService;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PlatformCatalogsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/parameter")
@Api(value="parameterController",description="参数Api")
@PropertySource({"classpath:application.properties"})
public class ParameterController {


	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="catalogsId",value="品目id",required=true,dataType="Long",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Parameter> getPage(@ModelAttribute PageBean page,Long catalogsId){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Parameter> list = parameterService.getParamterByCatalogsId(catalogsId);
		return new PageInfo<Parameter>(list);
	}
	@ApiOperation(value="根据品目id同步品目参数",notes="根据品目id同步品目参数api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="catalogsId",value="品目id",required=true,dataType="Long",paramType="query"),
		
	})
	@RequestMapping(value="/initParam",method=RequestMethod.GET)
	public Map<String, Object> initParam(Long catalogsId){
		/*int saveInitParam = parameterService.saveInitParam(mySiteCode,catalogsId);*/
		return ResultUtil.result(0, 0); 
	}
	
	/**  
	 * <p>Title: getParameterJoinByCatalogId</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param catalogId
	 * @return  
	 */  
	@ApiOperation(value="根据 财政品目 id获取对应品目参数及对应参数值",notes="根据财政品目id获取对应品目参数及对应参数值api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="catalogId",value="财政品目id",required=true,dataType="Long",paramType="query"),
		
	})
	@RequestMapping(value="/getParameterJoinByCatalogId",method=RequestMethod.GET)
	public List<Parameter> getParameterJoinByCatalogId(Long catalogId){
		List<Parameter> parameters = parameterService.getParameterJoinByCatalogId(catalogId);
		List<Parameter> collect = parameters.stream().filter(e->!"品牌".equals(e.getName()) && !"型号".equals(e.getName()) ).collect(Collectors.toList());
		return collect;
	}
	
	/**  
	 * <p>Title: getParameterJoinByPlatformCatalogId</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param platformCatalogId
	 * @return  
	 */  
//	@ApiOperation(value="根据 平台品目 id获取对应品目参数及对应参数值",notes="根据平台品目id获取对应品目参数及对应参数值api")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="platformCatalogId",value="平台品目id",required=true,dataType="Long",paramType="query"),
//	})
//	@RequestMapping(value="/getParameterJoinByPlatformCatalogId",method=RequestMethod.GET)
//	public List<Parameter> getParameterJoinByPlatformCatalogId(Long platformCatalogId){
//		PlatformCatalogs platformCatalog = platformCatalogsService.selectByKey(platformCatalogId);
//		if (platformCatalog != null && platformCatalog.getCatalogId() != null) {
//			//平台品目对应财政品目id
//			List<Parameter> parameters = parameterService.getParameterJoinByCatalogId(platformCatalog.getCatalogId());
//			List<Parameter> collect = parameters.stream().filter(e->!"品牌".equals(e.getName()) && !"型号".equals(e.getName()) ).collect(Collectors.toList());
//			return collect;
//		}
//		return null;
//	}

	@ApiOperation(value="根据 平台品目 id获取对应品目参数及对应参数值",notes="根据平台品目id获取对应品目参数及对应参数值api")
	@ApiImplicitParams({
			@ApiImplicitParam(name="platformCatalogId",value="平台品目id",required=true,dataType="Long",paramType="query"),
	})
	@RequestMapping(value="/getParameterJoinByPlatformCatalogId",method=RequestMethod.GET)
	public List<Parameter> getParameterJoinByPlatformCatalogId(Long platformCatalogId){
		PlatformCatalogs platformCatalog = platformCatalogsService.selectByKey(platformCatalogId);
		if (platformCatalog != null) {
			//平台品目对应财政品目id
			List<Parameter> list = parameterService.getByCatalogId(platformCatalogId);
//			List<Parameter> collect = parameters.stream().filter(e->!"品牌".equals(e.getName()) && !"型号".equals(e.getName()) ).collect(Collectors.toList());
			return list;
		}
		return null;
	}



	@PostMapping(value = "/saveOrUpdate")
	public Map<String, Object> saveOrUpdate(@RequestBody Parameter parameter){
		return parameterService.saveOrUpdate(parameter);
	}

	@DeleteMapping(value = "/deleteById")
	public Map<String,Object> deleteById(Long id){
		int result = parameterService.delete(id);
		return ResultUtil.resultMessage(result,"");
	}

	@GetMapping(value = "/getAllParam")
	public List<Parameter> getAll(String paramName){
		return parameterService.selectAllByOrder(paramName);
	}

	@GetMapping(value = "/getParamByPage")
	public PageInfo<Parameter> getParamByPage(@ModelAttribute PageBean page,String paramName){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Parameter> list = parameterService.selectAllByOrder(paramName);
		return new PageInfo<Parameter>(list);
	}
}
