package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.PlatformCatalogs;

public interface PlatformCatalogsService extends BaseService<PlatformCatalogs> {

	/**
	 * 添加或者修改品目
	 * @param catalogs
	 * @return
	 */
   	public int saveOrUpdate(PlatformCatalogs catalogs);
   	
   	
   	/**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<PlatformCatalogs> getChildrenById(@Param("id") long id);
	
	
	/**
	 * 根据id 删除当前节点
	 * @param id
	 * @return
	 */
	
	public int deleteCatalogs(long id);
	
	
	/**
	 * 获取所有的父节点
	 * @param id
	 * @return
	 */
	public List<PlatformCatalogs> getParentById(Long id);


	/**  
	 * <p>Title: getSelectData</p>  
	 * <p>Description: 获取数据排除自身及子节点</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	public List<PlatformCatalogs> getSelectData(Long id);


	public int updateDelete(Long id, Integer isDelete);


	/**  
	 * <p>Title: findRelationById</p>  
	 * <p>Description: 根据id获取数据关联父节点数据</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	public PlatformCatalogs findRelationById(Long id);
	
	
	public List<PlatformCatalogs> getCatalogsBysupplierIdAndProId(Map<String,Object> map);
	
	/**
	 * 通过品目编码查询
	 * @param code
	 * @return
	 */
	public PlatformCatalogs findByCode(String code);


	/**  
	 * <p>Title: validateName</p>
	 * <p>Description: 校验同级品目相同名称</p>  
	 * @author YML 
	 * @param parentId
	 * @param id 
	 * @param name 
	 */
	public int validateName(Long parentId, Long id, String name);
	
	List<PlatformCatalogs> selectByTreeNamesOrCode(String treeNames, String code);
	
	/**
	 * 
	 * Description: 查询店铺经营范围
	 * 
	 * @data 2019年9月9日
	 * @param 
	 * @return
	 */
	List<PlatformCatalogs> selectShopCatalogs(Long shopId);
}
