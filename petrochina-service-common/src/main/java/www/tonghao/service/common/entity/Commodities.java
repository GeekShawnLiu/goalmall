package www.tonghao.service.common.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;


/**  
* <p>Title: Commodities</p>  
* <p>Description: 聚合商品</p>  
* @author YML  
* @date 2018年12月19日  
*/ 
public class Commodities extends BaseEntity{

    /** 
	 * 
	 */  
	private static final long serialVersionUID = 1L;

	/**
     * 品牌名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 平台品目ID
     */
    @Column(name = "catalog_id")
    private Long catalogId;

    /**
     * 品目名称
     */
    @Column(name = "catalog_name")
    private String catalogName;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 型号
     */
    private String model;

    /**
     * 销售数量
     */
    @Column(name = "sales_num")
    private Integer salesNum;

    /**
     * 销售金额
     */
    @Column(name = "sales_total")
    private BigDecimal salesTotal;
    
    @Transient
    private List<CommoditiesParameter> commoditiesParameters;
    
    /** 
     * 电商在售数量
     */ 
    @Transient
    private Integer mallNum;
    
    /** 
     * 协议在售数量
     */  
    @Transient
    private Integer protocolNum;
    
    /** 
     * 最低价
     */  
    @Transient
    private BigDecimal minPrice;

    /**
     * 获取品牌名称
     *
     * @return brand_name - 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
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
     * 获取采购品目ID
     *
     * @return catalog_id - 采购品目ID
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * 设置采购品目ID
     *
     * @param catalogId 采购品目ID
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
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
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取修改时间
     *
     * @return updated_at - 修改时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置修改时间
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
     * 获取销售数量
     *
     * @return sales_num - 销售数量
     */
    public Integer getSalesNum() {
        return salesNum;
    }

    /**
     * 设置销售数量
     *
     * @param salesNum 销售数量
     */
    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    /**
     * 获取销售金额
     *
     * @return sales_total - 销售金额
     */
    public BigDecimal getSalesTotal() {
        return salesTotal;
    }

    /**
     * 设置销售金额
     *
     * @param salesTotal 销售金额
     */
    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

	public List<CommoditiesParameter> getCommoditiesParameters() {
		return commoditiesParameters;
	}

	public void setCommoditiesParameters(
			List<CommoditiesParameter> commoditiesParameters) {
		this.commoditiesParameters = commoditiesParameters;
	}

	public Integer getMallNum() {
		return mallNum;
	}

	public void setMallNum(Integer mallNum) {
		this.mallNum = mallNum;
	}

	public Integer getProtocolNum() {
		return protocolNum;
	}

	public void setProtocolNum(Integer protocolNum) {
		this.protocolNum = protocolNum;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	
}