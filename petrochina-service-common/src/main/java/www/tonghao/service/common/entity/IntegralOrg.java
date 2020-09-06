package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:积分关联的机构
 * @date 2019年5月1日
 */
@Table(name = "integral_org")
public class IntegralOrg extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/**
     * 积分id
     */
    @Column(name = "integral_id")
    private Long integralId;

    /**
     * 机构id
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private String updatedAt;
    
    @Transient
    private String orgName;

    /**
     * 获取积分id
     *
     * @return integral_id - 积分id
     */
    public Long getIntegralId() {
        return integralId;
    }

    /**
     * 设置积分id
     *
     * @param integralId 积分id
     */
    public void setIntegralId(Long integralId) {
        this.integralId = integralId;
    }

    /**
     * 获取机构id
     *
     * @return org_id - 机构id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置机构id
     *
     * @param orgId 机构id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    
    
    
}