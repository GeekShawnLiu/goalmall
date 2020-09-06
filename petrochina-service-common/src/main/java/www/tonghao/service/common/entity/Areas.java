package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class Areas extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 地区名称
     */
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 父级
     */
    @ApiModelProperty(value="树级ID，例：根节点id/父节点id../自身id")
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 层级数
     */
    @ApiModelProperty(value="层级数")
    @Column(name = "tree_depth")
    private Integer treeDepth;

    /**
     * 是否有效
     */
    private Boolean published;

    /**
     * 树形ID
     */
    @ApiModelProperty(value="树级ID，例：父节点id..-自身id")
    @Column(name = "tree_ids")
    private String treeIds;

    /**
     * 树形名称
     */
    @ApiModelProperty(value="树级名称，例：父节点名称..-自身名称")
    @Column(name = "tree_names")
    private String treeNames;

    /**
     * 排序
     */
    private Float position;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    
    @Column(name = "is_parent")
    private String isParent;

    @Transient
    private boolean checked;
    
    @ApiModelProperty(value="父级地区")
    @Transient
    private Areas parent;
    
    /**
     * 获取地区名称
     *
     * @return name - 地区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置地区名称
     *
     * @param name 地区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父级
     *
     * @return parent_ids - 父级
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父级
     *
     * @param parentIds 父级
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
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
     * 获取是否有效
     *
     * @return published - 是否有效
     */
    public Boolean getPublished() {
        return published;
    }

    /**
     * 设置是否有效
     *
     * @param published 是否有效
     */
    public void setPublished(Boolean published) {
        this.published = published;
    }

    /**
     * 获取树形ID
     *
     * @return tree_ids - 树形ID
     */
    public String getTreeIds() {
        return treeIds;
    }

    /**
     * 设置树形ID
     *
     * @param treeIds 树形ID
     */
    public void setTreeIds(String treeIds) {
        this.treeIds = treeIds;
    }

    /**
     * 获取树形名称
     *
     * @return tree_names - 树形名称
     */
    public String getTreeNames() {
        return treeNames;
    }

    /**
     * 设置树形名称
     *
     * @param treeNames 树形名称
     */
    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
    }

    /**
     * 获取排序
     *
     * @return position - 排序
     */
    public Float getPosition() {
        return position;
    }

    /**
     * 设置排序
     *
     * @param position 排序
     */
    public void setPosition(Float position) {
        this.position = position;
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

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Areas getParent() {
		return parent;
	}

	public void setParent(Areas parent) {
		this.parent = parent;
	}
    
}