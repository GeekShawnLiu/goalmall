package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Suppliers;


public interface SuppliersMapper extends BaseMapper<Suppliers> {
	
	/**
	 * 条件查询协议内的供应商列表
	 * @param entity
	 * @return
	 */
	List<Suppliers> findListByEntity(Suppliers entity);
	
	/**
	 * 获取所有供应商
	 * @param map
	 * @return
	 */
	public List<Suppliers> getSuppliersAll(Map<String, Object> map);
	
	
	/**
	 *根据id获取所有供应商信息
	 * @param id
	 * @return
	 */
	public Suppliers getSuppliersById(Long id);
	/**
	 * 根据ids 查询多条数据
	 * @param ids
	 * @return
	 */
	public List<Suppliers> getSupplierIds(@Param("ids")Long[] ids);


	/**  
	 * <p>Title: getMallSuppliers</p>  
	 * <p>Description: 获取电商</p>  
	 * @author Yml  
	 * @return  
	 */  
	public List<Suppliers> getMallSuppliers();
	
	/**
	 * 根据id in查询可用供应商
	 * @param ids
	 * @return
	 */
	List<Suppliers> findUsableListIdIn(@Param("ids")Set<Long> ids);
	
	/**
	 * 根据协议查询可用供应商
	 * @param ids
	 * @return
	 */
	Set<Long> findUsableByProtocol();
	
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
	Suppliers selectSupplierInfo(@Param("id") Long id);
	
	/**
	 * 
	 * Description: 根据活动查询供应商
	 * 
	 * @data 2019年8月27日
	 * @param 
	 * @return
	 */
	List<Suppliers> selectByActivity(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 供应商code重复校验
	 * 
	 * @data 2019年9月3日
	 * @param 
	 * @return
	 */
	Integer vaSupplierCode(@Param("id")Long id, @Param("code")String code);
}