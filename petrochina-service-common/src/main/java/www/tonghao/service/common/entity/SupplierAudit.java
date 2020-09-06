package www.tonghao.service.common.entity;

import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:供应商审核
 * @date 2019年6月24日
 */
@Table(name = "supplier_audit")
public class SupplierAudit extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Long supplier_id;
	
	private String reason;
	
	private String createdAt;

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
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
	
	
}
