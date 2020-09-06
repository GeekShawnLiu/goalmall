package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "supplier_protocol_catalog")
@ApiModel(value="供应商协议代理品目")
public class SupplierProtocolCatalog extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 末级品目id
     */
    @Column(name = "catalogs_id")
    private Long catalogsId;

    /**
     * 供应商协议id 或者代理商协议
     */
    @Column(name = "supper_anent_id")
    private Long supperAnentId;
    
    
    /**
     *1供应商中标品目，2代理商代理品目
     */
    @Column(name = "type")
    private Integer type;
    
    /**
     *品目名称
     */
    @Column(name = "catalogs_name")
    private String catalogsName;
    
    /**
     *供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;
    
    /**
     *协议id
     */
    @Column(name = "protocol_id")
    private Long protocolId;
    

    /**
     * 获取末级品目id
     *
     * @return catalogs_id - 末级品目id
     */
    public Long getCatalogsId() {
        return catalogsId;
    }

    /**
     * 设置末级品目id
     *
     * @param catalogsId 末级品目id
     */
    public void setCatalogsId(Long catalogsId) {
        this.catalogsId = catalogsId;
    }

    /**
     * 获取供应商协议id
     *
     * @return supper_protocol_id - 供应商协议id
     */
    public Long getSupperAnentId() {
        return supperAnentId;
    }

    /**
     * 设置供应商协议id
     *
     * @param supperProtocolId 供应商协议id
     */
    public void setSupperAnentId(Long supperAnentId) {
        this.supperAnentId = supperAnentId;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Long protocolId) {
		this.protocolId = protocolId;
	}
    
    
}