package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.SupplierProtocol;

public interface SupplierProtocolService extends BaseService<SupplierProtocol> {
	
	/**
	 * 条件查询
	 * @param map
	 * @return
	 */
	public SupplierProtocol findEntityBySupplierProtocol(Map<String, Object> map);
	
	/**
	 * 统计分析
	 * @return
	 */
	public List selectDistinctSupplier();
}
