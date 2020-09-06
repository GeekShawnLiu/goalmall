package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:积分发放审核表
 * @date 2019年5月2日
 */
@Table(name = "integral_audit")
public class IntegralAudit extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
     * 积分发放id
     */
    @Column(name = "integral_grant_id")
    private Long integralGrantId;

    /**
     * 审核人id
     */
    @Column(name = "auditor_id")
    private Long auditorId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;


    /**
     * 获取积分发放id
     *
     * @return integral_grant_id - 积分发放id
     */
    public Long getIntegralGrantId() {
        return integralGrantId;
    }

    /**
     * 设置积分发放id
     *
     * @param integralGrantId 积分发放id
     */
    public void setIntegralGrantId(Long integralGrantId) {
        this.integralGrantId = integralGrantId;
    }

    /**
     * 获取审核人id
     *
     * @return auditor_id - 审核人id
     */
    public Long getAuditorId() {
        return auditorId;
    }

    /**
     * 设置审核人id
     *
     * @param auditorId 审核人id
     */
    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}