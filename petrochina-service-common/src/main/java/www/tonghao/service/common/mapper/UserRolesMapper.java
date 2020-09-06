package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.UserRoles;

public interface UserRolesMapper extends BaseMapper<UserRoles> {

	/**  
	 * <p>Title: getAssignRoles</p>  
	 * <p>Description:查用户可分配和已分配角色 </p>  
	 * @author Yml  
	 * @param userId
	 * @return  
	 */  
	List<Roles> getAssignRoles(@Param("userId") Long userId);

	/**  
	 * <p>Title: insertBatch</p>  
	 * <p>Description: 批量保存用户角色关联</p>  
	 * @author Yml  
	 * @param userId
	 * @param roleIdArry  
	 */  
	void insertBatch(@Param("userId")Long userId, @Param("roleIdArry") String[] roleIdArry);
	
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
	List<Roles> getUserRoles(@Param("userId") Long userId);
}