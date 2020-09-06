package www.tonghao.service.common.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;

public class Permissions extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父级
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 层级数
     */
    @Column(name = "tree_depth")
    @ApiModelProperty(value="层级数")
    private Integer treeDepth;

    /**
     * 1:导航2:折叠菜单3:菜单4:按钮
     */
    @ApiModelProperty(value="类型，1:导航2:折叠菜单3:菜单4:按钮")
    private Integer type;

    /**
     * url地址
     */
    private String url;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;

    /**
     * iconName
     */
    @Column(name = "icon_name")
    private String iconName;

    /**
     * 权限标识
     */
    @ApiModelProperty(value="权限标识")
    private String perms;

    /**
     * 默认0:未删除，1:已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 默认0：正常，1:冻结
     */
    @ApiModelProperty(value="0：正常，1:冻结")
    @Column(name = "is_frozen")
    private Integer isFrozen;

    /**
     * 树级ID
     */
    @ApiModelProperty(value="树级ID，例：根节点id-父节点id-自身id")
    @Column(name = "tree_ids")
    private String treeIds;

    /**
     * 树级名称
     */
    @ApiModelProperty(value="树级名称，例：根节点名称-父节点名称-自身名称")
    @Column(name = "tree_names")
    private String treeNames;
    
    @Transient
    private Boolean checked;
    
    @Transient
    private Permissions parent; //父级权限菜单

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
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父级
     *
     * @return parent_id - 父级
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级
     *
     * @param parentId 父级
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
     * 获取1:导航2:折叠菜单3:菜单4:按钮
     *
     * @return type - 1:导航2:折叠菜单3:菜单4:按钮
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1:导航2:折叠菜单3:菜单4:按钮
     *
     * @param type 1:导航2:折叠菜单3:菜单4:按钮
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取url地址
     *
     * @return url - url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url地址
     *
     * @param url url地址
     */
    public void setUrl(String url) {
        this.url = url;
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
     * 获取iconName
     *
     * @return iconName - iconName
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * 设置iconName
     *
     * @param iconName iconName
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * 获取权限标识
     *
     * @return perms - 权限标识
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 设置权限标识
     *
     * @param perms 权限标识
     */
    public void setPerms(String perms) {
        this.perms = perms;
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

    /**
     * 获取默认0：正常，1:冻结
     *
     * @return is_frozen - 默认0：正常，1:冻结
     */
    public Integer getIsFrozen() {
        return isFrozen;
    }

    /**
     * 设置默认0：正常，1:冻结
     *
     * @param isFrozen 默认0：正常，1:冻结
     */
    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Permissions getParent() {
		return parent;
	}

	public void setParent(Permissions parent) {
		this.parent = parent;
	}
	
	
	/**
	 * 获得节点列表。从父节点到自身。
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<Permissions> getNodeList() {
		LinkedList<Permissions> list = Lists.newLinkedList();
		Permissions node = this;
		while (node != null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}
	
    
}