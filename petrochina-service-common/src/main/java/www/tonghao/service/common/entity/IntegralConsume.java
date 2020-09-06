package www.tonghao.service.common.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:积分消费明细
 * @date 2019年5月9日
 */
@Table(name = "integral_consume")
public class IntegralConsume extends BaseEntity{

    /**@Description:
	 * @date 2019年5月9日
	 */
	private static final long serialVersionUID = 1L;

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
     * 积分数量
     */
    @Column(name = "integral_num")
    private BigDecimal integralNum;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Long orderId;
    
    /**
     * 1消费  2退还
     */
    @Column(name = "type")
    private Integer type;
    
    @Column(name = "reason")
    private String reason;
    
    @Transient
    private String realName;
    @Transient
    private String depName;
    @Transient
    private String sn;
    @Transient
    private String emallSn;
    @Transient
    private String supplierName;
    @Transient
    private String ordersState;
    @Transient
    private String orderCreatedAt;
    @Transient
    private BigDecimal total;
    @Transient
    private String integralName;
    @Transient
    private String orderCompletedAt;
    @Transient
    private Long payId;
    
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


    public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
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
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getEmallSn() {
		return emallSn;
	}

	public void setEmallSn(String emallSn) {
		this.emallSn = emallSn;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getOrdersState() {
		return ordersState;
	}

	public void setOrdersState(String ordersState) {
		this.ordersState = ordersState;
	}

	public String getOrderCreatedAt() {
		return orderCreatedAt;
	}

	public void setOrderCreatedAt(String orderCreatedAt) {
		this.orderCreatedAt = orderCreatedAt;
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

	public String getOrderCompletedAt() {
		return orderCompletedAt;
	}

	public void setOrderCompletedAt(String orderCompletedAt) {
		this.orderCompletedAt = orderCompletedAt;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}
}