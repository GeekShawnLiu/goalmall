package www.tonghao.service.common.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;





import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="采购人库")
public class Organization extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
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
    @ApiModelProperty(value="层级数")
    @Column(name = "tree_depth")
    private Integer treeDepth;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;

    /**
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 树级ID
     */
    @ApiModelProperty(value="树级ID")
    @Column(name = "tree_ids")
    private String treeIds;

    /**
     * 树级名称
     */
    @ApiModelProperty(value="树级名称")
    @Column(name = "tree_names")
    private String treeNames;

    /**
     * 单位简称
     */
    @ApiModelProperty(value="单位简称")
    @Column(name = "short_name")
    private String shortName;

    /**
     * 法人姓名
     */
    @ApiModelProperty(value="法人姓名")
    @Column(name = "legal_name")
    private String legalName;

    /**
     * 法人身份证
     */
    @ApiModelProperty(value="法人身份证")
    @Column(name = "legal_id_number")
    private String legalIdNumber;

    /**
     * 组织机构代码
     */
    @ApiModelProperty(value="组织机构代码")
    private String code;

    /**
     * 地区
     */
    @ApiModelProperty(value="地区")
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 办公场所
     */
    @ApiModelProperty(value="办公场所")
    @Column(name = "office_space")
    private String officeSpace;

    /**
     * 单位电话
     */
    @ApiModelProperty(value="单位电话")
    @Column(name = "dep_telephone")
    private String depTelephone;

    /**
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 单位传真
     */
    @ApiModelProperty(value="单位传真")
    @Column(name = "dep_fax")
    private String depFax;

    /**
     * 单位网址
     */
    @ApiModelProperty(value="单位网址")
    @Column(name = "dep_website")
    private String depWebsite;

    /**
     * 开户银行
     */
    @ApiModelProperty(value="开户银行")
    private String bank;

    /**
     * 是否允许向下分配机构 1:是 2：否
     */
    @ApiModelProperty(value="是否允许向下分配机构 1:是 2：否")
    @Column(name = "is_assign")
    private String isAssign;

    /**
     * 行业类别
     */
    @ApiModelProperty(value="行业类别")
    @Column(name = "industry_category")
    private Long industryCategory;
    
    @Transient
    private String industryCategoryName;
    
    @Transient
    private String depPropertiesName;

    /**
     * 单位性质
     */
    @ApiModelProperty(value="单位性质")
    @Column(name = "dep_properties")
    private Long depProperties;
    
    /**
     * 是否是父节点
     */
    @ApiModelProperty(value="是否是父节点")
    @Column(name = "is_parent")
    private String isParent;
    
    /**
     * 机构类型 1：采购人/集采机构 2：监管机构
     */
    @ApiModelProperty(value="机构类型 1：采购人 2：监管机构")
    @Column(name = "type")
    private Integer type;
    
    @Transient
    private boolean checked;
    
    /**
     * 地区编码
     */
    @Column(name = "region_code")
    private String regionCode;
    
    /**
     * 地区名称
     */
    @Column(name = "area_name")
    private String areaName;
    
    /**
     * 地区id
     */
    @Column(name = "area_ids")
    private String areaIds;
    
    /**
     * 机构树是否展开标识
     */
    @Transient
    private Boolean open;
    
    /**
     * 机构logo路径
     */
    @ApiModelProperty(value="机构logo路径")
    @Column(name = "logo_path")
    private String logoPath;
    
    @Transient
    private List<OrgSupplier> orgSupplierList;
    
    @Transient
    private String supplierIds;

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
     * 获取单位名称
     *
     * @return name - 单位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置单位名称
     *
     * @param name 单位名称
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
     * 获取排序
     *
     * @return priority - 排序
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * 设置排序
     *
     * @param priority 排序
     */
    public void setPriority(Float priority) {
        this.priority = priority;
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete 是否删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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
     * 获取单位简称
     *
     * @return short_name - 单位简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置单位简称
     *
     * @param shortName 单位简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取法人姓名
     *
     * @return legal_name - 法人姓名
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * 设置法人姓名
     *
     * @param legalName 法人姓名
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * 获取法人身份证
     *
     * @return legal_id_number - 法人身份证
     */
    public String getLegalIdNumber() {
        return legalIdNumber;
    }

    /**
     * 设置法人身份证
     *
     * @param legalIdNumber 法人身份证
     */
    public void setLegalIdNumber(String legalIdNumber) {
        this.legalIdNumber = legalIdNumber;
    }

    /**
     * 获取组织机构代码
     *
     * @return code - 组织机构代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置组织机构代码
     *
     * @param code 组织机构代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取地区
     *
     * @return area_id - 地区
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 设置地区
     *
     * @param areaId 地区
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取办公场所
     *
     * @return office_space - 办公场所
     */
    public String getOfficeSpace() {
        return officeSpace;
    }

    /**
     * 设置办公场所
     *
     * @param officeSpace 办公场所
     */
    public void setOfficeSpace(String officeSpace) {
        this.officeSpace = officeSpace;
    }

    /**
     * 获取单位电话
     *
     * @return dep_telephone - 单位电话
     */
    public String getDepTelephone() {
        return depTelephone;
    }

    /**
     * 设置单位电话
     *
     * @param depTelephone 单位电话
     */
    public void setDepTelephone(String depTelephone) {
        this.depTelephone = depTelephone;
    }

    /**
     * 获取邮编
     *
     * @return zip_code - 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取单位传真
     *
     * @return dep_fax - 单位传真
     */
    public String getDepFax() {
        return depFax;
    }

    /**
     * 设置单位传真
     *
     * @param depFax 单位传真
     */
    public void setDepFax(String depFax) {
        this.depFax = depFax;
    }

    /**
     * 获取单位网址
     *
     * @return dep_website - 单位网址
     */
    public String getDepWebsite() {
        return depWebsite;
    }

    /**
     * 设置单位网址
     *
     * @param depWebsite 单位网址
     */
    public void setDepWebsite(String depWebsite) {
        this.depWebsite = depWebsite;
    }

    /**
     * 获取开户银行
     *
     * @return bank - 开户银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置开户银行
     *
     * @param bank 开户银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取是否允许向下分配机构 1:是 2：否
     *
     * @return is_assign - 是否允许向下分配机构 1:是 2：否
     */
    public String getIsAssign() {
        return isAssign;
    }

    /**
     * 设置是否允许向下分配机构 1:是 2：否
     *
     * @param isAssign 是否允许向下分配机构 1:是 2：否
     */
    public void setIsAssign(String isAssign) {
        this.isAssign = isAssign;
    }

    /**
     * 获取行业类别
     *
     * @return industry_category - 行业类别
     */
    public Long getIndustryCategory() {
        return industryCategory;
    }

    /**
     * 设置行业类别
     *
     * @param industryCategory 行业类别
     */
    public void setIndustryCategory(Long industryCategory) {
        this.industryCategory = industryCategory;
    }

    /**
     * 获取单位性质
     *
     * @return dep_properties - 单位性质
     */
    public Long getDepProperties() {
        return depProperties;
    }

    /**
     * 设置单位性质
     *
     * @param depProperties 单位性质
     */
    public void setDepProperties(Long depProperties) {
        this.depProperties = depProperties;
    }

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getIndustryCategoryName() {
		return industryCategoryName;
	}

	public void setIndustryCategoryName(String industryCategoryName) {
		this.industryCategoryName = industryCategoryName;
	}

	public String getDepPropertiesName() {
		return depPropertiesName;
	}

	public void setDepPropertiesName(String depPropertiesName) {
		this.depPropertiesName = depPropertiesName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((areaIds == null) ? 0 : areaIds.hashCode());
		result = prime * result
				+ ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		result = prime * result + (checked ? 1231 : 1237);
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((depFax == null) ? 0 : depFax.hashCode());
		result = prime * result
				+ ((depProperties == null) ? 0 : depProperties.hashCode());
		result = prime
				* result
				+ ((depPropertiesName == null) ? 0 : depPropertiesName
						.hashCode());
		result = prime * result
				+ ((depTelephone == null) ? 0 : depTelephone.hashCode());
		result = prime * result
				+ ((depWebsite == null) ? 0 : depWebsite.hashCode());
		result = prime
				* result
				+ ((industryCategory == null) ? 0 : industryCategory.hashCode());
		result = prime
				* result
				+ ((industryCategoryName == null) ? 0 : industryCategoryName
						.hashCode());
		result = prime * result
				+ ((isAssign == null) ? 0 : isAssign.hashCode());
		result = prime * result
				+ ((isDelete == null) ? 0 : isDelete.hashCode());
		result = prime * result
				+ ((isParent == null) ? 0 : isParent.hashCode());
		result = prime * result
				+ ((legalIdNumber == null) ? 0 : legalIdNumber.hashCode());
		result = prime * result
				+ ((legalName == null) ? 0 : legalName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((officeSpace == null) ? 0 : officeSpace.hashCode());
		result = prime * result + ((open == null) ? 0 : open.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result
				+ ((regionCode == null) ? 0 : regionCode.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result
				+ ((treeDepth == null) ? 0 : treeDepth.hashCode());
		result = prime * result + ((treeIds == null) ? 0 : treeIds.hashCode());
		result = prime * result
				+ ((treeNames == null) ? 0 : treeNames.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		Organization other = (Organization) obj;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (areaIds == null) {
			if (other.areaIds != null)
				return false;
		} else if (!areaIds.equals(other.areaIds))
			return false;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		if (bank == null) {
			if (other.bank != null)
				return false;
		} else if (!bank.equals(other.bank))
			return false;
		if (checked != other.checked)
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
		if (depFax == null) {
			if (other.depFax != null)
				return false;
		} else if (!depFax.equals(other.depFax))
			return false;
		if (depProperties == null) {
			if (other.depProperties != null)
				return false;
		} else if (!depProperties.equals(other.depProperties))
			return false;
		if (depPropertiesName == null) {
			if (other.depPropertiesName != null)
				return false;
		} else if (!depPropertiesName.equals(other.depPropertiesName))
			return false;
		if (depTelephone == null) {
			if (other.depTelephone != null)
				return false;
		} else if (!depTelephone.equals(other.depTelephone))
			return false;
		if (depWebsite == null) {
			if (other.depWebsite != null)
				return false;
		} else if (!depWebsite.equals(other.depWebsite))
			return false;
		if (industryCategory == null) {
			if (other.industryCategory != null)
				return false;
		} else if (!industryCategory.equals(other.industryCategory))
			return false;
		if (industryCategoryName == null) {
			if (other.industryCategoryName != null)
				return false;
		} else if (!industryCategoryName.equals(other.industryCategoryName))
			return false;
		if (isAssign == null) {
			if (other.isAssign != null)
				return false;
		} else if (!isAssign.equals(other.isAssign))
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
		if (legalIdNumber == null) {
			if (other.legalIdNumber != null)
				return false;
		} else if (!legalIdNumber.equals(other.legalIdNumber))
			return false;
		if (legalName == null) {
			if (other.legalName != null)
				return false;
		} else if (!legalName.equals(other.legalName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (officeSpace == null) {
			if (other.officeSpace != null)
				return false;
		} else if (!officeSpace.equals(other.officeSpace))
			return false;
		if (open == null) {
			if (other.open != null)
				return false;
		} else if (!open.equals(other.open))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (regionCode == null) {
			if (other.regionCode != null)
				return false;
		} else if (!regionCode.equals(other.regionCode))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public List<OrgSupplier> getOrgSupplierList() {
		return orgSupplierList;
	}

	public void setOrgSupplierList(List<OrgSupplier> orgSupplierList) {
		this.orgSupplierList = orgSupplierList;
	}

	public String getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(String supplierIds) {
		this.supplierIds = supplierIds;
	}
	
}