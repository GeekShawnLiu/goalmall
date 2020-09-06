package www.tonghao.mall.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

public class Bill extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 开始日期
     */
    @Column(name = "begin_at")
    private String beginAt;

    /**
     * 结束日期
     */
    @Column(name = "end_at")
    private String endAt;

    /**
     * 采购人用户ID
     */
    @Column(name = "purchaser_uid")
    private Long purchaserUid;

    /**
     * 采购人姓名
     */
    @Column(name = "purchaser_name")
    private String purchaserName;

    /**
     * 状态 1 未审核，2审核 ,3退回
     */
    private Integer status;
    
    /**
     * 是否删除 0未删除，1删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
    
    
    /**
     * 创建人id
     */
    @Column(name = "create_user_id")
    private Long createUserId;
    
    
    /**
     * 创建人名称
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 对账名称
     */
    private String title;
    
    
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 账单明细
     */
    @Transient
    private List<BillDetail> details;
    
    /**
     * 订单id
     */
    @Transient
    private String orderIds;
    /**
     * 
     * 订单笔数
     */
     @Transient
     private long number;
     
     private String remark;
     

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
     * 获取开始日期
     *
     * @return begin_at - 开始日期
     */
    public String getBeginAt() {
        return beginAt;
    }

    /**
     * 设置开始日期
     *
     * @param beginAt 开始日期
     */
    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    /**
     * 获取结束日期
     *
     * @return end_at - 结束日期
     */
    public String getEndAt() {
        return endAt;
    }

    /**
     * 设置结束日期
     *
     * @param endAt 结束日期
     */
    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    /**
     * 获取采购人用户ID
     *
     * @return purchaser_uid - 采购人用户ID
     */
    public Long getPurchaserUid() {
        return purchaserUid;
    }

    /**
     * 设置采购人用户ID
     *
     * @param purchaserUid 采购人用户ID
     */
    public void setPurchaserUid(Long purchaserUid) {
        this.purchaserUid = purchaserUid;
    }

    /**
     * 获取采购人姓名
     *
     * @return purchaser_name - 采购人姓名
     */
    public String getPurchaserName() {
        return purchaserName;
    }

    /**
     * 设置采购人姓名
     *
     * @param purchaserName 采购人姓名
     */
    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取对账名称
     *
     * @return title - 对账名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置对账名称
     *
     * @param title 对账名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

	public List<BillDetail> getDetails() {
		return details;
	}

	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

    
}