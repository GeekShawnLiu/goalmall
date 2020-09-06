package www.tonghao.platform.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "catalogs_parts")
public class CatalogsParts extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "update_at")
    private String updateAt;

    /**
     * 配件名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0，启用 1暂停
     */
    private Integer status;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "catalogs_id")
    private Long catalogsId;


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

    /**
     * 获取配件名称
     *
     * @return name - 配件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配件名称
     *
     * @param name 配件名称
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
     * 获取0，启用 1暂停
     *
     * @return status - 0，启用 1暂停
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0，启用 1暂停
     *
     * @param status 0，启用 1暂停
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return catalogs_id
     */
    public Long getCatalogsId() {
        return catalogsId;
    }

    /**
     * @param catalogsId
     */
    public void setCatalogsId(Long catalogsId) {
        this.catalogsId = catalogsId;
    }
}