package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "order_invoice")
public class OrderInvoice extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 开具方式：普通、增值税发票
     */
    @ApiModelProperty(value="开具方式：1：增值税普通发票、2：增值税专用发票、3：电子发票")
    @Column(name = "invoice_type")
    private Short invoiceType;

    /**
     * 企业名称
     */
    private String enterprise;

    /**
     * 纳税人识别码
     */
    @Column(name = "id_code")
    private String idCode;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 电话
     */
    private String phone;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 开户账号
     */
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 注册地址
     */
    private String address;

    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "invoice_id")
    private Long invoiceId;
    
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取开具方式：普通、增值税发票
     *
     * @return invoice_type - 开具方式：普通、增值税发票
     */
    public Short getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置开具方式：普通、增值税发票
     *
     * @param invoiceType 开具方式：普通、增值税发票
     */
    public void setInvoiceType(Short invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 获取企业名称
     *
     * @return enterprise - 企业名称
     */
    public String getEnterprise() {
        return enterprise;
    }

    /**
     * 设置企业名称
     *
     * @param enterprise 企业名称
     */
    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    /**
     * 获取纳税人识别码
     *
     * @return id_code - 纳税人识别码
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * 设置纳税人识别码
     *
     * @param idCode 纳税人识别码
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    /**
     * 获取公司名称
     *
     * @return company - 公司名称
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置公司名称
     *
     * @param company 公司名称
     */
    public void setCompany(String company) {
        this.company = company;
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
     * 获取开户银行
     *
     * @return bank - 开户银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置开户银行
     *
     * @param bank 开户银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取开户账号
     *
     * @return bank_account - 开户账号
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置开户账号
     *
     * @param bankAccount 开户账号
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取注册地址
     *
     * @return address - 注册地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置注册地址
     *
     * @param address 注册地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
}