package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.RolePermissions;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.service.PermissionsService;
import www.tonghao.service.common.service.RolePermissionsService;
import www.tonghao.service.common.service.RolesService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: RoleController</p>  

* <p>Description: 角色管理</p>  

* @author Yml  

* @date 2018年10月28日  

*/  
@Api(value="roleController",description="角色管理api")
@RestController
@RequestMapping("/roleController")
public class RoleController {

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	/**  
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param roles
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="分页查询列表",notes="获取角色列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Roles> list(@ModelAttribute Roles roles, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", roles.getName());
		List<Roles> list = rolesService.find(map);
		return new PageInfo<Roles>(list);
	}
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param roles
	 * @return  
	 */  
	@ApiOperation(value="添加角色",notes="添加角色api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Roles roles){
		int result = rolesService.saveRole(roles);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param roles
	 * @return  
	 */  
	@ApiOperation(value="更新角色",notes="更新角色api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody Roles roles){
		roles.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int result = rolesService.updateNotNull(roles);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除角色",notes="删除角色api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		Roles role = rolesService.selectByKey(id);
		if (role != null && role.getIsInit() == 1) {
			return ResultUtil.resultMessage(0, "内置角色不能删除！");
		}
		int result = rolesService.delete(id);
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
	public Roles getById(Long id){
		Roles roles = rolesService.selectByKey(id);
		return roles;
	}
	
	/**  
	 * <p>Title: getAssignPermission</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param roleId
	 * @return  
	 */  
	@ApiOperation(value="查询角色分配权限",notes="查询角色分配权限api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleId",value="角色id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getAssignPermission",method=RequestMethod.GET)
	public List<Permissions> getAssignPermission(Long roleId){
		//获取权限菜单以及是否关联角色
		List<Permissions> list = rolePermissionsService.getAssignPermission(roleId);
		return list;
	}
	
	/**  
	 * <p>Title: getPermissionByRoleId</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param roleId
	 * @return  
	 */  
	@ApiOperation(value="获取角色关联的权限菜单",notes="查询获取角色关联的权限菜单api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleId",value="角色id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getPermissionByRoleId",method=RequestMethod.GET)
	public List<Permissions> getPermissionByRoleId(Long roleId){
		//获取角色关联的权限菜单
		List<Permissions> list = rolePermissionsService.getPermissionByRoleId(roleId);
		return list;
	}
	
	/**  
	 * <p>Title: saveAssignPermissions</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param rolePermissions
	 * @return  
	 */  
	@ApiOperation(value="保存分配权限",notes="保存分配权限api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleId",value="角色id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="permissionIds",value="权限菜单id",required=true,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/saveAssignPermissions",method=RequestMethod.POST)
	public Map<String, Object> saveAssignPermissions(@RequestBody RolePermissions rolePermissions){
		int result = rolePermissionsService.saveAssignPermissions(rolePermissions.getRoleId(), rolePermissions.getPermissionIds());
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: saveAssignPermissions</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param rolePermissions
	 * @return  
	 */  
	@ApiOperation(value="删除分配权限",notes="删除分配权限api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleId",value="角色id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="permissionId",value="权限菜单id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/deleteAssignPermissions",method=RequestMethod.DELETE)
	public Map<String, Object> deleteAssignPermissions(RolePermissions rolePermissions){
		int result = rolePermissionsService.deleteAssignPermissions(rolePermissions);
		return ResultUtil.resultMessage(result, "");
	}
}


