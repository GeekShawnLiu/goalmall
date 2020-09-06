package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Users;



public interface UsersMapper extends BaseMapper<Users> {
	
	/**
	 * 根据登录名查询
	 * @param id
	 * @return
	 */
	Users findById(Long id);
	
	/**
	 * 根据登录名查询
	 * @param loginName
	 * @return
	 */
	Users findByLoginName(@Param("loginName")String loginName, @Param("type")Integer type);
	
	/**
	 * 通过用户类型查找组织机构id
	 * @param type 用户类型：1：采购人，2：集采机构，3：监管机构，4：供应商:5：专家、
	 * @return
	 */
	List<Long> findDeptIdByType(int type);
	
	/**
	 * 
	 * Description: 根据手机号查询
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	List<Users> findByMobile(@Param("mobile")String mobile);
	
	/**
	 * 
	 * Description: 修改密码
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	int updatePassword(@Param("id")Long id, @Param("encryptedPassword")String encryptedPassword);
	
	/**
	 * 
	 * Description: 用户名/手机号  登录查询
	 * 
	 * @data 2019年9月3日
	 * @param 
	 * @return
	 */
	List<Users> findByLogin(@Param("loginName")String loginName, @Param("type")Integer type);
}