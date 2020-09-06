package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Roles;


public interface RolesMapper extends BaseMapper<Roles> {
	
	/**
	 * 得到用户角色
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
}