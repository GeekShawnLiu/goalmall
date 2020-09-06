package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.common.utils.ProductUtil;
import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="楼层品目")
@Table(name = "floor_platform_catalog")
public class FloorPlatformCatalog extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

	@ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 平台品目ID
     */
	@ApiModelProperty(value="平台品目ID")
    @Column(name = "platform_catalog_id")
    private Long platformCatalogId;

    /**
     * 平台品目名称
     */
	@ApiModelProperty(value="平台品目名称")
    @Column(name = "platform_catalog_name")
    private String platformCatalogName;
    
    /**
     * 楼层产品
     */
	@ApiModelProperty(value="创建时间")
    @Transient
    private List<MallProducts> products;
    
    /**
     * 特惠产品
     */
    @Transient
    @ApiModelProperty(value="特惠产品")
    private List<MallProducts> benefitProducts;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;

    @ApiModelProperty(value="楼层ID")
    @Column(name = "floor_id")
    private Long floorId;
    
    /**
     * 品目类型（m商城品目p平台品目）
     */
    private String catalogType;


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
     * 获取平台品目ID
     *
     * @return platform_catalog_id - 平台品目ID
     */
    public Long getPlatformCatalogId() {
        return platformCatalogId;
    }

    /**
     * 设置平台品目ID
     *
     * @param platformCatalogId 平台品目ID
     */
    public void setPlatformCatalogId(Long platformCatalogId) {
        this.platformCatalogId = platformCatalogId;
    }

    /**
     * 获取平台品目名称
     *
     * @return platform_catalog_name - 平台品目名称
     */
    public String getPlatformCatalogName() {
        return platformCatalogName;
    }

    /**
     * 设置平台品目名称
     *
     * @param platformCatalogName 平台品目名称
     */
    public void setPlatformCatalogName(String platformCatalogName) {
        this.platformCatalogName = platformCatalogName;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return floor_id
     */
    public Long getFloorId() {
        return floorId;
    }

    /**
     * @param floorId
     */
    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }
    
    
    public String getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}
	
	public List<MallProducts> getProducts() {
		return products;
	}

	public void setProducts(List<MallProducts> products) {
		this.products = products;
	}

	public List<MallProducts> getBenefitProducts() {
		return benefitProducts;
	}

	public void setBenefitProducts(List<MallProducts> benefitProducts) {
		this.benefitProducts = benefitProducts;
	}
    
	public String getUrl(){
		return ProductUtil.getMallCatalogUrl(platformCatalogId);
	}
}