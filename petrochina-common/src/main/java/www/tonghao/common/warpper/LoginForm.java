package www.tonghao.common.warpper;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "登录")
public class LoginForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户类型：1：采购人，2：集采机构，3：监管机构，4：供应商:5：专家
	 */
	@ApiModelProperty(value = "用户类型：1：采购人，2：集采机构，3：监管机构，4：供应商，5：专家，6：采购中心")
	private Integer type;

	@ApiModelProperty(value = "登录方式：1：用户名--密码登录， 2：忘记密码--手机号修改密码并登录， 3：手机--验证码登录")
	private Integer loginWay;

	@ApiModelProperty(value = "登录帐号")
	private String username;

	@ApiModelProperty(value = "用户名登录密码")
	private String password;

	@ApiModelProperty(value = "手机号登录密码")
	private String password2;

	@ApiModelProperty(value = "确认密码")
	private String confirmPassword;

	@ApiModelProperty(value = "记住我")
	private Boolean rememberMe;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "手机验证码")
	private String code;

	@ApiModelProperty(value = "登录来源： 1:pc  2:h5  3:小程序")
	private String loginSource;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(Integer loginWay) {
		this.loginWay = loginWay;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
	}
}
