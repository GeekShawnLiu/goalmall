package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "unit_catalogs")
public class UnitCatalogs extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 计量单位id
     */
    @ApiModelProperty(value="计量单位id")
    @Column(name = "unit_id")
    private Long unitId;

    /**
     * 品目id
     */
    @ApiModelProperty(value="品目id")
    @Column(name = "catalogs_id")
    private Long catalogsId;

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
     * 获取计量单位id
     *
     * @return unit_id - 计量单位id
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * 设置计量单位id
     *
     * @param unitId 计量单位id
     */
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * 获取品目id
     *
     * @return catalogs_id - 品目id
     */
    public Long getCatalogsId() {
        return catalogsId;
    }

    /**
     * 设置品目id
     *
     * @param catalogsId 品目id
     */
    public void setCatalogsId(Long catalogsId) {
        this.catalogsId = catalogsId;
    }
}