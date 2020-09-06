package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="快速链接")
@Table(name = "quick_link")
public class QuickLink extends BaseEntity{
	
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
     * 链接名称
     */
    @ApiModelProperty(value="链接名称")
    private String name;

    /**
     * 链接地址
     */
    @ApiModelProperty(value="链接地址")
    private String url;

    /**
     * 状态 1:启用 2：停用
     */
    @ApiModelProperty(value="状态 1:启用 2：停用")
    private String status;

    /**
     * 网站LOGO
     */
    @ApiModelProperty(value="网站LOGO")
    private String logo;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;

    
    @ApiModelProperty(value="排序")
    @Column(name = "parent_id")
    private Long parentId;
    
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
     * 获取链接名称
     *
     * @return name - 链接名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置链接名称
     *
     * @param name 链接名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取链接地址
     *
     * @return url - 链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接地址
     *
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取状态 1:启用 2：停用
     *
     * @return status - 状态 1:启用 2：停用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 1:启用 2：停用
     *
     * @param status 状态 1:启用 2：停用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取网站LOGO
     *
     * @return logo - 网站LOGO
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置网站LOGO
     *
     * @param logo 网站LOGO
     */
    public void setLogo(String logo) {
        this.logo = logo;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
    
}