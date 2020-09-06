package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "deal_contract_person")
public class DealContractPerson extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    @Column(name = "identity_card")
    private String identityCard;

    /**
     * 合同明细id
     */
    @Column(name = "contract_detail_id")
    private Long contractDetailId;


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
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取身份证号
     *
     * @return identity_card - 身份证号
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * 设置身份证号
     *
     * @param identityCard 身份证号
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /**
     * 获取合同明细id
     *
     * @return contract_detail_id - 合同明细id
     */
    public Long getContractDetailId() {
        return contractDetailId;
    }

    /**
     * 设置合同明细id
     *
     * @param contractDetailId 合同明细id
     */
    public void setContractDetailId(Long contractDetailId) {
        this.contractDetailId = contractDetailId;
    }
}