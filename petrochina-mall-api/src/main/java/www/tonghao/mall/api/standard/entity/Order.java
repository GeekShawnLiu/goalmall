package www.tonghao.mall.api.standard.entity;

import java.math.BigDecimal;
import java.util.List;

import www.tonghao.mall.api.standard.attwrap.CreOrdSku;

public class Order {

	/**
	 * 阳光公采商城的订单单号 
     *必填
	 */
	private String yggcOrder;
	
	private List<CreOrdSku> creOrdSkus;
	
	/**
	 * 收货人
	 * 必填
	 */
	private String name;
	
	/**
	 * 一级地址
	 * 必填
	 */
	private String province;
	
	/**
	 * 二级地址
	 * 必填
	 */
	private String city;
	
	/**
	 * 三级地址
	 * 必填
	 */
	private String county;
	
	/**
	 * 详细地址
	 * 必填
	 */
	private String address;
	/**
	 * 邮编
	 * 必填
	 */
	private String zip;
	
	/**
	 * 座机号
	 * 必填
	 */
	private String phone;
	
	/**
	 * 手机号
	 * 必填
	 */
	private String mobile;
	
	/**
	 * 邮箱
	 * 必填
	 */
	private String email;
	
	/**
	 * 备注
	 * 非必填
	 */
	private String remark;
	
	/**
	 * 采购单位名称
	 * 非必填
	 */
	private String depName;
	
	/**
	 * 发票抬头
	 * 必填
	 */
	private String invoiceTitle;
	
	/**
	 * 1 普通发票;2 增值税发票；3 电子发票；默认为普通发票；
	 * 必填
	 */
	private int invoiceType;
	
	/**
	 * 纳税人识别号
	 * 必填
	 */
	private String invoiceOrgCode;
	
	/**
	 * 增值票收票人
	 * 备注：当 invoice_type=2 且时则此字段必填
	 */
	private String invoiceName;
	
	/**
	 * 注册电话
     *备注：当 invoice_type=2 且时则此字段必填
	 */
	private String invoicePhone;
	
	/**
	 * 开户银行
     *备注：当 invoice_type=2 且时则此字段必填
	 */
	private String invoiceBank;
	
	/**
	 * 开户行帐号
     *备注：当 invoice_type=2 且时则此字段必填
	 */
	private String invoiceBankCode;
	
	/**
	 * 注册地址
     *备注：当 invoice_type=2 且时则此字段必填
	 */
	private String invoiceAddress;
	
	/**
	 * 收票联系电话
	 * 非必填
	 */
	private String invoiceMobile;
	
	/**
	 * 收票地址
	 * 非必填
	 */
	private String invoiceReceiptAddress;
	
	/**
	 * 1：货到付款，2：国库集中支付（转帐）， 3：在线支付， 4：支票
	 * 必填
	 */
	private int payment;
	
	/**
	 * 订单金额（包含运费）
	 * 必填
	 */
	private BigDecimal orderPrice;
	
	/**
	 * 运费
	 * 必填
	 */
	private BigDecimal freight;
	
	/**
	 * 订单模式：1-协议价（默认）)；2-团购模式； 3-特惠模式; 4-阶梯价模式
	 * 必填
	 */
	private int mode;

	
	public String getYggcOrder() {
		return yggcOrder;
	}

	public void setYggcOrder(String yggcOrder) {
		this.yggcOrder = yggcOrder;
	}

	public List<CreOrdSku> getCreOrdSkus() {
		return creOrdSkus;
	}

	public void setCreOrdSkus(List<CreOrdSku> creOrdSkus) {
		this.creOrdSkus = creOrdSkus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceOrgCode() {
		return invoiceOrgCode;
	}

	public void setInvoiceOrgCode(String invoiceOrgCode) {
		this.invoiceOrgCode = invoiceOrgCode;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
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

	public String getInvoiceBankCode() {
		return invoiceBankCode;
	}

	public void setInvoiceBankCode(String invoiceBankCode) {
		this.invoiceBankCode = invoiceBankCode;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getInvoiceMobile() {
		return invoiceMobile;
	}

	public void setInvoiceMobile(String invoiceMobile) {
		this.invoiceMobile = invoiceMobile;
	}

	public String getInvoiceReceiptAddress() {
		return invoiceReceiptAddress;
	}

	public void setInvoiceReceiptAddress(String invoiceReceiptAddress) {
		this.invoiceReceiptAddress = invoiceReceiptAddress;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	
	
	
}
