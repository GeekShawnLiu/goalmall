package www.tonghao.service.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:售后
 * @date 2019年7月11日
 */
@Table(name = "after_sale")
public class AfterSale extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
    /**
     * 当前状态：
     * 0:待审核，
     * 1:不通过，
     * 2：通过，
     * 3：买家货物邮寄中，
     * 4：卖家货物邮寄中，
     * 5：售后完成， 
     * 6：卖家确认收货，待退款，
	 * 7：卖家确认收货，驳回退款，
     */
	@ApiModelProperty(value="当前状态：0:待审核，1:不通过，2：通过，3：买家货物邮寄中，4：卖家货物邮寄中，5：售后完成， 6：卖家确认收货，待退款，7：卖家确认收货，驳回退款，")
    private Integer status;

    /**
     * 售后类型
     */
    @ApiModelProperty(value="售后类型：1：换货，2：退货退款，3：退款")
    private Integer type;

    /**
     * 支付方式
     */
    @ApiModelProperty(value="支付方式")
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 退款
     */
    @ApiModelProperty(value="退款金额")
    private BigDecimal refund;

    /**
     * 售后原因
     */
    @ApiModelProperty(value="售后原因")
    private String reason;

    /**
     * 问题描述
     */
    @ApiModelProperty(value="问题描述")
    private String description;

    /**
     * 凭证图片
     */
    @ApiModelProperty(value="凭证图片")
    @Column(name = "certificate_img")
    private String certificateImg;

    /**
     * 售后编号
     */
    @Column(name = "after_sale_sn")
    private String afterSaleSn;

    /**
     * 订单编号
     */
    @Column(name = "orders_sn")
    private String ordersSn;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品数量
     */
    @ApiModelProperty(value="商品数量")
    @Column(name = "product_num")
    private Integer productNum;

    /**
     * 商品价格
     */
    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**
     * 商品sku
     */
    private String sku;

    /**
     * 供应商名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

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
    
    @Column(name = "order_itmes_id")
    private Long orderItmesId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "supplier_id")
    private Long supplierId;
    
    //供应商地址
    @ApiModelProperty(value="供应商的收货地址")
    @Column(name = "supplier_address")
    private String supplierAddress;
    
    //采购人快递号
    @ApiModelProperty(value="采购人填写的快递号")
    @Column(name = "purchaser_express_num")
    private String purchaserExpressNum;
    
    //采购人快递名称
    @ApiModelProperty(value="采购人填写的快递名称")
    @Column(name = "purchaser_express_name")
    private String purchaserExpressName;
    
    //供应商快递号
    @ApiModelProperty(value="供应商填写的快递号")
    @Column(name = "supplier_express_num")
    private String supplierExpressNum;
    
    //供应商快递名称
    @ApiModelProperty(value="供应商填写的快递名称")
    @Column(name = "supplier_express_name")
    private String supplierExpressName;
    
    //售后凭证
    @ApiModelProperty(value="售后凭证")
    @Column(name = "after_sale_img")
    private String afterSaleImg;
    
    //不退款类型：1:不退款，2：可退部分金额
    @ApiModelProperty(value="不退款类型：1:不退款，2：可退部分金额")
    @Column(name = "no_refund_type")
    private Integer noRefundType;
    
    //不退款理由
    @ApiModelProperty(value="不退款理由")
    @Column(name = "no_refund_reason")
    private String noRefundReason;
    
    //凭证图片名称
    @ApiModelProperty(value="凭证图片名称")
    @Column(name = "certificate_img_name")
    private String certificateImgName;
    
    //售后凭证图片名称
    @ApiModelProperty(value="售后凭证图片名称")
    @Column(name = "after_sale_img_name")
    private String afterSaleImgName;
    
    //是否重新提交：不是0,1是
    @ApiModelProperty(value="是否重新提交：0否,1是")
    @Column(name = "is_resubmit")
    private Integer isResubmit;
    
    //是否退款：0否,1是
    @Column(name = "is_refund")
    private Integer isRefund;
    
    @Transient
    private String toDayMaxSn;
    
    @Transient
    private Long resubmitId;
    
    @ApiModelProperty(value="供应商售后审核信息")
    @Transient
    private AfterSaleAudit afterSaleAudit;
    
    @ApiModelProperty(value="商品缩略图")
    @Transient
    private String productThumbnail;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCertificateImg() {
		return certificateImg;
	}

	public void setCertificateImg(String certificateImg) {
		this.certificateImg = certificateImg;
	}

	public String getAfterSaleSn() {
		return afterSaleSn;
	}

	public void setAfterSaleSn(String afterSaleSn) {
		this.afterSaleSn = afterSaleSn;
	}

	public String getOrdersSn() {
		return ordersSn;
	}

	public void setOrdersSn(String ordersSn) {
		this.ordersSn = ordersSn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

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

	public Long getOrderItmesId() {
		return orderItmesId;
	}

	public void setOrderItmesId(Long orderItmesId) {
		this.orderItmesId = orderItmesId;
	}

	public String getToDayMaxSn() {
		return toDayMaxSn;
	}

	public void setToDayMaxSn(String toDayMaxSn) {
		this.toDayMaxSn = toDayMaxSn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getPurchaserExpressNum() {
		return purchaserExpressNum;
	}

	public void setPurchaserExpressNum(String purchaserExpressNum) {
		this.purchaserExpressNum = purchaserExpressNum;
	}

	public String getPurchaserExpressName() {
		return purchaserExpressName;
	}

	public void setPurchaserExpressName(String purchaserExpressName) {
		this.purchaserExpressName = purchaserExpressName;
	}

	public String getSupplierExpressNum() {
		return supplierExpressNum;
	}

	public void setSupplierExpressNum(String supplierExpressNum) {
		this.supplierExpressNum = supplierExpressNum;
	}

	public String getSupplierExpressName() {
		return supplierExpressName;
	}

	public void setSupplierExpressName(String supplierExpressName) {
		this.supplierExpressName = supplierExpressName;
	}

	public String getAfterSaleImg() {
		return afterSaleImg;
	}

	public void setAfterSaleImg(String afterSaleImg) {
		this.afterSaleImg = afterSaleImg;
	}

	public AfterSaleAudit getAfterSaleAudit() {
		return afterSaleAudit;
	}

	public void setAfterSaleAudit(AfterSaleAudit afterSaleAudit) {
		this.afterSaleAudit = afterSaleAudit;
	}

	public Integer getNoRefundType() {
		return noRefundType;
	}

	public void setNoRefundType(Integer noRefundType) {
		this.noRefundType = noRefundType;
	}

	public String getNoRefundReason() {
		return noRefundReason;
	}

	public void setNoRefundReason(String noRefundReason) {
		this.noRefundReason = noRefundReason;
	}

	public String getCertificateImgName() {
		return certificateImgName;
	}

	public void setCertificateImgName(String certificateImgName) {
		this.certificateImgName = certificateImgName;
	}

	public String getAfterSaleImgName() {
		return afterSaleImgName;
	}

	public void setAfterSaleImgName(String afterSaleImgName) {
		this.afterSaleImgName = afterSaleImgName;
	}

	public Integer getIsResubmit() {
		return isResubmit;
	}

	public void setIsResubmit(Integer isResubmit) {
		this.isResubmit = isResubmit;
	}

	public Long getResubmitId() {
		return resubmitId;
	}

	public void setResubmitId(Long resubmitId) {
		this.resubmitId = resubmitId;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	public String getProductThumbnail() {
		return productThumbnail;
	}

	public void setProductThumbnail(String productThumbnail) {
		this.productThumbnail = productThumbnail;
	}





}
