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

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.MeasurementUnit;
import www.tonghao.platform.service.MeasurementUnitService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="measurementUnitController",description="计量单位管理api")
@RestController
@RequestMapping("/measurementUnitController")
public class MeasurementUnitController {

	@Autowired
	private MeasurementUnitService measurementUnitService;
	
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<MeasurementUnit> list(@ModelAttribute MeasurementUnit measurementUnit, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", measurementUnit.getName());
		if (measurementUnit.getStatus() != null) {
			map.put("status", measurementUnit.getStatus());
		}
		List<MeasurementUnit> list = measurementUnitService.find(map);
		return new PageInfo<MeasurementUnit>(list);
	}
	
	/**  
	 * <p>Title: saveOrUpdate</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param measurementUnit
	 * @return  
	 */  
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String,Object> saveOrUpdate(@RequestBody MeasurementUnit measurementUnit){
		int result = measurementUnitService.saveOrUpdate(measurementUnit);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public MeasurementUnit getById(Long id){
		MeasurementUnit measurementUnit = measurementUnitService.selectByKey(id);
		return measurementUnit;
	}
	
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = measurementUnitService.delete(id);
		return ResultUtil.resultMessage(result, "");
	}
}
