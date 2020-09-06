package www.tonghao.mall.api.sn.entity;

import java.util.List;

import com.suning.api.entity.govbus.MixpayorderAddRequest.Sku;

public class Order {
  /**
   * 详细地址
   * 必填
   */
  private String address;
  
  /**
   * 订单金额
   * 必填
   */
  private String amount;
  
  /**
   * 市编码
   * 必填
   */
  private String cityId;
  
  /**
   * 子公司账号(母公司下单：不传；子公司下单传：传子公司账号)
   * 看情况传
   */
  private String companyCustNo;
  
  /**
   * 区/县编码
   * 必填
   */
  private String countyId;
  
  /**
   * 邮箱
   * 非必填
   */
  private String email;
  
  /**
   * 发票内容：1-明细（invoiceState是否开票=1时必填）
   * 非必填
   */
  private String invoiceContent;
  
  /**
   * 是否开发票(1=开，0=不开)
   * 必填
   */
  private String invoiceState;
  
  /**
   * 发票抬头（invoiceState是否开票=1 时必填）
   */
  private String invoiceTitle;
  
  /**
   * 发票类型：1-增值税专票；
   * 2-普票（即卷票，票随货走）；
   * 3-增值税普票（即平推式普票，票货分离）；
   * 4-电子发票 ，（invoiceState是否开票=1时必填）
   */
  private String invoiceType;
  
  /**
   * 手机号
   * 必填
   */
  private String mobile;
  
  /**
   * 订单类型(0.实时型订单 1. 预占型订单)
   * 必填
   */
  private String orderType;
  
  /**
   * 支付方式(01-在线支付-易付宝；03-货到付款现金；04-货到付款POS；09-预付款；08-账期；020-在线支付-网银 1-第三方支付；)
   * 必填
   */
  private String payment;
  
  /**
   * 省编码
   * 必填
   */
  private String provinceId;
  
  /**
   * 收货人(收货人姓名不能超过12个汉字)
   * 必填
   */
  private String receiverName;
  
  /**
   * 备注
   * 非必填
   */
  private String remark;
  
  /**
   * 运费
   * 必填
   */
  private String servFee;
  
  /**
   * 纳税人识别号
   * 必填
   */
  private String taxNo;
  
  /**
   * 座机号
   * 非必填
   */
  private String telephone;
  
  
  /**
   * 镇编码
   * 非必填
   */
  private String townId;
  
  /**
   * 客户交易单号
   * 必填
   */
  private String tradeNo;
  
  /**
   * 邮编
   * 非必填
   */
  private String zip;
  
  
  private List<Sku> sku;

  /**
   * 增票注册电话（发票类型为1时必传）
   */
  private String regTel;
  
  /**
   * 增票银行注册账户（发票类型为1时必传）
   */
  private String regAccount;
  
  /**
   * 增票注册地址（发票类型为1时必传）
   */
  private String regAdd;
  /**
   * 增票注册公司名称（发票类型为1时必传）
   */
  private String companyName;
  /**
   * 增票注册银行（发票类型为1时必传）
   */
  private String regBank;
  
  /**
   * 收票件人姓名（发票类型为1/3时必传）
   */
  private String consigneeName;
  /**
   * 收票件人地址（发票类型为1/3时必传）
   */
  private String consigneeAddress;
  /**
   * 收票件人电话（发票类型为1/3时必传）
   */
  private String consigneeMobileNum;

public String getAddress() {
	return address;
}


public void setAddress(String address) {
	this.address = address;
}


public String getAmount() {
	return amount;
}


public void setAmount(String amount) {
	this.amount = amount;
}


public String getCityId() {
	return cityId;
}


public void setCityId(String cityId) {
	this.cityId = cityId;
}


public String getCompanyCustNo() {
	return companyCustNo;
}


public void setCompanyCustNo(String companyCustNo) {
	this.companyCustNo = companyCustNo;
}


public String getCountyId() {
	return countyId;
}


public void setCountyId(String countyId) {
	this.countyId = countyId;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public String getInvoiceContent() {
	return invoiceContent;
}


public void setInvoiceContent(String invoiceContent) {
	this.invoiceContent = invoiceContent;
}


public String getInvoiceState() {
	return invoiceState;
}


public void setInvoiceState(String invoiceState) {
	this.invoiceState = invoiceState;
}


public String getInvoiceTitle() {
	return invoiceTitle;
}


public void setInvoiceTitle(String invoiceTitle) {
	this.invoiceTitle = invoiceTitle;
}


public String getInvoiceType() {
	return invoiceType;
}


public void setInvoiceType(String invoiceType) {
	this.invoiceType = invoiceType;
}


public String getMobile() {
	return mobile;
}


public void setMobile(String mobile) {
	this.mobile = mobile;
}


public String getOrderType() {
	return orderType;
}


public void setOrderType(String orderType) {
	this.orderType = orderType;
}


public String getPayment() {
	return payment;
}


public void setPayment(String payment) {
	this.payment = payment;
}


public String getProvinceId() {
	return provinceId;
}


public void setProvinceId(String provinceId) {
	this.provinceId = provinceId;
}


public String getReceiverName() {
	return receiverName;
}


public void setReceiverName(String receiverName) {
	this.receiverName = receiverName;
}


public String getRemark() {
	return remark;
}


public void setRemark(String remark) {
	this.remark = remark;
}


public String getServFee() {
	return servFee;
}


public void setServFee(String servFee) {
	this.servFee = servFee;
}


public String getTaxNo() {
	return taxNo;
}


public void setTaxNo(String taxNo) {
	this.taxNo = taxNo;
}


public String getTelephone() {
	return telephone;
}


public void setTelephone(String telephone) {
	this.telephone = telephone;
}


public String getTownId() {
	return townId;
}


public void setTownId(String townId) {
	this.townId = townId;
}


public String getTradeNo() {
	return tradeNo;
}


public void setTradeNo(String tradeNo) {
	this.tradeNo = tradeNo;
}


public String getZip() {
	return zip;
}


public void setZip(String zip) {
	this.zip = zip;
}


public List<Sku> getSku() {
	return sku;
}


public void setSku(List<Sku> sku) {
	this.sku = sku;
}


public String getRegTel() {
	return regTel;
}


public void setRegTel(String regTel) {
	this.regTel = regTel;
}


public String getRegAccount() {
	return regAccount;
}


public void setRegAccount(String regAccount) {
	this.regAccount = regAccount;
}


public String getRegAdd() {
	return regAdd;
}


public void setRegAdd(String regAdd) {
	this.regAdd = regAdd;
}


public String getCompanyName() {
	return companyName;
}


public void setCompanyName(String companyName) {
	this.companyName = companyName;
}


public String getRegBank() {
	return regBank;
}


public void setRegBank(String regBank) {
	this.regBank = regBank;
}


public String getConsigneeName() {
	return consigneeName;
}


public void setConsigneeName(String consigneeName) {
	this.consigneeName = consigneeName;
}


public String getConsigneeAddress() {
	return consigneeAddress;
}


public void setConsigneeAddress(String consigneeAddress) {
	this.consigneeAddress = consigneeAddress;
}


public String getConsigneeMobileNum() {
	return consigneeMobileNum;
}


public void setConsigneeMobileNum(String consigneeMobileNum) {
	this.consigneeMobileNum = consigneeMobileNum;
}


  
}
