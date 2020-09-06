package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Organization;
/**
 * 

* <p>Title: BiddingOrders</p>  

* <p>Description: </p>  

* @author mys  

* @date 2018年11月23日
 */
public class BiddingOrders extends BaseEntity{

	private static final long serialVersionUID = 1L;
    /**
     * 竞价项目名称
     */
	@ApiModelProperty(value="项目名称")
    @Column(name = "bid_name")
    private String bidName;

    /**
     * 项目编号
     */
	@ApiModelProperty(value="项目编号")
    @Column(name = "project_num")
    private String projectNum;

    /**
     * 计划编号
     */
	@ApiModelProperty(value="计划编号")
    @Column(name = "plan_id")
    private Long planId;

    /**
     * 竞价开始时间
     */
	@ApiModelProperty(value="竞价开始时间")
    @Column(name = "start_time")
    private String startTime;

   
    /**
     * 竞价状态  0完成 1竞价中 2中止 3暂存 4失败 5结果待确认
     */
	@ApiModelProperty(value="竞价状态  0完成 1竞价中 2中止 3暂存 4失败 5结果待确认")
    private Integer status;

    /**
     * 竞价类型  单品牌  多品牌
     */
	@ApiModelProperty(value="竞价类型")
    @Column(name = "bid_type")
    private Integer bidType;

    /**
     * 交货日期
     */
	@ApiModelProperty(value="交货日期")
    private String cycle;

    /**
     * 收货地址
     */
	@ApiModelProperty(value="收货地址")
    @Column(name = "receiving_address")
    private String receivingAddress;

    @ApiModelProperty(value="联系人")
    private String linkman;
    
    @ApiModelProperty(value="联系电话")
    private String tel;
    
    @ApiModelProperty(value="其他支付方式")
    private String others;
    
    @ApiModelProperty(value="采购人选择这家供应商的理由")
    private String reason;
	
	/**
     * 创建时间
     */
    @Column(name = "create_at")
    private String createAt;

    /**
     * 更新时间
     */
    @Column(name = "update_at")
    private String updateAt;

    /**
     * 付款条款
     */
    @ApiModelProperty(value="付款条款")
	private String clause;

    /**
     * 竞价到期时间
     */
    @ApiModelProperty(value="竞价到期时间")
    @Column(name = "bidding_endtime")
    private String biddingEndtime;
    
    
    @ApiModelProperty(value="支付方式1 货到验收  2 其他")
    @Column(name="pay_way")
    private Integer payWay;

    
    @ApiModelProperty(name="是否分包")
    @Column(name="is_subpackage")
    private Integer isSubpackage;
    
    
    @ApiModelProperty(value="用户ID")
    @Column(name="user_id")
    private Long userId;
    
    @ApiModelProperty(value="预算总金额")
    private BigDecimal total;
    
    @ApiModelProperty(value="预算内资金")
    @Column(name="budget_in")
    private BigDecimal budgetIn;
    
    @ApiModelProperty(value="预算财政资金")
    @Column(name="budget_finance")
    private BigDecimal budgetFinance;
    
    @ApiModelProperty(value="自筹资金")
    @Column(name="budget_self")
    private BigDecimal budgetSelf;
    
    @ApiModelProperty(value="分包包号")
    @Column(name="subpackage_num")
    private String subpackageNum;
    
    @ApiModelProperty(value="竞价时长")
    @Column(name="bidding_time")
    private Integer biddingTime;
    
    
    @ApiModelProperty(name="计划编号")
    @Column(name="plan_code")
    private String planCode;
    
    @ApiModelProperty(name="是否是第一家标记")
    @Column(name="is_first")
    private Integer isFirst;
    
    
    @Column(name="supplier_id")
    private Long supplierId;
    
    @Column(name="contract_id")
    private Long contractId;
    
    /** 
     * 合同路径
     */  
    @Column(name="contract_path")
    private String contractPath;
  
	@ApiModelProperty("采购人选择的供应商")
    @Transient
    private BiddingOrdersSuppliers winSuppliers; 
	
	@Transient
    private  List<BiddingOrdersSuppliers> biddingOrdersSuppliers;
	
	@Transient
	private BigDecimal price;
	
	@Transient
	private Organization organization;

	@ApiModelProperty("已报价家数")
	@Transient
	private Integer count;
	/**
	 * 组织机构ID
	 */
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@Column(name = "dept_name")
	private String deptName;

	@ApiModelProperty("竞价类型 1 小型机")
	@Column(name = "bidding_type")
	private Integer biddingType;

	public Integer getBiddingType() {
		return biddingType;
	}


	public void setBiddingType(Integer biddingType) {
		this.biddingType = biddingType;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Integer getIsSubpackage() {
		return isSubpackage;
	}


	public void setIsSubpackage(Integer isSubpackage) {
		this.isSubpackage = isSubpackage;
	}


	public Integer getPayWay() {
		return payWay;
	}


	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public Organization getOrganization() {
		return organization;
	}


	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Transient
	private List<BiddingOrdersItems> biddingOrdersItems;
	
	public List<BiddingOrdersItems> getBiddingOrdersItems() {
		return biddingOrdersItems;
	}


	public void setBiddingOrdersItems(List<BiddingOrdersItems> biddingOrdersItems) {
		this.biddingOrdersItems = biddingOrdersItems;
	}
	
    public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
     * 获取竞价项目名称
     *
     * @return bid_name - 竞价项目名称
     */
    public String getBidName() {
        return bidName;
    }

    /**
     * 设置竞价项目名称
     *
     * @param bidName 竞价项目名称
     */
    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    /**
     * 获取项目编号
     *
     * @return project_num - 项目编号
     */
    public String getProjectNum() {
        return projectNum;
    }

    /**
     * 设置项目编号
     *
     * @param projectNum 项目编号
     */
    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    /**
     * 获取计划编号
     *
     * @return plan_num - 计划编号
     */
    public Long getPlanId() {
        return planId;
    }

    /**
     * 设置计划编号
     *
     * @param planNum 计划编号
     */
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    /**
     * 获取竞价开始时间
     *
     * @return start_time - 竞价开始时间
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置竞价开始时间
     *
     * @param startTime 竞价开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

 

    /**
     * 获取竞价状态 完成 竞价中 中止 暂存 失败 结果待确认
     *
     * @return status - 竞价状态 完成 竞价中 中止 暂存 失败 结果待确认
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置竞价状态 完成 竞价中 中止 暂存 失败 结果待确认
     *
     * @param status 竞价状态 完成 竞价中 中止 暂存 失败 结果待确认
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取竞价类型  单品牌  多品牌
     *
     * @return bid_type - 竞价类型  单品牌  多品牌
     */
    public Integer getBidType() {
        return bidType;
    }

    /**
     * 设置竞价类型  单品牌  多品牌
     *
     * @param bidType 竞价类型  单品牌  多品牌
     */
    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

    /**
     * 获取交货日期
     *
     * @return cycle - 交货日期
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * 设置交货日期
     *
     * @param cycle 交货日期
     */
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    /**
     * 获取收货地址
     *
     * @return receiving_address - 收货地址
     */
    public String getReceivingAddress() {
        return receivingAddress;
    }

    /**
     * 设置收货地址
     *
     * @param receivingAddress 收货地址
     */
    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取更新时间
     *
     * @return update_at - 更新时间
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置更新时间
     *
     * @param updateAt 更新时间
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

 

    /**
     * 获取付款条款
     *
     * @return clause - 付款条款
     */
    public String getClause() {
        return clause;
    }

    /**
     * 设置付款条款
     *
     * @param clause 付款条款
     */
    public void setClause(String clause) {
        this.clause = clause;
    }

    /**
     * 获取竞价到期时间
     *
     * @return bidding_endtime - 竞价到期时间
     */
    public String getBiddingEndtime() {
        return biddingEndtime;
    }

    /**
     * 设置竞价到期时间
     *
     * @param biddingEndtime 竞价到期时间
     */
    public void setBiddingEndtime(String biddingEndtime) {
        this.biddingEndtime = biddingEndtime;
    }


	public String getContractPath() {
		return contractPath;
	}


	public void setContractPath(String contractPath) {
		this.contractPath = contractPath;
	}
	  public String getReason() {
			return reason;
		}


		public void setReason(String reason) {
			this.reason = reason;
		}


		public String getOthers() {
			return others;
		}


		public void setOthers(String others) {
			this.others = others;
		}


		public String getLinkman() {
			return linkman;
		}


		public void setLinkman(String linkman) {
			this.linkman = linkman;
		}


		public String getTel() {
			return tel;
		}


		public void setTel(String tel) {
			this.tel = tel;
		}
	    
	    public Long getContractId() {
			return contractId;
		}


		public void setContractId(Long contractId) {
			this.contractId = contractId;
		}


		public Long getSupplierId() {
			return supplierId;
		}


		public void setSupplierId(Long supplierId) {
			this.supplierId = supplierId;
		}
  
  public BiddingOrdersSuppliers getWinSuppliers() {
		return winSuppliers;
	}


	public void setWinSuppliers(BiddingOrdersSuppliers winSuppliers) {
		this.winSuppliers = winSuppliers;
	}


	public Integer getIsFirst() {
		return isFirst;
	}


	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}


	public String getPlanCode() {
		return planCode;
	}


	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}


	public String getSubpackageNum() {
		return subpackageNum;
	}


	public void setSubpackageNum(String subpackageNum) {
		this.subpackageNum = subpackageNum;
	}


	public Integer getBiddingTime() {
		return biddingTime;
	}


	public void setBiddingTime(Integer biddingTime) {
		this.biddingTime = biddingTime;
	}


	public BigDecimal getTotal() {
		return total;
	}


	public void setTotal(BigDecimal total) {
		this.total = total;
	}


	public BigDecimal getBudgetIn() {
		return budgetIn;
	}


	public void setBudgetIn(BigDecimal budgetIn) {
		this.budgetIn = budgetIn;
	}


	public BigDecimal getBudgetFinance() {
		return budgetFinance;
	}


	public void setBudgetFinance(BigDecimal budgetFinance) {
		this.budgetFinance = budgetFinance;
	}


	public BigDecimal getBudgetSelf() {
		return budgetSelf;
	}


	public void setBudgetSelf(BigDecimal budgetSelf) {
		this.budgetSelf = budgetSelf;
	}


	public List<BiddingOrdersSuppliers> getBiddingOrdersSuppliers() {
		return biddingOrdersSuppliers;
	}


	public void setBiddingOrdersSuppliers(List<BiddingOrdersSuppliers> biddingOrdersSuppliers) {
		this.biddingOrdersSuppliers = biddingOrdersSuppliers;
	}
}