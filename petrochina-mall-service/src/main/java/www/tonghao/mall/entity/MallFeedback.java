package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Users;

@ApiModel(value="商城意见反馈")
@Table(name = "mall_feedback")
public class MallFeedback extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="创建日期")
	@Column(name = "created_at")
    private String createdAt;

	@ApiModelProperty(value="修改日期")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 意见描述
     */
	@ApiModelProperty(value="意见描述")
    private String description;

	@ApiModelProperty(value="创建人")
    private String creater;

    /**
     * 创建人用户ID
     */
	@ApiModelProperty(value="创建人用户ID")
    @Column(name = "creater_uid")
    private Long createrUid;

    /**
     * 联系电话
     */
	@ApiModelProperty(value="联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;
	
    /**
     * 内容
     */
	@ApiModelProperty(value="内容")
    private String content;
	
	@Transient
	@ApiModelProperty(value="创建人账号")
	private Users user; //创建人user

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

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
     * @return creater
     */
    public String getCreater() {
        return creater;
    }

    /**
     * @param creater
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 获取创建人用户ID
     *
     * @return creater_uid - 创建人用户ID
     */
    public Long getCreaterUid() {
        return createrUid;
    }

    /**
     * 设置创建人用户ID
     *
     * @param createrUid 创建人用户ID
     */
    public void setCreaterUid(Long createrUid) {
        this.createrUid = createrUid;
    }

    /**
     * 获取联系电话
     *
     * @return contact_phone - 联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置联系电话
     *
     * @param contactPhone 联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
    
}