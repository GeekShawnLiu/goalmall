package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**  

* <p>Title: PlatformCatalogs</p>  

* <p>Description: 平台(集采)品目</p>  

* @author Yml  

* @date 2018年11月14日  

*/  
@Table(name = "platform_catalogs")
public class PlatformCatalogs extends BaseEntity{
	
    /**
	 * 
	 */
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
     * 目录名称
     */
    private String name;

    /**
     * 父级ID
     */
    @ApiModelProperty(value="父级ID")
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 层级数
     */
    @Column(name = "tree_depth")
    private Integer treeDepth;

    /**
     * 详情
     */
    private String descs;

    /**
     * 是否可用
     */
    @ApiModelProperty(value="是否可用 0:可用 1：不可用")
    private Integer usable;

    /**
     * 树级ID
     */
    @Column(name = "tree_ids")
    private String treeIds;

    /**
     * 树级名称
     */
    @Column(name = "tree_names")
    private String treeNames;

    /**
     * 图片
     */
    private String pic;

    /**
     * 优先级排序
     */
    private Float priority;

    /**
     * 是否为父节点
     */
    @Column(name = "is_parent")
    private String isParent;

    /**
     * 品目编号
     */
    private String code;

    /**
     * 品目标识  集采collection  非集采noCollection 单品牌singleBrand  多品牌multiBrands 
     */
    @ApiModelProperty(value="集采collection  非集采noCollection 单品牌singleBrand  多品牌multiBrands")
    private String types;
    
    /**
     * 通用资源配置
     */
    @ApiModelProperty(value="通用资源配置")
    @Column(name = "normal_price")
    private BigDecimal normalPrice;
    
    /**
     * 商城品目ID
     */
    @Column(name = "mall_catalog_id")
    private Long mallCatalogId;
    
    @Transient
    private PlatformCatalogs parent; //父级品目
    
    @Transient
    private Catalogs catalog;//关联财政品目
    
    @Transient
    private List<PlatformCatalogs> children;//子品目
    
    @Transient
    private boolean checked;

    /**
     * 映射财政品目id
     */
    @Column(name = "catalog_id")
    private Long catalogId;
    
    /**
     * 交易形式
     */
    @Column(name = "transaction_form")
    private String transactionForm;
    
    /**
     * 适用系统   超市直购mall-direct, 超市竞价mall-bid, 批量集采central, 定点采购fix
     */
    @ApiModelProperty(value="超市直购mall-direct, 超市竞价mall-bid, 批量集采central, 定点采购fix")
    private String systems;
    
    /**
     * 是否删除 0:否 1：是
     */
    @Column(name = "is_delete")
    private Integer isDelete;
    
    /** 
     * 商品审核类型  0：自动审核  1：人工审核
     */  
    @ApiModelProperty(value="商品审核类型  0：自动审核  1：人工审核")
    @Column(name = "product_flag")
    private Integer productFlag;
    
    /** 
     * 是否价格监测
     */  
    @ApiModelProperty(value="是否价格监测")
    @Column(name = "is_price_monitor")
    private Boolean isPriceMonitor;
    
    /** 
     * 是否监测上下架
     */  
    @ApiModelProperty(value="是否监测上下架")
    @Column(name = "is_marketable_monitor")
    private Boolean isMarketableMonitor;
    
    /** 
     * 是否重点监测品目
     */  
    @ApiModelProperty(value="是否重点监测品目")
    @Column(name = "is_key_monitor")
    private Boolean isKeyMonitor;
    
    /** 
     * 是否使用统一参数
     */  
    @ApiModelProperty(value="是否使用统一参数")
    @Column(name = "is_unified_parameter")
    private Boolean isUnifiedParameter;
    
    /** 
     * 是否统一参数上下架
     */  
    @ApiModelProperty(value="是否统一参数上下架")
    @Column(name = "is_marketable_unified_parameter")
    private Boolean isMarketableUnifiedParameter;
    
    /** 
     * 价格区间
     */  
    @ApiModelProperty(value="品目价格区间")
    @Column(name = "price_range")
    private String priceRange;

    /**
     * 品目参数
     */
    @Transient
    private List<CatalogParameter> parametersList;

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取修改时间
     *
     * @return updated_at - 修改时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置修改时间
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取目录名称
     *
     * @return name - 目录名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置目录名称
     *
     * @param name 目录名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父级ID
     *
     * @return parent_id - 父级ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级ID
     *
     * @param parentId 父级ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取层级数
     *
     * @return tree_depth - 层级数
     */
    public Integer getTreeDepth() {
        return treeDepth;
    }

    /**
     * 设置层级数
     *
     * @param treeDepth 层级数
     */
    public void setTreeDepth(Integer treeDepth) {
        this.treeDepth = treeDepth;
    }

    /**
     * 获取详情
     *
     * @return descs - 详情
     */
    public String getDescs() {
        return descs;
    }

    /**
     * 设置详情
     *
     * @param descs 详情
     */
    public void setDescs(String descs) {
        this.descs = descs;
    }

    /**
     * 获取是否可用
     *
     * @return usable - 是否可用
     */
    public Integer getUsable() {
        return usable;
    }

    /**
     * 设置是否可用
     *
     * @param usable 是否可用
     */
    public void setUsable(Integer usable) {
        this.usable = usable;
    }

    /**
     * 获取树级ID
     *
     * @return tree_ids - 树级ID
     */
    public String getTreeIds() {
        return treeIds;
    }

    /**
     * 设置树级ID
     *
     * @param treeIds 树级ID
     */
    public void setTreeIds(String treeIds) {
        this.treeIds = treeIds;
    }

    /**
     * 获取树级名称
     *
     * @return tree_names - 树级名称
     */
    public String getTreeNames() {
        return treeNames;
    }

    /**
     * 设置树级名称
     *
     * @param treeNames 树级名称
     */
    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
    }

    /**
     * 获取图片
     *
     * @return pic - 图片
     */
    public String getPic() {
        return pic;
    }

    /**
     * 设置图片
     *
     * @param pic 图片
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 获取优先级排序
     *
     * @return priority - 优先级排序
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * 设置优先级排序
     *
     * @param priority 优先级排序
     */
    public void setPriority(Float priority) {
        this.priority = priority;
    }

    /**
     * 获取是否为父节点
     *
     * @return is_parent - 是否为父节点
     */
    public String getIsParent() {
        return isParent;
    }

    /**
     * 设置是否为父节点
     *
     * @param isParent 是否为父节点
     */
    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    /**
     * 获取品目编号
     *
     * @return code - 品目编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置品目编号
     *
     * @param code 品目编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取品目标识
     *
     * @return types - 品目标识  
     */
    public String getTypes() {
        return types;
    }

    /**
     * 设置品目标识
     *
     * @param types 品目标识
     */
    public void setTypes(String types) {
        this.types = types;
    }

    /**
     * 获取映射基础品目id
     *
     * @return catalog_id - 映射基础品目id
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * 设置映射基础品目id
     *
     * @param catalogId 映射基础品目id
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

	public String getTransactionForm() {
		return transactionForm;
	}

	public void setTransactionForm(String transactionForm) {
		this.transactionForm = transactionForm;
	}

	public String getSystems() {
		return systems;
	}

	public void setSystems(String systems) {
		this.systems = systems;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public PlatformCatalogs getParent() {
		return parent;
	}

	public void setParent(PlatformCatalogs parent) {
		this.parent = parent;
	}

	public Catalogs getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalogs catalog) {
		this.catalog = catalog;
	}

	public List<PlatformCatalogs> getChildren() {
		return children;
	}

	public void setChildren(List<PlatformCatalogs> children) {
		this.children = children;
	}
	
	public BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}
	
	public Long getMallCatalogId() {
		return mallCatalogId;
	}

	public void setMallCatalogId(Long mallCatalogId) {
		this.mallCatalogId = mallCatalogId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((catalogId == null) ? 0 : catalogId.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((descs == null) ? 0 : descs.hashCode());
		result = prime * result
				+ ((isDelete == null) ? 0 : isDelete.hashCode());
		result = prime * result
				+ ((isParent == null) ? 0 : isParent.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((systems == null) ? 0 : systems.hashCode());
		result = prime * result
				+ ((transactionForm == null) ? 0 : transactionForm.hashCode());
		result = prime * result
				+ ((treeDepth == null) ? 0 : treeDepth.hashCode());
		result = prime * result + ((treeIds == null) ? 0 : treeIds.hashCode());
		result = prime * result
				+ ((treeNames == null) ? 0 : treeNames.hashCode());
		result = prime * result + ((types == null) ? 0 : types.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((usable == null) ? 0 : usable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlatformCatalogs other = (PlatformCatalogs) obj;
		if (catalogId == null) {
			if (other.catalogId != null)
				return false;
		} else if (!catalogId.equals(other.catalogId))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (descs == null) {
			if (other.descs != null)
				return false;
		} else if (!descs.equals(other.descs))
			return false;
		if (isDelete == null) {
			if (other.isDelete != null)
				return false;
		} else if (!isDelete.equals(other.isDelete))
			return false;
		if (isParent == null) {
			if (other.isParent != null)
				return false;
		} else if (!isParent.equals(other.isParent))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (systems == null) {
			if (other.systems != null)
				return false;
		} else if (!systems.equals(other.systems))
			return false;
		if (transactionForm == null) {
			if (other.transactionForm != null)
				return false;
		} else if (!transactionForm.equals(other.transactionForm))
			return false;
		if (treeDepth == null) {
			if (other.treeDepth != null)
				return false;
		} else if (!treeDepth.equals(other.treeDepth))
			return false;
		if (treeIds == null) {
			if (other.treeIds != null)
				return false;
		} else if (!treeIds.equals(other.treeIds))
			return false;
		if (treeNames == null) {
			if (other.treeNames != null)
				return false;
		} else if (!treeNames.equals(other.treeNames))
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (usable == null) {
			if (other.usable != null)
				return false;
		} else if (!usable.equals(other.usable))
			return false;
		return true;
	}

	public Integer getProductFlag() {
		return productFlag;
	}

	public void setProductFlag(Integer productFlag) {
		this.productFlag = productFlag;
	}

	public Boolean getIsPriceMonitor() {
		return isPriceMonitor;
	}

	public void setIsPriceMonitor(Boolean isPriceMonitor) {
		this.isPriceMonitor = isPriceMonitor;
	}

	public Boolean getIsMarketableMonitor() {
		return isMarketableMonitor;
	}

	public void setIsMarketableMonitor(Boolean isMarketableMonitor) {
		this.isMarketableMonitor = isMarketableMonitor;
	}

	public Boolean getIsKeyMonitor() {
		return isKeyMonitor;
	}

	public void setIsKeyMonitor(Boolean isKeyMonitor) {
		this.isKeyMonitor = isKeyMonitor;
	}

	public Boolean getIsUnifiedParameter() {
		return isUnifiedParameter;
	}

	public void setIsUnifiedParameter(Boolean isUnifiedParameter) {
		this.isUnifiedParameter = isUnifiedParameter;
	}

	public Boolean getIsMarketableUnifiedParameter() {
		return isMarketableUnifiedParameter;
	}

	public void setIsMarketableUnifiedParameter(Boolean isMarketableUnifiedParameter) {
		this.isMarketableUnifiedParameter = isMarketableUnifiedParameter;
	}
	
	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	@Override
	public String toString() {
		return "PlatformCatalogs [createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", name=" + name + ", parentId=" + parentId
				+ ", treeDepth=" + treeDepth + ", descs=" + descs + ", usable="
				+ usable + ", treeIds=" + treeIds + ", treeNames=" + treeNames
				+ ", pic=" + pic + ", priority=" + priority + ", isParent="
				+ isParent + ", code=" + code + ", types=" + types
				+ ", parent=" + parent + ", catalogId=" + catalogId
				+ ", transactionForm=" + transactionForm + ", systems="
				+ systems + ", isDelete=" + isDelete + "]";
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

    public List<CatalogParameter> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<CatalogParameter> parametersList) {
        this.parametersList = parametersList;
    }
}