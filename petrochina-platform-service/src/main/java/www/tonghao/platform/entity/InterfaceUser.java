package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "interface_user")
public class InterfaceUser extends BaseEntity {
    

    @Column(name = "user_name")
    private String userName;

    private String password;

    @Column(name = "created_at")
    private String createdAt;

    /**
     * token
     */
    private String tokens;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户code
     */
    private String code;

    /**
     * 过期时间
     */
    @Column(name = "expired_at")
    private String expiredAt;



    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 获取token
     *
     * @return tokens - token
     */
    public String getTokens() {
        return tokens;
    }

    /**
     * 设置token
     *
     * @param tokens token
     */
    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取用户code
     *
     * @return code - 用户code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置用户code
     *
     * @param code 用户code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取过期时间
     *
     * @return expired_at - 过期时间
     */
    public String getExpiredAt() {
        return expiredAt;
    }

    /**
     * 设置过期时间
     *
     * @param expiredAt 过期时间
     */
    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }
}