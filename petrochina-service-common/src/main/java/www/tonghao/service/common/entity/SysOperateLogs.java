package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "sys_operate_logs")
public class SysOperateLogs extends BaseEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 操作者
     */
    private String operator;
    
    /**
     * 操作人单位名称
     */
    @Column(name="operator_dep")
    private String operatorDep;

    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value="访问渠道： 1:pc  2:h5  3:小程序")
    private String event;

    /**
     * 日志类型 1：操作日志 2：登录日志
     */
    @Column(name = "log_type")
    @ApiModelProperty(value="日志类型 1：操作日志 2：登录日志")
    private Byte logType;

    /**
     * 系统名称
     */
    @Column(name = "sys_name")
    @ApiModelProperty(value="系统名称")
    private String sysName;

    /**
     * 模块名称
     */
    @Column(name = "module_name")
    @ApiModelProperty(value="模块名称")
    private String moduleName;

    /**
     * ip
     */
    private String ip;

    /**
     * 用户名
     */
    @Column(name = "login_name")
    private String loginName;

    @Transient
    private String startTime;
    
    @Transient
    private String endTime;
    
    @Transient
    @ApiModelProperty(value="姓名")
    private String realName;
    
    @Transient
    @ApiModelProperty(value="部门")
    private String department;
    
    @Transient
    private Users user;
    
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
     * 获取事件描述
     *
     * @return description - 事件描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置事件描述
     *
     * @param desc 事件描述
     */
    public void setDription(String description) {
        this.description = description;
    }

    /**
     * 获取操作者
     *
     * @return operator - 操作者
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作者
     *
     * @param operator 操作者
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String getOperatorDep() {
		return operatorDep;
	}

	public void setOperatorDep(String operatorDep) {
		this.operatorDep = operatorDep;
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
     * @return event
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * 获取日志类型 1：操作日志 2：登录日志
     *
     * @return log_type - 日志类型 1：操作日志 2：登录日志
     */
    public Byte getLogType() {
        return logType;
    }

    /**
     * 设置日志类型 1：操作日志 2：登录日志
     *
     * @param logType 日志类型 1：操作日志 2：登录日志
     */
    public void setLogType(Byte logType) {
        this.logType = logType;
    }

    /**
     * 获取系统名称
     *
     * @return sys_name - 系统名称
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * 设置系统名称
     *
     * @param sysName 系统名称
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    /**
     * 获取模块名称
     *
     * @return module_name - 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设置模块名称
     *
     * @param moduleName 模块名称
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 获取ip
     *
     * @return ip - ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
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

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean isCudLog() {
		return false;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}