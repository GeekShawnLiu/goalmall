package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="品目")
public class Catalogs extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 目录名称
     */
    @ApiModelProperty(value="目录名称")
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
     * 详情
     */
    @ApiModelProperty(value="详情")
    private String descs;

    /**
     * 是否可用  1启用 0禁止
     */
    @ApiModelProperty(value="是否可用")
    private Integer usable;

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
     * 图片
     */
    @ApiModelProperty(value="图片")
    private String pic;

    /**
     * 优先级排序
     */
    @ApiModelProperty(value="优先级排序")
    private Float priority;

    /**
     * 是否为父节点
     */
    @ApiModelProperty(value="是否为父节点")
    @Column(name = "is_parent")
    private String isParent;
    
    /**
     * 品目编号
     */
    @ApiModelProperty(value="品目编号")
    @Column(name = "code")
    private String code;
    
    /**
     * 品目标识 1 节能 2环保
     */
    @ApiModelProperty(value="品目标识 1 节能 2环保")
    @Column(name = "types")
    private String types;

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
     * @return desc - 详情
     */
    public String getDescs() {
        return descs;
    }

    /**
     * 设置详情
     *
     * @param desc 详情
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

}