package www.tonghao.platform.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import www.tonghao.service.common.base.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class Plan extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 计划id
     */
    @Column(name = "plan_id")
    private String planId;

    /**
     * 计划编号
     */
    @ApiModelProperty(value="计划编号")
    @Column(name = "plan_code")
    private String planCode;

    /**
     * 填表时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 计划使用年月
     */
    @Column(name = "use_month")
    private String useMonth;

    /**
     * 主管部门
     */
    private String superior;

    /**
     * 主管部门编码
     */
    @Column(name = "superior_code")
    private String superiorCode;

    /**
     * 基层预算单位
     */
    private String department;

    /**
     * 基层预算单位编码
     */
    @Column(name = "department_code")
    private String departmentCode;

    /**
     * 区划编码
     */
    private String region;

    /**
     * 代理机构
     */
    private String agency;
    /**
     * 代理机构编码
     */
    @Column(name = "agency_code")
    private String agencyCode;
    /**
     * 采购方式 ： 网上超市13，定点08，批量10
     */
    @Column(name = "procurement_method")
    private String procurementMethod;

    /**
     * 审批文号
     */
    @Column(name = "audit_number")
    private String auditNumber;

    /**
     * 进口产品文号
     */
    @Column(name = "import_number")
    private String importNumber;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 电话
     */
    private String tel;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private String createdTime;

    /**
     * 状态 0:待使用 1:已使用 5:已退回 6:已完成
     */
    @Column(name = "status")
    private Integer status;
    
    /**
     * 使用次数
     */
    @Column(name = "usage_count")
    private Integer usageCount;
    
    
    /**
     * 项目编号
     */
    @Column(name = "project_code")
    private String projectCode;
    
    @Transient
    private List<PlanItem> items;
    
    /**
	 * 是否预采购 “1”为是预采购，“0”为不是预采购s
	 */
    @Column(name = "is_advance")
	private String isAdvance;
	
	/**
	 * 是否有集成费
	 * “0”为无，“1”为有，只有批量集采计划有，其他都为0
	 */
    @Column(name = "integrated_cost")
	private String integratedCost;
	
	/**
	 * 预采购的唯一标识
	 * 只有推送预采购的正式采购建议书时会有值
	 */
    @Column(name = "advance_plan_id")
	private String advancePlanId;
    
    /** 
     * 计划单名称
     */  
    @Column(name = "plan_name")
    private String planName;
    
    /**
     * 预算总额
     */
    @Transient
    private BigDecimal budgetTotal;
  
	/**
     * 采购方式名称
     */
    @Transient
    private String procurementMethodName;
    
    @Transient
    private String categoryName;
    

    /**
     * 剩余金额
     */
    @Transient
    private BigDecimal totalBalance;
    public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	/**
     * 获取计划id
     *
     * @return plan_id - 计划id
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * 设置计划id
     *
     * @param planId 计划id
     */
    public void setPlanId(String planId) {
        this.planId = planId;
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
     * 获取填表时间
     *
     * @return created_at - 填表时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置填表时间
     *
     * @param createdAt 填表时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取计划使用年月
     *
     * @return use_month - 计划使用年月
     */
    public String getUseMonth() {
        return useMonth;
    }

    /**
     * 设置计划使用年月
     *
     * @param useMonth 计划使用年月
     */
    public void setUseMonth(String useMonth) {
        this.useMonth = useMonth;
    }

    /**
     * 获取主管部门
     *
     * @return superior - 主管部门
     */
    public String getSuperior() {
        return superior;
    }

    /**
     * 设置主管部门
     *
     * @param superior 主管部门
     */
    public void setSuperior(String superior) {
        this.superior = superior;
    }

    /**
     * 获取主管部门编码
     *
     * @return superior_code - 主管部门编码
     */
    public String getSuperiorCode() {
        return superiorCode;
    }

    /**
     * 设置主管部门编码
     *
     * @param superiorCode 主管部门编码
     */
    public void setSuperiorCode(String superiorCode) {
        this.superiorCode = superiorCode;
    }

    /**
     * 获取基层预算单位
     *
     * @return department - 基层预算单位
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置基层预算单位
     *
     * @param department 基层预算单位
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取基层预算单位编码
     *
     * @return department_code - 基层预算单位编码
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    /**
     * 设置基层预算单位编码
     *
     * @param departmentCode 基层预算单位编码
     */
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    /**
     * 获取区划编码
     *
     * @return region - 区划编码
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置区划编码
     *
     * @param region 区划编码
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取代理机构
     *
     * @return agency - 代理机构
     */
    public String getAgency() {
        return agency;
    }

    /**
     * 设置代理机构
     *
     * @param agency 代理机构
     */
    public void setAgency(String agency) {
        this.agency = agency;
    }

    /**
     * 获取采购方式
     *
     * @return procurement_method - 采购方式
     */
    public String getProcurementMethod() {
        return procurementMethod;
    }

    /**
     * 设置采购方式
     *
     * @param procurementMethod 采购方式
     */
    public void setProcurementMethod(String procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    /**
     * 获取审批文号
     *
     * @return audit_number - 审批文号
     */
    public String getAuditNumber() {
        return auditNumber;
    }

    /**
     * 设置审批文号
     *
     * @param auditNumber 审批文号
     */
    public void setAuditNumber(String auditNumber) {
        this.auditNumber = auditNumber;
    }

    /**
     * 获取进口产品文号
     *
     * @return import_number - 进口产品文号
     */
    public String getImportNumber() {
        return importNumber;
    }

    /**
     * 设置进口产品文号
     *
     * @param importNumber 进口产品文号
     */
    public void setImportNumber(String importNumber) {
        this.importNumber = importNumber;
    }

    /**
     * 获取联系人
     *
     * @return linkman - 联系人
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * 设置联系人
     *
     * @param linkman 联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * 获取电话
     *
     * @return tel - 电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置电话
     *
     * @param tel 电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public List<PlanItem> getItems() {
		return items;
	}

	public void setItems(List<PlanItem> items) {
		this.items = items;
	}
    
    /**
     * 获得预算总额
     * @return
     */
    public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}
    
    /**
     * 设置预算总额
     * @param budgetTotal
     */
	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getUsageCount() {
		return usageCount;
	}

	public void setUsageCount(Integer usageCount) {
		this.usageCount = usageCount;
	}

	public String getProcurementMethodName() {
		return procurementMethodName;
	}

	public void setProcurementMethodName(String procurementMethodName) {
		this.procurementMethodName = procurementMethodName;
	}

	public String getCategoryName() {
		if(StringUtils.isEmpty(this.categoryName)){
			List<PlanItem> itemList = getItems();
			if(itemList!=null&&itemList.size()>0){
				return itemList.get(0).getCategoryName();
			}
		}
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/**
	 * 统计可用余额
	 * @return
	 */
    public BigDecimal getTotalBalance(){
    	BigDecimal usableBalanceTotal = BigDecimal.ZERO;
    	List<PlanItem> itemList = getItems();
    	if(itemList!=null){
    		for(PlanItem item:itemList){
    			if(item.getBudgetTotal()!=null){
    				BigDecimal usedBudget = item.getUsedBudget()!=null?item.getUsedBudget():BigDecimal.ZERO;
    				BigDecimal usableBalance = item.getBudgetTotal().subtract(usedBudget);
    				usableBalanceTotal = usableBalanceTotal.add(usableBalance);
    			}
    		}
    	}
    	return usableBalanceTotal;
    	//return getTotalBudgetInBalance().add(getTotalBudgetFinanceBalance()).add(getTotalBudgetSelfBalance());
    }

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getIsAdvance() {
		return isAdvance;
	}

	public void setIsAdvance(String isAdvance) {
		this.isAdvance = isAdvance;
	}

	public String getIntegratedCost() {
		return integratedCost;
	}

	public void setIntegratedCost(String integratedCost) {
		this.integratedCost = integratedCost;
	}

	public String getAdvancePlanId() {
		return advancePlanId;
	}

	public void setAdvancePlanId(String advancePlanId) {
		this.advancePlanId = advancePlanId;
	}

	/**
	 * 是否预采购
	 * @return
	 */
	public boolean isPrePurchase(){
		if(getPlanCode()!=null&&StringUtils.startsWithIgnoreCase("getPlanCode()", "Y")){
			return true;
		}
		return false;
	}
	
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * 预算内余额
	 * @return
	 */
	@JsonIgnore
	@Deprecated
    public BigDecimal getTotalBudgetInBalance(){
    	BigDecimal total = BigDecimal.ZERO;
    	List<PlanItem> itemList = getItems();
    	if(itemList!=null){
    		for(PlanItem item:itemList){
    			total = total.add(item.getBudgetInBalance());
    		}
    	}
    	return total;
    }
    /**
     * 财政金额余额
     * @return
     */
	@JsonIgnore
	@Deprecated
    public BigDecimal getTotalBudgetFinanceBalance(){
    	BigDecimal total = BigDecimal.ZERO;
    	List<PlanItem> itemList = getItems();
    	if(itemList!=null){
    		for(PlanItem item:itemList){
    			total = total.add(item.getBudgetFinanceBalance());
    		}
    	}
    	return total;
    }
    /**
     * 自筹余额
     * @return
     */
	@JsonIgnore
	@Deprecated
    public BigDecimal getTotalBudgetSelfBalance(){
    	BigDecimal total = BigDecimal.ZERO;
    	List<PlanItem> itemList = getItems();
    	if(itemList!=null){
    		for(PlanItem item:itemList){
    			total = total.add(item.getBudgetSelfBalance());
    		}
    	}
    	return total;
    }
}