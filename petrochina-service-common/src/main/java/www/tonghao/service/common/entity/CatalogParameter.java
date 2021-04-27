package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "catalog_parameter")
public class CatalogParameter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 品目id
     */
    @Column(name = "catalog_id")
    private Long catalogId;

    /**
     * 参数id
     */
    @Column(name = "parameter_id")
    private Long parameterId;

    /**
     * 参数名称s
     */
    @Column(name = "parameter_name")
    private String parameterName;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}
