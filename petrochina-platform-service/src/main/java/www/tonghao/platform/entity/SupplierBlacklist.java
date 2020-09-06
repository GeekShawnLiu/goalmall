package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "supplier_blacklist")
public class SupplierBlacklist extends BaseEntity {

    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 备注
     */
    private String remark;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

    @Transient
    private Double score;

    /**
     * 获取供应商id
     *
     * @return supplierId - 供应商id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商id
     *
     * @param supplierid 供应商id
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取供应商名称
     *
     * @return supplier_name - 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置供应商名称
     *
     * @param supplierName 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return create_at
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * @param createAt
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    /**
     * @return update_at
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * @param updateAt
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
    
    
}