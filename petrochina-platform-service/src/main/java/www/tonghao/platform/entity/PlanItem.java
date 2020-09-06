package www.tonghao.platform.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Table(name = "plan_item")
public class PlanItem extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    /**
     * 品目编码
     */
    @Column(name = "category_code")
    private String categoryCode;

    /**
     * 品目名称
     */
    @ApiModelProperty(value="品目名称")
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 数量
     */
    @ApiModelProperty(value="计划采购数量")
    private Float num;

    /**
     * 技术规格
     */
    private String spec;

    /**
     * 预算
     */
    @ApiModelProperty(value="预算")
    @Column(name = "budget_in")
    private BigDecimal budgetIn;

    /**
     * 财政专户
     */
    @ApiModelProperty(value="财政专户")
    @Column(name = "budget_finance")
    private BigDecimal budgetFinance;

    /**
     * 自筹
     */
    @ApiModelProperty(value="自筹")
    @Column(name = "budget_self") 
    private BigDecimal budgetSelf;

    /**
     * 进口品目名称
     */
    @Column(name = "import_categor_name")
    private String importCategorName;

    /**
     * 进口品目编码
     */
    @Column(name = "import_categor_code")
    private String importCategorCode;

    /**
     * 进口产品名称
     */
    @Column(name = "import_product_name")
    private String importProductName;

    /**
     * 计划id
     */
    @Column(name = "plan_id")
    private Long planId;


    /**
     * 计划业务id
     */
    @Column(name="plan_rev_id")
    private String planRevId;
    
    @ApiModelProperty("已使用预算余额")
    @Column(name="used_budget_in")
    private BigDecimal usedBudgetIn;
    
    @ApiModelProperty("已使用财政预算")
    @Column(name="used_budget_finance")
    private BigDecimal usedBudgetFinance;
    
    @ApiModelProperty("已使用自筹金额")
    @Column(name="used_budget_self")
    private BigDecimal usedBudgetSelf;
    
    
    @ApiModelProperty("总金额")
    @Column(name="budget_total")
    private BigDecimal budgetTotal;
    
    @ApiModelProperty("计划已使用金额")
    @Column(name="used_budget")
    private BigDecimal usedBudget;
    

	/**
     * 获取品目编码
     *
     * @return category_code - 品目编码
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * 设置品目编码
     *
     * @param categoryCode 品目编码
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 获取品目名称
     *
     * @return category_name - 品目名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置品目名称
     *
     * @param categoryName 品目名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 获取计量单位
     *
     * @return unit - 计量单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置计量单位
     *
     * @param unit 计量单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Float getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Float num) {
        this.num = num;
    }

    /**
     * 获取技术规格
     *
     * @return spec - 技术规格
     */
    public String getSpec() {
        return spec;
    }

    /**
     * 设置技术规格
     *
     * @param spec 技术规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 获取预算
     *
     * @return budget_in - 预算
     */
    public BigDecimal getBudgetIn() {
        return budgetIn;
    }

    /**
     * 设置预算
     *
     * @param budgetIn 预算
     */
    public void setBudgetIn(BigDecimal budgetIn) {
        this.budgetIn = budgetIn;
    }

    /**
     * 获取财政专户
     *
     * @return budget_finance - 财政专户
     */
    public BigDecimal getBudgetFinance() {
        return budgetFinance;
    }

    /**
     * 设置财政专户
     *
     * @param budgetFinance 财政专户
     */
    public void setBudgetFinance(BigDecimal budgetFinance) {
        this.budgetFinance = budgetFinance;
    }

    /**
     * 获取自筹
     *
     * @return budget_self - 自筹
     */
    public BigDecimal getBudgetSelf() {
        return budgetSelf;
    }

    /**
     * 设置自筹
     *
     * @param budgetSelf 自筹
     */
    public void setBudgetSelf(BigDecimal budgetSelf) {
        this.budgetSelf = budgetSelf;
    }

    /**
     * 获取进口品目名称
     *
     * @return import_categor_name - 进口品目名称
     */
    public String getImportCategorName() {
        return importCategorName;
    }

    /**
     * 设置进口品目名称
     *
     * @param importCategorName 进口品目名称
     */
    public void setImportCategorName(String importCategorName) {
        this.importCategorName = importCategorName;
    }

    /**
     * 获取进口品目编码
     *
     * @return import_categor_code - 进口品目编码
     */
    public String getImportCategorCode() {
        return importCategorCode;
    }

    /**
     * 设置进口品目编码
     *
     * @param importCategorCode 进口品目编码
     */
    public void setImportCategorCode(String importCategorCode) {
        this.importCategorCode = importCategorCode;
    }

    /**
     * 获取进口产品名称
     *
     * @return import_product_name - 进口产品名称
     */
    public String getImportProductName() {
        return importProductName;
    }

    /**
     * 设置进口产品名称
     *
     * @param importProductName 进口产品名称
     */
    public void setImportProductName(String importProductName) {
        this.importProductName = importProductName;
    }

    /**
     * 获取计划id
     *
     * @return plan_id - 计划id
     */
    public Long getPlanId() {
        return planId;
    }

    /**
     * 设置计划id
     *
     * @param planId 计划id
     */
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

	public String getPlanRevId() {
		return planRevId;
	}

	public void setPlanRevId(String planRevId) {
		this.planRevId = planRevId;
	}

	public BigDecimal getUsedBudgetIn() {
		return usedBudgetIn;
	}

	public void setUsedBudgetIn(BigDecimal usedBudgetIn) {
		this.usedBudgetIn = usedBudgetIn;
	}

	public BigDecimal getUsedBudgetFinance() {
		return usedBudgetFinance;
	}

	public void setUsedBudgetFinance(BigDecimal usedBudgetFinance) {
		this.usedBudgetFinance = usedBudgetFinance;
	}

	public BigDecimal getUsedBudgetSelf() {
		return usedBudgetSelf;
	}

	public void setUsedBudgetSelf(BigDecimal usedBudgetSelf) {
		this.usedBudgetSelf = usedBudgetSelf;
	}

	/**
	 * 预算内余额
	 * @return
	 */
	@JsonIgnore
	@Deprecated
    public BigDecimal getBudgetInBalance(){
    	if(getUsedBudgetIn()!=null){
    		return getBudgetIn().subtract(getUsedBudgetIn());
    	}
    	return getBudgetIn();
    }
	
    /**
     * 财政金额余额
     * @return
     */
	@JsonIgnore
	@Deprecated
    public BigDecimal getBudgetFinanceBalance(){
    	if(getUsedBudgetFinance()!=null){
    		return getBudgetFinance().subtract(getUsedBudgetFinance());
    	}
    	return getBudgetFinance();
    }
	
    /**
     * 自筹余额
     * @return
     */
	@JsonIgnore
	@Deprecated
    public BigDecimal getBudgetSelfBalance(){
    	if(getUsedBudgetSelf()!=null){
    		return getBudgetSelf().subtract(getUsedBudgetSelf());
    	}
    	return getBudgetSelf();
    }
    
    /**  
     * <p>Title: getTotalBudget</p>
     * <p>Description: 预算总额</p>  
     * @author YML 
     * @return 
     */
    public BigDecimal getTotalBudget(){
    	BigDecimal totalBudget = BigDecimal.ZERO;
    	if(getBudgetFinance() != null){
    		totalBudget = totalBudget.add(getBudgetFinance());
		}
    	if(getBudgetIn() != null){
    		totalBudget = totalBudget.add(getBudgetIn());
    	}
    	if (getBudgetSelf() != null) {
    		totalBudget = totalBudget.add(getBudgetSelf());
		}
    	return totalBudget;
    }

	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
    
    /**
     * 获取计划已使用金额
     * @return
     */
    public BigDecimal getUsedBudget() {
		return usedBudget;
	}
    
    /**
     * 设置计划已使用金额
     * @param usedBudget
     */
	public void setUsedBudget(BigDecimal usedBudget) {
		this.usedBudget = usedBudget;
	}
    
}