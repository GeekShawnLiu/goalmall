package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name="org_supplier")
public class OrgSupplier extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "org_id")
	private Long orgId;
	
	@Column(name = "supplier_id")
	private Long supplierId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
}
