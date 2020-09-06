package www.tonghao.service.common.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.RolePermissions;
import www.tonghao.service.common.mapper.RolePermissionsMapper;
import www.tonghao.service.common.service.RolePermissionsService;

@Service("rolePermissionsService")
@Transactional
public class RolePermissionsServiceImpl extends BaseServiceImpl<RolePermissions> implements RolePermissionsService {

	@Autowired
	private RolePermissionsMapper rolePermissionsMapper;
	
	@Override
	public int deleteAssignPermissions(RolePermissions rolePermissions) {
		int result = rolePermissionsMapper.delete(rolePermissions);
		return result;
	}

	@Override
	public List<Permissions> getAssignPermission(Long roleId) {
		return rolePermissionsMapper.getAssignPermission(roleId);
	}

	@Override
	public List<Permissions> getPermissionByRoleId(Long roleId) {
		return rolePermissionsMapper.getPermissionByRoleId(roleId);
	}

	@Override
	public int saveAssignPermissions(Long roleId, String permissionIds) {
		if (roleId != null && StringUtils.isNotBlank(permissionIds)) {
			//删除之前的角色权限关联关系
			RolePermissions record = new RolePermissions();
			record.setRoleId(roleId);
			rolePermissionsMapper.delete(record);
			String[] permissionIdArry = permissionIds.split(",");
			rolePermissionsMapper.insertBatch(roleId, permissionIdArry);
		}
		return 1;
	}

}
