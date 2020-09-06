package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.SupplierProtocolCatalog;
import www.tonghao.service.common.entity.Suppliers;

public interface SupplierProtocolCatalogMapper extends BaseMapper<SupplierProtocolCatalog> {

	public List<SupplierProtocolCatalog> getSupplierProtocolCatalogBySupplierId(@Param("supplierId") Long supplierId);

	/**
	 * 通过品目id查询
	 * @return
	 */
	public List<SupplierProtocolCatalog> findListByCatalogId(Long catalogId);
	
	/**
	 * 通过供应商id查询 
	 * @return
	 */
	public List<SupplierProtocolCatalog> findListBySupplierId(Long supplierId);

	/**  
	 * <p>Title: findFixSupplier</p>
	 * <p>Description: 查询定点供应商</p>  
	 * @author YML 
	 * @param map
	 * @return 
	 */
	public List<Suppliers> findFixSupplier(Map<String, Object> map);
	
	/**
	 * 条件查询
	 * @return
	 */
	public List<SupplierProtocolCatalog> findList(Map<String, Object> map);
	
}