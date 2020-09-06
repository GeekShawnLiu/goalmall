package www.tonghao.platform.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "integrity_configuration")
public class IntegrityConfiguration extends BaseEntity {

    /**
     * 信用因素
     */
    private String title;

    @Column(name = "minus_points")
    private BigDecimal minusPoints;

    /**
     * 0 不限制，1限制
     */
    @Column(name = "is_stint")
    private Integer isStint;

    /**
     * 0 启用，1不启用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0 未删除，1已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;


    /**
     * 获取信用因素
     *
     * @return title - 信用因素
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置信用因素
     *
     * @param title 信用因素
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return minus_points
     */
    public BigDecimal getMinusPoints() {
        return minusPoints;
    }

    /**
     * @param minusPoints
     */
    public void setMinusPoints(BigDecimal minusPoints) {
        this.minusPoints = minusPoints;
    }

    /**
     * 获取0 不限制，1限制
     *
     * @return is_stint - 0 不限制，1限制
     */
    public Integer getIsStint() {
        return isStint;
    }

    /**
     * 设置0 不限制，1限制
     *
     * @param isStint 0 不限制，1限制
     */
    public void setIsStint(Integer isStint) {
        this.isStint = isStint;
    }

    /**
     * 获取0 启用，1不启用
     *
     * @return status - 0 启用，1不启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 启用，1不启用
     *
     * @param status 0 启用，1不启用
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取0 未删除，1已删除
     *
     * @return is_delete - 0 未删除，1已删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置0 未删除，1已删除
     *
     * @param isDelete 0 未删除，1已删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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