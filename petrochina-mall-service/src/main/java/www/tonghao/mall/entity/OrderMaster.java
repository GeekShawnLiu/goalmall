package www.tonghao.mall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "order_master")
public class OrderMaster extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 主订单号
     */
    private String sn;

    /**
     * 订单总金额(含快递等费用)
     */
    private BigDecimal total;

    /**
     * 总邮费
     */
    private BigDecimal freight;

    /**
     * 采购单位id
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 采购单位名称
     */
    @Column(name = "org_name")
    private Long orgName;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取主订单号
     *
     * @return sn - 主订单号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置主订单号
     *
     * @param sn 主订单号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取订单总金额(含快递等费用)
     *
     * @return total - 订单总金额(含快递等费用)
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * 设置订单总金额(含快递等费用)
     *
     * @param total 订单总金额(含快递等费用)
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * 获取总邮费
     *
     * @return freight - 总邮费
     */
    public BigDecimal getFreight() {
        return freight;
    }

    /**
     * 设置总邮费
     *
     * @param freight 总邮费
     */
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    /**
     * 获取采购单位id
     *
     * @return org_id - 采购单位id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置采购单位id
     *
     * @param orgId 采购单位id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取采购单位名称
     *
     * @return org_name - 采购单位名称
     */
    public Long getOrgName() {
        return orgName;
    }

    /**
     * 设置采购单位名称
     *
     * @param orgName 采购单位名称
     */
    public void setOrgName(Long orgName) {
        this.orgName = orgName;
    }
}