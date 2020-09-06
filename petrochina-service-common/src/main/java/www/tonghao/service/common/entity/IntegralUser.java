package www.tonghao.service.common.entity;

import java.math.BigDecimal;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:积分用户表
 * @date 2019年5月2日
 */
@Table(name = "integral_user")
public class IntegralUser extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 积分id
     */
    @Column(name = "integral_id")
    private Long integralId;

    /**
     * 活动id
     */
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 余额
     */
    @ApiModelProperty(value="余额")
    private BigDecimal balance;
    
    /**
     * 总积分
     */
    @ApiModelProperty(value="总积分")
    private BigDecimal total;

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
    
    @ApiModelProperty(value="积分名称")
    @Transient
    private String integralName;
    
    @ApiModelProperty(value="活动名称")
    @Transient
    private String activityName;
    
    @ApiModelProperty(value="活动开始时间")
    @Transient
    private String activityStartAt;
    
    @ApiModelProperty(value="活动结束时间")
    @Transient
    private String activityEndAt;
    
    @ApiModelProperty(value="姓名")
    @Transient
    private String realName;
    
    @ApiModelProperty(value="已消费")
    @Transient
    private BigDecimal consumed;
    
    @ApiModelProperty(value="导入批次")
    @Transient
    private String batch;
    
    @Transient
    private boolean isExchange;
    
    @Transient
    private String activityOnlineAt;
    
    @Transient
    private Integer isOffline;
    
    @Transient
    private Integer status;
    
    @Transient
    private String mobile;
    
    @Transient
    private String email;
    @Transient
    private BigDecimal startTotal;
    @Transient
    private BigDecimal endTotal;
    @Transient
    private BigDecimal startBalance;
    @Transient
    private BigDecimal endBalance;
    @Transient
    private BigDecimal startConsumed;
    @Transient
    private BigDecimal endConsumed;
    
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
     * 获取余额
     *
     * @return balance - 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     *
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getIntegralName() {
		return integralName;
	}

	public void setIntegralName(String integralName) {
		this.integralName = integralName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityStartAt() {
		return activityStartAt;
	}

	public void setActivityStartAt(String activityStartAt) {
		this.activityStartAt = activityStartAt;
	}

	public String getActivityEndAt() {
		return activityEndAt;
	}

	public void setActivityEndAt(String activityEndAt) {
		this.activityEndAt = activityEndAt;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public BigDecimal getConsumed() {
		return consumed;
	}

	public void setConsumed(BigDecimal consumed) {
		this.consumed = consumed;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public boolean isExchange() {
		return isExchange;
	}

	public void setIsExchange(boolean isExchange) {
		this.isExchange = isExchange;
	}

	public String getActivityOnlineAt() {
		return activityOnlineAt;
	}

	public void setActivityOnlineAt(String activityOnlineAt) {
		this.activityOnlineAt = activityOnlineAt;
	}

	public Integer getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(Integer isOffline) {
		this.isOffline = isOffline;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getStartTotal() {
		return startTotal;
	}

	public void setStartTotal(BigDecimal startTotal) {
		this.startTotal = startTotal;
	}

	public BigDecimal getEndTotal() {
		return endTotal;
	}

	public void setEndTotal(BigDecimal endTotal) {
		this.endTotal = endTotal;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public BigDecimal getStartConsumed() {
		return startConsumed;
	}

	public void setStartConsumed(BigDecimal startConsumed) {
		this.startConsumed = startConsumed;
	}

	public BigDecimal getEndConsumed() {
		return endConsumed;
	}

	public void setEndConsumed(BigDecimal endConsumed) {
		this.endConsumed = endConsumed;
	}
}