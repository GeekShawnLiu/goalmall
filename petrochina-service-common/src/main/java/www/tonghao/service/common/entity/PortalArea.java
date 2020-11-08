package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;

public class PortalArea extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 专区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 品目id
     */
    @Column(name = "catalog_id")
    private Long catalogId;

    /**
     * 品目名称
     */
    @Column(name = "catalog_name")
    private String catalogName;

    /**
     * 主图路径
     */
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 专区排序
     */
    @Column(name = "area_sort")
    private Integer areaSort;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(Integer areaSort) {
        this.areaSort = areaSort;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
