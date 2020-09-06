package www.tonghao.service.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:活动关联的商品
 * @date 2019年4月25日
 */
@Table(name = "activity_product")
public class ActivityProduct extends BaseEntity{

    /**@Description:
	 * @date 2019年4月25日
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 活动id
     */
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;
    
    /**
     * 排序
     */
    private Long rank;
    
    /**
     * 
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private String updatedAt;
    
    @ApiModelProperty(value="商品名称")
    @Transient
    private String productsName; //商品名称
    
    @ApiModelProperty(value="商品品目名称")
    @Transient
    private String catalogsName; //商品品目名称
    
    @ApiModelProperty(value="sku")
    @Transient
    private String sku; //sku
    
    @ApiModelProperty(value="售价")
    @Transient
    private BigDecimal price; //售价
    
    @ApiModelProperty(value="供应商")
    @Transient
    private String supplierName; //供应商
    
    @ApiModelProperty(value="活动类型")
    @Transient
    private Integer activityType; //活动类型
    
    @ApiModelProperty(value="活动名称")
    @Transient
    private String activityName; //活动名称
    
    @ApiModelProperty(value="商品id集合")
    @Transient
    private Long[] productIds;
    
    @ApiModelProperty(value="品目id集合")
    @Transient
    private Long[] catalogsIds;
    
    @Transient
    private Long supplierId;
    
    @Transient
    private BigDecimal startPrice;
    
    @Transient
    private BigDecimal endPrice;
    
    @ApiModelProperty(value="当前页",name="page")
    @Transient
    private Integer page;
    
    @ApiModelProperty(value="一页显示的条数",name="rows")
    @Transient
    private Integer rows;
    
    @ApiModelProperty(value="状态  0：暂存,1：待审核,2：退回,3：上架,4：下架")
    @Transient
    private Integer status;
    
    @Transient
    private Long catalogsId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Long[] getProductIds() {
		return productIds;
	}

	public void setProductIds(Long[] productIds) {
		this.productIds = productIds;
	}

	public Long[] getCatalogsIds() {
		return catalogsIds;
	}

	public void setCatalogsIds(Long[] catalogsIds) {
		this.catalogsIds = catalogsIds;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCatalogsId() {
		return catalogsId;
	}

	public void setCatalogsId(Long catalogsId) {
		this.catalogsId = catalogsId;
	}

    
}