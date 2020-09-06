package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: 售后订单信息
 * 
 * @version 2019年7月29日
 * @since JDK1.8
 */
@ApiModel(value = "订单")
@Table(name = "orders")
public class AfterSaleOrders extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "updated_at")
	private String updatedAt;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "title")
	private String title;

	private String sn;

	@ApiModelProperty(value = "订单状态")
	@Column(name = "orders_state")
	private OrderStatus ordersState;

	/**
	 * 收获地址ID
	 */
	@ApiModelProperty(value = "收获地址ID")
	@Column(name = "address_id")
	private Long addressId;

	/**
	 * 地区ID
	 */
	@Column(name = "area_id")
	private Long areaId;

	/**
	 * 收货人
	 */
	@ApiModelProperty(value = "收货人")
	@Column(name = "consignee_name")
	private String consigneeName;

	/**
	 * 收货详细地址
	 */
	@ApiModelProperty(value = "收货详细地址")
	private String addr;

	/**
	 * 邮编
	 */
	@ApiModelProperty(value = "邮编")
	@Column(name = "zip_code")
	private String zipCode;

	/**
	 * 收货人手机号
	 */
	@ApiModelProperty(value = "收货人手机号")
	private String mobile;

	/**
	 * 收货人电话
	 */
	@ApiModelProperty(value = "收货人电话")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 商品总金额
	 */
	@ApiModelProperty(value = "商品总金额")
	@Column(name = "items_price")
	private BigDecimal itemsPrice;

	/**
	 * 邮费
	 */
	@ApiModelProperty(value = "邮费")
	private BigDecimal freight;

	/**
	 * 订单总金额(含快递等费用)
	 */
	@ApiModelProperty(value = "订单总金额(含快递等费用)")
	private BigDecimal total;

	/**
	 * 供应商ID
	 */
	@ApiModelProperty(value = "供应商ID")
	@Column(name = "supplier_id")
	private Long supplierId;

	/**
	 * 发票类型
	 */
	@ApiModelProperty(value = "发票类型")
	@Column(name = "invoice_type")
	private String invoiceType;

	/**
	 * 发票抬头
	 */
	@ApiModelProperty(value = "发票抬头")
	@Column(name = "invoice_title")
	private String invoiceTitle;

	/**
	 * 纳税人识别号
	 */
	@ApiModelProperty(value = "纳税人识别号")
	@Column(name = "invoice_tax_code")
	private String invoiceTaxCode;

	/**
	 * 发票注册电话
	 */
	@ApiModelProperty(value = "发票注册电话")
	@Column(name = "invoice_phone")
	private String invoicePhone;

	/**
	 * 发票开户行
	 */
	@ApiModelProperty(value = "发票开户行")
	@Column(name = "invoice_bank")
	private String invoiceBank;

	/**
	 * 发票开户行账号
	 */
	@ApiModelProperty(value = "发票开户行账号")
	@Column(name = "invoice_bank_account")
	private String invoiceBankAccount;

	/**
	 * 发票ID
	 */
	@ApiModelProperty(value = "发票ID")
	@Column(name = "invoice_id")
	private Long invoiceId;

	/**
	 * 主订单ID
	 */
	@ApiModelProperty(value = "主订单ID")
	@Column(name = "master_id")
	private Long masterId;

	/**
	 * 供应商名称
	 */
	@ApiModelProperty(value = "供应商名称")
	@Column(name = "supplier_name")
	private String supplierName;

	/**
	 * 电商订单编号
	 */
	@ApiModelProperty(value = "电商订单编号")
	@Column(name = "emall_sn")
	private String emallSn;

	/**
	 * 是否软删除 0:否1:删除
	 */
	@ApiModelProperty(value = "是否软删除 0:否1:删除")
	@Column(name = "is_delete")
	private Integer isDelete;

	/**
	 * 电商父订单id
	 */
	@ApiModelProperty(value = "电商父订单id")
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 完成时间
	 */
	@ApiModelProperty(value = "完成时间")
	@Column(name = "completed_at")
	private String completedAt;
	/**
	 * 采购单位
	 */
	@Column(name = "purchaser_name")
	private String purchaserName;

	/**
	 * 采购地区编码
	 */
	@Column(name = "purchaser_region_code")
	private String purchaserRegionCode;

	/**
	 * 付款方式
	 */
	@ApiModelProperty(value = "付款方式")
	@Column(name = "payway")
	private String payway;

	/**
	 * 付款方式ID
	 */
	@ApiModelProperty(value = "付款方式ID")
	@Column(name = "pay_id")
	private Long payId;

	/**
	 * 付款金额
	 */
	@ApiModelProperty(value = "付款金额")
	@Column(name = "paid_amount")
	private BigDecimal paidAmount;

	/**
	 * 对账状态 0 未对账 1已对账
	 */
	@Column(name = "bill_status")
	private Integer billStatus;

	/**
	 * 协议总金额
	 */
	@Column(name = "protocol_total")
	private BigDecimal protocolTotal;

	/**
	 * 混合支付时 支付积分数量
	 */
	@Column(name = "pay_integral")
	private BigDecimal payIntegral;

	/**
	 * 混合支付时 个人支付数量
	 */
	@Column(name = "pay_money")
	private BigDecimal payMoney;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public OrderStatus getOrdersState() {
		return ordersState;
	}

	public void setOrdersState(OrderStatus ordersState) {
		this.ordersState = ordersState;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getItemsPrice() {
		return itemsPrice;
	}

	public void setItemsPrice(BigDecimal itemsPrice) {
		this.itemsPrice = itemsPrice;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceTaxCode() {
		return invoiceTaxCode;
	}

	public void setInvoiceTaxCode(String invoiceTaxCode) {
		this.invoiceTaxCode = invoiceTaxCode;
	}

	public String getInvoicePhone() {
		return invoicePhone;
	}

	public void setInvoicePhone(String invoicePhone) {
		this.invoicePhone = invoicePhone;
	}

	public String getInvoiceBank() {
		return invoiceBank;
	}

	public void setInvoiceBank(String invoiceBank) {
		this.invoiceBank = invoiceBank;
	}

	public String getInvoiceBankAccount() {
		return invoiceBankAccount;
	}

	public void setInvoiceBankAccount(String invoiceBankAccount) {
		this.invoiceBankAccount = invoiceBankAccount;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getEmallSn() {
		return emallSn;
	}

	public void setEmallSn(String emallSn) {
		this.emallSn = emallSn;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserRegionCode() {
		return purchaserRegionCode;
	}

	public void setPurchaserRegionCode(String purchaserRegionCode) {
		this.purchaserRegionCode = purchaserRegionCode;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public BigDecimal getProtocolTotal() {
		return protocolTotal;
	}

	public void setProtocolTotal(BigDecimal protocolTotal) {
		this.protocolTotal = protocolTotal;
	}

	public BigDecimal getPayIntegral() {
		return payIntegral;
	}

	public void setPayIntegral(BigDecimal payIntegral) {
		this.payIntegral = payIntegral;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
}
