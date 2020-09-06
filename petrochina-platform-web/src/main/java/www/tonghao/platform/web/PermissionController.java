package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import www.tonghao.common.utils.TreeBuilder;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.service.PermissionsService;
import www.tonghao.utils.UserUtil;

/**  

* <p>Title: PermissionController</p>  

* <p>Description: 权限菜单管理</p>  

* @author Yml  

* @date 2018年10月29日  

*/  
@Api(value="permissionController",description="权限菜单管理api")
@RestController
@RequestMapping("/permissionController")
public class PermissionController {
	
	@Autowired
	private PermissionsService permissionsService;
	
	/**  
	 * <p>Title: queryTree</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param parentId
	 * @return  
	 */  
	@ApiOperation(value="查询子集权限菜单",notes="查询子集权限菜单数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="parentId",value="上级ID",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/queryTree",method=RequestMethod.GET)
	public List<Permissions> queryTree(Long parentId){
		Example example = new Example(Permissions.class);
		Criteria criteria = example.createCriteria();
		if (parentId == null) {
			criteria.andEqualTo("parentId", 0);
		}else {
			criteria.andEqualTo("parentId", parentId);
		}
		criteria.andEqualTo("isDelete", 0);
		example.setOrderByClause("priority asc");
		List<Permissions> list = permissionsService.selectByExample(example);
		return list;
	}
	
	@ApiOperation(value="根据id获取所有的子节点(包括其本身)",notes="根据id获取所有的子节点(包括其本身)api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenById",method=RequestMethod.GET)
	public List<Permissions> getChildrenById(long id){
		List<Permissions> childrenById = permissionsService.getChildrenById(id);
		return childrenById;
	}
	
	@ApiOperation(value="根据id获取所有的子节点",notes="根据id获取所有的子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getOtherChildrenById",method=RequestMethod.GET)
	public List<TreeNode> getOtherChildrenById(long id,HttpServletRequest request){
		List<TreeNode> treeList = UserUtil.userTreePerms(id,request);
		return new TreeBuilder().treeToList(treeList);
	}
	/*
	@RequestMapping(value="/getOtherChildrenById",method=RequestMethod.GET)
	public List<Permissions> getOtherChildrenById(long id){
		List<Permissions> childrenById = permissionsService.getOtherChildrenById(id);
		return childrenById;
	}
	*/
	/**  
	 * <p>Title: getAll</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @return  
	 */  
	@ApiOperation(value="获取所有数据",notes="获取所有数据api")
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public List<Permissions> getAll(){
		Example example = new Example(Permissions.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isDelete", 0);
		example.setOrderByClause("tree_depth,priority");
		List<Permissions> list = permissionsService.selectByExample(example);
		return list;
	}
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param permissions
	 * @return  
	 */  
	@ApiOperation(value="添加权限菜单",notes="添加权限菜单api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Permissions permissions){
		int result = permissionsService.savePermissions(permissions);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param permissions
	 * @return  
	 */  
	@ApiOperation(value="更新权限菜单",notes="更新权限菜单api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody Permissions permissions){
		int result = permissionsService.updatePermissions(permissions);
		return ResultUtil.resultMessage(result, "");
	}
	
	@ApiOperation(value="假删除权限菜单",notes="假删除权限菜单api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/updateIsDelete",method=RequestMethod.POST)
	public Map<String,Object> updateIsDelete(@RequestParam(name="id") Long id){
		Permissions entity = new Permissions();
		entity.setId(id);
		entity.setIsDelete(1);
		entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int result = permissionsService.updateNotNull(entity);
		return ResultUtil.resultMessage(result, "");
	}
	

	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除权限菜单",notes="删除权限菜单api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = permissionsService.delete(id);
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
	public Permissions getById(Long id){
		Permissions permissions = permissionsService.selectByKey(id);
		return permissions;
	}
	
	/**  
	 * <p>Title: getSelectData</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="获取数据排除自身及子节点",notes="获取数据排除自身及子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getSelectData",method=RequestMethod.GET)
	public List<Permissions> getSelectData(Long id){
		List<Permissions> permissions = permissionsService.getSelectData(id);
		return permissions;
	} 
	
}
