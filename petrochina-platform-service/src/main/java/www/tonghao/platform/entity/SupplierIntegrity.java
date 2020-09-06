package www.tonghao.platform.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "supplier_integrity")
public class SupplierIntegrity extends BaseEntity {

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "integrity_id")
    private Long integrityId;

    /**
     * 扣除分值
     */
    @Column(name = "deducting_score")
    private BigDecimal deductingScore;

    /**
     * 减分信用描述
     */
    private String remark;

    /**
     * 扣减因素原因
     */
    @Column(name = "integrity_title")
    private String integrityTitle;

    /**
     * 0 不限制，1限制
     */
    @Column(name = "is_transaction")
    private Integer isTransaction;

    /**
     * 处罚，停止时间
     */
    @Column(name = "create_at")
    private String createAt;

    /**
     * 恢复时间
     */
    @Column(name = "recover_at")
    private String recoverAt;
    
    /**
     * 操作人ip
     */
    @Column(name = "address_ip")
    private String addressIp;


    /**
     * @return supplier_id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * @param supplierId
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * @return integrity_id
     */
    public Long getIntegrityId() {
        return integrityId;
    }

    /**
     * @param integrityId
     */
    public void setIntegrityId(Long integrityId) {
        this.integrityId = integrityId;
    }

    /**
     * 获取扣除分值
     *
     * @return deducting_score - 扣除分值
     */
    public BigDecimal getDeductingScore() {
        return deductingScore;
    }

    /**
     * 设置扣除分值
     *
     * @param deductingScore 扣除分值
     */
    public void setDeductingScore(BigDecimal deductingScore) {
        this.deductingScore = deductingScore;
    }

    /**
     * 获取减分信用描述
     *
     * @return remark - 减分信用描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置减分信用描述
     *
     * @param remark 减分信用描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取扣减因素原因
     *
     * @return integrity_title - 扣减因素原因
     */
    public String getIntegrityTitle() {
        return integrityTitle;
    }

    /**
     * 设置扣减因素原因
     *
     * @param integrityTitle 扣减因素原因
     */
    public void setIntegrityTitle(String integrityTitle) {
        this.integrityTitle = integrityTitle;
    }

    /**
     * 获取0 不限制，1限制
     *
     * @return is_transaction - 0 不限制，1限制
     */
    public Integer getIsTransaction() {
        return isTransaction;
    }

    /**
     * 设置0 不限制，1限制
     *
     * @param isTransaction 0 不限制，1限制
     */
    public void setIsTransaction(Integer isTransaction) {
        this.isTransaction = isTransaction;
    }

    /**
     * 获取处罚，停止时间
     *
     * @return create_at - 处罚，停止时间
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * 设置处罚，停止时间
     *
     * @param createAt 处罚，停止时间
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取恢复时间
     *
     * @return recover_at - 恢复时间
     */
    public String getRecoverAt() {
        return recoverAt;
    }

    /**
     * 设置恢复时间
     *
     * @param recoverAt 恢复时间
     */
    public void setRecoverAt(String recoverAt) {
        this.recoverAt = recoverAt;
    }

	public String getAddressIp() {
		return addressIp;
	}

	public void setAddressIp(String addressIp) {
		this.addressIp = addressIp;
	}
    
    
    
}