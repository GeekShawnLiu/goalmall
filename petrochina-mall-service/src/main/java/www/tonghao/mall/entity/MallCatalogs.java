package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.PlatformCatalogs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

@ApiModel(value="商城品目")
@Table(name = "mall_catalogs")
public class MallCatalogs extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 父级品目ID
     */
    @ApiModelProperty(value="父级品目ID")
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 商城品目名称
     */
    @ApiModelProperty(value="商城品目名称")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;

    /**
     * 是否展示
     */
    @ApiModelProperty(value="是否展示")
    @Column(name = "is_display")
    private Boolean isDisplay;
    
    @ApiModelProperty(value="icon图标")
    private String icon;
    
    @ApiModelProperty(value="层级数")
    @Column(name = "tree_depth")
    private Integer treeDepth;
    
    @ApiModelProperty(value="是否竞价品目")
    @Column(name = "is_bidding")
    private Boolean isBidding;
    
    @Transient
    private MallCatalogs parent;
    
    @ApiModelProperty(value="手机h5图片路径")
    @Column(name = "mobile_img")
    private String mobileImg;
    
    @ApiModelProperty(value="图片格式")
    @Column(name = "img_format")
    private String imgFormat;
    
    @Transient
    private String isParent;
    
    
    
    /**
     * 是否选中 0否 1是
     */
    @Transient
    private Integer isChecked;
    
    /**
     * 品目展示平台
     */
    @ApiModelProperty(value="一级品目展示平台")
    @Column(name = "display_platform")
    private String displayPlatform;
    
	/**
     * 平台品目列表
     */
    @Transient
    private List<PlatformCatalogs> platformCatalogList;
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

    public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
     * 获取商城品目名称
     *
     * @return name - 商城品目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商城品目名称
     *
     * @param name 商城品目名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取是否展示
     *
     * @return is_display - 是否展示
     */
    public Boolean getIsDisplay() {
        return isDisplay;
    }

    /**
     * 设置是否展示
     *
     * @param isDisplay 是否展示
     */
    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }
    
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	public Integer getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(Integer treeDepth) {
		this.treeDepth = treeDepth;
	}
	
	public Boolean getIsBidding() {
		return isBidding;
	}

	public void setIsBidding(Boolean isBidding) {
		this.isBidding = isBidding;
	}

	public MallCatalogs getParent() {
		return parent;
	}

	public void setParent(MallCatalogs parent) {
		this.parent = parent;
	}

	public List<PlatformCatalogs> getPlatformCatalogList() {
		return platformCatalogList;
	}

	public void setPlatformCatalogList(List<PlatformCatalogs> platformCatalogList) {
		this.platformCatalogList = platformCatalogList;
	}
	
    public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}
	
	/**
	 * 获得节点
	 * @return
	 */
	@JsonIgnore
	public List<MallCatalogs> getNodeList(){
		LinkedList<MallCatalogs> list = Lists.newLinkedList();
		MallCatalogs node = this;
		while (node!=null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}
	
	/**
	 * 获得根节点
	 * @return
	 */
	@JsonIgnore
	public MallCatalogs getRootNode(){
		MallCatalogs node = this;
		MallCatalogs parentNode = null;
		while (node != null) {
			node = node.getParent();
			if(node!=null){
				parentNode = node;
			}
		}
		
		if(parentNode==null){
			parentNode = this;
		}
		return parentNode;
	}

	public String getMobileImg() {
		return mobileImg;
	}

	public void setMobileImg(String mobileImg) {
		this.mobileImg = mobileImg;
	}

	public String getImgFormat() {
		return imgFormat;
	}

	public void setImgFormat(String imgFormat) {
		this.imgFormat = imgFormat;
	}

	public String getDisplayPlatform() {
		return displayPlatform;
	}

	public void setDisplayPlatform(String displayPlatform) {
		this.displayPlatform = displayPlatform;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
	
	
}