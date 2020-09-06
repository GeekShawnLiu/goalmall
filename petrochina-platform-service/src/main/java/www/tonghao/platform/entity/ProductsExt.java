package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


/**  

* <p>Title: ProductsExt</p>  

* <p>Description: 商品拓展表，即台式计算机的商品对应显示的品牌型号数据</p>  

* @author Yml  

* @date 2018年12月4日  

*/  
@Table(name = "products_ext")
public class ProductsExt extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    /**
     * 商品拓展表 主键id
     */
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 型号
     */
    private String model;

    /**
     * 名牌名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 财政品目id
     */
    @Column(name = "catalog_id")
    private Long catalogId;

    /**
     * 财政品目名称
     */
    @Column(name = "catalog_name")
    private String catalogName;

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
     * 获取商品拓展表 主键id
     *
     * @return updated_at - 商品拓展表 主键id
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置商品拓展表 主键id
     *
     * @param updatedAt 商品拓展表 主键id
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
     * 获取名牌名称
     *
     * @return brand_name - 名牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置名牌名称
     *
     * @param brandName 名牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取财政品目id
     *
     * @return catalog_id - 财政品目id
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * 设置财政品目id
     *
     * @param catalogId 财政品目id
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * 获取财政品目名称
     *
     * @return catalog_name - 财政品目名称
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * 设置财政品目名称
     *
     * @param catalogName 财政品目名称
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
}