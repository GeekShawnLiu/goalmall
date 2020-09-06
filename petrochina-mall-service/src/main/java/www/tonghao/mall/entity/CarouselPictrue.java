package www.tonghao.mall.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:轮播图
 * @date 2019年4月24日
 */
@Table(name = "carousel_pictrue")
public class CarouselPictrue extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
     * 名字
     */
	 @Column(name = "name")
    private String name;

    /**
     * 路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 大小
     */
    @Column(name = "size")
    private Long size;
    
    /**
     * 是否删除:默认0未删除，1已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
    
    /**
     * 1:pc,2:手机,3:小程序
     */
    @ApiModelProperty(value="类型：1:pc,2:手机,3:小程序      活动页轮播图 4:pc,5:手机,6:小程序")
    @Column(name = "type")
    private Integer type;
    
    /**
     * 活动id
     */
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;
    
    /**
     * 排序
     */
    @Column(name = "rank")
    private Long rank;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private String updatedAt;
    
    /**
     * 活动名称
     */
    @Transient
    private String activityName;
    
    /**
     * 获取名字
     *
     * @return name - 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名字
     *
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取路径
     *
     * @return path - 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取大小
     *
     * @return size - 大小
     */
    public Long getSize() {
        return size;
    }

    /**
     * 设置大小
     *
     * @param size 大小
     */
    public void setSize(Long size) {
        this.size = size;
    }

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
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
    
}