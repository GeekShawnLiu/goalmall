package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.RolePermissions;

public interface RolePermissionsMapper extends BaseMapper<RolePermissions> {

	/**  
	 * <p>Title: getAssignPermission</p>  
	 * <p>Description: 获取权限菜单以及是否关联角色</p>  
	 * @author Yml  
	 * @param roleId
	 * @return  
	 */  
	List<Permissions> getAssignPermission(@Param("roleId") Long roleId);

	/**  
	 * <p>Title: getPermissionByRoleId</p>  
	 * <p>Description: 获取角色关联的权限菜单</p>  
	 * @author Yml  
	 * @param roleId
	 * @return  
	 */  
	List<Permissions> getPermissionByRoleId(@Param("roleId") Long roleId);

	/**  
	 * <p>Title: insertBatch</p>  
	 * <p>Description: 批量插入角色权限关联</p>  
	 * @author Yml  
	 * @param roleId
	 * @param permissionIdArry  
	 */  
	void insertBatch(@Param("roleId") Long roleId, @Param("permissionIdArry") String[] permissionIdArry);
}