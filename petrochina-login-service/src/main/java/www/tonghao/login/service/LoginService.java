package www.tonghao.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import www.tonghao.common.warpper.LoginForm;
import www.tonghao.service.common.entity.Users;

public interface LoginService {

	/**
	 * 
	 * Description: 发送验证码
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	Map<String, Object> sendMobile(String mobile, String isResetPassword);
	
	/**
	 * 
	 * Description: 短信登录
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	Map<String, Object> loginByMobile(LoginForm loginForm, HttpServletRequest request);
	
	/**
	 * 
	 * Description: 用户名登录
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	Map<String, Object> loginByLoginName(LoginForm loginForm, HttpServletRequest request);
	
	/**
	 * 
	 * Description: 登录检测
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	Map<String, Object> check(HttpServletRequest request);
	
	/**
	 * 
	 * Description: 退出登录
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	Map<String, Object> logout(HttpServletRequest request);
	
	/**
	 * 
	 * Description: 登录成功事件
	 * 
	 * @data 2019年5月3日
	 * @param 
	 * @return
	 */
	void loginSuccessEvent(Users user, String loginSource);
	
	/**
	 * 瑞信登录
	 * @param user
	 * @param request
	 * @param loginSource
	 * @return
	 */
	String loginRuiXin(Users user, HttpServletRequest request, String loginSource);
	
	/**
	 * 
	 * Description: 手机验证码登录
	 * 
	 * @data 2019年8月24日
	 * @param 
	 * @return
	 */
	Map<String, Object> loginByMobileCode(LoginForm loginForm, HttpServletRequest request);
}
