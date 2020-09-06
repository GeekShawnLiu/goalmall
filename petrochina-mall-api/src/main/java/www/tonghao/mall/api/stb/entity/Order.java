package www.tonghao.mall.api.stb.entity;

import java.util.List;
import java.util.Map;

public class Order {

	private String thirdOrder;//内部订单号
	
	private List<Map<String, String>> Sku;//"skuId":商品编号, "num":商品数量
	
	private String name;//收货人
	
	private String province;//一级地址
	
	private String city;//二级地址
	
	private String county; //三级地址
	
	private String address;//详细地址（收货人)
	
	private String zip;//邮编（收货人）
	
	private String phone;//座机号 (与mobile其中一个有值即可)（收货人）
	
	private String mobile;//手机号 （与phone其中一个有值即可）（收货人）
	
	private String email;//邮箱（订货人邮箱）
	
	private String remark;//备注（少于100字） 非必须
	
	private String creatorName;//订货人姓名
	
	private String creatorPhone;//座机号 (与mobile其中一个有值即可)（订货人）
	
	private String creatorMobile;//手机号 （与phone其中一个有值即可）（订货人）
	
	private String createdTime;//下订单时间（yyyy-MM-dd hh:mm:ss）
	
	private String invoiceState;//是否开发票【1=开，0=不开】【必传】
	
	private String invoiceType;//发票类型【1=普通发票】【必传】2=增值税 4=电子发票
	
	private String companyName;//发票抬头
	
	private String companyAdd;//发票地址  非必填
	
	private String BillingPhone;//注册电话 非必填
	
	private String TaxNo;//税号 非必填
	
	private String BankName;//银行名称 非必填
	
	private String BankAccno;//银行账户 非必填
	
	private String invoiceContent;//发票内容如【1:明细，3：电脑配件，19:耗材，22：办公用品】【必传】
	
	private String payment;//付款方式0：在线支付 1：货到付款 2：赊销 6:供应商代收
	
	private String customerName;//单位名称：用于区别集团下的不同客户

	public String getThirdOrder() {
		return thirdOrder;
	}

	public void setThirdOrder(String thirdOrder) {
		this.thirdOrder = thirdOrder;
	}

	public List<Map<String, String>> getSku() {
		return Sku;
	}

	public void setSku(List<Map<String, String>> sku) {
		Sku = sku;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorPhone() {
		return creatorPhone;
	}

	public void setCreatorPhone(String creatorPhone) {
		this.creatorPhone = creatorPhone;
	}

	public String getCreatorMobile() {
		return creatorMobile;
	}

	public void setCreatorMobile(String creatorMobile) {
		this.creatorMobile = creatorMobile;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getInvoiceState() {
		return invoiceState;
	}

	public void setInvoiceState(String invoiceState) {
		this.invoiceState = invoiceState;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAdd() {
		return companyAdd;
	}

	public void setCompanyAdd(String companyAdd) {
		this.companyAdd = companyAdd;
	}

	public String getBillingPhone() {
		return BillingPhone;
	}

	public void setBillingPhone(String billingPhone) {
		BillingPhone = billingPhone;
	}

	public String getTaxNo() {
		return TaxNo;
	}

	public void setTaxNo(String taxNo) {
		TaxNo = taxNo;
	}

	public String getBankName() {
		return BankName;
	}

	public void setBankName(String bankName) {
		BankName = bankName;
	}

	public String getBankAccno() {
		return BankAccno;
	}

	public void setBankAccno(String bankAccno) {
		BankAccno = bankAccno;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	
}
