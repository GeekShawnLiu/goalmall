package www.tonghao.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.crypt.BCrypt;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.mall.service.UserRegisterService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.OrganizationMapper;
import www.tonghao.service.common.mapper.RolesMapper;
import www.tonghao.service.common.mapper.UserRolesMapper;
import www.tonghao.service.common.mapper.UsersMapper;

@Service("userRegisterService")
public class UserRegisterServiceImpl extends BaseServiceImpl<Users> implements UserRegisterService{

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private OrganizationMapper organizationMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private RolesMapper rolesMapper;
	
	@Autowired
	private UserRolesMapper userRolesMapper;
	
	@Override
	public Map<String, Object> registerInfo(Users user) {
		Map<String, Object> checkInfo = checkInfo(user);
		if(checkInfo.get("status").equals("success")){
			String encryptedPassword = BCrypt.hashpw(user.getEncryptedPassword(), BCrypt.gensalt());
			user.setEncryptedPassword(encryptedPassword);
			user.setCreatedAt(DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			user.setType(1);//1采购人
			//会员注册默认--C端用户机构
			Example example = new Example(Organization.class);
			
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("name", "C端用户机构");
			createCriteria.andEqualTo("isDelete", 0);
			List<Organization> selectByExample = organizationMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(selectByExample)){
				user.setTypeId(selectByExample.get(0).getId());
				user.setDepId(selectByExample.get(0).getId());
				user.setDepName(selectByExample.get(0).getName());
			}
			int result = usersMapper.insertSelective(user);
			if(result > 0){
				Example roleExample = new Example(Roles.class);
				Criteria createCriteria2 = roleExample.createCriteria();
				createCriteria2.andEqualTo("code", "CGR");
				List<Roles> roleList = rolesMapper.selectByExample(roleExample);
				if(CollectionUtils.isNotEmpty(roleList)){
					Roles roles = roleList.get(0);
					UserRoles ur=new UserRoles();
					ur.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					ur.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					ur.setRoleId(roles.getId());
					ur.setUserId(user.getId());
					userRolesMapper.insertSelective(ur);
				}
			}
			return ResultUtil.resultMessage(result, "");
		}
		return checkInfo;
	}
	
	public Map<String, Object> checkInfo(Users user){
		if(StringUtils.isNotBlank(user.getCode())){
			Users u = new Users();
			u.setCode(user.getCode());
			List<Users> select = usersMapper.select(u);
			if(CollectionUtils.isNotEmpty(select)){
				return ResultUtil.error("员工编号被注册");
			}
		}else{
			return ResultUtil.error("请填写员工编号");
		}
		
		if(StringUtils.isNotBlank(user.getLoginName())){
			Users u = new Users();
			u.setLoginName(user.getLoginName());
			List<Users> select = usersMapper.select(u);
			if(CollectionUtils.isNotEmpty(select)){
				return ResultUtil.error("用户名已被注册");
			}
		}else{
			return ResultUtil.error("请填写用户名");
		}
		
		if(StringUtils.isNotBlank(user.getMobile())){
			boolean moblie = ValidatorUtil.isMoblie(user.getMobile());
			if(!moblie){
				return ResultUtil.error("手机号格式不正确");
			}
			
			Users u = new Users();
			u.setMobile(user.getMobile());
			List<Users> select = usersMapper.select(u);
			if(CollectionUtils.isNotEmpty(select)){
				return ResultUtil.error("手机号已被注册");
			}
		}else{
			return ResultUtil.error("请填写手机号");
		}
		
		if(StringUtils.isBlank(user.getMobileCode())){
			return ResultUtil.error("请填写验证码");
		}else{
			if(!redisDao.isNotKey(user.getMobile())){
				return ResultUtil.error("验证码已过期，请重新发送");
			}
			String redisCode = redisDao.getValue(user.getMobile()) + "";
			if(!user.getMobileCode().equals(redisCode)){
				return ResultUtil.error("验证码输入错误");
			}
		}
		
		if(StringUtils.isBlank(user.getRealName())){
			return ResultUtil.error("请填写姓名");
		}
		
		if(StringUtils.isNotBlank(StringUtil.strTrim(user.getEmail()))){
			boolean email = ValidatorUtil.isEmail(user.getEmail());
			if(!email){
				return ResultUtil.error("邮箱格式不正确");
			}
			
			Users u = new Users();
			u.setEmail(user.getEmail());
			List<Users> select = usersMapper.select(u);
			if(CollectionUtils.isNotEmpty(select)){
				return ResultUtil.error("邮箱已被注册");
			}
		}/*else{
			return ResultUtil.error("请填写邮箱");
		}*/
		
		String encryptedPassword = user.getEncryptedPassword();
		String confirmPassword = user.getConfirmPassword();
		boolean flag = true;
		if(StringUtils.isBlank(encryptedPassword)){
			flag = false;
			return ResultUtil.error("请填写密码");
		}
		if(StringUtils.isBlank(confirmPassword)){
			flag = false;
			return ResultUtil.error("请填写确认密码");
		}
		if(flag && !encryptedPassword.equals(confirmPassword)){
			return ResultUtil.error("两次密码不一致");
		}
		
		return ResultUtil.success("");
	}

}
