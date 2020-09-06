package www.tonghao.service.common.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;





import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class Users extends BaseEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 登录名
     */
    @Column(name = "login_name")
    @ApiModelProperty(value="登录名")
    private String loginName;

    /**
     * 加密密码
     */
    @Column(name = "encrypted_password")
    @ApiModelProperty(value="密码",name="encryptedPassword")
    private String encryptedPassword;

    /**
     * 确认密码
     */
    @Transient
    @ApiModelProperty(value="确认密码",name="confirmPassword")
    private String confirmPassword;
    
    /**
     * 登录次数
     */
    @Column(name = "sign_in_count")
    @ApiModelProperty(value="登录次数")
    private Integer signInCount;

    /**
     * 登录时间
     */
    @Column(name = "current_sign_in_at")
    @ApiModelProperty(value="登录时间")
    private String currentSignInAt;

    /**
     * 上次登录时间
     */
    @Column(name = "last_sign_in_at")
    @ApiModelProperty(value="上次登录时间")
    private String lastSignInAt;

    /**
     * 登录IP
     */
    @Column(name = "current_sign_in_ip")
    @ApiModelProperty(value="登录IP")
    private String currentSignInIp;

    /**
     * 上次登录IP
     */
    @Column(name = "last_sign_in_ip")
    @ApiModelProperty(value="上次登录IP")
    private String lastSignInIp;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 默认0:未删除，1:已删除
     */
    @Column(name = "is_delete")
    @ApiModelProperty(value="是否删除，0:未删除，1:已删除")
    private Integer isDelete;

    /**
     * 创建人ID
     */
    @Column(name = "founder_id")
    @ApiModelProperty(value="创建人ID")
    private Long founderId;

    /**
     * 单位ID
     */
    @Column(name = "dep_id")
    @ApiModelProperty(value="单位ID")
    private Long depId;

    /**
     * 单位名称
     */
    @Column(name = "dep_name")
    @ApiModelProperty(value="单位名称")
    private String depName;
    
    /**
     * 数据权限 1:全部  2:本单位 3:个人
     */
    @Column(name = "data_power")
    @ApiModelProperty(value="数据权限")
    private String dataPower;

    /**
     * 用户类型：1：采购人，4：供应商、6：运营单位
     * 
     */
    @ApiModelProperty(value="用户类型：1：采购人，4：供应商、6：运营单位")
    private Integer type;
    
    /**
     * 旧密码
     */
    @Transient
    @ApiModelProperty(value="旧密码",name="oldPassword")
    private String oldPassword;
    
    /**
     * 新密码
     */
    @Transient
    @ApiModelProperty(value="新密码",name="newPassword")
    private String newPassword;
    
    /**
     * 用户角色
     */
    @Transient
    private List<Roles> roles;
    
    /**
     * 组织机构
     */
    @Transient
    private Organization org;
    
    /**
     *业务关联id
     */
    @Column(name = "type_id")
    private Long typeId;
    
    /**
     * 电话
     */
    @Column(name = "tel")
    private String tel;
    
    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;
    
    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String idCard;
    
    /**
     * 编号
     */
    @Column(name = "code")
    private String code;
    
    /**
     * 传真
     */
    @Column(name = "fax")
    private String fax;
    
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    
    /**
     * 供应商注册用户1是，0不是
     */
    @Column(name = "is_key")
    private Integer isKey;
    
    /**
     * 角色类别
     */
    @Column(name = "role_type")
    private String roleType;
    
    /** 
     * 苏宁账号
     */  
    @Column(name = "suning_account")
    private String suningAccount;
    
    @Transient
    private boolean isSuperAdmin;
    
    @Transient
    private boolean isAdmin;
    
    @Transient
    private Suppliers supplier;
    
    @Transient
    private List<TreeNode> list;
    
    @Column(name = "nick_name")
    @ApiModelProperty(value="用户昵称")
    private String nickName;
    
    @Column(name = "head_portrait_path")
    @ApiModelProperty(value="用户头像文件路径")
    private String headPortraitPath;
    
    @Transient
    @ApiModelProperty(value="新手机号")
    private String newMobile;
    
    @Transient
    @ApiModelProperty(value="手机验证码")
    private String mobileCode;
    
    
    @Column(name = "department")
    @ApiModelProperty(value="手动添加部门")
    private String department;
    
    @Column(name = "openid")
    @ApiModelProperty(value="openid")
    private String openid;
    
    @Column(name = "remark")
    @ApiModelProperty(value="备注")
    private String remark;
    
    @Transient
    private String roleNames;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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

    /**
     * 获取登录名
     *
     * @return login_name - 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录名
     *
     * @param loginName 登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取加密密码
     *
     * @return encrypted_password - 加密密码
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * 设置加密密码
     *
     * @param encryptedPassword 加密密码
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * 获取登录次数
     *
     * @return sign_in_count - 登录次数
     */
    public Integer getSignInCount() {
        return signInCount;
    }

    /**
     * 设置登录次数
     *
     * @param signInCount 登录次数
     */
    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }

    /**
     * 获取登录时间
     *
     * @return current_sign_in_at - 登录时间
     */
    public String getCurrentSignInAt() {
        return currentSignInAt;
    }

    /**
     * 设置登录时间
     *
     * @param currentSignInAt 登录时间
     */
    public void setCurrentSignInAt(String currentSignInAt) {
        this.currentSignInAt = currentSignInAt;
    }

    /**
     * 获取上次登录时间
     *
     * @return last_sign_in_at - 上次登录时间
     */
    public String getLastSignInAt() {
        return lastSignInAt;
    }

    /**
     * 设置上次登录时间
     *
     * @param lastSignInAt 上次登录时间
     */
    public void setLastSignInAt(String lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }

    /**
     * 获取登录IP
     *
     * @return current_sign_in_ip - 登录IP
     */
    public String getCurrentSignInIp() {
        return currentSignInIp;
    }

    /**
     * 设置登录IP
     *
     * @param currentSignInIp 登录IP
     */
    public void setCurrentSignInIp(String currentSignInIp) {
        this.currentSignInIp = currentSignInIp;
    }

    /**
     * 获取上次登录IP
     *
     * @return last_sign_in_ip - 上次登录IP
     */
    public String getLastSignInIp() {
        return lastSignInIp;
    }

    /**
     * 设置上次登录IP
     *
     * @param lastSignInIp 上次登录IP
     */
    public void setLastSignInIp(String lastSignInIp) {
        this.lastSignInIp = lastSignInIp;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
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
     * 获取创建人ID
     *
     * @return founder_id - 创建人ID
     */
    public Long getFounderId() {
        return founderId;
    }

    /**
     * 设置创建人ID
     *
     * @param founderId 创建人ID
     */
    public void setFounderId(Long founderId) {
        this.founderId = founderId;
    }

    /**
     * 获取单位ID
     *
     * @return dep_id - 单位ID
     */
    public Long getDepId() {
        return depId;
    }

    /**
     * 设置单位ID
     *
     * @param depId 单位ID
     */
    public void setDepId(Long depId) {
        this.depId = depId;
    }

    /**
     * 获取单位名称
     *
     * @return dep_name - 单位名称
     */
    public String getDepName() {
        return depName;
    }

    /**
     * 设置单位名称
     *
     * @param depName 单位名称
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * 获取数据权限
     *
     * @return dataPower - 数据权限
     */
	public String getDataPower() {
		return dataPower;
	}

	 /**
     * 设置数据权限
     *
     * @param dataPower 数据权限
     */
	public void setDataPower(String dataPower) {
		this.dataPower = dataPower;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSuningAccount() {
		return suningAccount;
	}

	public void setSuningAccount(String suningAccount) {
		this.suningAccount = suningAccount;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<TreeNode> getList() {
		return list;
	}

	public void setList(List<TreeNode> list) {
		this.list = list;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsKey() {
		return isKey;
	}

	public void setIsKey(Integer isKey) {
		this.isKey = isKey;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPortraitPath() {
		return headPortraitPath;
	}

	public void setHeadPortraitPath(String headPortraitPath) {
		this.headPortraitPath = headPortraitPath;
	}

	public String getNewMobile() {
		return newMobile;
	}

	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
}