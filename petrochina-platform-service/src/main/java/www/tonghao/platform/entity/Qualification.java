package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="资质管理")
public class Qualification extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 资质名称
     */
    @ApiModelProperty(value="资质名称")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 0 启用  1禁用
     */
    @ApiModelProperty(value="0 启用  1禁用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;
    
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;
    
    
    @ApiModelProperty(value="产品id")
    @Column(name = "catalogs_id")
    private Integer catalogsId;

    /**
     * 获取资质名称
     *
     * @return name - 资质名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资质名称
     *
     * @param name 资质名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取0 启用  1禁用
     *
     * @return status - 0 启用  1禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 启用  1禁用
     *
     * @param status 0 启用  1禁用
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

	public Float getPriority() {
		return priority;
	}

	public void setPriority(Float priority) {
		this.priority = priority;
	}

	public Integer getCatalogsId() {
		return catalogsId;
	}

	public void setCatalogsId(Integer catalogsId) {
		this.catalogsId = catalogsId;
	}
    
}