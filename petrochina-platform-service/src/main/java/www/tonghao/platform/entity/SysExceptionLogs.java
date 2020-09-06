package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "sys_exception_logs")
public class SysExceptionLogs extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 异常标题
     */
    private String title;

    /**
     * 异常名
     */
    private String name;

    /**
     * 异常message信息
     */
    private String message;

    /**
     * 控制类
     */
    private String controller;

    /**
     * 控制类方法
     */
    @Column(name = "controller_method")
    private String controllerMethod;

    /**
     * 请求方式 GET POST
     */
    @Column(name = "http_method")
    private String httpMethod;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 本机IP
     */
    @Column(name = "local_ip")
    private String localIp;

    /**
     * 用户名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 请求url
     */
    @Column(name = "http_url")
    private String httpUrl;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 参数
     */
    private String params;

    /**
     * 异常堆栈信息
     */
    @Column(name = "stack_trace")
    private String stackTrace;

    @Transient
    private String startTime;
    
    @Transient
    private String endTime;
    
    /**
     * @return created_at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取异常标题
     *
     * @return title - 异常标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置异常标题
     *
     * @param title 异常标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取异常名
     *
     * @return name - 异常名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置异常名
     *
     * @param name 异常名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取异常message信息
     *
     * @return message - 异常message信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置异常message信息
     *
     * @param message 异常message信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取控制类
     *
     * @return controller - 控制类
     */
    public String getController() {
        return controller;
    }

    /**
     * 设置控制类
     *
     * @param controller 控制类
     */
    public void setController(String controller) {
        this.controller = controller;
    }

    /**
     * 获取控制类方法
     *
     * @return controller_method - 控制类方法
     */
    public String getControllerMethod() {
        return controllerMethod;
    }

    /**
     * 设置控制类方法
     *
     * @param controllerMethod 控制类方法
     */
    public void setControllerMethod(String controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    /**
     * 获取请求方式 GET POST
     *
     * @return http_method - 请求方式 GET POST
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * 设置请求方式 GET POST
     *
     * @param httpMethod 请求方式 GET POST
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * 获取请求IP
     *
     * @return ip - 请求IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置请求IP
     *
     * @param ip 请求IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取本机IP
     *
     * @return local_ip - 本机IP
     */
    public String getLocalIp() {
        return localIp;
    }

    /**
     * 设置本机IP
     *
     * @param localIp 本机IP
     */
    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    /**
     * 获取用户名
     *
     * @return login_name - 用户名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置用户名
     *
     * @param loginName 用户名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取请求url
     *
     * @return http_url - 请求url
     */
    public String getHttpUrl() {
        return httpUrl;
    }

    /**
     * 设置请求url
     *
     * @param httpUrl 请求url
     */
    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取参数
     *
     * @return params - 参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置参数
     *
     * @param params 参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取异常堆栈信息
     *
     * @return stack_trace - 异常堆栈信息
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * 设置异常堆栈信息
     *
     * @param stackTrace 异常堆栈信息
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public boolean isCudLog() {
		return false;
	}
    
}