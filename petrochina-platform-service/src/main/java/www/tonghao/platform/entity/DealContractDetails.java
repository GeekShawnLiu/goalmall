package www.tonghao.platform.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Review;


@Table(name = "deal_contract_details")
public class DealContractDetails extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 品目编码
     */
    @Column(name = "catalog_code")
    private String catalogCode;

    /**
     * 品目名称
     */
    @Column(name = "catalog_name")
    private String catalogName;

    /**
     * 产地
     */
    @Column(name = "produce_area")
    private String produceArea;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 品牌
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 产品属性
     */
    private String attrs;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 品牌型号
     */
    @Column(name = "brand_model")
    private String brandModel;

    /**
     * 商品型号
     */
    @Deprecated
    private String model;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 预算内金额
     */
    @Column(name = "budget_in")
    private BigDecimal budgetIn;

    /**
     * 财政内金额
     */
    @Column(name = "budget_finance")
    private BigDecimal budgetFinance;

    /**
     * 自筹金额
     */
    @Column(name = "budget_self")
    private BigDecimal budgetSelf;
    

    @Column(name = "contract_id")
    private Long contractId;
    
    /**
     * 总金额
     */
    private BigDecimal total;
    
    /**
     * 面积
     */
    @Column(name = "area")
    private BigDecimal area;
    
    /**
     * 工种
     */
    @Column(name = "work_type")
    private String workType;
    
    /**
     * 服务要求
     */
    @Column(name = "service_require")
    private String serviceRequire;
    

	/**
     * 服务内容
     */
    @Column(name = "service_content")
    private String serviceContent;
    
    /**
     * 车牌号
     */
    @Column(name = "license_plate_num")
    private String licensePlateNum;
    
    /**
     * 车架号
     */
    @Column(name = "frame_num")
    private String frameNum;
    
    /**
     * 发动机号
     */
    @Column(name = "engine_number")
    private String engineNumber;
    
    /**
     * 厂牌型号
     */
    @Column(name = "factory_plate_model")
    private String factoryPlateModel;
    
    /**
     * 初次登记日期
     */
    @Column(name = "first_register_date")
    private String firstRegisterDate;
    
    /**
     * 交强险保单号
     */
    @Column(name = "traffic_code")
    private String trafficCode;
    
    /**
     * 交强险保费
     */
    @Column(name = "traffic_price")
    private BigDecimal trafficPrice;
    
    /**
     * 交强险开始日期
     */
    @Column(name = "traffic_start")
    private String trafficStart;
    
    /**
     * 交强险结束日期
     */
    @Column(name = "traffic_end")
    private String trafficEnd;
    
    /**
     * 商业险保单号
     */
    @Column(name = "business_code")
    private String businessCode;
    
    /**
     * 商业险保费
     */
    @Column(name = "business_price")
    private BigDecimal businessPrice;
    
    /**
     * 商业险开始日期
     */
    @Column(name = "business_start")
    private String businessStart;
    
    /**
     * 商业险结束日期
     */
    @Column(name = "business_end")
    private String businessEnd;
    
    /**
     * 车船税
     */
    @Column(name = "vehicle_tax")
    private BigDecimal vehicleTax;
    
    /**
     * 技术指标
     */
    @Column(name = "technology_param")
    private String technologyParam;
    
    /**
     * 加价率
     */
    @Column(name = "add_rate")
    private String addRate;
    
    /**
     * 维保类型（1工时 2材料）
     */
    @Column(name = "require_type")
    private Integer requireType;
    
    /**
     * 响应参数
     */
    @Column(name = "response_param")
    private String responseParam;
    
	/** 
     * 人员
     */  
    @Transient
    private List<DealContractPerson> persons;
    
    @Transient
    private Review review;
    
    
    
    public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public BigDecimal getTotal() {
		return total;
	}
    
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

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
     * 获取品目编码
     *
     * @return catalog_code - 品目编码
     */
    public String getCatalogCode() {
        return catalogCode;
    }

    /**
     * 设置品目编码
     *
     * @param catalogCode 品目编码
     */
    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    /**
     * 获取品目名称
     *
     * @return catalog_name - 品目名称
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * 设置品目名称
     *
     * @param catalogName 品目名称
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * 获取产地
     *
     * @return produce_area - 产地
     */
    public String getProduceArea() {
        return produceArea;
    }

    /**
     * 设置产地
     *
     * @param produceArea 产地
     */
    public void setProduceArea(String produceArea) {
        this.produceArea = produceArea;
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
     * 获取品牌
     *
     * @return brand_name - 品牌
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置品牌
     *
     * @param brandName 品牌
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取产品属性
     *
     * @return attrs - 产品属性
     */
    public String getAttrs() {
        return attrs;
    }

    /**
     * 设置产品属性
     *
     * @param attrs 产品属性
     */
    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取品牌型号
     *
     * @return brand_model - 品牌型号
     */
    public String getBrandModel() {
        return brandModel;
    }

    /**
     * 设置品牌型号
     *
     * @param brandModel 品牌型号
     */
    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
    }

    /**
     * 获取商品型号
     *
     * @return model - 商品型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置商品型号
     *
     * @param model 商品型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取单价
     *
     * @return price - 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 获取财政内金额
     *
     * @return budget_finance - 财政内金额
     */
    public BigDecimal getBudgetFinance() {
        return budgetFinance;
    }

    /**
     * 设置财政内金额
     *
     * @param budgetFinance 财政内金额
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

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
    public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getServiceRequire() {
		return serviceRequire;
	}

	public void setServiceRequire(String serviceRequire) {
		this.serviceRequire = serviceRequire;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	
	
	public String getLicensePlateNum() {
		return licensePlateNum;
	}

	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	public String getFrameNum() {
		return frameNum;
	}

	public void setFrameNum(String frameNum) {
		this.frameNum = frameNum;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getFactoryPlateModel() {
		return factoryPlateModel;
	}

	public void setFactoryPlateModel(String factoryPlateModel) {
		this.factoryPlateModel = factoryPlateModel;
	}

	public String getFirstRegisterDate() {
		return firstRegisterDate;
	}

	public void setFirstRegisterDate(String firstRegisterDate) {
		this.firstRegisterDate = firstRegisterDate;
	}

	public String getTrafficCode() {
		return trafficCode;
	}

	public void setTrafficCode(String trafficCode) {
		this.trafficCode = trafficCode;
	}

	public BigDecimal getTrafficPrice() {
		return trafficPrice;
	}

	public void setTrafficPrice(BigDecimal trafficPrice) {
		this.trafficPrice = trafficPrice;
	}

	public String getTrafficStart() {
		return trafficStart;
	}

	public void setTrafficStart(String trafficStart) {
		this.trafficStart = trafficStart;
	}

	public String getTrafficEnd() {
		return trafficEnd;
	}

	public void setTrafficEnd(String trafficEnd) {
		this.trafficEnd = trafficEnd;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public BigDecimal getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(BigDecimal businessPrice) {
		this.businessPrice = businessPrice;
	}

	public String getBusinessStart() {
		return businessStart;
	}

	public void setBusinessStart(String businessStart) {
		this.businessStart = businessStart;
	}

	public String getBusinessEnd() {
		return businessEnd;
	}

	public void setBusinessEnd(String businessEnd) {
		this.businessEnd = businessEnd;
	}

	public BigDecimal getVehicleTax() {
		return vehicleTax;
	}

	public void setVehicleTax(BigDecimal vehicleTax) {
		this.vehicleTax = vehicleTax;
	}
	
    /**
     * 获取技术指标
     * @return
     */
    public String getTechnologyParam() {
		return technologyParam;
	}
    
    /**
     * 设置技术指标
     * @param technologyParam
     */
	public void setTechnologyParam(String technologyParam) {
		this.technologyParam = technologyParam;
	}
	
	public String getAddRate() {
		return addRate;
	}

	public void setAddRate(String addRate) {
		this.addRate = addRate;
	}

	public Integer getRequireType() {
		return requireType;
	}

	public void setRequireType(Integer requireType) {
		this.requireType = requireType;
	}
	
    /**
     * 获得响应参数
     * @return
     */
	public String getResponseParam() {
		return responseParam;
	}
	
	/**
	 * 设置响应参数
	 * @param responseParam
	 */
	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}
	
	public List<DealContractPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<DealContractPerson> persons) {
		this.persons = persons;
	}
	
}