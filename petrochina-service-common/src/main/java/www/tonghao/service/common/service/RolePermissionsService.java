package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.RolePermissions;

public interface RolePermissionsService extends BaseService<RolePermissions> {

	/**  
	 * <p>Title: deleteAssignPermissions</p>  
	 * <p>Description: 删除分配权限</p>  
	 * @author Yml  
	 * @param rolePermissions
	 * @return  
	 */  
	int deleteAssignPermissions(RolePermissions rolePermissions);

	/**  
	 * <p>Title: getAssignPermission</p>  
	 * <p>Description: 获取权限菜单以及是否关联角色</p>  
	 * @author Yml  
	 * @param roleId
	 * @return  
	 */  
	List<Permissions> getAssignPermission(Long roleId);

	/**  
	 * <p>Title: getPermissionByRoleId</p>  
	 * <p>Description: 获取角色关联的权限菜单</p>  
	 * @author Yml  
	 * @param roleId
	 * @return  
	 */  
	List<Permissions> getPermissionByRoleId(Long roleId);

	/**  
	 * <p>Title: saveAssignPermissions</p>  
	 * <p>Description: 保存角色权限</p>  
	 * @author Yml  
	 * @param roleId
	 * @param permissionIds
	 * @return  
	 */  
	int saveAssignPermissions(Long roleId, String permissionIds);


}
