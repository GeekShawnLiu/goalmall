package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="收货地址")
@Table(name = "receiver_addresses")
public class ReceiverAddresses extends BaseEntity{
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
     * 所属用户
     */
	@ApiModelProperty(value="所属用户")
    @Column(name = "user_id")
    private Long userId;

    /**
     * 收货人姓名
     */
	@ApiModelProperty(value="收货人姓名")
    @Column(name = "consignee_name")
    private String consigneeName;

    /**
     * 邮编
     */
	@ApiModelProperty(value="邮编")
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 手机
     */
	@ApiModelProperty(value="手机")
    private String mobile;

    /**
     * 电话
     */
	@ApiModelProperty(value="电话")
    private String phone;

    /**
     * 地区编号
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 地区名称
     */
    @ApiModelProperty(value="地区名称")
    @Column(name = "area_name")
    private String areaName;

    /**
     * 详细地址
     */
    @ApiModelProperty(value="详细地址")
    private String addr;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value="电子邮箱")
    private String email;

    /**
     * 别名
     */
    @ApiModelProperty(value="别名,家庭|公司等")
    private String alias;

    /**
     * 是否默认地址
     */
    @ApiModelProperty(value="是否默认地址")
    @Column(name = "is_default")
    private Boolean isDefault;
    
    @ApiModelProperty(value="地区ids")
    @Transient
    private String treeIds;

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
     * 获取所属用户
     *
     * @return user_id - 所属用户
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置所属用户
     *
     * @param userId 所属用户
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取收货人姓名
     *
     * @return consignee_name - 收货人姓名
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * 设置收货人姓名
     *
     * @param consigneeName 收货人姓名
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
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
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取地区编号
     *
     * @return area_id - 地区编号
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 设置地区编号
     *
     * @param areaId 地区编号
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取地区名称
     *
     * @return area_name - 地区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置地区名称
     *
     * @param areaName 地区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取详细地址
     *
     * @return addr - 详细地址
     */
    public String getAddr() {
        return addr;
    }

    /**
     * 设置详细地址
     *
     * @param addr 详细地址
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取别名
     *
     * @return alias - 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 获取是否默认地址
     *
     * @return is_default - 是否默认地址
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认地址
     *
     * @param isDefault 是否默认地址
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

	public String getTreeIds() {
		return treeIds;
	}

	public void setTreeIds(String treeIds) {
		this.treeIds = treeIds;
	}
    
}