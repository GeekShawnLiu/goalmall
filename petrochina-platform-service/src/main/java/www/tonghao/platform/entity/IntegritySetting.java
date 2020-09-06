package www.tonghao.platform.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "integrity_setting")
public class IntegritySetting extends BaseEntity {

    /**
     * 处罚分数线
     */
    @Column(name = "penalty_score")
    private BigDecimal penaltyScore;

    /**
     * 处罚周期
     */
    private Integer cycle;

    /**
     * 协议期内，累计限制暂停次数
     */
    private Integer requirement;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

   

    /**
     * 获取处罚分数线
     *
     * @return penalty_score - 处罚分数线
     */
    public BigDecimal getPenaltyScore() {
        return penaltyScore;
    }

    /**
     * 设置处罚分数线
     *
     * @param penaltyScore 处罚分数线
     */
    public void setPenaltyScore(BigDecimal penaltyScore) {
        this.penaltyScore = penaltyScore;
    }

    /**
     * 获取处罚周期
     *
     * @return cycle - 处罚周期
     */
    public Integer getCycle() {
        return cycle;
    }

    /**
     * 设置处罚周期
     *
     * @param cycle 处罚周期
     */
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    /**
     * 获取协议期内，累计限制暂停次数
     *
     * @return requirement - 协议期内，累计限制暂停次数
     */
    public Integer getRequirement() {
        return requirement;
    }

    /**
     * 设置协议期内，累计限制暂停次数
     *
     * @param requirement 协议期内，累计限制暂停次数
     */
    public void setRequirement(Integer requirement) {
        this.requirement = requirement;
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
}