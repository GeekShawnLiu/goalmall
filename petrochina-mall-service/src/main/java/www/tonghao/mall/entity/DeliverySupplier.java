package www.tonghao.mall.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

/**  

* <p>Title: DeliverySupplier</p>  

* <p>Description: 配送关联供应商</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@Table(name = "delivery_supplier")
public class DeliverySupplier extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 配送要求id
     */
    @Column(name = "delivery_id")
    private Long deliveryId;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

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
     * 获取配送要求id
     *
     * @return delivery_id - 配送要求id
     */
    public Long getDeliveryId() {
        return deliveryId;
    }

    /**
     * 设置配送要求id
     *
     * @param deliveryId 配送要求id
     */
    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * 获取供应商id
     *
     * @return supplier_id - 供应商id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商id
     *
     * @param supplierId 供应商id
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}