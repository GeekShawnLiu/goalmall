package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.SuppliersUpdate;

public interface SuppliersUpdateService extends BaseService<SuppliersUpdate>{

	List<SuppliersUpdate> findList(Map<String, Object> map);
	
	SuppliersUpdate findById(Long id);
	
	/**
	 * 
	 * Description: 获取修改之前的信息
	 * 
	 * @data 2019年9月6日
	 * @param 
	 * @return
	 */
	Suppliers getOldInfo(Long supplierId);
	
	/**
	 * 
	 * Description: 提交申请
	 * 
	 * @data 2019年9月6日
	 * @param 
	 * @return
	 */
	Map<String, Object> submitUpdate(SuppliersUpdate suppliersUpdate);
	
	/**
	 * 
	 * Description: 审核
	 * 
	 * @data 2019年9月6日
	 * @param 
	 * @return
	 */
	Map<String, Object> auditUpdate(SuppliersUpdate suppliersUpdate);
	
	/**
	 * 
	 * Description: 查询经营范围
	 * 
	 * @data 2019年9月9日
	 * @param 
	 * @return
	 */
	List<PlatformCatalogs> getCatalogsTree(Long supplierId);
	
}
