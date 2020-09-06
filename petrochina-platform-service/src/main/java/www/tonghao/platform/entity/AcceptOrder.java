package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "accept_order")
public class AcceptOrder extends BaseEntity {
   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 合同id
     */
    @Column(name = "contract_id")
    private String contractId;

    /**
     * 验收日期
     */
    @Column(name = "acceptance_date")
    private String acceptanceDate;

    /**
     * 验收方式
     */
    @Column(name = "acceptance_type")
    private String acceptanceType;

    /**
     * 验收阶段
     */
    @Column(name = "acceptance_stage")
    private String acceptanceStage;

    /**
     * 0否、1是
     */
    @Column(name = "is_open")
    private Integer isOpen;

    /**
     * 供应商意见
     */
    @Column(name = "supplier_opinion")
    private String supplierOpinion;

    /**
     * 采购人意见
     */
    @Column(name = "unit_opinion")
    private String unitOpinion;

    /**
     * 验收意见
     */
    @Column(name = "acceptance_opinion")
    private String acceptanceOpinion;

    /**
     * 验收小组名单
     */
    @Column(name = "acceptance_team")
    private String acceptanceTeam;

    /**
     * 附件地址
     */
    private String url;


    /**
     * 获取合同id
     *
     * @return contract_id - 合同id
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 设置合同id
     *
     * @param contractId 合同id
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * 获取验收日期
     *
     * @return acceptance_date - 验收日期
     */
    public String getAcceptanceDate() {
        return acceptanceDate;
    }

    /**
     * 设置验收日期
     *
     * @param acceptanceDate 验收日期
     */
    public void setAcceptanceDate(String acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    /**
     * 获取验收方式
     *
     * @return acceptance_type - 验收方式
     */
    public String getAcceptanceType() {
        return acceptanceType;
    }

    /**
     * 设置验收方式
     *
     * @param acceptanceType 验收方式
     */
    public void setAcceptanceType(String acceptanceType) {
        this.acceptanceType = acceptanceType;
    }

    /**
     * 获取验收阶段
     *
     * @return acceptance_stage - 验收阶段
     */
    public String getAcceptanceStage() {
        return acceptanceStage;
    }

    /**
     * 设置验收阶段
     *
     * @param acceptanceStage 验收阶段
     */
    public void setAcceptanceStage(String acceptanceStage) {
        this.acceptanceStage = acceptanceStage;
    }

    /**
     * 获取0否、1是
     *
     * @return is_open - 0否、1是
     */
    public Integer getIsOpen() {
        return isOpen;
    }

    /**
     * 设置0否、1是
     *
     * @param isOpen 0否、1是
     */
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 获取供应商意见
     *
     * @return supplier_opinion - 供应商意见
     */
    public String getSupplierOpinion() {
        return supplierOpinion;
    }

    /**
     * 设置供应商意见
     *
     * @param supplierOpinion 供应商意见
     */
    public void setSupplierOpinion(String supplierOpinion) {
        this.supplierOpinion = supplierOpinion;
    }

    /**
     * 获取采购人意见
     *
     * @return unit_opinion - 采购人意见
     */
    public String getUnitOpinion() {
        return unitOpinion;
    }

    /**
     * 设置采购人意见
     *
     * @param unitOpinion 采购人意见
     */
    public void setUnitOpinion(String unitOpinion) {
        this.unitOpinion = unitOpinion;
    }

    /**
     * 获取验收意见
     *
     * @return acceptance_opinion - 验收意见
     */
    public String getAcceptanceOpinion() {
        return acceptanceOpinion;
    }

    /**
     * 设置验收意见
     *
     * @param acceptanceOpinion 验收意见
     */
    public void setAcceptanceOpinion(String acceptanceOpinion) {
        this.acceptanceOpinion = acceptanceOpinion;
    }

    /**
     * 获取验收小组名单
     *
     * @return acceptance_team - 验收小组名单
     */
    public String getAcceptanceTeam() {
        return acceptanceTeam;
    }

    /**
     * 设置验收小组名单
     *
     * @param acceptanceTeam 验收小组名单
     */
    public void setAcceptanceTeam(String acceptanceTeam) {
        this.acceptanceTeam = acceptanceTeam;
    }

    /**
     * 获取附件地址
     *
     * @return url - 附件地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置附件地址
     *
     * @param url 附件地址
     */
    public void setUrl(String url) {
        this.url = url;
    }
}