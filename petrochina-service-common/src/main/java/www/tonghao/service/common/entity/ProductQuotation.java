package www.tonghao.service.common.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@ApiModel(value = "商品行情信息")
@Table(name = "product_quotation")
public class ProductQuotation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty(value = "品目id")
    @Column(name = "catalog_id")
    private Long catalogId;

    @ApiModelProperty(value = "售价")
    @Column(name = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "协议价")
    @Column(name = "protocol_price")
    private BigDecimal protocolPrice;

    @ApiModelProperty(value = "库存")
    @Column(name = "stock")
    private Integer stock;

    @ApiModelProperty(value = "销量")
    @Column(name = "sales")
    private Integer sales;

    @ApiModelProperty(value = "是否删除 0:否 1：是")
    @Column(name = "is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "状态 3：上架，4：下架")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = "sku")
    @Column(name = "sku")
    private String sku;

    @ApiModelProperty(value = "对接平台id")
    @Column(name = "platform_info_id")
    private Long platformInfoId;

    @ApiModelProperty(value = "对接平台code")
    @Column(name = "platform_info_code")
    private String platformInfoCode;

    @ApiModelProperty(value = "所属协议")
    @Column(name = "protocol_id")
    private Long protocolId;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @ApiModelProperty(value = "修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    @Transient
    private Products products;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProtocolPrice() {
        return protocolPrice;
    }

    public void setProtocolPrice(BigDecimal protocolPrice) {
        this.protocolPrice = protocolPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getPlatformInfoId() {
        return platformInfoId;
    }

    public void setPlatformInfoId(Long platformInfoId) {
        this.platformInfoId = platformInfoId;
    }

    public String getPlatformInfoCode() {
        return platformInfoCode;
    }

    public void setPlatformInfoCode(String platformInfoCode) {
        this.platformInfoCode = platformInfoCode;
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
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

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}
