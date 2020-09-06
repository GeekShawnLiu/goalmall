package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "mapping_area")
public class MappingArea extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "area_id")
    private Long areaId;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "mapping_code")
    private String mappingCode;

    @Column(name = "emall_code")
    private String emallCode;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    @Transient
    private Areas areas;
    
    @Transient
    private String treeNames;
    
    @Transient
    private String supplierName;



    /**
     * @return area_id
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * @return area_name
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return mapping_code
     */
    public String getMappingCode() {
        return mappingCode;
    }

    /**
     * @param mappingCode
     */
    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    /**
     * @return emall_code
     */
    public String getEmallCode() {
        return emallCode;
    }

    /**
     * @param emallCode
     */
    public void setEmallCode(String emallCode) {
        this.emallCode = emallCode;
    }

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

	public Areas getAreas() {
		return areas;
	}

	public void setAreas(Areas areas) {
		this.areas = areas;
	}

	public String getTreeNames() {
		return treeNames;
	}

	public void setTreeNames(String treeNames) {
		this.treeNames = treeNames;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
    
    
}