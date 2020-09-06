package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel("企业性质")
@Table(name = "enterprise_nature")
public class EnterpriseNature extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 企业名称
     */
    @ApiModelProperty(value="企业名称")
    private String name;

    /**
     * 排名
     */
    @ApiModelProperty(value="排序")
    private Float priority;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 状态 1:启用 2：停用
     */
    @ApiModelProperty(value="状态 1:启用 2：停用")
    private Integer status;

    /**
     * 获取企业名称
     *
     * @return name - 企业名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置企业名称
     *
     * @param name 企业名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return priority
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * @param priority
     */
    public void setPriority(Float priority) {
        this.priority = priority;
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

    /**
     * 获取添加时间
     *
     * @return created_at - 添加时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置添加时间
     *
     * @param createdAt 添加时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
}