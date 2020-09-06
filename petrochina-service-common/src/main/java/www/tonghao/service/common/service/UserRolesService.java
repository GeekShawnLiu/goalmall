package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.UserRoles;

public interface UserRolesService extends BaseService<UserRoles> {

	/**  
	 * <p>Title: deleteAssignRoles</p>  
	 * <p>Description: 保存用户分配角色</p>  
	 * @author Yml  
	 * @param userRoles
	 * @return  
	 */  
	int deleteAssignRoles(UserRoles userRoles);

	/**  
	 * <p>Title: getAssignRoles</p>  
	 * <p>Description: 查用户可分配和已分配角色</p>  
	 * @author Yml  
	 * @param userId
	 * @return  
	 */  
	List<Roles> getAssignRoles(Long userId);

	/**  
	 * <p>Title: saveAssignRoles</p>  
	 * <p>Description: 批量保存用户角色关联</p>  
	 * @author Yml  
	 * @param userId
	 * @param roleIds
	 * @return  
	 */  
	int saveAssignRoles(Long userId, String roleIds);
	
	/**
	 * 
	 * Description: 获取供应商子账号角色信息
	 * 
	 * @data 2019年8月30日
	 * @param 
	 * @return
	 */
	List<Roles> getSupplierUserRoles(Long userId);

	/**
	 * 
	 * Description: 获取用户分配的角色
	 * 
	 * @data 2019年9月3日
	 * @param 
	 * @return
	 */
	List<Roles> getUserRoles(Long userId);
}
