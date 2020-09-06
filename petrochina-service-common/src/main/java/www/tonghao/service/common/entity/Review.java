package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.common.enums.ReviewGrade;
import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="评价")
public class Review extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="创建日期")
	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 订单项
     */
    @ApiModelProperty(value="订单项ID")
    @Column(name="order_item_id")
    private Long orderItemId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value="产品ID")
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品图片
     */
    @ApiModelProperty(value="产品图片")
    @Column(name = "product_pic")
    private String productPic;

    /**
     * 产品名称
     */
    @ApiModelProperty(value="产品名称")
    @Column(name = "product_name")
    private String productName;

    /**
     * 订单编号
     */
    @ApiModelProperty(value="订单编号")
    @Column(name = "order_sn")
    private String orderSn;

    /**
     * 订单创建时间
     */
    @ApiModelProperty(value="订单创建时间")
    @Column(name = "order_created_at")
    private String orderCreatedAt;
    
    @ApiModelProperty(value = "成交合同明细ID")
    @Column(name = "contract_detail_id")
    private Long contractDetailId;
    
    @ApiModelProperty(value = "成交合同ID")
    @Column(name = "contract_id")
    private Long contractId;
    
    @ApiModelProperty(value = "项目编号")
    @Column(name = "project_code")
    private String projectCode;

    /**
     * 综合评分
     */
    @ApiModelProperty(value="综合评分")
    @Column(name = "total_grade")
    @Deprecated
    private ReviewGrade totalGrade;

    /**
     * 质量评分
     */
    @ApiModelProperty(value="质量评分")
    @Column(name = "quality_grade")
    private ReviewGrade qualityGrade;

    /**
     * 服务评分
     */
    @ApiModelProperty(value="服务评分")
    @Column(name = "service_grade")
    private ReviewGrade serviceGrade;

    /**
     * 送货评分
     */
    @ApiModelProperty(value="送货评分")
    @Column(name = "delivery_grade")
    private ReviewGrade deliveryGrade;

    /**
     * 价格评分
     */
    @ApiModelProperty(value="价格评分")
    @Column(name = "price_grade")
    private ReviewGrade priceGrade;

    /**
     * 是否匿名
     */
    @ApiModelProperty(value="是否匿名")
    @Column(name = "is_anonymous")
    private Boolean isAnonymous;
    
    /**
     * 用户
     */
    @ApiModelProperty(value="用户")
    @Transient
    private Users user;

    @ApiModelProperty(value="商品型号")
    @Transient
    private String model;
    
    /**
     * 评论内容
     */
    @ApiModelProperty(value="评论内容")
    private String content;

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
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	/**
     * 获取产品ID
     *
     * @return product_id - 产品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取产品图片
     *
     * @return product_pic - 产品图片
     */
    public String getProductPic() {
        return productPic;
    }

    /**
     * 设置产品图片
     *
     * @param productPic 产品图片
     */
    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    /**
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取订单编号
     *
     * @return order_sn - 订单编号
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 设置订单编号
     *
     * @param orderSn 订单编号
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取订单创建时间
     *
     * @return order_created_at - 订单创建时间
     */
    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    /**
     * 设置订单创建时间
     *
     * @param orderCreatedAt 订单创建时间
     */
    public void setOrderCreatedAt(String orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }
    

    public Long getContractDetailId() {
		return contractDetailId;
	}

	public void setContractDetailId(Long contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
     * 获取综合评分
     *
     * @return total_grade - 综合评分
     */
    public ReviewGrade getTotalGrade() {
        return totalGrade;
    }

    /**
     * 设置综合评分
     *
     * @param totalGrade 综合评分
     */
    public void setTotalGrade(ReviewGrade totalGrade) {
        this.totalGrade = totalGrade;
    }

    /**
     * 获取质量评分
     *
     * @return quality_grade - 质量评分
     */
    public ReviewGrade getQualityGrade() {
        return qualityGrade;
    }

    /**
     * 设置质量评分
     *
     * @param qualityGrade 质量评分
     */
    public void setQualityGrade(ReviewGrade qualityGrade) {
        this.qualityGrade = qualityGrade;
    }

    /**
     * 获取服务评分
     *
     * @return service_grade - 服务评分
     */
    public ReviewGrade getServiceGrade() {
        return serviceGrade;
    }

    /**
     * 设置服务评分
     *
     * @param serviceGrade 服务评分
     */
    public void setServiceGrade(ReviewGrade serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    /**
     * 获取送货评分
     *
     * @return delivery_grade - 送货评分
     */
    public ReviewGrade getDeliveryGrade() {
        return deliveryGrade;
    }

    /**
     * 设置送货评分
     *
     * @param deliveryGrade 送货评分
     */
    public void setDeliveryGrade(ReviewGrade deliveryGrade) {
        this.deliveryGrade = deliveryGrade;
    }

    /**
     * 获取价格评分
     *
     * @return price_grade - 价格评分
     */
    public ReviewGrade getPriceGrade() {
        return priceGrade;
    }

    /**
     * 设置价格评分
     *
     * @param priceGrade 价格评分
     */
    public void setPriceGrade(ReviewGrade priceGrade) {
        this.priceGrade = priceGrade;
    }

    /**
     * 获取是否匿名
     *
     * @return is_anonymous - 是否匿名
     */
    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    /**
     * 设置是否匿名
     *
     * @param isAnonymous 是否匿名
     */
    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}