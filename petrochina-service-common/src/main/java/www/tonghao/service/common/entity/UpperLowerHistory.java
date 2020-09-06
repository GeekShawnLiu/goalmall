package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "upper_lower_history")
public class UpperLowerHistory extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 理由
     */
    private String reason;

    @Column(name = "product_id")
    private Long productId;

    /**
     * 操作人id
     */
    @Column(name = "operate_id")
    private Long operateId;

    /**
     * 类型 1：申请上架,2：退回,3：上架,4：下架
     */
    @ApiModelProperty(value="类型 1：申请上架,2：退回,3：上架,4：下架")
    private Integer type;

    /**
     * 审核状态 1：审核通过  2：审核不通过 3:价格监测上架  4：价格监测下架  5：协议价不高于官网价的98% 6：特供专供商品
     */
    @ApiModelProperty(value="审核状态 1：审核通过  2：审核不通过  3:价格监测价格合理上架  4：价格监测高于市场合理价下架 5：协议价不高于官网价的98% 6：特供专供商品")
    private Integer status;

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
     * 获取理由
     *
     * @return reason - 理由
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置理由
     *
     * @param reason 理由
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return product_id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取操作人id
     *
     * @return operate_id - 操作人id
     */
    public Long getOperateId() {
        return operateId;
    }

    /**
     * 设置操作人id
     *
     * @param operateId 操作人id
     */
    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    /**
     * 类型 1：申请上架,2：退回,3：上架,4：下架
     *
     * @return type - 类型 1：申请上架,2：退回,3：上架,4：下架
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 1：申请上架,2：退回,3：上架,4：下架
     *
     * @param type 类型 1：申请上架,2：退回,3：上架,4：下架
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取审核状态 1：审核通过  2：审核不通过
     *
     * @return status - 审核状态 1：审核通过  2：审核不通过
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置审核状态 1：审核通过  2：审核不通过
     *
     * @param status 审核状态 1：审核通过  2：审核不通过
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}