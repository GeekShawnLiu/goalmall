package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name="order_items")
public class AfterSaleOrdersItem extends BaseEntity {

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

	@ApiModelProperty(value = "计划财政金额")
	@Column(name = "planitem_budget_finance")
	private BigDecimal planItemBudgetFinance;

	@ApiModelProperty(value = "计划自筹金额")
	@Column(name = "planitem_budget_self")
	private BigDecimal planItemBudgetSelf;

	@ApiModelProperty(value = "电商行号")
	@Column(name = "emall_item_id")
	private String emallItemId;

	@ApiModelProperty(value = "商品参数")
	@Column(name = "params")
	private String params;// 商品参数
	
	@ApiModelProperty("活动ID")
	@Column(name = "activity_id")
	private Long activityId;

	@ApiModelProperty("是否已评价 默认0未评价  1已评价")
	@Column(name = "is_evaluation")
	private Integer isEvaluation;

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getProtocolPrice() {
		return protocolPrice;
	}

	public void setProtocolPrice(BigDecimal protocolPrice) {
		this.protocolPrice = protocolPrice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public BigDecimal getPlanItemBudgetSelf() {
		return planItemBudgetSelf;
	}

	public void setPlanItemBudgetSelf(BigDecimal planItemBudgetSelf) {
		this.planItemBudgetSelf = planItemBudgetSelf;
	}

	public String getEmallItemId() {
		return emallItemId;
	}

	public void setEmallItemId(String emallItemId) {
		this.emallItemId = emallItemId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Integer getIsEvaluation() {
		return isEvaluation;
	}

	public void setIsEvaluation(Integer isEvaluation) {
		this.isEvaluation = isEvaluation;
	}
}
