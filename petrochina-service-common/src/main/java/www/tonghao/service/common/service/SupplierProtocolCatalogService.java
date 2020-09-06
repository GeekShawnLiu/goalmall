package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.SupplierProtocolCatalog;

public interface SupplierProtocolCatalogService extends BaseService<SupplierProtocolCatalog> {
	
	/**
	 * 通过品目id查询
	 * @return
	 */
	List<SupplierProtocolCatalog> findListByCatalogId(Long catalogId);
	
	/**
	 * 通过供应商id查询 
	 * @return
	 */
	public List<SupplierProtocolCatalog> findListBySupplierId(Long supplierId);
	
	/**
	 * 条件查询
	 * @return
	 */
	public List<SupplierProtocolCatalog> findList(Map<String, Object> map);
}
