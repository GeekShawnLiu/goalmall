package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.Roles;

public interface PermissionsService extends BaseService<Permissions> {

	/**  
	 * <p>Title: saveRole</p>  
	 * <p>Description: 保存权限菜单</p>  
	 * @author Yml  
	 * @param roles
	 * @return  
	 */  
	int savePermissions(Permissions permissions);

	/**  
	 * <p>Title: getChildrenById</p>  
	 * <p>Description: 获取所有的子节点</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	List<Permissions> getChildrenById(long id);

	/**  
	 * <p>Title: updatePermissions</p>  
	 * <p>Description: 更新权限菜单</p>  
	 * @author Yml  
	 * @param permissions
	 * @return  
	 */  
	int updatePermissions(Permissions permissions);

	/**  
	 * <p>Title: getSelectData</p>  
	 * <p>Description: 获取所有数据排除自身及子节点</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	List<Permissions> getSelectData(Long id);

	/**  
	 * <p>Title: getOntherChildrenById</p>  
	 * <p>Description: 根据id获取所有的子节点(不包括本身节点)</p>  
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
	Permissions findByUrl(String url);
	
	/**
	 * 查询角色可用权限
	 * @param roles
	 * @return
	 */
	List<Permissions> findUsableRolesPerms(List<Roles> roles);
	
	/**
	 * 转树节点
	 * @param list
	 * @return
	 */
	List<TreeNode> buildTree(List<Permissions> list);

}
