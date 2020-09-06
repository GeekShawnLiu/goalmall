package www.tonghao.platform.entity;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: 补单商品信息明细
 * 
 * @version 2019年6月24日
 * @since JDK1.8
 */
@Table(name="supplement_order_item")
public class SupplementOrderItem extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    @ApiModelProperty(value="删除标识 默认0：未删除，1：删除")
    @Column(name = "is_delete")
    private Integer isDelete;
    
    @ApiModelProperty(value="商品id")
    @Column(name = "product_id")
    private Long productId;
    
    @ApiModelProperty(value="商品名称")
    @Column(name = "product_name")
    private String productName;
    
    @ApiModelProperty(value="商品数量")
    @Column(name = "product_num")
    private Integer productNum;
    
    @ApiModelProperty(value="商品单价")
    @Column(name = "product_price")
    private BigDecimal productPrice;
    
    @ApiModelProperty(value="供应商id")
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @ApiModelProperty(value="供应商名称")
    @Column(name = "supplier_name")
    private String supplierName;
    
    @ApiModelProperty(value="补单信息id")
    @Column(name = "supplement_order_id")
    private Long supplementOrderId;
    
    @Transient
    private SupplementOrder supplementOrder;

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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getSupplementOrderId() {
		return supplementOrderId;
	}

	public void setSupplementOrderId(Long supplementOrderId) {
		this.supplementOrderId = supplementOrderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public SupplementOrder getSupplementOrder() {
		return supplementOrder;
	}

	public void setSupplementOrder(SupplementOrder supplementOrder) {
		this.supplementOrder = supplementOrder;
	}
}
