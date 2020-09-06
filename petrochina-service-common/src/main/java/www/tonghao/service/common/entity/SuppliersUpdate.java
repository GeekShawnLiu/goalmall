package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "suppliers_update")
public class SuppliersUpdate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 供应商名称
	 */
	@ApiModelProperty(value = "供应商名称")
	private String name;

	/**
	 * 简称
	 */
	@ApiModelProperty(value = "简称")
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 法人姓名
	 */
	@ApiModelProperty(value = "法人姓名")
	@Column(name = "legal_name")
	private String legalName;

	/**
	 * 法人公民身份证号
	 */
	@ApiModelProperty(value = "法人公民身份证号")
	@Column(name = "legal_id_number")
	private String legalIdNumber;

	/**
	 * 法人身份证复印件
	 */
	@Column(name = "legal_id_card")
	private String legalIdCard;

	/**
	 * 联系人姓名
	 */
	@ApiModelProperty(value = "联系人姓名")
	@Column(name = "contacts_name")
	private String contactsName;

	/**
	 * 联系人传真
	 */
	@ApiModelProperty(value = "联系人传真")
	@Column(name = "contacts_fax")
	private String contactsFax;

	/**
	 * 联系人固定电话
	 */
	@ApiModelProperty(value = "联系人固定电话")
	@Column(name = "contacts_phone")
	private String contactsPhone;

	/**
	 * 成立日期
	 */
	@ApiModelProperty(value = "成立日期")
	@Column(name = "register_date")
	private String registerDate;

	/**
	 * 企业网址
	 */
	@ApiModelProperty(value = "企业网址")
	@Column(name = "company_website")
	private String companyWebsite;

	/**
	 * 企业注册地址
	 */
	@ApiModelProperty(value = "企业注册地址")
	@Column(name = "register_address")
	private String registerAddress;

	/**
	 * 企业注册地邮编
	 */
	@ApiModelProperty(value = "企业注册地邮编")
	@Column(name = "register_zip_code")
	private String registerZipCode;

	/**
	 * 所属行业
	 */
	@ApiModelProperty(value = "所属行业")
	@Column(name = "industry")
	private String industry;

	/**
	 * 统一社会信用代码
	 */
	@ApiModelProperty(value = "统一社会信用代码")
	@Column(name = "credit_code")
	private String creditCode;

	/**
	 * 营业执照扫描件
	 */
	@Column(name = "licence_img")
	private String licenceImg;

	/**
	 * 注册资本（人民币：万元）
	 */
	@ApiModelProperty(value = "注册资本")
	@Column(name = "registered_capital")
	private BigDecimal registeredCapital;

	/**
	 * 注册币种
	 */
	@ApiModelProperty(value = "注册币种")
	@Column(name = "currency_type")
	private String currencyType;

	/**
	 * 企业性质
	 */
	@ApiModelProperty(value = "企业性质")
	@Column(name = "nature")
	private Long nature;

    /**
     * 营业执照有效期
     */
    @ApiModelProperty(value="营业执照有效期")
    @Column(name = "licence_validity")
    private String licenceValidity;
	
	/**
	 * 营业期限结束时间
	 */
	@ApiModelProperty(value = "营业期限结束时间")
	@Column(name = "licence_validity_end")
	private String licenceValidityEnd;

	/**
	 * 一般纳税人资质
	 */
	@ApiModelProperty(value = "一般纳税人资质")
	@Column(name = "taxpayer_qualification")
	private String taxpayerQualification;

	/**
	 * 企业简介
	 */
	@ApiModelProperty(value = "企业简介")
	@Column(name = "remark")
	private String remark;
	
	
	@ApiModelProperty(value = "经营范围")
	@Column(name = "business_scope")
	private String businessScope;

	/**
	 * 变更供应商id
	 */
	@ApiModelProperty(value = "变更供应商id")
	@Column(name = "supplier_id")
	private Long supplierId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@Column(name = "user_login_name")
	private String userLoginName;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@Column(name = "user_real_name")
	private String userRealName;

	@ApiModelProperty(value = "用户电话")
	@Column(name = "user_mobile")
	private String userMobile;

	@ApiModelProperty(value = "用户邮箱")
	@Column(name = "user_email")
	private String userEmail;

	@ApiModelProperty(value = "店铺名称")
	@Column(name = "shop_name")
	private String shopName;

	@ApiModelProperty(value = "店铺logo")
	@Column(name = "shop_logo")
	private String shopLogo;
	
	@ApiModelProperty(value = "品目id")
	@Transient
	private String catalogsId;
	
	@ApiModelProperty(value = "品目名称")
	@Transient
	private String catalogsName;

	@ApiModelProperty(value = "客服邮箱")
	@Column(name = "customer_service_email")
	private String customerServiceEmail;

	@ApiModelProperty(value = "客服联系电话")
	@Column(name = "customer_service_phone")
	private String customerServicePhone;

	@ApiModelProperty(value = "编号")
	@Column(name = "sn")
	private String sn;

	@ApiModelProperty(value = "审核状态   1审核中  2审核通过   3审核驳回")
	@Column(name = "audit_status")
	private Integer auditStatus;

	@ApiModelProperty(value = "是否删除")
	@Column(name = "is_delete")
	private Integer isDelete;

	@ApiModelProperty(value = "提交时间")
	@Column(name = "submit_at")
	private String submitAt;

	@ApiModelProperty(value = "审核时间")
	@Column(name = "audit_at")
	private String auditAt;
	
	@ApiModelProperty(value = "审核原因")
	@Column(name = "reason")
	private String reason;
	
	@ApiModelProperty(value = "用户id")
	@Column(name = "user_id")
	private Long userId;
	
	@ApiModelProperty(value = "店铺id")
	@Column(name = "shop_id")
	private Long shopId;
	
	@Transient
	private Users users;
	
	@Transient
	private Shop shop;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalIdNumber() {
		return legalIdNumber;
	}

	public void setLegalIdNumber(String legalIdNumber) {
		this.legalIdNumber = legalIdNumber;
	}

	public String getLegalIdCard() {
		return legalIdCard;
	}

	public void setLegalIdCard(String legalIdCard) {
		this.legalIdCard = legalIdCard;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsFax() {
		return contactsFax;
	}

	public void setContactsFax(String contactsFax) {
		this.contactsFax = contactsFax;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getRegisterZipCode() {
		return registerZipCode;
	}

	public void setRegisterZipCode(String registerZipCode) {
		this.registerZipCode = registerZipCode;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getLicenceImg() {
		return licenceImg;
	}

	public void setLicenceImg(String licenceImg) {
		this.licenceImg = licenceImg;
	}

	public BigDecimal getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(BigDecimal registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Long getNature() {
		return nature;
	}

	public void setNature(Long nature) {
		this.nature = nature;
	}

	public String getLicenceValidityEnd() {
		return licenceValidityEnd;
	}

	public void setLicenceValidityEnd(String licenceValidityEnd) {
		this.licenceValidityEnd = licenceValidityEnd;
	}

	public String getTaxpayerQualification() {
		return taxpayerQualification;
	}

	public void setTaxpayerQualification(String taxpayerQualification) {
		this.taxpayerQualification = taxpayerQualification;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getCustomerServiceEmail() {
		return customerServiceEmail;
	}

	public void setCustomerServiceEmail(String customerServiceEmail) {
		this.customerServiceEmail = customerServiceEmail;
	}

	public String getCustomerServicePhone() {
		return customerServicePhone;
	}

	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(String submitAt) {
		this.submitAt = submitAt;
	}

	public String getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(String auditAt) {
		this.auditAt = auditAt;
	}

	public String getCatalogsId() {
		return catalogsId;
	}

	public void setCatalogsId(String catalogsId) {
		this.catalogsId = catalogsId;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getLicenceValidity() {
		return licenceValidity;
	}

	public void setLicenceValidity(String licenceValidity) {
		this.licenceValidity = licenceValidity;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
}
