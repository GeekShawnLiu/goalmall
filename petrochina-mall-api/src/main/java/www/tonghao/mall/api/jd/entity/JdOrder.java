package www.tonghao.mall.api.jd.entity;

import java.util.List;

public class JdOrder {
	
	/**
	 * 第三方的订单单号 
	 * 必须
	 */
    private String thirdOrder;
    
    /**
     * 商品信息
     * 必须
     */
    private List<OrderSku> sku;
    
    /**
     * 收货人
     * 必须
     */
    private String name;
    
    
    /**
     * 一级地址 
     * 必须
     */
    private String province;
    
    /**
     * 二级地址
     * 必须
     */
    private String city;
    
    /**
     * 三级地址
     * 必须
     */
    private String county;
    
    /**
     * 四级地址  (如果该地区有四级地址，则必须传递四级地址，没有四级地址则传 0) 
     * 必须
     */
    private String town;
    
    /**
     * 详细地址
     * 必须
     * 
     */
    private String address;
    
    /**
     * 邮编
     * 非必须
     */
    private String zip;
    
    /**
     * 座机号  
     * 非必须
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
     * 开票方式(1 为随货开票（普票已废弃，此开票方式针对普票），0 为订单预借，
     * 2 为集中开票 4订单完成后开票（仅电子发票） ) 
     * 必填
     */
    private int invoiceState;
    
    /**
     * 1 普通发票(已废弃) 2 增值税发票 3 电子发票 
     * 必填
     */
    private  int invoiceType;
    
    /**
     * 发票类型：4 个人，5 单位 
     * 必填
     */
    private int selectedInvoiceTitle;
    
    
    /**
     * 发票抬头
     * 发票抬头  (如果 selectedInvoiceTitle=5 则此字段必须) 
     * 必须
     */
    private String companyName;
    
    
    /**
     * 纳税人识别号
     * 必须
     */
    private String regCode;
    
    /**
     * 1:明细，3：电脑配件，19:耗材，22：办公用品备注:若增值发票则只能选 
     * 1 明细 目前只支持1明细   100商品类别
     * 必须
     */
    private int invoiceContent;
    
    /**
     * 支付方式 (1：货到付款，2：邮局付款，4：余额支付，5：公司转账（公对公转账），7：网银钱包，101：金采支付)
     * 必须
     */
    private int paymentType;
    
    /**
     * 使用余额 paymentType=4 时，此值固定是 1 其他支付方式 0 
     * * 必须
     */
    private int isUseBalance;
    
    /**
     * 是否预占库存，0 是预占库存（需要调用确认订单接口），1 是不预占库存  
     * * 必须    
     */
    private int submitState;
    
    /**
     * 增票资质公司名称
     *备注：当invoiceType=2 且invoiceState=1时则此字段必填
     */
    private String regCompanyName;
    
    /**
     * 增票资质注册地址
     *备注：当invoiceType=2 且invoiceState=1时则此字段必填
     */
    private String regAddr;
    
    /**
     * 增票资质注册电话
     *备注：当invoiceType=2 且invoiceState=1时则此字段必填
     */
    private String regPhone;
    
    /**
     * 增票资质注册银行
      *备注：当invoiceType=2 且invoiceState=1时则此字段必填
     */
    private String regBank;
    
    /**
     * 增票资质注册账号（目前仅提供给沃易购使用）
     *备注：当invoiceType=2 且invoiceState=1时则此字段必填
     */
    private String regBankAccount;
    
    /**
     * 必须
     */
    private ExtContent extContents;
    
    
    /**
     * invoiceName 
     * 增值票收票人姓名 
     *备注：当 invoiceType=2 且 invoiceState=1 时则此字段必填 
     */
    private String invoiceName;
    
    /**
     * 增值票收票人电话 
     *备注：当 invoiceType=2 且 invoiceState=1 时则此字段必填 
     */
    private String invoicePhone;
    
    /**
     * 增值票收票人所在省(京东地址编码) 
     *备注：当 invoiceType=2 且 invoiceState=1 时则此字段必填
     */
    private String invoiceProvice;
    
    /**
     * 增值票收票人所在市(京东地址编码) 
      *备注：当 invoiceType=2 且 invoiceState=1 时则此字段必填 
     */
    private String invoiceCity;
    
    /**
     * 增值票收票人所在区/县(京东地址编码) 
     *备注：当 invoiceType=2 且 invoiceState=1 时则此字段必填 
     */
    private String invoiceCounty;
    
    /**
     * 增值票收票人所在地址 
     *备注：当 invoiceType=2 且 invoiceState=1 时则此字段必填
     */
    private String invoiceAddress;
    
    /**
     * 下单价格模式0: 客户端订单价格快照不做验证对比，还是以京东价格正常下单; 
     *1:必需验证客户端订单价格快照，如果快照与京东价格不一致返回下单失败，需要更新商品价格后，重新下单; 
     */
    private int doOrderPriceMode;
    
    /**
     * oOrderPriceMode=1时，必须。
     */
    private List<SkuPrice> orderPriceSnap;
    
    /**
     * 采购单号
     * 长度范围【1-26】 (个别客户有需求，会把这个采购单号打印到物流配送单和发票上面)
     */
    private String poNo;
    
    /**
     * 单账号多点结算时， 根据该字段分别结算(结算单位用于在下单传过来客户实际付款单位，
     * 这个字段我们会写到结算平台，用于运营对账（区分相同PIN下到底是哪个客户下单）)
     * ( 测试账号下面有张三李四买的东西，你们填张三或李四，就可以区分到底谁买的东西了)
     */
    private String customerName;

	public String getThirdOrder() {
		return thirdOrder;
	}

	public void setThirdOrder(String thirdOrder) {
		this.thirdOrder = thirdOrder;
	}

	public List<OrderSku> getSku() {
		return sku;
	}

	public void setSku(List<OrderSku> sku) {
		this.sku = sku;
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

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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

	public int getInvoiceState() {
		return invoiceState;
	}

	public void setInvoiceState(int invoiceState) {
		this.invoiceState = invoiceState;
	}

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	public int getSelectedInvoiceTitle() {
		return selectedInvoiceTitle;
	}

	public void setSelectedInvoiceTitle(int selectedInvoiceTitle) {
		this.selectedInvoiceTitle = selectedInvoiceTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public int getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(int invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public int getIsUseBalance() {
		return isUseBalance;
	}

	public void setIsUseBalance(int isUseBalance) {
		this.isUseBalance = isUseBalance;
	}

	public int getSubmitState() {
		return submitState;
	}

	public void setSubmitState(int submitState) {
		this.submitState = submitState;
	}

	public String getRegCompanyName() {
		return regCompanyName;
	}

	public void setRegCompanyName(String regCompanyName) {
		this.regCompanyName = regCompanyName;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getRegPhone() {
		return regPhone;
	}

	public void setRegPhone(String regPhone) {
		this.regPhone = regPhone;
	}

	public String getRegBank() {
		return regBank;
	}

	public void setRegBank(String regBank) {
		this.regBank = regBank;
	}

	public String getRegBankAccount() {
		return regBankAccount;
	}

	public void setRegBankAccount(String regBankAccount) {
		this.regBankAccount = regBankAccount;
	}

	public ExtContent getExtContents() {
		return extContents;
	}

	public void setExtContents(ExtContent extContents) {
		this.extContents = extContents;
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

	public String getInvoiceProvice() {
		return invoiceProvice;
	}

	public void setInvoiceProvice(String invoiceProvice) {
		this.invoiceProvice = invoiceProvice;
	}

	public String getInvoiceCity() {
		return invoiceCity;
	}

	public void setInvoiceCity(String invoiceCity) {
		this.invoiceCity = invoiceCity;
	}

	public String getInvoiceCounty() {
		return invoiceCounty;
	}

	public void setInvoiceCounty(String invoiceCounty) {
		this.invoiceCounty = invoiceCounty;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public int getDoOrderPriceMode() {
		return doOrderPriceMode;
	}

	public void setDoOrderPriceMode(int doOrderPriceMode) {
		this.doOrderPriceMode = doOrderPriceMode;
	}

	public List<SkuPrice> getOrderPriceSnap() {
		return orderPriceSnap;
	}

	public void setOrderPriceSnap(List<SkuPrice> orderPriceSnap) {
		this.orderPriceSnap = orderPriceSnap;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
    
    
    
    
}
