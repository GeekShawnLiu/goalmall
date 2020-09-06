package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "invoice_type")
public class InvoiceType extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 简称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 全称
     */
    @ApiModelProperty(value="全称")
    @Column(name = "full_name")
    private String fullName;

    /**
     * 默认0：正常，1:冻结
     */
    @ApiModelProperty(value="0：正常，1:冻结")
    @Column(name = "is_frozen")
    private Integer isFrozen;

    /**
     * 排序
     */
    private Float priority;

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
     * 获取简称
     *
     * @return short_name - 简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置简称
     *
     * @param shortName 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取全称
     *
     * @return full_name - 全称
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置全称
     *
     * @param fullName 全称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取默认0：正常，1:冻结
     *
     * @return is_frozen - 默认0：正常，1:冻结
     */
    public Integer getIsFrozen() {
        return isFrozen;
    }

    /**
     * 设置默认0：正常，1:冻结
     *
     * @param isFrozen 默认0：正常，1:冻结
     */
    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * 获取排序
     *
     * @return priority - 排序
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * 设置排序
     *
     * @param priority 排序
     */
    public void setPriority(Float priority) {
        this.priority = priority;
    }
}