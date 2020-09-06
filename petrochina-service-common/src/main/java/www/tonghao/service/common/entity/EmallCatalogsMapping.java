package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Suppliers;


@Table(name = "emall_catalogs")
public class EmallCatalogsMapping extends BaseEntity {

    /** 
	 * 
	 */  
	private static final long serialVersionUID = 1L;

	@Column(name = "emall_id")
    private Long emallId;

    /**
     * 电商code
     */
    @Column(name = "emall_code")
    private String emallCode;

    /**
     * 供应商名称
     */
    @Column(name = "emall_name")
    private String emallName;

    /**
     * 供应商品目id
     */
    @Column(name = "emall_catalogs_id")
    private String emallCatalogsId;

    /**
     * 供应商品目名称
     */
    @Column(name = "emall_catalogs_name")
    private String emallCatalogsName;

    /**
     * 基础品目id
     */
    @Column(name = "catalogs_id")
    private Long catalogsId;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    @Transient
    private Suppliers supplier;
    
    @Transient
    private PlatformCatalogs platformCatalogs;


    /**
     * @return emall_id
     */
    public Long getEmallId() {
        return emallId;
    }

    /**
     * @param emallId
     */
    public void setEmallId(Long emallId) {
        this.emallId = emallId;
    }

    /**
     * 获取电商code
     *
     * @return emall_code - 电商code
     */
    public String getEmallCode() {
        return emallCode;
    }

    /**
     * 设置电商code
     *
     * @param emallCode 电商code
     */
    public void setEmallCode(String emallCode) {
        this.emallCode = emallCode;
    }

    /**
     * 获取供应商名称
     *
     * @return emall_name - 供应商名称
     */
    public String getEmallName() {
        return emallName;
    }

    /**
     * 设置供应商名称
     *
     * @param emallName 供应商名称
     */
    public void setEmallName(String emallName) {
        this.emallName = emallName;
    }

    /**
     * 获取供应商品目id
     *
     * @return emall_catalogs_id - 供应商品目id
     */
    public String getEmallCatalogsId() {
        return emallCatalogsId;
    }

    /**
     * 设置供应商品目id
     *
     * @param emallCatalogsId 供应商品目id
     */
    public void setEmallCatalogsId(String emallCatalogsId) {
        this.emallCatalogsId = emallCatalogsId;
    }

    /**
     * 获取供应商品目名称
     *
     * @return emall_catalogs_name - 供应商品目名称
     */
    public String getEmallCatalogsName() {
        return emallCatalogsName;
    }

    /**
     * 设置供应商品目名称
     *
     * @param emallCatalogsName 供应商品目名称
     */
    public void setEmallCatalogsName(String emallCatalogsName) {
        this.emallCatalogsName = emallCatalogsName;
    }

    /**
     * 获取基础品目id
     *
     * @return catalogs_id - 基础品目id
     */
    public Long getCatalogsId() {
        return catalogsId;
    }

    /**
     * 设置基础品目id
     *
     * @param catalogsId 基础品目id
     */
    public void setCatalogsId(Long catalogsId) {
        this.catalogsId = catalogsId;
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

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public PlatformCatalogs getPlatformCatalogs() {
		return platformCatalogs;
	}

	public void setPlatformCatalogs(PlatformCatalogs platformCatalogs) {
		this.platformCatalogs = platformCatalogs;
	}
    
}