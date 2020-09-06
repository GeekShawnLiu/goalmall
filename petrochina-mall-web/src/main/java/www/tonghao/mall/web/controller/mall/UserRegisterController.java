package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.service.UserRegisterService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.MobileCodeService;
import www.tonghao.service.common.service.UsersService;

/**
 * @Description:会员注册
 * @date 2019年6月17日
 */
@Api(value="userRegisterController",description="用户注册")
@RestController
@RequestMapping("/userRegister")
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
	@Autowired
	private UsersService usersService;
	
	@Value("${smsurl}")
	private String smsurl;
	
	@ApiOperation(value="会员注册")
	@ApiImplicitParams({
		@ApiImplicitParam(name="code",value="员工编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="loginName",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobileCode",value="验证码",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="realName",value="姓名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="encryptedPassword",value="密码",required=false,dataType="string"),
		@ApiImplicitParam(name="confirmPassword",value="确认密码",required=false,dataType="string")
	})
	@PostMapping("/registerInfo")
	public Map<String,Object> registerInfo(@RequestBody Users user){
		/*Users user = new Users();
		user.setCode(code);
		user.setLoginName(loginName);
		user.setMobile(mobile);
		user.setMobileCode(mobileCode);
		user.setRealName(realName);
		user.setEmail(email);
		user.setEncryptedPassword(encryptedPassword);
		user.setConfirmPassword(confirmPassword);*/
		return userRegisterService.registerInfo(user);
	}
	
	@ApiOperation(value="获取验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(name="mobile",value="手机号",required=true,dataType="string")
	})
	@GetMapping("/getMobileCode")
	public Map<String,Object> getMobileCode(String mobile){
		if(StringUtils.isNotBlank(mobile)){
			Example example = new Example(Users.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("mobile", mobile);
			createCriteria.andEqualTo("isDelete", 0);
			List<Users> select = usersService.selectByExample(example);
			if(CollectionUtils.isNotEmpty(select)){
				return ResultUtil.error("手机号已被注册");
			}
		}
		return mobileCodeService.sendMobile(mobile, smsurl);
	}
}
