package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="供应商规模")
@Table(name = "supplier_scale")
public class SupplierScale extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 供应商规模名称
     */
    @ApiModelProperty("供应商规模名称")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Float priority;

    /**
     * 状态 1:启用 2：停用
     */
    @ApiModelProperty("状态 1:启用 2：停用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 获取供应商规模名称
     *
     * @return name - 供应商规模名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置供应商规模名称
     *
     * @param name 供应商规模名称
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * 获取状态 1:启用 2：停用
     *
     * @return status - 状态 1:启用 2：停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1:启用 2：停用
     *
     * @param status 状态 1:启用 2：停用
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取修改时间
     *
     * @return updated_at - 修改时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置修改时间
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}