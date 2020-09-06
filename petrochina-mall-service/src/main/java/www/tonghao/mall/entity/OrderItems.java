package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Products;

@ApiModel(value="订单项")
@Table(name = "order_items")
public class OrderItems extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String name;

    /**
     * 单价
     */
    @ApiModelProperty("单价")
    private BigDecimal price;
    
    /**
     * 协议价
     */
    @ApiModelProperty("协议价")
    @Column(name = "protocol_price")
    private BigDecimal protocolPrice;

    /**
     * 购买数量
     */
    @ApiModelProperty("购买数量")
    private Integer num;

    /**
     * 商品品目名称
     */
    @ApiModelProperty("商品品目名称")
    @Column(name = "catalog_name")
    private String catalogName;

    /**
     * 市场价
     */
    @ApiModelProperty("市场价")
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String sn;

    /**
     * 商品缩略图
     */
    @ApiModelProperty("商品缩略图")
    private String thumbnail;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    @Column(name = "product_id")
    private Long productId;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单sn")
    @Column(name = "order_sn")
    private String orderSn;
    

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    @Column(name = "order_id")
    private Long orderId;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String model;

    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    private String brand;
    
    @Column(name = "planitem_budget_in")
    private BigDecimal planItemBudgetIn;
    
    @ApiModelProperty(value="计划财政金额")
    @Column(name = "planitem_budget_finance")
    private BigDecimal planItemBudgetFinance;
    
    @ApiModelProperty(value="计划自筹金额")
    @Column(name = "planitem_budget_self")
    private BigDecimal planItemBudgetSelf;
    
    @ApiModelProperty(value="电商行号")
    @Column(name = "emall_item_id")
    private String emallItemId;
    
    @ApiModelProperty(value="商品参数")
    @Column(name="params")
    private String params;//商品参数
    
    @ApiModelProperty(value="所属订单")
    @Transient
    private Orders order;
    
    @ApiModelProperty(value="产品")
    @Transient
    private Products product;
    
    @ApiModelProperty("活动ID")
    @Column(name = "activity_id")
    private Long activityId;
    
    @ApiModelProperty("是否已评价 默认0未评价  1已评价")
    @Column(name = "is_evaluation")
    private Integer isEvaluation;
    
    @ApiModelProperty(value="毛利")
    @Transient
    private BigDecimal profit;
    
    @ApiModelProperty(value="毛利率")
    @Transient
    private String profitRate;
    
    @ApiModelProperty(value="一级分类")
    @Transient
    private String oneCatalogName;
    
    @ApiModelProperty(value="二级分类")
    @Transient
    private String twoCatalogName;
    
    @ApiModelProperty(value="三级分类")
    @Transient
    private String threeCatalogName;
    
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
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取单价
     *
     * @return price - 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取购买数量
     *
     * @return num - 购买数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置购买数量
     *
     * @param num 购买数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品目录名称
     *
     * @return catalog_name - 商品目录名称
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * 设置商品目录名称
     *
     * @param catalogName 商品目录名称
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取商品编号
     *
     * @return sn - 商品编号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置商品编号
     *
     * @param sn 商品编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取商品缩略图
     *
     * @return thumbnail - 商品缩略图
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 设置商品缩略图
     *
     * @param thumbnail 商品缩略图
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 获取物品ID
     *
     * @return product_id - 物品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置物品ID
     *
     * @param productId 物品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
	/**
     * 获取型号
     *
     * @return model - 型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置型号
     *
     * @param model 型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取品牌
     *
     * @return brand - 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌
     *
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}
	
	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
	
	public BigDecimal getPlanItemBudgetIn() {
		return planItemBudgetIn;
	}

	public void setPlanItemBudgetIn(BigDecimal planItemBudgetIn) {
		this.planItemBudgetIn = planItemBudgetIn;
	}

	public BigDecimal getPlanItemBudgetFinance() {
		return planItemBudgetFinance;
	}

	public void setPlanItemBudgetFinance(BigDecimal planItemBudgetFinance) {
		this.planItemBudgetFinance = planItemBudgetFinance;
	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getEmallItemId() {
		return emallItemId;
	}

	public void setEmallItemId(String emallItemId) {
		this.emallItemId = emallItemId;
	}

	public BigDecimal getPlanItemBudgetSelf() {
		return planItemBudgetSelf;
	}

	public void setPlanItemBudgetSelf(BigDecimal planItemBudgetSelf) {
		this.planItemBudgetSelf = planItemBudgetSelf;
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	public BigDecimal getSubtotal() {
		if (getNum() != null) {
			return getPrice().multiply(new BigDecimal(getNum()));
		} else {
			return new BigDecimal(0);
		}
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public BigDecimal getProtocolPrice() {
		return protocolPrice;
	}

	public void setProtocolPrice(BigDecimal protocolPrice) {
		this.protocolPrice = protocolPrice;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getIsEvaluation() {
		return isEvaluation;
	}

	public void setIsEvaluation(Integer isEvaluation) {
		this.isEvaluation = isEvaluation;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public String getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}

	public String getOneCatalogName() {
		return oneCatalogName;
	}

	public void setOneCatalogName(String oneCatalogName) {
		this.oneCatalogName = oneCatalogName;
	}

	public String getTwoCatalogName() {
		return twoCatalogName;
	}

	public void setTwoCatalogName(String twoCatalogName) {
		this.twoCatalogName = twoCatalogName;
	}

	public String getThreeCatalogName() {
		return threeCatalogName;
	}

	public void setThreeCatalogName(String threeCatalogName) {
		this.threeCatalogName = threeCatalogName;
	}
}