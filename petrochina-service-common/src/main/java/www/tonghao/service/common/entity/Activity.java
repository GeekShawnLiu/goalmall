package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:活动
 * @date 2019年4月29日
 */
public class Activity extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
     * 活动名称
     */
	@ApiModelProperty(value="活动名称")
    private String name;

    /**
     * 活动开始时间
     */
	@ApiModelProperty(value="活动开始时间")
    @Column(name = "start_at")
    private String startAt;

    /**
     * 活动结束时间
     */
	@ApiModelProperty(value="活动结束时间")
    @Column(name = "end_at")
    private String endAt;

    /**
     * 活动上线开始时间
     */
	@ApiModelProperty(value="活动上线时间")
    @Column(name = "online_at")
    private String onlineAt;

    /**
     * 活动类型：1积分活动，2促销活动
     */
	@ApiModelProperty(value="活动类型：1积分活动，2促销活动")
    private Integer type;

    /**
     * pc图片路径
     */
	@ApiModelProperty(value="pc图片路径")
    @Column(name = "pc_img_path")
    private String pcImgPath;
	
	/**
	 * pc图片名称
	 */
	@ApiModelProperty(value="pc图片名称")
	@Column(name = "pc_img_name")
	private String pcImgName;

    /**
     * h5图片路径
     */
	@ApiModelProperty(value="h5图片路径")
    @Column(name = "h5_img_path")
    private String h5ImgPath;
	/**
	 * h5图片名称
	 */
	@ApiModelProperty(value="h5图片名称")
	@Column(name = "h5_img_name")
	private String h5ImgName;

    /**
     * 小程序图片路径
     */
	@ApiModelProperty(value="小程序图片路径")
    @Column(name = "applets_img_path")
    private String appletsImgPath;
	
	/**
     * 小程序图片名称
     */
	@ApiModelProperty(value="小程序图片名称")
    @Column(name = "applets_img_name")
    private String appletsImgName;

    /**
     * 是否删除:默认0未删除，1已删除
     */
	@ApiModelProperty(value="")
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
    
    /**
     * 展示图片名称
     */
    @Column(name = "img_name")
    private String imgName;
    
    /**
     * 展示图片路径
     */
    @Column(name = "img_path")
    private String imgPath;
    
    /**
     * 立即下线,默认0否，1是
     */
    @Column(name = "is_offline")
    private Integer isOffline;
    
    /**
     * 机构
     */
    @ApiModelProperty(value="机构")
    @Transient
    private List<ActivityOrg> activityOrgList;
    
    /**
     * 商品
     */
    @ApiModelProperty(value="商品")
    @Transient
    private List<ActivityProduct> activityProductList;
    
    @ApiModelProperty(value="商品")
    @Transient
    private List<Products> ProductsList;
    
    @ApiModelProperty(value="上线状态")
    @Transient
    private String onlineState;
    
    @ApiModelProperty(value="活动进行状态")
    @Transient
    private String activityState;
    
    @ApiModelProperty(value="消费积分")
    @Transient
    private BigDecimal consumptionIntegral;

    @ApiModelProperty(value="积分余额")
    @Transient
    private BigDecimal integralBalance;
    
    @ApiModelProperty(value="活动总额")
    @Transient
    private BigDecimal activityTotal;
    
    @ApiModelProperty(value="支付现金")
    @Transient
    private BigDecimal money;
    
    @Transient
    private String orgName;
    
    @ApiModelProperty(value="活动分类  0福瑞商城 1爱心超市 2积分乐园")
    @Column(name = "kind")
    private Integer kind;

    /**
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取活动开始时间
     *
     * @return start_at - 活动开始时间
     */
    public String getStartAt() {
        return startAt;
    }

    /**
     * 设置活动开始时间
     *
     * @param startAt 活动开始时间
     */
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    /**
     * 获取活动结束时间
     *
     * @return end_at - 活动结束时间
     */
    public String getEndAt() {
        return endAt;
    }

    /**
     * 设置活动结束时间
     *
     * @param endAt 活动结束时间
     */
    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }


    public String getOnlineAt() {
		return onlineAt;
	}

	public void setOnlineAt(String onlineAt) {
		this.onlineAt = onlineAt;
	}

	/**
     * 获取活动类型：1积分活动，2促销活动
     *
     * @return type - 活动类型：1积分活动，2促销活动
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置活动类型：1积分活动，2促销活动
     *
     * @param type 活动类型：1积分活动，2促销活动
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public String getPcImgPath() {
		return pcImgPath;
	}

	public void setPcImgPath(String pcImgPath) {
		this.pcImgPath = pcImgPath;
	}

	public String getPcImgName() {
		return pcImgName;
	}

	public void setPcImgName(String pcImgName) {
		this.pcImgName = pcImgName;
	}

	public String getH5ImgPath() {
		return h5ImgPath;
	}

	public void setH5ImgPath(String h5ImgPath) {
		this.h5ImgPath = h5ImgPath;
	}

	public String getH5ImgName() {
		return h5ImgName;
	}

	public void setH5ImgName(String h5ImgName) {
		this.h5ImgName = h5ImgName;
	}

	public String getAppletsImgPath() {
		return appletsImgPath;
	}

	public void setAppletsImgPath(String appletsImgPath) {
		this.appletsImgPath = appletsImgPath;
	}

	public String getAppletsImgName() {
		return appletsImgName;
	}

	public void setAppletsImgName(String appletsImgName) {
		this.appletsImgName = appletsImgName;
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

	public List<ActivityOrg> getActivityOrgList() {
		return activityOrgList;
	}

	public void setActivityOrgList(List<ActivityOrg> activityOrgList) {
		this.activityOrgList = activityOrgList;
	}

	public List<ActivityProduct> getActivityProductList() {
		return activityProductList;
	}

	public void setActivityProductList(List<ActivityProduct> activityProductList) {
		this.activityProductList = activityProductList;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}

	public String getActivityState() {
		return activityState;
	}

	public void setActivityState(String activityState) {
		this.activityState = activityState;
	}

	public List<Products> getProductsList() {
		return ProductsList;
	}

	public void setProductsList(List<Products> productsList) {
		ProductsList = productsList;
	}

	public BigDecimal getConsumptionIntegral() {
		return consumptionIntegral;
	}

	public void setConsumptionIntegral(BigDecimal consumptionIntegral) {
		this.consumptionIntegral = consumptionIntegral;
	}

	public BigDecimal getIntegralBalance() {
		return integralBalance;
	}

	public void setIntegralBalance(BigDecimal integralBalance) {
		this.integralBalance = integralBalance;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(Integer isOffline) {
		this.isOffline = isOffline;
	}

	public BigDecimal getActivityTotal() {
		return activityTotal;
	}

	public void setActivityTotal(BigDecimal activityTotal) {
		this.activityTotal = activityTotal;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}
}