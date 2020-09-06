package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "after_sale_refund_log")
public class AfterSaleRefundLog extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "after_sale_id")
	private Long afterSaleId;
	
	private String operator;
	
	@Column(name = "created_at")
    private String createdAt;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Long getAfterSaleId() {
		return afterSaleId;
	}

	public void setAfterSaleId(Long afterSaleId) {
		this.afterSaleId = afterSaleId;
	}
	
}
