package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.common.enums.RoleCode;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Roles;

public interface RolesService extends BaseService<Roles> {

	/**  
	 * <p>Title: saveRole</p>  
	 * <p>Description: 保存角色</p>  
	 * @author Yml  
	 * @param roles
	 * @return  
	 */  
	int saveRole(Roles roles);

	/**
	 * 获取用户角色
	 * @param userId
	 * @return
	 */
	List<Roles> getUserRoles(Long userId);

	/**  
	 * <p>Title: find</p>  
	 * <p>Description: 查询角色</p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<Roles> find(Map<String, Object> map);
	
	/**
	 * 根据Code查询角色
	 * @param code
	 * @return
	 */
	Roles findByCode(RoleCode code);
}
