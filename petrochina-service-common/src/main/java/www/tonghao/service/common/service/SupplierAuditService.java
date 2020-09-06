package www.tonghao.service.common.service;

import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.SupplierAudit;

public interface SupplierAuditService extends BaseService<SupplierAudit>{
	
	public Map<String, Object> audit(String reason, Long supplierId, Integer status, String code);

}
