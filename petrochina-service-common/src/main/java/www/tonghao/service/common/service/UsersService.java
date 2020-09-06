package www.tonghao.service.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.tonghao.common.enums.RoleCode;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.Users;

public interface UsersService extends BaseService<Users> {

	/**  
	 * <p>Title: updateIsDelte</p>  
	 * <p>Description: 更新是否删除字段</p>  
	 * @author Yml  
	 * @param id
	 * @param isDelete
	 * @return  
	 */  
	int updateIsDelte(Long id, Integer isDelete);

	/**  
	 * <p>Title: saveUser</p>  
	 * <p>Description: 保存用户信息</p>  
	 * @author Yml  
	 * @param user
	 * @return  
	 */  
	int saveUser(Users user);
	
	/**
	 * 保存角色
	 * @param user
	 * @param defalutRoleCode
	 * @return
	 */
	int saveUser(Users user,RoleCode defalutRoleCode);

	/**  
	 * <p>Title: checkLoginName</p>  
	 * <p>Description: 校验用户名</p>  
	 * @author Yml  
	 * @param id
	 * @param loginName
	 * @return  
	 */  
	long checkLoginName(Long id, String loginName);
	
	/**  
	 * <p>Title: checkAuthentication</p>  
	 * <p>Description: 登录校验</p>  
	 * @author Yml  
	 * @param loginName
	 * @param password
	 * @param encryptedPassword
	 * @return  
	 */  
	HashMap<String, Object> checkAuthentication(String loginName, String password, String encryptedPassword);

	/**  
	 * <p>Title: checkPassword</p>  
	 * <p>Description: 检查用户密码</p>  
	 * @author Yml  
	 * @param id
	 * @param oldPassword
	 * @return  
	 */  
	Boolean checkPassword(Long id, String oldPassword);

	/**  
	 * <p>Title: resetPassword</p>  
	 * <p>Description: 用户重置密码</p>  
	 * @author Yml  
	 * @param id
	 * @param encryptedPassword
	 * @return  
	 */  
	int resetPassword(Long id, String newPassword);

	/**  
	 * <p>Title: getUserSystems</p>  
	 * <p>Description: 获取用户可授权系统列表</p>  
	 * @author Yml  
	 * @param userId
	 * @return  
	 */  
	List<Permissions> getUserSystems(Long userId);

	/**  
	 * <p>Title: saveUserSystems</p>  
	 * <p>Description: 保存用户系统授权</p>  
	 * @author Yml  
	 * @param userId
	 * @param systemIds
	 * @return  
	 */  
	int saveUserSystems(Long userId, String systemIds);

	/**
	 * 根据用户名查找
	 * @param id
	 * @return
	 */
	Users findById(Long id);
	
	/**
	 * 根据用户名查找
	 * @param loginName
	 * @return
	 */
	Users findByLoginName(String loginName, Integer type);
	
	/**
	 * 通过用户类型查找组织机构id
	 * @param type 用户类型：1：采购人，2：集采机构，3：监管机构，4：供应商:5：专家、
	 * @return
	 */
	List<Long> findDeptIdByType(int type);
	
	/**
	 * 
	 * Description: 校验用户信息
	 * 
	 * @data 2019年5月31日
	 * @param 
	 * @return
	 */
	Map<String, Object> vaUserInfo(Users user);
	
	/**
	 * 
	 * Description: 修改密码
	 * 
	 * @data 2019年6月14日
	 * @param 
	 * @return
	 */
	Map<String, Object> updatePassword(Users user);
	
	/**
	 * 
	 * Description: 修改手机号
	 * 
	 * @data 2019年6月14日
	 * @param 
	 * @return
	 */
	Map<String, Object> updateMobile(Users user, String oldMobile);
}
