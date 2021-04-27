package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import www.tonghao.common.utils.ProductUtil;
import www.tonghao.common.warpper.ProductAttributeModel;
import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="产品")
public class Products  extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Products(){}
	
	@ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

	@ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 编号
     */
	@ApiModelProperty(value="编号")
    private String sn;

    /**
     * 名称
     */
	@ApiModelProperty(value="名称")
    private String name;
	
    /**
     * 售价
     */
	@ApiModelProperty(value="售价")
    private BigDecimal price;
	
	/**
     * 协议价
     */
	@ApiModelProperty(value="协议价")
    @Column(name = "protocol_price")
    private BigDecimal protocolPrice;

    /**
     * 市场价
     */
	@ApiModelProperty(value="市场价")
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
	@ApiModelProperty(value="成本价")
    private BigDecimal cost;

    /**
     * 主图url
     */
	@ApiModelProperty(value="主图url")
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 主图名称
     */
	@ApiModelProperty(value="主图名称")
    @Column(name = "cover_name")
    private String coverName;

    /**
     * 单位
     */
	@ApiModelProperty(value="单位")
    private String unit;

    /**
     * 重量
     */
	@ApiModelProperty(value="重量")
    private String weight;

    /**
     * 库存
     */
	@ApiModelProperty(value="库存")
    private Integer stock;

    /**
     * 平台品目ID
     */
    @ApiModelProperty(value = "平台品目ID")
    @Column(name = "catalog_id")
    private Long catalogId;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value="品牌ID")
    @Column(name = "brand_id")
    private Long brandId;
    
    /**
     * 品牌名称
     */
    @ApiModelProperty(value="电商品牌名称")
    @Column(name = "mall_brand_name")
    private String mallBrandName;
    
    /**
     * 电商参数
     */
    @ApiModelProperty(value="电商参数")
    @Column(name = "param")
    private String param;
    
    /**
     * 商品配置
     */
    @ApiModelProperty(value="商品配置")
    @Column(name = "ware")
    private String ware;
    
    /**
     * 商品产地
     */
    @ApiModelProperty(value="商品产地")
    @Column(name = "product_area")
    private String productArea;
    
    /**
     * 条形码
     */
    @ApiModelProperty(value="条形码")
    @Column(name = "upc")
    private String upc;

    /**
     * 型号
     */
    @ApiModelProperty(value="型号")
    private String model;

    /**
     * 创建人ID
     */
    @Column(name = "user_id")
    @ApiModelProperty(value="创建人ID")
    private Long userId;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value="供应商ID")
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 销量
     */
    @ApiModelProperty(value="销量")
    private Integer sales;

    /**
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    private Integer hits;

    /**
     * 是否上架  0：是 1：否
     */
    @Transient
    @Column(name = "is_marketable")
    private Byte isMarketable;

    /**
     * 月销量
     */
    @ApiModelProperty(value="月销量")
    @Column(name = "month_sales")
    private Integer monthSales;

    /**
     * 月销量更新索引值(日期格式:yyyyMM)
     */
    @ApiModelProperty(value="月销量更新索引值(日期格式:yyyyMM)")
    @Column(name = "month_sales_index")
    private Integer monthSalesIndex;

    /**
     * 是否删除 0:否  1：是
     */
    @ApiModelProperty(value="是否删除 0:否  1：是")
    @Column(name = "is_delete")
    private Integer isDelete;
    
    /**
     * 
     */
    @ApiModelProperty(value="sku")
    private String sku;
    
    /**
     * 所属协议
     */
    @ApiModelProperty(value="所属协议")
    @Column(name = "protocol_id")	
    private Long protocolId;
    
    /**
     *品牌名称
     */
    @ApiModelProperty(value="品牌名称")
    @Column(name = "brand_name")
    private String brandName;
    
    /**
     * 品目名称
     */
    @ApiModelProperty(value="品目名称")
    @Column(name = "catalog_name")
    private String catalogName;
    
    /**
     * 供应商名称
     */
    @ApiModelProperty(value="供应商名称")
    @Column(name = "supplier_name")
    private String supplierName;
    
    /**
     * 商品状态  0：暂存,1：待审核,2：退回,3：上架,4：下架
     */
    @ApiModelProperty(value="状态  0：暂存,1：待审核,2：退回,3：上架,4：下架")
    private Integer status;
    
    /**
     * 协议名称
     */
    @ApiModelProperty(value="协议名称")
    @Column(name = "protocol_name")
    private String protocolName;
    
    /**
     * 厂商标识
     */
    @ApiModelProperty(" 厂商标识")
    @Column(name = "manufacturer_ident")
    private String manufacturerIdent;
    
    /**
     * 售后服务
     */
    @ApiModelProperty("售后服务")
    @Column(name = "after_sale_service")
    private String afterSaleService;
    
    /**
     * 图文详情
     */
    @ApiModelProperty("图文详情")
    private String detail;
    
    /**
     * 供应商协议关联id
     */
    @ApiModelProperty("供应商协议关联id")
    @Column(name = "supplier_protocol_id")
    private Integer supplierProtocolId;
    
    /**
     * 财政品目id
     */
    @ApiModelProperty("财政品目id")
    @Column(name = "gov_catalog_id")
    private Long govCatalogId;
    
    /**
     * 厂商官网url地址
     */
    @ApiModelProperty("厂商官网url地址")
    @Column(name = "factory_URL")
    private String factoryURL;
    
    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String description;
    
    /** 
     * isCompleteMachine 台式计算机是否整机  0：是 ，1不是
     */  
    @Column(name = "is_complete_machine")
    private Integer isCompleteMachine;
    
    /** 
     * 质保期(年)
     */  
    @ApiModelProperty("质保期(年)")
    @Column(name = "guarantee_period")
    private String guaranteePeriod;
    
    /** 
     * 商品聚合id
     */  
    @Column(name = "commodities_id")
    private Long commoditiesId;
    
    /**
     * 0 不节能，1节能
     */
    @Column(name = "is_energy_saving")
    private Integer isEnergySaving;
    
    /** 
     * 是否批量  0：不是  1：是
     */  
    @Column(name = "is_batch")
    @ApiModelProperty("是否批量  0：不是  1：是")
    private Integer isBatch;
    
    @Column(name = "car_type")
    @ApiModelProperty("车辆类型 1：新能源车辆 2：非新能源车辆")
    private Integer carType;
    
    /** 
     * 节能证书编号
     */  
    @Column(name = "energy_save_certNo")
    @ApiModelProperty("节能证书编号")
    private String energySaveCertNo;

    /**
     * 节能证书图片路径
     */
    @Column(name = "energy_save_certImg")
    @ApiModelProperty("节能证书图片路径")
    private String energySaveCertImg;

    /** 
     * 产地类型1：国内  2：进口
     */  
    @Column(name = "product_area_type")
    @ApiModelProperty("产地类型1：国内  2：进口")
    private Integer productAreaType;
    
    @ApiModelProperty("优惠率 (小数点,如:0.1)")
    @Transient
    private Float benefitRate;
    
    @Transient
	private Brand brand;
    
    @Transient
	private Protocol protocol;
    
    @Transient
    private PlatformCatalogs platformCatalogs;
    
    @Transient
    private List<ProductPics> pics;
    
    @Transient
  	@ApiModelProperty(value="产品属性")
  	private ProductAttributeModel attributeModel;
      
	@ApiModelProperty(value="供应商")
  	@Transient
  	private Suppliers supplier;
    
	@ApiModelProperty(value="是否调价 0否 1是")
	@Column(name = "is_change_price")
  	private Integer isChangePrice;
	
	
	@ApiModelProperty(value="是否显示3 上架  4下架")
	@Column(name = "is_show")
  	private Integer isShow;
	
	@Transient
	@ApiModelProperty(value="上浮金额")
	private Float increasePrice;
	
	
	@Transient
	@ApiModelProperty(value="增幅比")
	private Float increaseThan;
	
	@Transient
	@ApiModelProperty(value="调价前折扣率")
	private Float oldDiscount;
	
	@Transient
	@ApiModelProperty(value="调价后折扣率")
	private Float newDiscount;
	
	@Transient
	@ApiModelProperty(value="调价前折扣率开始")
	private Float beginOldDiscount;
	
	@Transient
	@ApiModelProperty(value="调价前折扣率结束")
	private Float endOldDiscount;
	
	@ApiModelProperty(value="电商商品id")
	@Column(name = "emall_cataloge_id")
	private String emallCatalogeId;
	
	@Column(name = "wxintroduction")
	private String wxintroduction;
	
	@Column(name = "appintroduce")
	private String appintroduce;
	
	//是否厂商配送 1是 0否
	@Column(name = "is_factory_ship")
	private Integer isFactoryShip;

    /**
     * 是否环保 0否 1是
     */
    @Column(name = "is_environment")
    private Integer isEnvironment;

    /**
     * 环保证书编号
     */
    @Column(name = "environment_certNo")
    @ApiModelProperty("环保证书编号")
    private String environmentCertNo;

    /**
     * 环保证书图片路径
     */
    @Column(name = "environment_certImg")
    @ApiModelProperty("环保证书图片路径")
    private String environmentCertImg;

    /**
     * 节能证书有效期
     */
    @Column(name = "energy_save_cert_indate")
    @ApiModelProperty("节能证书有效期")
    private String energySaveCertIndate;

    /**
     * 环保证书有效期
     */
    @Column(name = "environment_cert_indate")
    @ApiModelProperty("环保证书有效期")
    private String environmentCertIndate;

	
	@Transient
    private List<ProductParameter> productParameters;
    
	@Transient
	private List<ProductChangePrice> priceList;
	
	
    /**
     * 车辆参数
     */
    @Transient
    private String carParam;
    
    /**
     * 车辆价格
     */
    @Transient
    private BigDecimal carPrice;
    
    
    

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
     * 获取编号
     *
     * @return sn - 编号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置编号
     *
     * @param sn 编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取售价
     *
     * @return price - 售价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置售价
     *
     * @param price 售价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取成本价
     *
     * @return cost - 成本价
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 设置成本价
     *
     * @param cost 成本价
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 获取主图url
     *
     * @return cover_url - 主图url
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * 设置主图url
     *
     * @param coverUrl 主图url
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    /**
     * 获取主图名称
     *
     * @return cover_name - 主图名称
     */
    public String getCoverName() {
        return coverName;
    }

    /**
     * 设置主图名称
     *
     * @param coverName 主图名称
     */
    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取重量
     *
     * @return weight - 重量
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取平台品目ID
     *
     * @return catalog_id - 平台品目ID
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * 设置品目ID
     *
     * @param catalogId 品目ID
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * 获取品牌ID
     *
     * @return brand_id - 品牌ID
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 设置品牌ID
     *
     * @param brandId 品牌ID
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取型号
     *
     * @return model - 型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置型号
     *
     * @param model 型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取创建人ID
     *
     * @return user_id - 创建人ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置创建人ID
     *
     * @param userId 创建人ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取供应商ID
     *
     * @return supplier_id - 供应商ID
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商ID
     *
     * @param supplierId 供应商ID
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取销量
     *
     * @return sales - 销量
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * 设置销量
     *
     * @param sales 销量
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * 获取点击量
     *
     * @return hits - 点击量
     */
    public Integer getHits() {
        return hits;
    }

    /**
     * 设置点击量
     *
     * @param hits 点击量
     */
    public void setHits(Integer hits) {
        this.hits = hits;
    }

    /**
     * 获取是否上架  0：是 1：否
     *
     * @return is_marketable - 是否上架  0：是 1：否
     */
    public Byte getIsMarketable() {
        return isMarketable;
    }

    /**
     * 设置是否上架  0：是 1：否
     *
     * @param isMarketable 是否上架  0：是 1：否
     */
    public void setIsMarketable(Byte isMarketable) {
        this.isMarketable = isMarketable;
    }

    /**
     * 获取月销量
     *
     * @return month_sales - 月销量
     */
    public Integer getMonthSales() {
        return monthSales;
    }

    /**
     * 设置月销量
     *
     * @param monthSales 月销量
     */
    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    /**
     * 获取月销量更新索引值(日期格式:yyyyMM)
     *
     * @return month_sales_index - 月销量更新索引值(日期格式:yyyyMM)
     */
    public Integer getMonthSalesIndex() {
        return monthSalesIndex;
    }

    /**
     * 设置月销量更新索引值(日期格式:yyyyMM)
     *
     * @param monthSalesIndex 月销量更新索引值(日期格式:yyyyMM)
     */
    public void setMonthSalesIndex(Integer monthSalesIndex) {
        this.monthSalesIndex = monthSalesIndex;
    }

    /**
     * 获取是否删除 0:否  1：是
     *
     * @return is_delete - 是否删除 0:否  1：是
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除 0:否  1：是
     *
     * @param isDelete 是否删除 0:否  1：是
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Long protocolId) {
		this.protocolId = protocolId;
	}

	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public List<ProductPics> getPics() {
		return pics;
	}

	public void setPics(List<ProductPics> pics) {
		this.pics = pics;
	}

	public String getManufacturerIdent() {
		return manufacturerIdent;
	}

	public void setManufacturerIdent(String manufacturerIdent) {
		this.manufacturerIdent = manufacturerIdent;
	}

	public String getAfterSaleService() {
		return afterSaleService;
	}

	public void setAfterSaleService(String afterSaleService) {
		this.afterSaleService = afterSaleService;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getSupplierProtocolId() {
		return supplierProtocolId;
	}

	public void setSupplierProtocolId(Integer supplierProtocolId) {
		this.supplierProtocolId = supplierProtocolId;
	}

	public Long getGovCatalogId() {
		return govCatalogId;
	}

	public void setGovCatalogId(Long govCatalogId) {
		this.govCatalogId = govCatalogId;
	}

	public String getFactoryURL() {
		return factoryURL;
	}

	public void setFactoryURL(String factoryURL) {
		this.factoryURL = factoryURL;
	}

	

	public Float getBenefitRate() {
		return benefitRate;
	}

	public void setBenefitRate(Float benefitRate) {
		this.benefitRate = benefitRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Integer getIsCompleteMachine() {
		return isCompleteMachine;
	}

	public void setIsCompleteMachine(Integer isCompleteMachine) {
		this.isCompleteMachine = isCompleteMachine;
	}

	public String getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(String guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}
	
	public String getMallBrandName() {
		return mallBrandName;
	}

	public void setMallBrandName(String mallBrandName) {
		this.mallBrandName = mallBrandName;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getWare() {
		return ware;
	}

	public void setWare(String ware) {
		this.ware = ware;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public Long getCommoditiesId() {
		return commoditiesId;
	}

	public void setCommoditiesId(Long commoditiesId) {
		this.commoditiesId = commoditiesId;
	}
	
	

	/**
	 * 是否允许直购
	 * @return
	 */
	public boolean isAllowPurchase(){
		if(StringUtils.isNotEmpty(getSku())){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否电商商品
	 * @return
	 */
	public boolean isEmall(){
		if(StringUtils.isNotBlank(getSku())){
			return true;
		}
		return false;
	}

	public Integer getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Integer isBatch) {
		this.isBatch = isBatch;
	}

	public Integer getIsEnergySaving() {
		return isEnergySaving;
	}

	public void setIsEnergySaving(Integer isEnergySaving) {
		this.isEnergySaving = isEnergySaving;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	
	
    /**
     * 获得车辆参数
     * @return
     */
    public String getCarParam() {
		return carParam;
	}
    
    /**
     * 设置车辆参数
     * @param carParam
     */
	public void setCarParam(String carParam) {
		this.carParam = carParam;
	}

	public String getEnergySaveCertNo() {
		return energySaveCertNo;
	}

	public void setEnergySaveCertNo(String energySaveCertNo) {
		this.energySaveCertNo = energySaveCertNo;
	}

	public Integer getProductAreaType() {
		return productAreaType;
	}

	public void setProductAreaType(Integer productAreaType) {
		this.productAreaType = productAreaType;
	}
	
    /**
     * 获得车辆价格
     * @return
     */
	public BigDecimal getCarPrice() {
		return carPrice;
	}
	
	/**
	 * 设置车辆价格
	 * @param carPrice
	 */
	public void setCarPrice(BigDecimal carPrice) {
		this.carPrice = carPrice;
	}
	
	/**
	 * 产地类型label
	 * @return
	 */
	public String getProductAreaTypeLabel(){
		if(getProductAreaType()!=null){
			if(getProductAreaType().equals(1)){
				return "国内";
			}else if(getProductAreaType().equals(2)){
				return "进口";
			}
		}
		return "";
	}

	public ProductAttributeModel getAttributeModel() {
		return attributeModel;
	}

	public void setAttributeModel(ProductAttributeModel attributeModel) {
		this.attributeModel = attributeModel;
	}

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public List<ProductParameter> getProductParameters() {
		return productParameters;
	}

	public void setProductParameters(List<ProductParameter> productParameters) {
		this.productParameters = productParameters;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public PlatformCatalogs getPlatformCatalogs() {
		return platformCatalogs;
	}

	public void setPlatformCatalogs(PlatformCatalogs platformCatalogs) {
		this.platformCatalogs = platformCatalogs;
	}
	
	@ApiModelProperty(value="商品Url链接")
	public String getUrl(){
		return ProductUtil.getMallProductUrl(getCatalogId(), getId());
	}

	public BigDecimal getProtocolPrice() {
		return protocolPrice;
	}

	public void setProtocolPrice(BigDecimal protocolPrice) {
		this.protocolPrice = protocolPrice;
	}

	public Integer getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(Integer isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public Float getIncreasePrice() {
		return increasePrice;
	}

	public void setIncreasePrice(Float increasePrice) {
		this.increasePrice = increasePrice;
	}

	public Float getIncreaseThan() {
		return increaseThan;
	}

	public void setIncreaseThan(Float increaseThan) {
		this.increaseThan = increaseThan;
	}

	public Float getOldDiscount() {
		return oldDiscount;
	}

	public void setOldDiscount(Float oldDiscount) {
		this.oldDiscount = oldDiscount;
	}

	public Float getNewDiscount() {
		return newDiscount;
	}

	public void setNewDiscount(Float newDiscount) {
		this.newDiscount = newDiscount;
	}

	public Float getBeginOldDiscount() {
		return beginOldDiscount;
	}

	public void setBeginOldDiscount(Float beginOldDiscount) {
		this.beginOldDiscount = beginOldDiscount;
	}

	public Float getEndOldDiscount() {
		return endOldDiscount;
	}

	public void setEndOldDiscount(Float endOldDiscount) {
		this.endOldDiscount = endOldDiscount;
	}

	public List<ProductChangePrice> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<ProductChangePrice> priceList) {
		this.priceList = priceList;
	}

	public String getEmallCatalogeId() {
		return emallCatalogeId;
	}

	public void setEmallCatalogeId(String emallCatalogeId) {
		this.emallCatalogeId = emallCatalogeId;
	}
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getWxintroduction() {
		return wxintroduction;
	}

	public void setWxintroduction(String wxintroduction) {
		this.wxintroduction = wxintroduction;
	}

	public String getAppintroduce() {
		return appintroduce;
	}

	public void setAppintroduce(String appintroduce) {
		this.appintroduce = appintroduce;
	}

	public Integer getIsFactoryShip() {
		return isFactoryShip;
	}

	public void setIsFactoryShip(Integer isFactoryShip) {
		this.isFactoryShip = isFactoryShip;
	}

    public String getEnergySaveCertImg() {
        return energySaveCertImg;
    }

    public void setEnergySaveCertImg(String energySaveCertImg) {
        this.energySaveCertImg = energySaveCertImg;
    }

    public Integer getIsEnvironment() {
        return isEnvironment;
    }

    public void setIsEnvironment(Integer isEnvironment) {
        this.isEnvironment = isEnvironment;
    }

    public String getEnvironmentCertNo() {
        return environmentCertNo;
    }

    public void setEnvironmentCertNo(String environmentCertNo) {
        this.environmentCertNo = environmentCertNo;
    }

    public String getEnvironmentCertImg() {
        return environmentCertImg;
    }

    public void setEnvironmentCertImg(String environmentCertImg) {
        this.environmentCertImg = environmentCertImg;
    }

    public String getEnergySaveCertIndate() {
        return energySaveCertIndate;
    }

    public void setEnergySaveCertIndate(String energySaveCertIndate) {
        this.energySaveCertIndate = energySaveCertIndate;
    }

    public String getEnvironmentCertIndate() {
        return environmentCertIndate;
    }

    public void setEnvironmentCertIndate(String environmentCertIndate) {
        this.environmentCertIndate = environmentCertIndate;
    }
}

