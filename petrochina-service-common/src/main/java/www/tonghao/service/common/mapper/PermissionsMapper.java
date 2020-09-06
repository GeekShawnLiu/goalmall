package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.Roles;

public interface PermissionsMapper extends BaseMapper<Permissions> {

	/**  
	 * <p>Title: getChildrenById</p>  
	 * <p>Description: 根据id 获取所有的子节点，传入0 则获取所有</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	List<Permissions> getChildrenById(long id);

	/**  
	 * <p>Title: getSelectData</p>  
	 * <p>Description: 获取数据排除自身及子节点</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	List<Permissions> getSelectData(@Param("id") Long id);

	/**  
	 * <p>Title: getOtherChildrenById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	List<Permissions> getOtherChildrenById(long id);
	
	/**
	 * 根据Url查询单个
	 * @param url
	 * @return
	 */
	Permissions findByUrl(@Param(value="url") String url);
	
	/**
	 * 查询角色可用的权限
	 * @param roles
	 * @return
	 */
	List<Permissions> findUsableRolesPerms(@Param("roles")List<Roles> roles);
}