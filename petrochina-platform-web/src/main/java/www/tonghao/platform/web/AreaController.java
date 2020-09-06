package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.service.AreasService;

/**  

* <p>Title: AreaController</p>  

* <p>Description: 地区管理控制类</p>  

* @author Yml  

* @date 2018年10月29日  

*/  
@Api(value="areaController",description="地区管理api")
@RestController
@RequestMapping("/areaController")
public class AreaController {

	@Autowired
	private AreasService areasService;
	
	/**  
	 * <p>Title: queryTree</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param parentId
	 * @return  
	 */  
	@ApiOperation(value="查询子集地区",notes="查询子集地区数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="parentId",value="上级ID",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/queryTree",method={RequestMethod.GET})
	public List<Areas> queryTree(Long parentId){
		Example example = new Example(Areas.class);
		Criteria criteria = example.createCriteria();
		if (parentId == null) {
			criteria.andEqualTo("parentId", 0);
		}else {
			criteria.andEqualTo("parentId", parentId);
		}
		example.setOrderByClause("id asc");
		List<Areas> list = areasService.selectByExample(example);
		return list;
	}
	
	/**  
	 * <p>Title: queryTree</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param parentId
	 * @return  
	 */  
	@ApiOperation(value="查询子集地区",notes="查询子集地区数据api")
	@RequestMapping(value="/queryTree",method={RequestMethod.POST})
	public List<Areas> queryTreePOST(@RequestParam("id")Long id){
		Example example = new Example(Areas.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", id);
		List<Areas> list = areasService.selectByExample(example);
		return list;
	}
	
	@ApiOperation(value="根据id获取所有的子节点",notes="根据id获取所有的子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenById",method=RequestMethod.GET)
	public List<Areas> getChildrenById(long id){
		List<Areas> childrenById = areasService.getChildrenById(id);
		return childrenById;
	}
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param areas
	 * @return  
	 */  
	@ApiOperation(value="添加地区",notes="添加地区api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Areas areas){
		int result = areasService.saveAreas(areas);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param areas
	 * @return  
	 */  
	@ApiOperation(value="更新地区",notes="更新地区api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody Areas areas){
		areas.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int result = areasService.updateNotNull(areas);
		return ResultUtil.resultMessage(result, "");
	}

	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除地区",notes="删除地区api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = areasService.delete(id);
		return ResultUtil.resultMessage(result, "");
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
	public Areas getById(Long id){
		Areas areas = areasService.selectByKey(id);
		return areas;
	}
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenByLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<Areas> getChildrenByLevel(Long id){
		Example example=new Example(Areas.class);
		Criteria createCriteria = example.createCriteria();
		if(id!=null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		List<Areas> selectByExample = areasService.selectByExample(example);
		return selectByExample;
	}
	
	@ApiOperation(value="根据等级查询地区",notes="根据等级查询地区api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="level",value="level",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<Areas> getLevel(Integer level){
		Example example=new Example(Areas.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("treeDepth", level);
		List<Areas> selectByExample = areasService.selectByExample(example);
		return selectByExample;
	}
}
