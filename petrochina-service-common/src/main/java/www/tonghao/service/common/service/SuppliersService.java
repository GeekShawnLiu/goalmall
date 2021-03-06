package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Suppliers;


public interface SuppliersService extends BaseService<Suppliers> {

	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<Suppliers> findListByEntity(Suppliers entity);
	
	/**
	 * 添加或者修改供应商
	 * @param suppliers
	 * @return
	 */
	public int saveOrUpdate(Suppliers suppliers);
	
	/**
	 * 查询所有信息
	 * @param map
	 * @return
	 */
	public List<Suppliers> getSuppliersAll(Map<String, Object> map);
	
	/**
	 * 根据id查询供应商
	 * @param id
	 * @return
	 */
	public Suppliers getSuppliersById(@Param("id") Long id);
	
	public List<Suppliers> getSupplierIds(Long[] ids);

	/**  
	 * <p>Title: getMallSuppliers</p>  
	 * <p>Description: 获取电商列表</p>  
	 * @author Yml  
	 * @return  
	 */  
	public List<Suppliers> getMallSuppliers();
	
	
	public int saveSupplierAndUser(List<Suppliers> suppliers);
	
	
	public int saveSupplierApi(Suppliers suppliers);
	
	/**
	 * 包含查询可用的供应商
	 * @param ids
	 * @return
	 */
	List<Suppliers> findUsableListIdIn(Set<Long> ids);
	
	/**
	 * 
	 * Description: 查询所有的没有用户的供应商
	 * 
	 * @data 2019年5月10日
	 * @param 
	 * @return
	 */
	List<Suppliers> findNoUserSuppliers(Map<String, Object> map);
	
	/**
	 * @Description:查询供应商信息（包含用户和店铺）
	 * @date 2019年6月24日
	 */
	Suppliers selectSupplierInfo(Long id);
	
	/**
	 * 
	 * Description: 根据活动查询供应商
	 * 
	 * @data 2019年8月27日
	 * @param 
	 * @return
	 */
	List<Suppliers> selectByActivity(Map<String, Object> map);
}
