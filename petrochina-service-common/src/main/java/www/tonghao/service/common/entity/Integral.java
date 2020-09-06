package www.tonghao.service.common.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:活动
 * @date 2019年4月26日
 */
public class Integral extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
     * 积分名称
     */
	@ApiModelProperty(value="积分名称")
    private String name;

    /**
     * 积分描述
     */
	@ApiModelProperty(value="积分描述")
    private String description;

    /**
     * 机构id
     */
	@ApiModelProperty(value="机构id")
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 总积分
     */
	@ApiModelProperty(value="总额度")
    private BigDecimal total;

    /**
     * 确认额度(审核通过用户积分到账)
     */
	@Column(name = "confirm_quota")
    private BigDecimal confirmQuota;
    
    /**
     * 发放额度
     */
	@Column(name = "grant_quota")
    private BigDecimal grantQuota;

    /**
     * 活动id
     */
    @ApiModelProperty(value="活动")
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    @Column(name = "start_at")
    private String startAt;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    @Column(name = "end_at")
    private String endAt;

    /**
     * 是否删除:默认0未删除，1已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

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
    
    @ApiModelProperty(value="活动名称")
    @Transient
    private String activityName;
    
    @ApiModelProperty(value = "机构名称")
    @Transient
    private String orgName;
    
    @ApiModelProperty(value = "活动开始时间")
    @Transient
    private String activityStartAt;
    
    @ApiModelProperty(value = "活动结束时间")
    @Transient
    private String activityEndAt;
    
    @ApiModelProperty(value = "活动关联的机构")
    @Transient
    private List<IntegralOrg> integralOrgList;
    
    @Transient
    private Integer isIntegralUser;//是否分配用户

    /**
     * 获取积分名称
     *
     * @return name - 积分名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置积分名称
     *
     * @param name 积分名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取积分描述
     *
     * @return description - 积分描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置积分描述
     *
     * @param description 积分描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取机构id
     *
     * @return org_id - 机构id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置机构id
     *
     * @param orgId 机构id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取总积分
     *
     * @return total - 总积分
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * 设置总积分
     *
     * @param total 总积分
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
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
     * 获取结束时间
     *
     * @return end_at - 结束时间
     */
    public String getEndAt() {
        return endAt;
    }

    /**
     * 设置结束时间
     *
     * @param endAt 结束时间
     */
    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    /**
     * 获取是否删除:默认0未删除，1已删除
     *
     * @return is_delete - 是否删除:默认0未删除，1已删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除:默认0未删除，1已删除
     *
     * @param isDelete 是否删除:默认0未删除，1已删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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

	public String getStartAt() {
		return startAt;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public BigDecimal getConfirmQuota() {
		return confirmQuota;
	}

	public void setConfirmQuota(BigDecimal confirmQuota) {
		this.confirmQuota = confirmQuota;
	}

	public BigDecimal getGrantQuota() {
		return grantQuota;
	}

	public void setGrantQuota(BigDecimal grantQuota) {
		this.grantQuota = grantQuota;
	}

	public List<IntegralOrg> getIntegralOrgList() {
		return integralOrgList;
	}

	public void setIntegralOrgList(List<IntegralOrg> integralOrgList) {
		this.integralOrgList = integralOrgList;
	}

	public Integer getIsIntegralUser() {
		return isIntegralUser;
	}

	public void setIsIntegralUser(Integer isIntegralUser) {
		this.isIntegralUser = isIntegralUser;
	}


}