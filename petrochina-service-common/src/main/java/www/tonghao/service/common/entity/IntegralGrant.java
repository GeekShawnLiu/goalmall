package www.tonghao.service.common.entity;

import java.math.BigDecimal;
import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "integral_grant")
public class IntegralGrant extends BaseEntity{

    /**@Description:
	 * @date 2019年4月28日
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 积分id
     */
    @Column(name = "integral_id")
    private Long integralId;

    /**
     * 积分名称
     */
    @Column(name = "integral_name")
    private String integralName;

    /**
     * 用户积分id
     */
    @Column(name = "integral_user_id")
    private Long integralUserId;

    /**
     * 活动id
     */
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 活动名称
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "user_realname")
    private String userRealname;
    
    /**
     * 机构id
     */
    @Column(name = "org_id")
    private Long orgId;
    
    /**
     * 机构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 积分数量
     */
    @Column(name = "integral_num")
    private BigDecimal integralNum;

    /**
     * '审核状态：0未审核，1审核中，2通过，3未通过'
     */
    private Integer state;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private String updatedAt;
    
    /**
     * 提交审核时间
     */
    @Column(name = "submit_audit")
    private String submitAudit;
    
    /**
     * 是否删除:默认0未删除，1已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
    
    /**
     * 审核时间
     */
    @Column(name = "audit_at")
    private String auditAt;
    
    /**
     * 导入批次
     */
    @Column(name = "batch")
    private String batch;
    
    @Transient
    private String batchNum;
    
    @Transient
    private String email;
    @Transient
    private String mobile;
    @Transient
    private String loginName;
    @Transient
    private String orderBySubmitAudit;
    @Transient
    private Integer notState;

    /**
     * 获取积分id
     *
     * @return integral_id - 积分id
     */
    public Long getIntegralId() {
        return integralId;
    }

    /**
     * 设置积分id
     *
     * @param integralId 积分id
     */
    public void setIntegralId(Long integralId) {
        this.integralId = integralId;
    }

    /**
     * 获取积分名称
     *
     * @return integral_name - 积分名称
     */
    public String getIntegralName() {
        return integralName;
    }

    /**
     * 设置积分名称
     *
     * @param integralName 积分名称
     */
    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    /**
     * 获取用户积分id
     *
     * @return integral_user_id - 用户积分id
     */
    public Long getIntegralUserId() {
        return integralUserId;
    }

    /**
     * 设置用户积分id
     *
     * @param integralUserId 用户积分id
     */
    public void setIntegralUserId(Long integralUserId) {
        this.integralUserId = integralUserId;
    }

    /**
     * 获取活动id
     *
     * @return activity_id - 活动id
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * 设置活动id
     *
     * @param activityId 活动id
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     * 获取活动名称
     *
     * @return activity_name - 活动名称
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * 设置活动名称
     *
     * @param activityName 活动名称
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return user_realname - 用户名
     */
    public String getUserRealname() {
        return userRealname;
    }

    /**
     * 设置用户名
     *
     * @param userRealname 用户名
     */
    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    /**
     * 获取积分数量
     *
     * @return integral_num - 积分数量
     */
    public BigDecimal getIntegralNum() {
        return integralNum;
    }

    /**
     * 设置积分数量
     *
     * @param integralNum 积分数量
     */
    public void setIntegralNum(BigDecimal integralNum) {
        this.integralNum = integralNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSubmitAudit() {
		return submitAudit;
	}

	public void setSubmitAudit(String submitAudit) {
		this.submitAudit = submitAudit;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(String auditAt) {
		this.auditAt = auditAt;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrderBySubmitAudit() {
		return orderBySubmitAudit;
	}

	public void setOrderBySubmitAudit(String orderBySubmitAudit) {
		this.orderBySubmitAudit = orderBySubmitAudit;
	}

	public Integer getNotState() {
		return notState;
	}

	public void setNotState(Integer notState) {
		this.notState = notState;
	}

	
}