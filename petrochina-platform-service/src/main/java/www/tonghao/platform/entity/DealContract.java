package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.common.enums.ReviewGrade;
import www.tonghao.common.utils.ChineseMoneyUtil;
import www.tonghao.platform.enums.ContractBizType;
import www.tonghao.platform.enums.DealContractStatus;
import www.tonghao.service.common.base.BaseEntity;


@Table(name = "deal_contract")
public class DealContract extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 合同uuid编号
     */
    @Column(name = "contract_uuid")
    private String contractUuid;

    /**
     * 区域编码
     */
    @Column(name = "region_code")
    private String regionCode;

    /**
     * 合同编号
     */
    private String sn;

    /**
     * 计划编号
     */
    @Column(name = "plan_code")
    private String planCode;

    /**
     * 计划金额
     */
    @Column(name = "plan_amount")
    private BigDecimal planAmount;

    /**
     * 采购项目编号
     */
    @Column(name = "project_code")
    private String projectCode;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 包号
     */
    @Column(name = "project_sub")
    private String projectSub;

    /**
     * 供应商名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 供应商银行账号
     */
    @Column(name = "supplier_bank_account")
    private String supplierBankAccount;

    /**
     * 供应商开户行
     */
    @Column(name = "supplier_bank")
    private String supplierBank;

    /**
     * 供货日期
     */
    @Column(name = "supply_date")
    private String supplyDate;

    /**
     * 配送地址
     */
    @Column(name = "delivery_place")
    private String deliveryPlace;

    /**
     * 交货方式
     */
    @Column(name = "delivery_mode")
    private String deliveryMode;

    /**
     * 代理机构编码
     */
    @Column(name = "agent_code")
    private String agentCode;

    /**
     * 代理机构名称
     */
    @Column(name = "agent_name")
    private String agentName;

    /**
     * 采购人编码
     */
    @Column(name = "unit_code")
    private String unitCode;

    /**
     * 采购人姓名
     */
    @Column(name = "unit_name")
    private String unitName;

    /**
     * 组织形式 2集中采购 3分散采购
     */
    @Column(name = "org_kind")
    private String orgKind;

    /**
     * 采购方式编码  网上超市13，定点08，批量10  【update by yanfeng 2019-01-25】
     */
    @Column(name = "buy_kind")
    private String buyKind;

    /**
     * 是否最后一个合同
     */
    @Column(name = "is_end")
    private Boolean isEnd;

    /**
     * 合同金额
     */
    @Column(name = "total_sum")
    private BigDecimal totalSum;

    /**
     * 预算内金额
     */
    @Column(name = "budget_in")
    private BigDecimal budgetIn;

    /**
     * 财政金额
     */
    @Column(name = "budget_finance")
    private BigDecimal budgetFinance;

    /**
     * 自筹金额
     */
    @Column(name = "budget_self")
    private BigDecimal budgetSelf;

    /**
     * 业务年度
     */
    @Column(name = "bdg_year")
    private Integer bdgYear;

    /**
     * 录入日期 格式yyyyMMddHHmmss
     */
    @Column(name = "input_date")
    private String inputDate;

    /**
     * 下达日期 格式yyyyMMddHHmmss
     */
    @Column(name = "down_date")
    private String downDate;

    /**
     * 附件url
     */
    @Column(name = "atta_url")
    private String attaUrl;

    /**
     * 合同业务类型
     * 业务类型：商城直购订单、网上竞价、定点服务、定点商品、定点工程
     */
    @Column(name = "biz_type")
    private ContractBizType bizType;

    /**
     * 业务关联ID
     */
    @Column(name = "biz_relation_id")
    private Long bizRelationId;

    /**
     * 状态 暂存、已发布、已备案、已作废
     */
    private DealContractStatus status;

    /**
     * 合同模板ID
     */
    @Column(name = "tpl_id")
    private Long tplId;
    
    /**
     * 支付方式
     */
    @ApiModelProperty(value="支付方式")
    @Column(name = "payway")
    private String payway;
    
    @ApiModelProperty(value="用户ID")
    @Column(name="user_id")
    private Long userId;
    
    @ApiModelProperty(value="合同名称")
    private String name;
    
    @Column(name="business_type")
    private Integer businessType;
    
    @Column(name="dep_name")
    private String depName;
    
    @Column(name="dep_id")
    private Long depId;
    /**
     * 是否验收
     */
    @Column(name="is_check")
    private Integer isCheck;
    
    /**
     * 报价方式（1按人工 2按面积）
     */
    @Column(name="quote_type")
    private Integer quoteType;
    
    /**
     * 服务期内支付周期（月份）
     */
    @Column(name="pay_service_month")
    private Integer payServiceMonth;
    
    /**
     * 合同生效支付率
     */
    @Column(name="pay_contract_rate")
    private Integer payContractRate;
    
    /**
	 * 合同生效支付率金额
	 * @return
	 */
	public BigDecimal getPayContractRateNum(){
		if(getTotalSum()!= null && getPayContractRate() != null){
			BigDecimal total = getTotalSum();//总金额
			BigDecimal big2 = new BigDecimal(getPayContractRate().toString());//支付比例数值
			BigDecimal bigRate = big2.divide(new BigDecimal(100), 2 ,BigDecimal.ROUND_HALF_UP);//支付比例（小数）
			return bigRate.multiply(total).setScale(2 ,BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}
	
	 /**
	 * 合同生效支付率金额大写
	 * @return
	 */
	public String getBigPayContractRateNum(){
		if(getPayContractRateNum() != null){
			return ChineseMoneyUtil.getChineseMoney(getPayContractRateNum());
		}
		return null;
	}
    
    /**
     * 服务期限
     */
    @Column(name="service_time")
    private String serviceTime;
    
    /**
     * 入场接管日
     */
    @Column(name="take_over_days")
    private Integer takeOverDays;
    
    /**
     * 合同验收支付率
     */
    @Column(name="pay_check_rate")
    private Integer payCheckRate;
    
    /**
   	 * 合同验收支付率金额
   	 * @return
   	 */
   	public BigDecimal getPayCheckRateNum(){
   		if(getTotalSum()!= null && getPayCheckRate() != null){
   			BigDecimal total = getTotalSum();//总金额
   			BigDecimal big2 = new BigDecimal(getPayCheckRate().toString());//支付比例数值
   			BigDecimal bigRate = big2.divide(new BigDecimal(100), 2 ,BigDecimal.ROUND_HALF_UP);//支付比例（小数）
   			return bigRate.multiply(total).setScale(2 ,BigDecimal.ROUND_HALF_UP);
   		}
   		return null;
   	}
   	
   	 /**
   	 * 合同生效支付率金额大写
   	 * @return
   	 */
   	public String getBigPaygetPayCheckRateRateNum(){
   		if(getPayCheckRateNum() != null){
   			return ChineseMoneyUtil.getChineseMoney(getPayCheckRateNum());
   		}
   		return null;
   	}
    
    /**
     * 服务承诺
     */
    @Column(name="service_promise")
    private String servicePromise;
    
    /**
     * 供应商ID
     */
    @Column(name="supplier_id")
    private Long supplierId;
    
    /**
     * 服务要求
     */
    @Column(name="service_require")
    private String serviceRequire;
    
    /**
     * 主卡卡号
     */
    @Column(name="main_card_number")
    private String mainCardNumber;
    
    /**
     * 副卡卡号
     */
    @Column(name="card_number")
    private String cardNumber;
    
    /**
     * 持卡人
     */
    @Column(name="holder")
    private String holder;
    
    /**
     * 持卡人电话
     */
    @Column(name="tel")
    private String tel;
    
    /**
     * 身份证号
     */
    @Column(name="identity_card")
    private String identityCard;
    
    /**
     * 车牌号
     */
    @Column(name="license_plate_num")
    private String licensePlateNum;
    
    /**
     * 车架号
     */
    @Column(name="frame_num")
    private String frameNum;
    
    /**
     * 厂牌型号
     */
    @Column(name="factory_plate_model")
    private String factoryPlateModel;
    
    /**
     * 发动机号
     */
    @Column(name="engine_number")
    private String engineNumber;
    
    /**
     * 初次登记日期
     */
    @Column(name="first_register_date")
    private String firstRegisterDate;
    
    /**
     * 送修日期
     */
    @Column(name = "start_date")
    private String startDate;
    
    /**
     * 出厂日期
     */
    @Column(name = "end_date")
    private String endDate;
    
    /**
     * 工时费用
     */
    @Column(name = "hours_total_fee")
    private BigDecimal hoursTotalFee;
    
    /**
     * 材料费用
     */
    @Column(name = "material_total_fee")
    private BigDecimal materialTotalFee;
    
    /**
     * 质保期
     */
    @Column(name = "guarantee_period")
    private Integer guaranteePeriod;
    
    /**
     * 评价级别
     */
    @Column(name = "review_grade")
    private ReviewGrade reviewGrade;
    
    /**
     * 采购凭证路径
     */
    @Column(name = "receipt_url")
    private String receiptUrl;
    
	@Transient
    private List<Pays> pays;
	

	public List<Pays> getPays() {
		return pays;
	}

	public void setPays(List<Pays> pays) {
		this.pays = pays;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	@Transient
    private DealContractTpl dealContractTpl;
	
	@Transient
	private String areaName;
    

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public DealContractTpl getDealContractTpl() {
		return dealContractTpl;
	}

	public void setDealContractTpl(DealContractTpl dealContractTpl) {
		this.dealContractTpl = dealContractTpl;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	@Transient
    private List<DealContractDetails> dealContractDetails;
    
    public List<DealContractDetails> getDealContractDetails() {
		return dealContractDetails;
	}

	public void setDealContractDetails(List<DealContractDetails> dealContractDetails) {
		this.dealContractDetails = dealContractDetails;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
     * 合同明细
     */
    @Transient
    private List<DealContractDetails> details;

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
     * 获取合同uuid编号
     *
     * @return contract_uuid - 合同uuid编号
     */
    public String getContractUuid() {
        return contractUuid;
    }

    /**
     * 设置合同uuid编号
     *
     * @param contractUuid 合同uuid编号
     */
    public void setContractUuid(String contractUuid) {
        this.contractUuid = contractUuid;
    }

    /**
     * 获取区域编码
     *
     * @return region_code - 区域编码
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * 设置区域编码
     *
     * @param regionCode 区域编码
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * 获取合同编号
     *
     * @return sn - 合同编号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置合同编号
     *
     * @param sn 合同编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取计划编号
     *
     * @return plan_code - 计划编号
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * 设置计划编号
     *
     * @param planCode 计划编号
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    /**
     * 获取计划金额
     *
     * @return plan_amount - 计划金额
     */
    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    /**
     * 设置计划金额
     *
     * @param planAmount 计划金额
     */
    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    /**
     * 获取采购项目编号
     *
     * @return project_code - 采购项目编号
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * 设置采购项目编号
     *
     * @param projectCode 采购项目编号
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * 获取项目名称
     *
     * @return project_name - 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取包号
     *
     * @return project_sub - 包号
     */
    public String getProjectSub() {
        return projectSub;
    }

    /**
     * 设置包号
     *
     * @param projectSub 包号
     */
    public void setProjectSub(String projectSub) {
        this.projectSub = projectSub;
    }

    /**
     * 获取供应商名称
     *
     * @return supplier_name - 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置供应商名称
     *
     * @param supplierName 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取供应商银行账号
     *
     * @return supplier_bank_account - 供应商银行账号
     */
    public String getSupplierBankAccount() {
        return supplierBankAccount;
    }

    /**
     * 设置供应商银行账号
     *
     * @param supplierBankAccount 供应商银行账号
     */
    public void setSupplierBankAccount(String supplierBankAccount) {
        this.supplierBankAccount = supplierBankAccount;
    }

    /**
     * 获取供应商开户行
     *
     * @return supplier_bank - 供应商开户行
     */
    public String getSupplierBank() {
        return supplierBank;
    }

    /**
     * 设置供应商开户行
     *
     * @param supplierBank 供应商开户行
     */
    public void setSupplierBank(String supplierBank) {
        this.supplierBank = supplierBank;
    }

    /**
     * 获取供货日期
     *
     * @return supply_date - 供货日期
     */
    public String getSupplyDate() {
        return supplyDate;
    }

    /**
     * 设置供货日期
     *
     * @param supplyDate 供货日期
     */
    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate;
    }

    /**
     * 获取配送地址
     *
     * @return delivery_place - 配送地址
     */
    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    /**
     * 设置配送地址
     *
     * @param deliveryPlace 配送地址
     */
    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    /**
     * 获取交货方式
     *
     * @return delivery_mode - 交货方式
     */
    public String getDeliveryMode() {
        return deliveryMode;
    }

    /**
     * 设置交货方式
     *
     * @param deliveryMode 交货方式
     */
    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    /**
     * 获取代理机构编码
     *
     * @return agent_code - 代理机构编码
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * 设置代理机构编码
     *
     * @param agentCode 代理机构编码
     */
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    /**
     * 获取代理机构名称
     *
     * @return agent_name - 代理机构名称
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 设置代理机构名称
     *
     * @param agentName 代理机构名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * 获取采购人编码
     *
     * @return unit_code - 采购人编码
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * 设置采购人编码
     *
     * @param unitCode 采购人编码
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * 获取采购人姓名
     *
     * @return unit_name - 采购人姓名
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 设置采购人姓名
     *
     * @param unitName 采购人姓名
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * 获取组织形式 2集中采购 3分散采购
     *
     * @return org_kind - 组织形式 2集中采购 3分散采购
     */
    public String getOrgKind() {
        return orgKind;
    }

    /**
     * 设置组织形式 2集中采购 3分散采购
     *
     * @param orgKind 组织形式 2集中采购 3分散采购
     */
    public void setOrgKind(String orgKind) {
        this.orgKind = orgKind;
    }

    /**
     * 获取采购方式编码 网上超市13，定点08，批量10
     *
     * @return buy_kind - 网上超市13，定点08，批量10
     */
    public String getBuyKind() {
        return buyKind;
    }

    /**
     * 设置采购方式编码 网上超市13，定点08，批量10
     *
     * @param buyKind 网上超市13，定点08，批量10
     */
    public void setBuyKind(String buyKind) {
        this.buyKind = buyKind;
    }

    /**
     * 获取是否最后一个合同
     *
     * @return is_end - 是否最后一个合同
     */
    public Boolean getIsEnd() {
        return isEnd;
    }

    /**
     * 设置是否最后一个合同
     *
     * @param isEnd 是否最后一个合同
     */
    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * 获取合同金额
     *
     * @return total_sum - 合同金额
     */
    public BigDecimal getTotalSum() {
        return totalSum;
    }

    /**
     * 设置合同金额
     *
     * @param totalSum 合同金额
     */
    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * 获取预算内金额
     *
     * @return budget_in - 预算内金额
     */
    public BigDecimal getBudgetIn() {
        return budgetIn;
    }

    /**
     * 设置预算内金额
     *
     * @param budgetIn 预算内金额
     */
    public void setBudgetIn(BigDecimal budgetIn) {
        this.budgetIn = budgetIn;
    }

    /**
     * 获取财政金额
     *
     * @return budget_finance - 财政金额
     */
    public BigDecimal getBudgetFinance() {
        return budgetFinance;
    }

    /**
     * 设置财政金额
     *
     * @param budgetFinance 财政金额
     */
    public void setBudgetFinance(BigDecimal budgetFinance) {
        this.budgetFinance = budgetFinance;
    }

    /**
     * 获取自筹金额
     *
     * @return budget_self - 自筹金额
     */
    public BigDecimal getBudgetSelf() {
        return budgetSelf;
    }

    /**
     * 设置自筹金额
     *
     * @param budgetSelf 自筹金额
     */
    public void setBudgetSelf(BigDecimal budgetSelf) {
        this.budgetSelf = budgetSelf;
    }

    /**
     * 获取业务年度
     *
     * @return bdg_year - 业务年度
     */
    public Integer getBdgYear() {
        return bdgYear;
    }

    /**
     * 设置业务年度
     *
     * @param bdgYear 业务年度
     */
    public void setBdgYear(Integer bdgYear) {
        this.bdgYear = bdgYear;
    }

    /**
     * 获取录入日期 格式YYYYMMDDhhmmss
     *
     * @return input_date - 录入日期 格式YYYYMMDDhhmmss
     */
    public String getInputDate() {
        return inputDate;
    }

    /**
     * 设置录入日期 格式YYYYMMDDhhmmss
     *
     * @param inputDate 录入日期 格式YYYYMMDDhhmmss
     */
    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * 获取下达日期 格式YYYYMMDDhhmmss
     *
     * @return down_date - 下达日期 格式YYYYMMDDhhmmss
     */
    public String getDownDate() {
        return downDate;
    }

    /**
     * 设置下达日期 格式YYYYMMDDhhmmss
     *
     * @param downDate 下达日期 格式YYYYMMDDhhmmss
     */
    public void setDownDate(String downDate) {
        this.downDate = downDate;
    }

    /**
     * 获取附件url
     *
     * @return atta_url - 附件url
     */
    public String getAttaUrl() {
        return attaUrl;
    }

    /**
     * 设置附件url
     *
     * @param attaUrl 附件url
     */
    public void setAttaUrl(String attaUrl) {
        this.attaUrl = attaUrl;
    }

    /**
     * 获取业务类型 mallorder:商城订单
     *
     * @return biz_type - 业务类型 mallorder:商城订单
     */
    public ContractBizType getBizType() {
        return bizType;
    }

    /**
     * 设置业务类型 mallorder:商城订单
     *
     * @param bizType 业务类型 mallorder:商城订单
     */
    public void setBizType(ContractBizType bizType) {
        this.bizType = bizType;
    }

    /**
     * 获取业务关联ID
     *
     * @return biz_relation_id - 业务关联ID
     */
    public Long getBizRelationId() {
        return bizRelationId;
    }

    /**
     * 设置业务关联ID
     *
     * @param bizRelationId 业务关联ID
     */
    public void setBizRelationId(Long bizRelationId) {
        this.bizRelationId = bizRelationId;
    }

 

    public DealContractStatus getStatus() {
		return status;
	}

	public void setStatus(DealContractStatus status) {
		this.status = status;
	}

	/**
     * 获取合同模板ID
     *
     * @return tpl_id - 合同模板ID
     */
    public Long getTplId() {
        return tplId;
    }

    /**
     * 设置合同模板ID
     *
     * @param tplId 合同模板ID
     */
    public void setTplId(Long tplId) {
        this.tplId = tplId;
    }
    
	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}
	
    public Integer getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(Integer quoteType) {
		this.quoteType = quoteType;
	}

	public Integer getPayServiceMonth() {
		return payServiceMonth;
	}

	public void setPayServiceMonth(Integer payServiceMonth) {
		this.payServiceMonth = payServiceMonth;
	}

	public Integer getPayContractRate() {
		return payContractRate;
	}

	public void setPayContractRate(Integer payContractRate) {
		this.payContractRate = payContractRate;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Integer getTakeOverDays() {
		return takeOverDays;
	}

	public void setTakeOverDays(Integer takeOverDays) {
		this.takeOverDays = takeOverDays;
	}
	
    public Integer getPayCheckRate() {
		return payCheckRate;
	}

	public void setPayCheckRate(Integer payCheckRate) {
		this.payCheckRate = payCheckRate;
	}

	public String getServicePromise() {
		return servicePromise;
	}

	public void setServicePromise(String servicePromise) {
		this.servicePromise = servicePromise;
	}
	
	public String getServiceRequire() {
		return serviceRequire;
	}

	public void setServiceRequire(String serviceRequire) {
		this.serviceRequire = serviceRequire;
	}
	
    /**
     * 获得主卡卡号
     */
	public String getMainCardNumber() {
		return mainCardNumber;
	}
	
	/**
	 * 设置主卡卡号
	 * @param mainCardNumber
	 */
	public void setMainCardNumber(String mainCardNumber) {
		this.mainCardNumber = mainCardNumber;
	}
	
	/**
	 * 获得副卡卡号
	 * @return
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * 设置副卡卡号
	 * @param cardNumber
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	/**
	 * 获得持卡人
	 * @return
	 */
	public String getHolder() {
		return holder;
	}
	
	/**
	 * 设置持卡人
	 * @param holder
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	
	/**
	 * 获得持卡人电话
	 * @return
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * 设置持卡人电话
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/**
	 * 获得身份证号
	 * @return
	 */
	public String getIdentityCard() {
		return identityCard;
	}
	
	/**
	 * 设置身份证号
	 * @param identityCard
	 */
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	
	/**
	 * 获得车牌号
	 * @return
	 */
	public String getLicensePlateNum() {
		return licensePlateNum;
	}
	
	/**
	 * 设置车牌号
	 * @param licensePlateNum
	 */
	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}
	
	/**
	 * 获得车架号
	 * @return
	 */
	public String getFrameNum() {
		return frameNum;
	}
	
	/**
	 * 设置车架号
	 * @param frameNum
	 */
	public void setFrameNum(String frameNum) {
		this.frameNum = frameNum;
	}
	
	/**
	 * 获得厂牌型号
	 * @return
	 */
	public String getFactoryPlateModel() {
		return factoryPlateModel;
	}
	
	/**
	 * 设置厂牌型号
	 * @param factoryPlateModel
	 */
	public void setFactoryPlateModel(String factoryPlateModel) {
		this.factoryPlateModel = factoryPlateModel;
	}
	
	
	public List<DealContractDetails> getDetails() {
		return details;
	}

	public void setDetails(List<DealContractDetails> details) {
		this.details = details;
	}
    
	/**
	 * 合同大写金额
	 * @return
	 */
	public String getBigTotalSum() {
		if(getTotalSum()!=null){
			return ChineseMoneyUtil.getChineseMoney(getTotalSum());
		}
		return null;
	}
	
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getFirstRegisterDate() {
		return firstRegisterDate;
	}

	public void setFirstRegisterDate(String firstRegisterDate) {
		this.firstRegisterDate = firstRegisterDate;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getHoursTotalFee() {
		return hoursTotalFee;
	}

	public void setHoursTotalFee(BigDecimal hoursTotalFee) {
		this.hoursTotalFee = hoursTotalFee;
	}

	public BigDecimal getMaterialTotalFee() {
		return materialTotalFee;
	}

	public void setMaterialTotalFee(BigDecimal materialTotalFee) {
		this.materialTotalFee = materialTotalFee;
	}

	public Integer getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(Integer guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}

	public ReviewGrade getReviewGrade() {
		return reviewGrade;
	}

	public void setReviewGrade(ReviewGrade reviewGrade) {
		this.reviewGrade = reviewGrade;
	}
	
    public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}
	
}