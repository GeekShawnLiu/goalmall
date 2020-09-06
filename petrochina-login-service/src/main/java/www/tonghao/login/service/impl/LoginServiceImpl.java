package www.tonghao.login.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import www.tonghao.common.constant.SmSConstant;
import www.tonghao.common.crypt.BCrypt;
import www.tonghao.common.jwt.TokenAuthenticationService;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.HttpClient;
import www.tonghao.common.utils.IpAddressUtil;
import www.tonghao.common.utils.NumUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.common.warpper.LoginForm;
import www.tonghao.login.service.LoginService;
import www.tonghao.login.util.RSAEncrypt;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.SysOperateLogs;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.OrganizationMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.mapper.SysOperateLogsMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.utils.UserUtil;

import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.databind.JsonNode;

@Service(value="loginService")
public class LoginServiceImpl implements LoginService {

	@Value("${smsurl}")
	private String smsurl;
	
	@Value("${login.private.key}")
	private String privateKey;
	
	//验证码过期时间 秒
	public static final long MOBILE_CODE_TIMEOUT_MILLISECONDS = 2 * 60 * 1000;
	
	//验证码过期时间 分钟
	public static final int MOBILE_CODE_TIMEOUT_MINUTES = 2;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private SysOperateLogsMapper sysOperateLogsMapper;
	
	@Autowired
	private OrganizationMapper organizationMapper;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Override
	public Map<String, Object> sendMobile(String mobile, String isResetPassword) {
		if(StringUtils.isBlank(mobile)){
			return ResultUtil.error("手机号不能为空");
		}
		List<Users> list = usersMapper.findByMobile(mobile);
		if("1".equals(isResetPassword)){
			//重置密码
			if(CollectionUtils.isEmpty(list)){
				return ResultUtil.error("用户不存在");
			}else if(list.size() > 1){
				return ResultUtil.error("手机号重复");
			}
		}else if("2".equals(isResetPassword)){
			//修改手机号
			if(!CollectionUtils.isEmpty(list)){
				return ResultUtil.error("手机号已存在");
			}
		}else if("3".equals(isResetPassword)){
			//登录验证码
			if(CollectionUtils.isEmpty(list)){
				return ResultUtil.error("用户不存在");
			}else if(list.size() > 1){
				return ResultUtil.error("手机号重复");
			}
		}else{
			return ResultUtil.error("参数错误");
		}
		if(redisDao.isNotKey(mobile)){
			return ResultUtil.error("验证码已发送，请稍后再试");
		}
		//获取验证码
		String code = NumUtil.createIdentifyCode(6);
		
		//发送短信
		//SendSmsApi.SendCode(code, MOBILE_CODE_TIMEOUT_MINUTES, mobile);
		Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", mobile);
		map.put("params", code+",1");
		map.put("template", SmSConstant.LOGIN_TEMPLATE_ID);
	   
		//验证码缓存
		String doGet = HttpClient.doGet(SmSConstant.SMSURL, map);
		System.out.println(doGet);
		JsonNode rootNode = JsonUtil.readTree(doGet);
		String result_code = rootNode.path("code").asText();
		if(!"85002".equals(result_code)) {
			redisDao.setKey(mobile, code, MOBILE_CODE_TIMEOUT_MILLISECONDS);
			return ResultUtil.success("短信已发送");
		}else {
			return ResultUtil.error("短信发送失败");
		}
		
	}

	@Override
	public Map<String, Object> loginByMobile(LoginForm loginForm, HttpServletRequest request) {
		String mobile = loginForm.getMobile();
		String code = loginForm.getCode();
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(code)){
			return ResultUtil.error("手机号和验证码不能为空");
		}
		String password2 = loginForm.getPassword2();
		String confirmPassword = loginForm.getConfirmPassword();
		if(password2 == null || confirmPassword == null){
			return ResultUtil.error("密码不能为空");
		}
		try {
			password2 = RSAEncrypt.decrypt(privateKey, password2.replaceAll("VUEVUE", "+"));
			confirmPassword = RSAEncrypt.decrypt(privateKey, confirmPassword.replaceAll("VUEVUE", "+"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		loginForm.setPassword2(password2);
		loginForm.setConfirmPassword(confirmPassword);
		if(StringUtils.isBlank(loginForm.getPassword2()) || StringUtils.isBlank(loginForm.getConfirmPassword())){
			return ResultUtil.error("密码不能为空");
		}else{
			if(!loginForm.getPassword2().equals(loginForm.getConfirmPassword())){
				return ResultUtil.error("密码不一致，请重新输入");
			}
		}
		List<Users> list = usersMapper.findByMobile(mobile);
		if(CollectionUtils.isEmpty(list)){
			return ResultUtil.error("用户不存在");
		}else if(list.size() > 1){
			return ResultUtil.error("手机号重复");
		}
		if(!redisDao.isNotKey(mobile)){
			return ResultUtil.error("验证码已过期，请重新发送");
		}
		Users user = list.get(0);
		if(user != null){
			String redisCode = redisDao.getValue(mobile) + "";
			if (!code.equals(redisCode)) {
				return ResultUtil.error("验证码输入错误");
			}else{
				String encryptedPassword = BCrypt.hashpw(loginForm.getConfirmPassword(), BCrypt.gensalt());
				usersMapper.updatePassword(user.getId(), encryptedPassword);
				user.setEncryptedPassword(encryptedPassword);
				return saveToken(user, request, loginForm.getLoginSource());
			}
		}else{
			return ResultUtil.error("登录失败,用户不存在");
		}
	}

	@Override
	public Map<String, Object> loginByLoginName(LoginForm loginForm, HttpServletRequest request) {
		if(StringUtils.isBlank(loginForm.getPassword())||StringUtils.isBlank(loginForm.getUsername())){
			return ResultUtil.error("登录账号和密码不能为空");
		}
		List<Users> findByLogin = usersMapper.findByLogin(loginForm.getUsername(), loginForm.getType());
		if(CollectionUtils.isNotEmpty(findByLogin) && findByLogin.size() == 1){
			Users user = findByLogin.get(0);
			String hashed = user.getEncryptedPassword();
			if(StringUtils.isBlank(hashed)){
				return ResultUtil.error("密码输入错误");
			}
			String pwd = null;
			try {
				pwd = RSAEncrypt.decrypt(privateKey, loginForm.getPassword().replaceAll("VUEVUE", "+"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pwd == null){
				return ResultUtil.error("密码输入错误");
			}
			if (!BCrypt.checkpw(pwd, hashed)) {
				return ResultUtil.error("密码输入错误");
			}else{
				return saveToken(user, request, loginForm.getLoginSource());
			}
		}else{
			return ResultUtil.error("登录失败,用户"+loginForm.getUsername()+"不存在");
		}
	}
	
	@Override
	public Map<String, Object> check(HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if (user != null) {
			Users loginUser = new Users();
			loginUser.setId(user.getId());
			loginUser.setRealName(user.getRealName());
			loginUser.setType(user.getType());
			Users selectByPrimaryKey = usersMapper.selectByPrimaryKey(user.getId());
			if(selectByPrimaryKey != null){
				loginUser.setHeadPortraitPath(selectByPrimaryKey.getHeadPortraitPath());
			}
			
			//供应商
			if(user.getSupplier() !=null){
				Suppliers supplier = suppliersMapper.selectByPrimaryKey(user.getSupplier().getId());
				loginUser.setSupplier(supplier);
			}
			
			Map<String, Object> loginUserMap = Maps.newHashMap();
			loginUserMap.put("loginUser", loginUser);
			//查询商城logo
			Organization org = getSecondLevelOrg(user.getDepId());
			loginUserMap.put("mallLogo", org == null ? null : org.getLogoPath());
			return ResultUtil.resultMessage(HttpResponseCode.OK, "", loginUserMap);
		}
		return ResultUtil.resultMessage(HttpResponseCode.UNAUTHORIZED, "没有登录");
	}

	@Override
	public Map<String, Object> logout(HttpServletRequest request) {
		String key = request.getHeader("token");
		if(key!=null){
			//清除Redis缓存的token信息
			String ipAddress = IpAddressUtil.getIpAddress(request);
			redisDao.deleteKey(key+"@"+ipAddress);
			return ResultUtil.success("");
		}
		return ResultUtil.error("没有获取到token");
	}

	@Override
	public void loginSuccessEvent(Users user, String loginSource) {
		String currDate = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
		user.setSignInCount((user.getSignInCount() == null ? 0 : user.getSignInCount())+1);
		user.setLastSignInAt(user.getCurrentSignInAt());
		user.setLastSignInIp(user.getCurrentSignInIp());
		user.setCurrentSignInAt(currDate);
		usersMapper.updateByPrimaryKeySelective(user);
		
		String desc = user.getLoginName()+"于"+currDate+"登入系统。";
		SysOperateLogs log = new SysOperateLogs();
		log.setCreatedAt(currDate);
		log.setUpdatedAt(currDate);
		log.setLogType((byte)2);
		log.setDescription(desc);
		log.setEvent(loginSource == null ? "1" : loginSource);
		log.setIp(user.getCurrentSignInIp());
		log.setDription(desc);
		log.setLoginName(user.getLoginName());
		log.setUserId(user.getId());
		log.setOperatorDep(user.getDepName());
		sysOperateLogsMapper.add(log);
	}
	
	/**
	 * 
	 * Description: 获取保存token
	 * 
	 * @data 2019年5月6日
	 * @param 
	 * @return
	 */
	public Map<String, Object> saveToken(Users user, HttpServletRequest request, String loginSource){
		try {
			String token = TokenAuthenticationService.addAuthentication(user.getLoginName());
			user.setCurrentSignInIp(IpAddressUtil.getIpAddress(request));
			loginSuccessEvent(user, loginSource);
			user= UserUtil.currentLoginUser(user.getId());
			String ipAddress = IpAddressUtil.getIpAddress(request);
			redisDao.setKey(token + "@" + ipAddress, user, TokenAuthenticationService.EXPIRATIONTIME);
			return ResultUtil.success(token);
		} catch (JSONException e) {
			return ResultUtil.error("登录失败,系统异常");
		}
	} 
	
	/**
	 * 
	 * Description: 查询二级机构
	 * 
	 * @data 2019年7月1日
	 * @param 
	 * @return
	 */
	public Organization getSecondLevelOrg(Long orgId){
		if(orgId == null){
			return null;
		}
		Organization organization = organizationMapper.selectByPrimaryKey(orgId);
		if(organization == null || organization.getTreeDepth() <= 2){
			return organization;
		}else{
			return getSecondLevelOrg(organization.getParentId());
		}
	}

	@Override
	public String loginRuiXin(Users user, HttpServletRequest request,
			String loginSource) {
		Map<String, Object> saveToken = saveToken(user, request, loginSource);
		if(saveToken.get("status").toString().equals("success")){
			return saveToken.get("message").toString();
		}
		return null;
	}

	@Override
	public Map<String, Object> loginByMobileCode(LoginForm loginForm, HttpServletRequest request) {
		String mobile = loginForm.getMobile();
		String code = loginForm.getCode();
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(code)){
			return ResultUtil.error("手机号和验证码不能为空");
		}
		List<Users> list = usersMapper.findByMobile(mobile);
		if(CollectionUtils.isEmpty(list)){
			return ResultUtil.error("用户不存在");
		}else if(list.size() > 1){
			return ResultUtil.error("手机号重复");
		}
		if(!redisDao.isNotKey(mobile)){
			return ResultUtil.error("验证码已过期，请重新发送");
		}
		Users user = list.get(0);
		if(user != null){
			String redisCode = redisDao.getValue(mobile) + "";
			if (!code.equals(redisCode)) {
				return ResultUtil.error("验证码输入错误");
			}else{
				return saveToken(user, request, loginForm.getLoginSource());
			}
		}else{
			return ResultUtil.error("登录失败,用户不存在");
		}
	
	}
}
