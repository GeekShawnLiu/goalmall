package www.tonghao.service.common.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.common.enums.ArticleTypePosition;
import www.tonghao.service.common.base.BaseEntity;


@Api(value="公告栏目")
@Table(name = "article_type")
public class ArticleType extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(value="栏目简称")
    @Column(name = "short_name")
    private String shortName;

    /**
     * 父级ID
     */
    @ApiModelProperty(value="父级id")
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 层级数
     */
    @ApiModelProperty(value="层级数")
    @Column(name = "tree_depth")
    private Integer treeDepth;

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
     * 显示顺序
     */
    @ApiModelProperty(value="显示顺序")
    private Float priority;

    /**
     * 状态 1：启用 2：停用
     */
    @ApiModelProperty(value="状态 1：启用 2：停用")
    private String status;

    /**
     * 是否父节点 true false
     */
    @ApiModelProperty(value="是否父节点")
    @Column(name = "is_parent")
    private String isParent;
    
    /**
     * 显示位置
     */
    private ArticleTypePosition position;
    
    @Transient
    @ApiModelProperty(value="栏目公告数")
    private int articleNum;

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
     * 获取栏目名称
     *
     * @return name - 栏目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置栏目名称
     *
     * @param name 栏目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取简称
     *
     * @return short_name - 简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置简称
     *
     * @param shortName 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
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
     * 获取显示顺序
     *
     * @return priority - 显示顺序
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * 设置显示顺序
     *
     * @param priority 显示顺序
     */
    public void setPriority(Float priority) {
        this.priority = priority;
    }

    /**
     * 获取状态 1：启用 2：停用
     *
     * @return status - 状态 1：启用 2：停用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 1：启用 2：停用
     *
     * @param status 状态 1：启用 2：停用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取是否父节点 true false
     *
     * @return is_parent - 是否父节点 true false
     */
    public String getIsParent() {
        return isParent;
    }

    /**
     * 设置是否父节点 true false
     *
     * @param isParent 是否父节点 true false
     */
    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
    
	public ArticleTypePosition getPosition() {
		return position;
	}

	public void setPosition(ArticleTypePosition position) {
		this.position = position;
	}

	public int getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}
    
    
}