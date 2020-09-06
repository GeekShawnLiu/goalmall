package www.tonghao.service.common.service;

import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.AfterSaleAudit;

public interface AfterSaleAuditService extends BaseService<AfterSaleAudit>{
	
	/**
	 * @Description:审核操作
	 * @date 2019年7月15日
	 */
	Map<String, Object> audit(AfterSaleAudit afterSaleAudit, Integer auditType, String supplierAddress);
	
	/**
	 * @Description:查询最近一条理由
	 * @date 2019年7月15日
	 */
	public AfterSaleAudit selectByCreatedAtMax(Long afterSaleId);
}
