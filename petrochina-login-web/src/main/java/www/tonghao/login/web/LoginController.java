package www.tonghao.login.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.jwt.TokenAuthenticationService;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.common.warpper.LoginForm;
import www.tonghao.login.service.LoginService;
import www.tonghao.mall.api.utils.HttpClient;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.OrganizationService;
import www.tonghao.service.common.service.RolesService;
import www.tonghao.service.common.service.UserRolesService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.util.RuiXinAPIBroker;
import www.tonghao.util.TokenApi;
import www.tonghao.util.UserApi;

import com.cnpc.ruixin.sdk.RuiXinAPI;
import com.cnpc.ruixin.sdk.auth.AuthAPI;
import com.cnpc.ruixin.sdk.auth.UserInfo;
import com.cnpc.ruixin.sdk.envirenment.EnvType;
import com.fasterxml.jackson.databind.JsonNode;

@Api(value="loginController",description="登录管理api")
@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UserRolesService userRolesService;
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private RolesService rolesService;
	
	@Value("${spring.profiles.active}")
	private String active;
	
	@ApiOperation(value = "发送手机验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(name="mobile", value="手机号", required=true, dataType="String", paramType="query"),
		@ApiImplicitParam(name="isResetPassword", value="标识 1：重置密码   2：更换手机号   3：登录验证码", required=false, dataType="String", paramType="query"),
	})
	@RequestMapping(value="/login/sendMobileCode", method=RequestMethod.POST)
	public Map<String,Object> sendMobileCode(String mobile, String isResetPassword, HttpServletRequest request){
		return loginService.sendMobile(mobile, isResetPassword);
	}
	
	@ApiOperation(value = "用户登录请求")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Map<String,Object> dologin(LoginForm loginForm, HttpServletRequest request){
		Integer loginWay = loginForm.getLoginWay();
		if(loginWay == null){
			return ResultUtil.error("请选择登录方式");
		}
		if(loginWay == 1){
			//用户名密码登陆
			return loginService.loginByLoginName(loginForm, request);
		}else if(loginWay == 2){
			//忘记密码--手机号修改密码并登录
			return loginService.loginByMobile(loginForm, request);
		}else if(loginWay == 3){
			//手机--验证码登录
			return loginService.loginByMobileCode(loginForm, request);
		}else{
			return ResultUtil.error("参数错误");
		}
	}
	
	@ApiOperation(value = "登录检测")
	@RequestMapping(value = "/login/check", method = RequestMethod.GET)
	public Map<String, Object> check(HttpServletRequest request) {
		return loginService.check(request);
	}
	
	@ApiOperation(value = "退出登录")
	@RequestMapping(value = "/logout",method=RequestMethod.POST)
	public Map<String,Object> logout(HttpServletRequest request) {
		return loginService.logout(request);
	}

	@ApiOperation(value = "部署环境标识")
	@RequestMapping(value = "/getActive",method=RequestMethod.GET)
	public Map<String, Object> getActive() {
		if(active == null){
			return ResultUtil.error("系统异常");
		}
		return ResultUtil.success(active);
	}
	
	@ApiOperation(value = "瑞信接口登录验证")
	@RequestMapping(value = "/login/ruixin",method=RequestMethod.GET)
	public Map<String, Object> ruixin(HttpServletRequest request,String token) {
		String ruixin="http://11.11.146.84:8080/MiddleWhatService/api/auth";
		//OutputStreamWriter out=null;
		try {
			Map<String, String> ruixinPost = ruixinPost(token);
			String status = ruixinPost.get("status");
			if("success".equals(status)) {
				String email = ruixinPost.get("email");
				Users users=new Users();
				users.setEmail(email);
				users.setIsDelete(0);
				Users user = usersService.selectEntityOne(users);
				if(user!=null) {
					String loginRuiXin = loginService.loginRuiXin(user, request, "2");
					return ResultUtil.success(loginRuiXin);
				}else {
					return ResultUtil.error("此账号未开通中油惠服服务！");
				}
			}else {
				return ResultUtil.error("登录接口请求异常，请联系管理员！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.error("登录接口请求异常，请联系管理员！");
		
	}
	
	@ApiOperation(value = "党建接口登录验证")
	@RequestMapping(value = "/login/djApp",method=RequestMethod.GET)
	public Map<String, Object> djApp(HttpServletRequest request,String token) {
		try {
			Map<String, String> djAppPost = djAppPost(token);
			if(djAppPost!=null) {
				String sap = djAppPost.get("sap");
				Users users=new Users();
				users.setCode(sap);
				users.setIsDelete(0);
				Users user = usersService.selectEntityOne(users);
				if(user!=null) {
					String loginRuiXin = loginService.loginRuiXin(user, request, "2");
					return ResultUtil.success(loginRuiXin);
				}else {
					String date = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
					Organization orge=new Organization();
					orge.setName("C端用户机构");
					Organization org = organizationService.selectEntityOne(orge);
					if(org!=null){
						Users use=new Users();
						use.setLoginName(djAppPost.get("phone"));
						use.setRealName(djAppPost.get("userName"));
						use.setMobile(djAppPost.get("phone"));
						use.setEmail(djAppPost.get("email"));
						use.setCode(djAppPost.get("sap"));
						use.setCreatedAt(date);
						use.setUpdatedAt(date);
						use.setType(1);
						use.setTypeId(org.getId());
						use.setDepId(org.getId());
						use.setDepName(org.getName());
						use.setIsDelete(0);
						int save = usersService.saveSelective(use);
						if(save>0){
							Roles entity=new Roles();
							entity.setCode("CGR");
							Roles roles = rolesService.selectEntityOne(entity);
							if(roles!=null){
								UserRoles ur=new UserRoles();
								ur.setCreatedAt(date);
								ur.setUpdatedAt(date);
								ur.setRoleId(roles.getId());
								ur.setUserId(use.getId());
								int user_role = userRolesService.save(ur);
								if(user_role>0){
									String loginRuiXin = loginService.loginRuiXin(use, request, "2");
									return ResultUtil.success(loginRuiXin);
								}
							}
						}
					}
					TokenApi tokenApi=new TokenApi();
					Map<String, String> tokenMap = tokenApi.getToken();
					if(tokenMap!=null){
						String djToken = tokenMap.get("token");
						String validateKey = tokenMap.get("validateKey");
						UserApi api=new UserApi();
						Map<String, String> userMap = api.getUser(djToken, validateKey);
					}else{
						System.out.println("token获取失败");
					}
				}
			}else {
				return ResultUtil.error("登录验证请求异常，请联系管理员！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.error("登录验证请求异常，请联系管理员！");
		
	}

	private boolean vaLoginInfo(String k,String v) {
		Example example=new Example(Users.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo(k, v);
		List<Users> loginName_list = usersService.selectByExample(example);
		if(!CollectionUtils.isEmpty(loginName_list)) {
			return true;
		}
		return false;
	}

	private Map<String, String> ruixinPost(String token)
			throws MalformedURLException, IOException, ProtocolException, UnsupportedEncodingException {
		Map<String, String> map=new HashMap<>();//80000274,pLyAokEW
		RuiXinAPI ruiXinAPI = RuiXinAPIBroker.getRuiXinAPI("30000028", "Nx2A3ali", EnvType.RX_PROD);
		//2. 获取认证服务api
		AuthAPI authAPI = ruiXinAPI.getAuthAPI();
		UserInfo ui = null;
		try {
			//3. 根据用户token获取用户信息
			ui = authAPI.getUserInfoByToken(token);
			System.out.println(JsonUtil.toJson(ui));
			if (null == ui) {
				//token验证失败
				map.put("status", "error");
			}else {
				map.put("status", "success");
				map.put("userName", ui.getUserName());//用户真实姓名
				map.put("email", ui.getEmail());//邮箱
				map.put("officeMobile", ui.getOfficeMobile());//手机号
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String, String> djAppPost(String token){
		Map<String, String> map=new HashMap<>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("rxToken", token);
		params.put("djToken", "");
		params.put("source", "2");
		String doPost = HttpClient.post("http://m.dj.cnpc.com.cn/party/fundamental/inner/userinfo/getForRx", params);
		//String doPost = HttpClient.post("http://210.12.209.212:10031/party/fundamental/inner/userinfo/getForRx", params);
		JsonNode readTree = JsonUtil.readTree(doPost);
	    if(readTree.path("success").asBoolean()){
	    	JsonNode data = readTree.path("data");
	    	String userName = data.path("userName").asText();
	    	if(!StringUtil.strIsEmpty(userName)){
	    		map.put("userName", userName);
	    	}
	    	String  email= data.path("email").asText();
	    	if(!StringUtil.strIsEmpty(email)){
	    		map.put("email", email);
	    	}
	    	String phone = data.path("phone").asText();
	    	if(!StringUtil.strIsEmpty(phone)){
	    		map.put("phone", phone);
	    	}
	    	String sap = data.path("sap").asText();
	    	if(!StringUtil.strIsEmpty(sap)){
	    		map.put("sap", sap);
	    	}
	    }
	    return map;
	}
	
	public static void main(String[] args) {
		//{"success":true,"errorCode":"200","message":"ok","data":{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":"269429508537468240","tenantId":"999999999","account":null,"userName":"高玉洁","email":null,"phone":"13500000139","fileId":null,"status":0,"orgId":"245849199567595973","orgName":"私有化三组党支部","unitName":null,"loginNum":null,"lastLoginTime":null,"personType":0,"createUserid":157419402729374176,"createTime":1547437000592,"updateUserid":157419402729374176,"updateTime":1547437000592,"isDelete":0,"isTrouble":0,"loginIp":null,"sap":"T0254122","idCard":"465292199203053460","orgCode":"000.001.999.005.006","layerCode":null,"canAddFriend":0,"isOpen":1,"canOpenPhone":0,"canOpenEmail":0,"canOpenTel":0,"canOpenFriend":0,"groupList":[],"roleList":[{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":"32","tenantId":"999999999","rolename":"党员","description":null,"roleCode":"dy","isEdit":0,"status":0,"createUserid":null,"createTime":1527716437789,"updateUserid":null,"updateTime":1527716437789,"isDelete":0,"layerCode":"3","layerName":null,"datapermId":0,"datapermDesc":null,"perCode":null,"operation":0,"roleList":null,"treeType":null,"orgName":"私有化三组党支部","orgId":"245849199567595973","orgCode":"000.001.999.005.006","userId":null,"rankWeight":1,"isChecked":null,"permCode":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":"22","tenantId":"999999999","rolename":"党支部书记","description":null,"roleCode":"dzbsj","isEdit":0,"status":0,"createUserid":null,"createTime":1527716437785,"updateUserid":157419402729374176,"updateTime":1528770357364,"isDelete":0,"layerCode":"3","layerName":null,"datapermId":0,"datapermDesc":null,"perCode":null,"operation":0,"roleList":null,"treeType":null,"orgName":"私有化三组党支部","orgId":"245849199567595973","orgCode":"000.001.999.005.006","userId":null,"rankWeight":40,"isChecked":null,"permCode":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","id":"23","tenantId":"999999999","rolename":"党支部管理员","description":null,"roleCode":"dzbgly","isEdit":0,"status":0,"createUserid":null,"createTime":1527716437804,"updateUserid":null,"updateTime":1527716437804,"isDelete":0,"layerCode":"3","layerName":null,"datapermId":0,"datapermDesc":null,"perCode":null,"operation":0,"roleList":null,"treeType":null,"orgName":"私有化三组党支部","orgId":"245849199567595973","orgCode":"000.001.999.005.006","userId":null,"rankWeight":-1,"isChecked":null,"permCode":null}],"userList":null,"operation":0,"orgIdEach":null,"memberId":null,"member":null,"urList":null,"token":null,"telephone":null,"dutyLevel":null,"uuid":null,"headImg":null,"friendType":null,"isCollection":null,"oldOrgId":null,"roleName":null,"maxOrgCode":null,"bizType":null,"realName":null}}
		Map<String, String> map=new HashMap<>();
		JsonNode readTree = JsonUtil.readTree("{\"success\":true,\"errorCode\":\"200\",\"message\":\"ok\",\"data\":{\"pageNo\":1,\"pageSize\":10,\"orderBy\":\"id desc\",\"id\":\"269429508537468240\",\"tenantId\":\"999999999\",\"account\":null,\"userName\":\"高玉洁\",\"email\":null,\"phone\":\"13500000139\",\"fileId\":null,\"status\":0,\"orgId\":\"245849199567595973\",\"orgName\":\"私有化三组党支部\",\"unitName\":null,\"loginNum\":null,\"lastLoginTime\":null,\"personType\":0,\"createUserid\":157419402729374176,\"createTime\":1547437000592,\"updateUserid\":157419402729374176,\"updateTime\":1547437000592,\"isDelete\":0,\"isTrouble\":0,\"loginIp\":null,\"sap\":\"T0254122\",\"idCard\":\"465292199203053460\",\"orgCode\":\"000.001.999.005.006\",\"layerCode\":null,\"canAddFriend\":0,\"isOpen\":1,\"canOpenPhone\":0,\"canOpenEmail\":0,\"canOpenTel\":0,\"canOpenFriend\":0,\"groupList\":[],\"roleList\":[{\"pageNo\":1,\"pageSize\":10,\"orderBy\":\"id desc\",\"id\":\"32\",\"tenantId\":\"999999999\",\"rolename\":\"党员\",\"description\":null,\"roleCode\":\"dy\",\"isEdit\":0,\"status\":0,\"createUserid\":null,\"createTime\":1527716437789,\"updateUserid\":null,\"updateTime\":1527716437789,\"isDelete\":0,\"layerCode\":\"3\",\"layerName\":null,\"datapermId\":0,\"datapermDesc\":null,\"perCode\":null,\"operation\":0,\"roleList\":null,\"treeType\":null,\"orgName\":\"私有化三组党支部\",\"orgId\":\"245849199567595973\",\"orgCode\":\"000.001.999.005.006\",\"userId\":null,\"rankWeight\":1,\"isChecked\":null,\"permCode\":null},{\"pageNo\":1,\"pageSize\":10,\"orderBy\":\"id desc\",\"id\":\"22\",\"tenantId\":\"999999999\",\"rolename\":\"党支部书记\",\"description\":null,\"roleCode\":\"dzbsj\",\"isEdit\":0,\"status\":0,\"createUserid\":null,\"createTime\":1527716437785,\"updateUserid\":157419402729374176,\"updateTime\":1528770357364,\"isDelete\":0,\"layerCode\":\"3\",\"layerName\":null,\"datapermId\":0,\"datapermDesc\":null,\"perCode\":null,\"operation\":0,\"roleList\":null,\"treeType\":null,\"orgName\":\"私有化三组党支部\",\"orgId\":\"245849199567595973\",\"orgCode\":\"000.001.999.005.006\",\"userId\":null,\"rankWeight\":40,\"isChecked\":null,\"permCode\":null},{\"pageNo\":1,\"pageSize\":10,\"orderBy\":\"id desc\",\"id\":\"23\",\"tenantId\":\"999999999\",\"rolename\":\"党支部管理员\",\"description\":null,\"roleCode\":\"dzbgly\",\"isEdit\":0,\"status\":0,\"createUserid\":null,\"createTime\":1527716437804,\"updateUserid\":null,\"updateTime\":1527716437804,\"isDelete\":0,\"layerCode\":\"3\",\"layerName\":null,\"datapermId\":0,\"datapermDesc\":null,\"perCode\":null,\"operation\":0,\"roleList\":null,\"treeType\":null,\"orgName\":\"私有化三组党支部\",\"orgId\":\"245849199567595973\",\"orgCode\":\"000.001.999.005.006\",\"userId\":null,\"rankWeight\":-1,\"isChecked\":null,\"permCode\":null}],\"userList\":null,\"operation\":0,\"orgIdEach\":null,\"memberId\":null,\"member\":null,\"urList\":null,\"token\":null,\"telephone\":null,\"dutyLevel\":null,\"uuid\":null,\"headImg\":null,\"friendType\":null,\"isCollection\":null,\"oldOrgId\":null,\"roleName\":null,\"maxOrgCode\":null,\"bizType\":null,\"realName\":null}}");
	    if(readTree.path("success").asBoolean()){
	    	JsonNode data = readTree.path("data");
	    	String userName = data.path("userName").asText();
	    	map.put("userName", userName);
	    	String  email= data.path("email").asText();
	    	map.put("email", email);
	    	String phone = data.path("phone").asText();
	    	map.put("phone", phone);
	    	String sap = data.path("sap").asText();
	    	map.put("sap", sap);
	    }
		/*Map<String, String> map=new HashMap<>();
		RuiXinAPI ruiXinAPI = RuiXinAPIBroker.getRuiXinAPI("30000001", "2Ws4o7a7", EnvType.RX_PROD);
		//2. 获取认证服务api
		AuthAPI authAPI = ruiXinAPI.getAuthAPI();
		UserInfo ui = null;
		try { 
			//3. 根据用户token获取用户信息
			ui = authAPI.getUserInfoByToken("123123");
			System.out.println(JsonUtil.toJson(ui));
		}catch(Exception e) {
			e.printStackTrace();
		}*/
	}
}
