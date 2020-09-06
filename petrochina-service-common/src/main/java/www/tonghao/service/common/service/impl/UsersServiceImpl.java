package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.crypt.BCrypt;
import www.tonghao.common.enums.RoleCode;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.entity.UserSystem;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.UserSystemMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.service.common.service.RolesService;
import www.tonghao.service.common.service.UserRolesService;
import www.tonghao.service.common.service.UserSystemService;
import www.tonghao.service.common.service.UsersService;

@Service("usersService")
@Transactional
public class UsersServiceImpl extends BaseServiceImpl<Users> implements UsersService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UserSystemMapper userSystemMapper;
	
	@Autowired
	private UserSystemService userSystemService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private UserRolesService userRolesService;
	
	@Autowired
	private RedisDao redisDao;
	
	@Override
	public int updateIsDelte(Long id, Integer isDelete) {
		Users user = new Users();
		user.setId(id);
		user.setIsDelete(isDelete);
		int result = updateNotNull(user);
		return result;
	}

	@Override
	public int saveUser(Users user) {
		user.setIsDelete(0);
		String nowDate = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
		user.setCreatedAt(nowDate);
		user.setUpdatedAt(nowDate);
		//密码加密
		String password = user.getEncryptedPassword();
		if(password != null && !password.trim().equals("")){
			String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			user.setEncryptedPassword(encryptedPassword);
		}
		int result = saveSelective(user);
		return result;
	}
	
	@Transactional
	@Override
	public int saveUser(Users user, RoleCode defalutRoleCode) {
		int res=0;
		Roles roles = rolesService.findByCode(defalutRoleCode);
		if(roles!=null){
			res = saveUser(user);
			UserRoles entity = new UserRoles();
			entity.setRoleId(roles.getId());
			entity.setUserId(user.getId());
			userRolesService.save(entity );
		}
		return res;
	}

	@Override
	public long checkLoginName(Long id, String loginName) {
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("loginName", loginName);
		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("isDelete", 0);
		long result = usersMapper.selectCountByExample(example);
		return result;
	}

	@Override
	public HashMap<String, Object> checkAuthentication(String loginName, String password, String encryptedPassword) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Boolean status = false;//是否通过验证
		String msg = "";//验证提示信息
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("loginName", loginName);
		List<Users> list = usersMapper.selectByExample(example);
		if (list != null && list.size() == 0) {
			//密码检查
			status = BCrypt.checkpw(password, encryptedPassword);
		} else if(list != null && list.size() > 1) {
			
			msg = "账户异常，请联系管理员";
		} else {
			
			msg = "用户名或密码错误";
		}
		map.put("status", status);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Boolean checkPassword(Long id, String oldPassword) {
		Boolean result = false;
		if (id != null) {
			Users user = usersMapper.selectByPrimaryKey(id);
			if (user != null && user.getEncryptedPassword() != null) {
				result = BCrypt.checkpw(oldPassword, user.getEncryptedPassword());
			}
		}
		return result;
	}

	@Override
	public int resetPassword(Long id, String newPassword) {
		Users user = new Users();
		user.setId(id);
		//密码加密
		String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		user.setEncryptedPassword(encryptedPassword);
		user.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		return updateNotNull(user);
	}
	
	@Override
	public List<Permissions> getUserSystems(Long userId) {
		return userSystemMapper.getUserSystems(userId);
	}

	@Override
	public int saveUserSystems(Long userId, String systemIds) {
		if (userId != null && StringUtils.isNotBlank(systemIds)) {
			//删除旧的用户与系统关联数据
			UserSystem record = new UserSystem();
			record.setUserId(userId);
			userSystemService.deleteByRecord(record);
			
			//保存新的用户与系统关联数据
			String[] systemIdArry = systemIds.split(",");
			UserSystem userSystem = null;
			for (String systemIdStr : systemIdArry) {
				Long systemId = Long.parseLong(systemIdStr);
				userSystem = new UserSystem();
				userSystem.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				userSystem.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				userSystem.setPermissionId(systemId);
				userSystem.setUserId(userId);
				userSystemService.saveSelective(userSystem);
			}
			return 1;
		}else{
			return 0;
		}
	}
	
	@Override
	public Users findById(Long id) {
		return usersMapper.findById(id);
	}

	@Override
	public Users findByLoginName(String loginName, Integer type) {
		return usersMapper.findByLoginName(loginName, type);
	}
	
	/**
	 * 通过用户类型查找
	 */
	@Override
	public List<Long> findDeptIdByType(int type) {
		return usersMapper.findDeptIdByType(type);
	}

	@Override
	public Map<String, Object> vaUserInfo(Users user) {
		if(user.getLoginName() != null && !"".equals(user.getLoginName())){
			Example loginNameExample = new Example(Users.class);
			Criteria loginNameCriteria = loginNameExample.createCriteria();
			loginNameCriteria.andEqualTo("loginName", user.getLoginName());
			loginNameCriteria.andEqualTo("isDelete", 0);
			if (user.getId() != null) {
				loginNameCriteria.andNotEqualTo("id", user.getId());
			}
			long result = usersMapper.selectCountByExample(loginNameExample);
			if(result > 0){
				return ResultUtil.resultMessage(ResultUtil.ERROR, "登录名已存在");
			}
		}else{
			return ResultUtil.resultMessage(ResultUtil.ERROR, "登录名不能为空");
		}
		
		if(user.getRealName() == null || "".equals(user.getRealName())){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "姓名不能为空");
		}
		
		if(user.getMobile() == null || "".equals(user.getMobile())){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "手机号不能为空");
		}else{
			Example mobileExample = new Example(Users.class);
			Criteria mobileCriteria = mobileExample.createCriteria();
			mobileCriteria.andEqualTo("mobile", user.getMobile());
			mobileCriteria.andEqualTo("isDelete", 0);
			if (user.getId() != null) {
				mobileCriteria.andNotEqualTo("id", user.getId());
			}
			long result = usersMapper.selectCountByExample(mobileExample);
			if(result > 0){
				return ResultUtil.resultMessage(ResultUtil.ERROR, "手机号已存在");
			}
		}
		
		if(user.getEmail() == null || "".equals(user.getEmail())){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "邮箱不能为空");
		}
		
		/*if(user.getIdCard() == null || "".equals(user.getIdCard())){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "身份证号不能为空");
		}else{
			Example idCardExample = new Example(Users.class);
			Criteria idCardCriteria = idCardExample.createCriteria();
			idCardCriteria.andEqualTo("idCard", user.getIdCard());
			idCardCriteria.andEqualTo("isDelete", 0);
			if (user.getId() != null) {
				idCardCriteria.andNotEqualTo("id", user.getId());
			}
			long result = usersMapper.selectCountByExample(idCardExample);
			if(result > 0){
				return ResultUtil.resultMessage(ResultUtil.ERROR, "身份证号已存在");
			}
		}
		
		if(user.getCode() == null || "".equals(user.getCode())){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "员工编号不能为空");
		}else{
			Example codeExample = new Example(Users.class);
			Criteria codeCriteria = codeExample.createCriteria();
			codeCriteria.andEqualTo("code", user.getCode());
			codeCriteria.andEqualTo("isDelete", 0);
			if (user.getId() != null) {
				codeCriteria.andNotEqualTo("id", user.getId());
			}
			long result = usersMapper.selectCountByExample(codeExample);
			if(result > 0){
				return ResultUtil.resultMessage(ResultUtil.ERROR, "手机号已存在");
			}
		}*/
		return ResultUtil.resultMessage(ResultUtil.SUCCESS, "");
	}

	@Override
	public Map<String, Object> updatePassword(Users user) {
		if(null == user.getOldPassword() || null == user.getNewPassword() || null == user.getConfirmPassword()){
			return ResultUtil.error("密码不能为空");
		}else{
			if(!user.getNewPassword().equals(user.getConfirmPassword())){
				return ResultUtil.error("密码不一致，请重新输入");
			}else if(!checkPassword(user.getId(), user.getOldPassword())){
				return ResultUtil.error("密码输入错误");
			}else{
				String encryptedPassword = BCrypt.hashpw(user.getNewPassword(), BCrypt.gensalt());
				int updatePassword = usersMapper.updatePassword(user.getId(), encryptedPassword);
				if(updatePassword > 0){
					return ResultUtil.success("修改成功");
				}else{
					return ResultUtil.error("修改失败");
				}
			}
		}
	}

	@Override
	public Map<String, Object> updateMobile(Users user, String oldMobile) {
		if(null == user.getMobileCode()){
			return ResultUtil.error("验证码不能为空");
		}
		if(null == user.getNewMobile() || null == user.getMobile()){
			return ResultUtil.error("手机号不能为空");
		}
		if(!user.getMobile().equals(oldMobile)){
			return ResultUtil.error("旧手机号输入错误");
		}
		String redisCode = redisDao.getValue(user.getNewMobile()) + "";
		if (redisCode.equals(user.getMobileCode())) {
			Users u = new Users();
			u.setId(user.getId());
			u.setMobile(user.getNewMobile());
			int updateByPrimaryKeySelective = usersMapper.updateByPrimaryKeySelective(u);
			if(updateByPrimaryKeySelective > 0){
				return ResultUtil.success("修改成功");
			}else{
				return ResultUtil.error("修改失败");
			}
		}else{
			return ResultUtil.error("验证码输入错误");
		}
	}
}
