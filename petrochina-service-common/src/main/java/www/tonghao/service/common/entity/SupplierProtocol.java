package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "supplier_protocol")
@ApiModel(value="供应商协议关联")
public class SupplierProtocol extends BaseEntity{
   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 供应商id
     */
    @Column(name = "supplier_id")
    @ApiModelProperty(value="供应商id")
    private Long supplierId;

    /**
     * 协议id
     */
    @Column(name = "protocol_id")
    @ApiModelProperty(value="协议id")
    private Long protocolId;

    /**
     * 折扣率
     */
    @ApiModelProperty(value="折扣率")
    private Float rate;

    /**
     * contact
     */
    @ApiModelProperty(value="折扣率")
    private String contact;
    
    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;
    
    /**
     * 是否分配代理商 1分配 2未分配
     */
    @ApiModelProperty(value="是否分配代理商 1分配 2未分配")
    @Column(name = "is_assign")
    private Integer isAssign;

    
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 2暂停销售，1正常
     */
    @ApiModelProperty(value="2暂停销售，1正常")
    private Integer status;


    @Transient
    @ApiModelProperty(value="供应商关联品目")
    private List<SupplierProtocolCatalog> protocolCatalogs;
    
    @Transient
    @ApiModelProperty(value="供应商")
    private Suppliers suppliers;
    
    @Transient
    @ApiModelProperty(value="关联的品目")
    private String catalogsIds;
    
    @Transient
    @ApiModelProperty(value="关联品牌ids")
    private String brandIds;
    
    @Transient
    @ApiModelProperty(value="关联品牌名称")
    private String brandNmas;
    
    /**
     * 获取供应商id
     *
     * @return supplier_id - 供应商id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商id
     *
     * @param supplierId 供应商id
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取协议id
     *
     * @return protocol_id - 协议id
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * 设置协议id
     *
     * @param protocolId 协议id
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    /**
     * 获取折扣率
     *
     * @return rate - 折扣率
     */
    public Float getRate() {
        return rate;
    }

    /**
     * 设置折扣率
     *
     * @param rate 折扣率
     */
    public void setRate(Float rate) {
        this.rate = rate;
    }

    /**
     * 获取联系人
     *
     * @return contact - 联系人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置联系人
     *
     * @param contact 联系人
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 获取联系电话
     *
     * @return contact_phone - 联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置联系电话
     *
     * @param contactPhone 联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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


    public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
     * 获取2暂停销售，1正常
     *
     * @return status - 2暂停销售，1正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置2暂停销售，1正常
     *
     * @param status 2暂停销售，1正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<SupplierProtocolCatalog> getProtocolCatalogs() {
		return protocolCatalogs;
	}

	public void setProtocolCatalogs(List<SupplierProtocolCatalog> protocolCatalogs) {
		this.protocolCatalogs = protocolCatalogs;
	}

	public Suppliers getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Suppliers suppliers) {
		this.suppliers = suppliers;
	}

	public Integer getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(Integer isAssign) {
		this.isAssign = isAssign;
	}

	public String getCatalogsIds() {
		return catalogsIds;
	}

	public void setCatalogsIds(String catalogsIds) {
		this.catalogsIds = catalogsIds;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

	public String getBrandNmas() {
		return brandNmas;
	}

	public void setBrandNmas(String brandNmas) {
		this.brandNmas = brandNmas;
	}
    
    
}