package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class Roles extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 角色名称
     */
    @ApiModelProperty(value="角色名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 是否进入后台false/0:否  true/1:是
     */
    @ApiModelProperty(value="是否进入后台false/0:否  true/1:是")
    @Column(name = "is_admin")
    private Boolean isAdmin;

    /**
     * 是否超级管理员 false/0:否  true/1:是
     */
    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin;
    

    /**
     * 默认0:未删除，1:已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
    
    /**
     * 是否内置角色  0：否， 1：是   
     */
    @ApiModelProperty(value="是否内置角色  0：否， 1：是 ")
    @Column(name = "is_init")
    private Integer isInit;
    
    /**
     * 是否供应商子账号  0：否， 1：是   
     */
    @ApiModelProperty(value="是否供应商子账号  0：否， 1：是 ")
    @Column(name = "is_supplier_child")
    private Integer isSupplierChild;
    
    /**
     * 唯一编码
     */
    @ApiModelProperty(value="唯一编码")
    private String code;
    
    @Transient
    @ApiModelProperty(value="是否被选中")
    private Boolean isChecked;
    
    /**
     * 角色关联人数
     */
    @Transient
    private Integer userCount;

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
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述
     *
     * @return desc - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param desc 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取是否进入后台
     *
     * @return is_admin - 是否进入后台
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否进入后台
     *
     * @param isAdmin 是否进入后台
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取是否超级管理员
     *
     * @return is_super_admin - 是否超级管理员
     */
    public Boolean getIsSuperAdmin() {
        return isSuperAdmin;
    }

    /**
     * 设置是否超级管理员
     *
     * @param isSuperAdmin 是否超级管理员
     */
    public void setIsSuperAdmin(Boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    /**
     * 获取默认0:未删除，1:已删除
     *
     * @return is_delete - 默认0:未删除，1:已删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置默认0:未删除，1:已删除
     *
     * @param isDelete 默认0:未删除，1:已删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getIsSupplierChild() {
		return isSupplierChild;
	}

	public void setIsSupplierChild(Integer isSupplierChild) {
		this.isSupplierChild = isSupplierChild;
	}
}