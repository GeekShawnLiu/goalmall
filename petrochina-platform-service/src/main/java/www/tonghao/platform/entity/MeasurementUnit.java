package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "measurement_unit")
public class MeasurementUnit extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String name;

    /**
     * 状态 0:启用 1：停用
     */
    @ApiModelProperty(value="状态 0:启用 1：停用")
    private Integer status;
    
    /**
     * 关联品目id数组
     */
    @Transient
    @ApiModelProperty(value="关联品目id数组")
    private Integer[] catalogsIdArry;
    
    /**
     * 关联品目名称
     */
    @Transient
    private String catalogsNames;

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
     * 获取单位名称
     *
     * @return name - 单位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置单位名称
     *
     * @param name 单位名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状态 0:启用 1：停用
     *
     * @return status - 状态 0:启用 1：停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0:启用 1：停用
     *
     * @param status 状态 0:启用 1：停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer[] getCatalogsIdArry() {
		return catalogsIdArry;
	}

	public void setCatalogsIdArry(Integer[] catalogsIdArry) {
		this.catalogsIdArry = catalogsIdArry;
	}

	public String getCatalogsNames() {
		return catalogsNames;
	}

	public void setCatalogsNames(String catalogsNames) {
		this.catalogsNames = catalogsNames;
	}

}