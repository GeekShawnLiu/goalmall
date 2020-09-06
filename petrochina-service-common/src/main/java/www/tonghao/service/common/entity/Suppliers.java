package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="供应商")
public class Suppliers extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value="供应商名称")
    private String name;

    /**
     * 法人姓名
     */
    @ApiModelProperty(value="法人姓名")
    @Column(name = "legal_name")
    private String legalName;

    /**
     * 简称
     */
    @ApiModelProperty(value="简称")
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    @Column(name = "contacts_name")
    private String contactsName;

    /**
     * 成立日期
     */
    @ApiModelProperty(value="成立日期")
    @Column(name = "register_date")
    private String registerDate;

    /**
     * 企业网址
     */
    @ApiModelProperty(value="企业网址")
    @Column(name = "company_website")
    private String companyWebsite;

    /**
     * 企业注册地址
     */
    @ApiModelProperty(value="企业注册地址")
    @Column(name = "register_address")
    private String registerAddress;

    /**
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 营业执照有效期
     */
    @ApiModelProperty(value="营业执照有效期")
    @Column(name = "licence_validity")
    private String licenceValidity;

    /**
     * 营业执照扫描件
     */
    @Column(name = "licence_img")
    private String licenceImg;

    /**
     * 银行账号
     */
    @ApiModelProperty(value="银行账号")
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 基本账户开户银行
     */
    @ApiModelProperty(value="开户银行")
    @Column(name = "opening_bank")
    private String openingBank;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value="统一社会信用代码")
    @Column(name = "credit_code")
    private String creditCode;

    /**
     * 注册资本（人民币：万元）
     */
    @ApiModelProperty(value="注册资本")
    @Column(name = "registered_capital")
    private BigDecimal registeredCapital;

    /**
     * 法人公民身份证号
     */
    @ApiModelProperty(value="法人公民身份证号")
    @Column(name = "legal_id_number")
    private String legalIdNumber;

    /**
     * 法人身份证复印件
     */
    @Column(name = "legal_id_card")
    private String legalIdCard;

    /**
     * 法人身份证有效期
     */
    @ApiModelProperty(value="法人身份证有效期")
    @Column(name = "legal_id_card_validity")
    private String legalIdCardValidity;

    /**
     * 企业注册地邮编
     */
    @ApiModelProperty(value="企业注册地邮编")
    @Column(name = "register_zip_code")
    private String registerZipCode;

    /**
     * 生产或经营地址
     */
    @ApiModelProperty(value="生产或经营地址")
    @Column(name = "production_business_address")
    private String productionBusinessAddress;

    /**
     * 联系人传真
     */
    @ApiModelProperty(value="联系人传真")
    @Column(name = "contacts_fax")
    private String contactsFax;

    /**
     * 联系人固定电话
     */
    @ApiModelProperty(value="联系人固定电话")
    @Column(name = "contacts_phone")
    private String contactsPhone;

    /**
     * 联系人手机号码
     */
    @ApiModelProperty(value="联系人手机号码")
    @Column(name = "contacts_mobile")
    private String contactsMobile;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty(value="联系人邮箱")
    @Column(name = "contacts_email")
    private String contactsEmail;
    
    /**
     * 企业性质
     */
    @ApiModelProperty(value="企业性质")
    private Long nature;
    
    
    /**
     * 注册时间
     */
    @ApiModelProperty(value="注册时间")
    @Column(name = "registration_time")
    private String registrationTime;
    
    /**
     *最新提交时间
     */
    @ApiModelProperty(value="最新提交时间")
    @Column(name = "submission_time")
    private String submissionTime;
    
    /**
     *最新审核日期
     */
    @ApiModelProperty(value="最新审核日期")
    @Column(name = "latest_review_time")
    private String latestReviewTime;
    
    /**
     *入库时间
     */
    @ApiModelProperty(value="入库时间")
    @Column(name = "storage_time")
    private String storageTime;
    
    /**
     *默认0提交审核，1审核通过，2审核不通过
     */
    @ApiModelProperty(value="默认0提交审核，1审核通过，2审核不通过")
    @Column(name = "status")
    private Integer status;
    
    
    /**
     *企业描述
     */
    @ApiModelProperty(value="企业描述")
    @Column(name = "remark")
    private String remark;
    
    /**
     * 供应商Logo地址
     */
    @ApiModelProperty(value="供应商Logo地址")
    @Column(name="logo_url")
    private String logoUrl;
    
    
    /**
     *供应商code
     */
    @ApiModelProperty(value="供应商code")
    private String code;
    
    @ApiModelProperty(value="供应商排序")
    private Float priority;
    
    @Column(name="native_name")
    private String nativeName;//本地供应商名称
    
    @Column(name="native_openbank")
    private String nativeOpenbank;//本地供应商开户行
    
    @Column(name="native_openbank_account")
    private String nativeOpenbankAccount;//本地供应商开户行
    
    @Column(name="credit_code_file")
    private String creditCodeFile;//统一社会信用代码复印件
    
    @Column(name="industry_category")
    private Long industryCategory;//企业类别
    
    @Column(name="areas")
    private String areas;//地区
    
    @Column(name="is_freeze")
    private Integer isFreeze;//1冻结，0未冻结
    
    @Transient 
    private String industryCategoryName;
    
    @Transient
    private String supplierScaleName;
    
    @Column(name="supplier_scale")
    private Long supplierScale;//供应商规模
    
    
    @Transient
    private Integer supplierUserCount;
    
    /**
     * 供应商企业服务
     */
    @ApiModelProperty(value="供应商企业服务")
    @Transient
    private String serviceContent;
    
    /**
     *其他资质
     */
    @ApiModelProperty(value="其他资质")
    @Transient
    private String otherQualification;
    
    /**
     *业务范围
     */
    @ApiModelProperty(value="业务范围")
    @Column(name="business_scope")
    private String businessScope;
    
    @Transient
    private Users users;
    
    @Transient
    private String agreementCode;
    
    @Transient
    private Integer fixType;
    
    @Transient
    private String bussinessIds;
    
    @Transient
    private String bussinessNames;
    @Transient
    private String brand;
    
    @Transient
    private List<Products> productList;
    
    @Transient
    private String areaIds;
    
    @ApiModelProperty(value="供应商标签1普通供应商  2扶贫供应商")
    @Column(name = "label_type")
    private Integer labelType;
    
    @Transient
    private List<Users> userList;
    
    @Column(name="register_address_id")
    private Long registerAddressId;//企业注册地址id
    
    @Column(name="industry")
    private String industry;//所属行业
    
    @Column(name="currency_type")
    private String currencyType;//注册币种
    
    @Column(name="licence_validity_end")
    private String licenceValidityEnd;//营业期限结束时间
    
    @Column(name="taxpayer_qualification")
    private String taxpayerQualification;//一般纳税人资质
    
    @Column(name="user_id")
    private Long userId;//用户id
    
    @Column(name="is_fill_bank")
    private Integer isFillBank;//是否填写银行信息:0否,1是
    
    @Transient
    private Shop shop; //店铺
    
    @Transient
    private SupplierAudit supplierAudit; //供应商审核
    
    @Column(name="freight")
    private BigDecimal freight;//订单起送价
    
    @Column(name="is_price")
    private Integer isPrice;//是否改价
    
    
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
     * 获取供应商名称
     *
     * @return name - 供应商名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置供应商名称
     *
     * @param name 供应商名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取法人姓名
     *
     * @return legal_name - 法人姓名
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * 设置法人姓名
     *
     * @param legalName 法人姓名
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * 获取简称
     *
     * @return nick_name - 简称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置简称
     *
     * @param nickName 简称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取联系人姓名
     *
     * @return contacts_name - 联系人姓名
     */
    public String getContactsName() {
        return contactsName;
    }

    /**
     * 设置联系人姓名
     *
     * @param contactsName 联系人姓名
     */
    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    /**
     * 获取成立日期
     *
     * @return register_date - 成立日期
     */
    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置成立日期
     *
     * @param registerDate 成立日期
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 获取企业网址
     *
     * @return company_website - 企业网址
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    /**
     * 设置企业网址
     *
     * @param companyWebsite 企业网址
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    /**
     * 获取企业注册地址
     *
     * @return register_address - 企业注册地址
     */
    public String getRegisterAddress() {
        return registerAddress;
    }

    /**
     * 设置企业注册地址
     *
     * @param registerAddress 企业注册地址
     */
    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete 是否删除
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取营业执照有效期
     *
     * @return licence_validity - 营业执照有效期
     */
    public String getLicenceValidity() {
        return licenceValidity;
    }

    /**
     * 设置营业执照有效期
     *
     * @param licenceValidity 营业执照有效期
     */
    public void setLicenceValidity(String licenceValidity) {
        this.licenceValidity = licenceValidity;
    }

    /**
     * 获取营业执照扫描件
     *
     * @return licence_img - 营业执照扫描件
     */
    public String getLicenceImg() {
        return licenceImg;
    }

    /**
     * 设置营业执照扫描件
     *
     * @param licenceImg 营业执照扫描件
     */
    public void setLicenceImg(String licenceImg) {
        this.licenceImg = licenceImg;
    }

    /**
     * 获取银行账号
     *
     * @return bank_account - 银行账号
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置银行账号
     *
     * @param bankAccount 银行账号
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取基本账户开户银行
     *
     * @return opening_bank - 基本账户开户银行
     */
    public String getOpeningBank() {
        return openingBank;
    }

    /**
     * 设置基本账户开户银行
     *
     * @param openingBank 基本账户开户银行
     */
    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    /**
     * 获取统一社会信用代码
     *
     * @return credit_code - 统一社会信用代码
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * 设置统一社会信用代码
     *
     * @param creditCode 统一社会信用代码
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    /**
     * 获取注册资本（人民币：万元）
     *
     * @return registered_capital - 注册资本（人民币：万元）
     */
    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    /**
     * 设置注册资本（人民币：万元）
     *
     * @param registeredCapital 注册资本（人民币：万元）
     */
    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    /**
     * 获取法人公民身份证号
     *
     * @return legal_id_number - 法人公民身份证号
     */
    public String getLegalIdNumber() {
        return legalIdNumber;
    }

    /**
     * 设置法人公民身份证号
     *
     * @param legalIdNumber 法人公民身份证号
     */
    public void setLegalIdNumber(String legalIdNumber) {
        this.legalIdNumber = legalIdNumber;
    }

    /**
     * 获取法人身份证复印件
     *
     * @return legal_id_card - 法人身份证复印件
     */
    public String getLegalIdCard() {
        return legalIdCard;
    }

    /**
     * 设置法人身份证复印件
     *
     * @param legalIdCard 法人身份证复印件
     */
    public void setLegalIdCard(String legalIdCard) {
        this.legalIdCard = legalIdCard;
    }

    /**
     * 获取法人身份证有效期
     *
     * @return legal_id_card_validity - 法人身份证有效期
     */
    public String getLegalIdCardValidity() {
        return legalIdCardValidity;
    }

    /**
     * 设置法人身份证有效期
     *
     * @param legalIdCardValidity 法人身份证有效期
     */
    public void setLegalIdCardValidity(String legalIdCardValidity) {
        this.legalIdCardValidity = legalIdCardValidity;
    }

    /**
     * 获取企业注册地邮编
     *
     * @return register_zip_code - 企业注册地邮编
     */
    public String getRegisterZipCode() {
        return registerZipCode;
    }

    /**
     * 设置企业注册地邮编
     *
     * @param registerZipCode 企业注册地邮编
     */
    public void setRegisterZipCode(String registerZipCode) {
        this.registerZipCode = registerZipCode;
    }

    /**
     * 获取生产或经营地址
     *
     * @return production_business_address - 生产或经营地址
     */
    public String getProductionBusinessAddress() {
        return productionBusinessAddress;
    }

    /**
     * 设置生产或经营地址
     *
     * @param productionBusinessAddress 生产或经营地址
     */
    public void setProductionBusinessAddress(String productionBusinessAddress) {
        this.productionBusinessAddress = productionBusinessAddress;
    }

    /**
     * 获取联系人传真
     *
     * @return contacts_fax - 联系人传真
     */
    public String getContactsFax() {
        return contactsFax;
    }

    /**
     * 设置联系人传真
     *
     * @param contactsFax 联系人传真
     */
    public void setContactsFax(String contactsFax) {
        this.contactsFax = contactsFax;
    }

    /**
     * 获取联系人固定电话
     *
     * @return contacts_phone - 联系人固定电话
     */
    public String getContactsPhone() {
        return contactsPhone;
    }

    /**
     * 设置联系人固定电话
     *
     * @param contactsPhone 联系人固定电话
     */
    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    /**
     * 获取联系人手机号码
     *
     * @return contacts_mobile - 联系人手机号码
     */
    public String getContactsMobile() {
        return contactsMobile;
    }

    /**
     * 设置联系人手机号码
     *
     * @param contactsMobile 联系人手机号码
     */
    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    /**
     * 获取联系人邮箱
     *
     * @return contacts_email - 联系人邮箱
     */
    public String getContactsEmail() {
        return contactsEmail;
    }

    /**
     * 设置联系人邮箱
     *
     * @param contactsEmail 联系人邮箱
     */
    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    /**
     * 获取企业性质
     *
     * @return nature - 企业性质
     */
    public Long getNature() {
        return nature;
    }

    /**
     * 设置企业性质
     *
     * @param nature 企业性质
     */
    public void setNature(Long nature) {
        this.nature = nature;
    }
    
	public String getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(String registrationTime) {
		this.registrationTime = registrationTime;
	}

	public String getSubmissionTime() {
		return submissionTime;
	}

	public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}

	public String getLatestReviewTime() {
		return latestReviewTime;
	}

	public void setLatestReviewTime(String latestReviewTime) {
		this.latestReviewTime = latestReviewTime;
	}

	public String getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(String storageTime) {
		this.storageTime = storageTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getAgreementCode() {
		return agreementCode;
	}

	public void setAgreementCode(String agreementCode) {
		this.agreementCode = agreementCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public List<Products> getProductList() {
		return productList;
	}

	public void setProductList(List<Products> productList) {
		this.productList = productList;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getOtherQualification() {
		return otherQualification;
	}

	public void setOtherQualification(String otherQualification) {
		this.otherQualification = otherQualification;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Integer getFixType() {
		return fixType;
	}

	public void setFixType(Integer fixType) {
		this.fixType = fixType;
	}

	public String getBussinessIds() {
		return bussinessIds;
	}

	public void setBussinessIds(String bussinessIds) {
		this.bussinessIds = bussinessIds;
	}

	public String getBussinessNames() {
		return bussinessNames;
	}

	public void setBussinessNames(String bussinessNames) {
		this.bussinessNames = bussinessNames;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getPriority() {
		return priority;
	}

	public void setPriority(Float priority) {
		this.priority = priority;
	}
	
	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public String getNativeOpenbank() {
		return nativeOpenbank;
	}

	public void setNativeOpenbank(String nativeOpenbank) {
		this.nativeOpenbank = nativeOpenbank;
	}

	public String getNativeOpenbankAccount() {
		return nativeOpenbankAccount;
	}

	public void setNativeOpenbankAccount(String nativeOpenbankAccount) {
		this.nativeOpenbankAccount = nativeOpenbankAccount;
	}

	public String getCreditCodeFile() {
		return creditCodeFile;
	}

	public void setCreditCodeFile(String creditCodeFile) {
		this.creditCodeFile = creditCodeFile;
	}

	public Long getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(Long industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getIndustryCategoryName() {
		return industryCategoryName;
	}

	public void setIndustryCategoryName(String industryCategoryName) {
		this.industryCategoryName = industryCategoryName;
	}

	public String getSupplierScaleName() {
		return supplierScaleName;
	}

	public void setSupplierScaleName(String supplierScaleName) {
		this.supplierScaleName = supplierScaleName;
	}

	public Long getSupplierScale() {
		return supplierScale;
	}

	public void setSupplierScale(Long supplierScale) {
		this.supplierScale = supplierScale;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public Integer getSupplierUserCount() {
		return supplierUserCount;
	}

	public void setSupplierUserCount(Integer supplierUserCount) {
		this.supplierUserCount = supplierUserCount;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}
	
	public Integer getLabelType() {
		return labelType;
	}

	public void setLabelType(Integer labelType) {
		this.labelType = labelType;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public Long getRegisterAddressId() {
		return registerAddressId;
	}

	public void setRegisterAddressId(Long registerAddressId) {
		this.registerAddressId = registerAddressId;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public SupplierAudit getSupplierAudit() {
		return supplierAudit;
	}

	public void setSupplierAudit(SupplierAudit supplierAudit) {
		this.supplierAudit = supplierAudit;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Integer getIsPrice() {
		return isPrice;
	}

	public void setIsPrice(Integer isPrice) {
		this.isPrice = isPrice;
	}

	public Integer getIsFillBank() {
		return isFillBank;
	}

	public void setIsFillBank(Integer isFillBank) {
		this.isFillBank = isFillBank;
	}

	
}