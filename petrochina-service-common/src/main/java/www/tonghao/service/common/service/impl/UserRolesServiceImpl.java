package www.tonghao.service.common.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.mapper.UserRolesMapper;
import www.tonghao.service.common.service.UserRolesService;

@Service("userRolesService")
@Transactional
public class UserRolesServiceImpl extends BaseServiceImpl<UserRoles> implements UserRolesService {

	@Autowired
	private UserRolesMapper userRolesMapper;
	
	@Override
	public int deleteAssignRoles(UserRoles userRoles) {
		return delete(userRoles);
	}

	@Override
	public List<Roles> getAssignRoles(Long userId) {
		return userRolesMapper.getAssignRoles(userId);
	}

	@Override
	public int saveAssignRoles(Long userId, String roleIds) {
		if (userId != null && StringUtils.isNotBlank(roleIds)) {
			//删除之前用户角色关联
			UserRoles record = new UserRoles();
			record.setUserId(userId);
			userRolesMapper.delete(record);
			//保存最新关联关系
			String[] roleIdArry = roleIds.split(",");
			userRolesMapper.insertBatch(userId, roleIdArry);
		}
		return 1;
	}

	@Override
	public List<Roles> getSupplierUserRoles(Long userId) {
		return userRolesMapper.getSupplierUserRoles(userId);
	}

	@Override
	public List<Roles> getUserRoles(Long userId) {
		return userRolesMapper.getUserRoles(userId);
	}

}
