package www.tonghao.service.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.SupplierProtocolCatalog;
import www.tonghao.service.common.mapper.SupplierProtocolCatalogMapper;
import www.tonghao.service.common.service.SupplierProtocolCatalogService;
@Service("supplierProtocolCatalogService")
public class SupplierProtocolCatalogServiceImpl extends BaseServiceImpl<SupplierProtocolCatalog> implements SupplierProtocolCatalogService {

	@Autowired
	private SupplierProtocolCatalogMapper supplierProtocolCatalogMapper;
	
	/**
	 * 通过品目id查询
	 * @return
	 */
	public List<SupplierProtocolCatalog> findListByCatalogId(Long catalogId) {
		return supplierProtocolCatalogMapper.findListByCatalogId(catalogId);
	}
	
	/**
	 * 通过供应商id查询 
	 * @return
	 */
	public List<SupplierProtocolCatalog> findListBySupplierId(Long supplierId) {
		return supplierProtocolCatalogMapper.findListBySupplierId(supplierId);
	}
	
	/**
	 * 条件查询
	 */
	public List<SupplierProtocolCatalog> findList(Map<String, Object> map) {
		return supplierProtocolCatalogMapper.findList(map);
	}

}
