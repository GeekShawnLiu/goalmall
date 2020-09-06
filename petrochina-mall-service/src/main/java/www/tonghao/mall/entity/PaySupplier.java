package www.tonghao.mall.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

/**  

* <p>Title: PaySupplier</p>  

* <p>Description: 支付方式关联供应商</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@Table(name = "pay_supplier")
public class PaySupplier extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "pay_way_id")
    private Long payWayId;

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
     * @return pay_way_id
     */
    public Long getPayWayId() {
        return payWayId;
    }

    /**
     * @param payWayId
     */
    public void setPayWayId(Long payWayId) {
        this.payWayId = payWayId;
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