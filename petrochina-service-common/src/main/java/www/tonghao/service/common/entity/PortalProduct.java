package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "portal_product")
public class PortalProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 主图url
     */
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 排序
     */
    @Column(name = "position")
    private Integer position;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
