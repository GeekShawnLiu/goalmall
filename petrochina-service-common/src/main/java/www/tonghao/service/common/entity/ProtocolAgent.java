package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "protocol_agent")
@ApiModel("代理商协议")
public class ProtocolAgent extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 代理商名称
     */
	@ApiModelProperty(value="代理商名称")
    @Column(name = "agent_name")
    private String agentName;

    /**
     * 代理品目
     */
	@ApiModelProperty(value="代理品目")
    @Column(name = "catalogsId")
    private Long catalogsid;
	
    /**
     * 地区
     */
	@ApiModelProperty(value="地区Id")
	@Transient
    private Long areaid;
	
	/**
     * 地区id拼接
     */
	@ApiModelProperty(value="地区id拼接")
	@Transient
    private String areaIds;
	
	/**
     * 地区name拼接
     */
	@ApiModelProperty(value="地区name拼接")
	@Transient
    private String areaNames;

    /**
     * 联系人
     */
	@ApiModelProperty(value="联系人")
    @Column(name = "user_name")
    private String userName;

    /**
     * 联系人电话
     */
	@ApiModelProperty(value="联系人电话")
    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 0正常，1删除
     */
    @ApiModelProperty(value="0正常，1删除")
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 协议id
     */
    @ApiModelProperty(value="协议id")
    @Column(name = "protocol_id")
    private Long protocolId;

    /**
     * 供应商协议id
     */
    @ApiModelProperty(value="供应商协议id")
    @Column(name = "supplier_protocol_id")
    private Long supplierProtocolId;

    
    @Transient
    @ApiModelProperty(value="代理产品目录名称")
    private String  catalogsName;
    
    @Transient
    @ApiModelProperty(value="代理产品目录ids")
    private String  catalogsIds;
    
    @Column(name = "supplier_agent_id")
    private Long  supplierAgentId;
    
    /**
     * 协议
     */
    @Transient
    private Protocol protocol;
    
    /**
     * 代理商的supplier
     */
    @Transient
    private Suppliers agentSupplier;

    /**
     * 获取代理商名称
     *
     * @return agent_name - 代理商名称
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 设置代理商名称
     *
     * @param agentName 代理商名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * 获取代理品目
     *
     * @return catalogsId - 代理品目
     */
    public Long getCatalogsid() {
        return catalogsid;
    }

    /**
     * 设置代理品目
     *
     * @param catalogsid 代理品目
     */
    public void setCatalogsid(Long catalogsid) {
        this.catalogsid = catalogsid;
    }

    /**
     * 获取地区
     *
     * @return areaId - 地区
     */
    public Long getAreaid() {
        return areaid;
    }

    /**
     * 设置地区
     *
     * @param areaid 地区
     */
    public void setAreaid(Long areaid) {
        this.areaid = areaid;
    }

    /**
     * 获取联系人
     *
     * @return user_name - 联系人
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置联系人
     *
     * @param userName 联系人
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取联系人电话
     *
     * @return user_phone - 联系人电话
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置联系人电话
     *
     * @param userPhone 联系人电话
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    /**
     * 获取0正常，1删除
     *
     * @return is_delete - 0正常，1删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置0正常，1删除
     *
     * @param isDelete 0正常，1删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
     * 获取供应商协议id
     *
     * @return supplier_protocol_id - 供应商协议id
     */
    public Long getSupplierProtocolId() {
        return supplierProtocolId;
    }

    /**
     * 设置供应商协议id
     *
     * @param supplierProtocolId 供应商协议id
     */
    public void setSupplierProtocolId(Long supplierProtocolId) {
        this.supplierProtocolId = supplierProtocolId;
    }

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(String areaNames) {
		this.areaNames = areaNames;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}

	public String getCatalogsIds() {
		return catalogsIds;
	}

	public void setCatalogsIds(String catalogsIds) {
		this.catalogsIds = catalogsIds;
	}

	public Long getSupplierAgentId() {
		return supplierAgentId;
	}

	public void setSupplierAgentId(Long supplierAgentId) {
		this.supplierAgentId = supplierAgentId;
	}

	public Suppliers getAgentSupplier() {
		return agentSupplier;
	}

	public void setAgentSupplier(Suppliers agentSupplier) {
		this.agentSupplier = agentSupplier;
	}
    
}