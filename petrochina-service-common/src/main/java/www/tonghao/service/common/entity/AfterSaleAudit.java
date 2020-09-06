package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "after_sale_audit")
public class AfterSaleAudit extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "after_sale_id")
	private Long afterSaleId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "user_name")
	private String userName;

    private String reason;
    
    @Column(name = "created_at")
    private String createdAt;

	public Long getAfterSaleId() {
		return afterSaleId;
	}

	public void setAfterSaleId(Long afterSaleId) {
		this.afterSaleId = afterSaleId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
