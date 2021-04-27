package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PlatformCatalogs;

public interface PlatformCatalogsMapper extends BaseMapper<PlatformCatalogs> {
	
	/**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<PlatformCatalogs> getChildrenById(@Param("id") long id);
	
	/**
	 * 获取所有的父节点
	 * @param id
	 * @return
	 */
	public List<PlatformCatalogs> getParentById(@Param("id") Long id);

	/**  
	 * <p>Title: getSelectData</p>  
	 * <p>Description: 获取数据排除自身及子节点</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	public List<PlatformCatalogs> getSelectData(@Param("id") Long id);

	public PlatformCatalogs findRelationById(Long id);
	
	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<PlatformCatalogs> findListByEntity(PlatformCatalogs entity);
	List<PlatformCatalogs> findListBySystem(PlatformCatalogs entity);
	
	public List<PlatformCatalogs> getCatalogsBysupplierIdAndProId(Map<String,Object> map);
	
	public PlatformCatalogs findByCode(String code);

	/**  
	 * <p>Title: validateName</p>
	 * <p>Description: 校验同级品目相同名称</p>  
	 * @author YML 
	 * @param parentId
	 * @param id
	 * @param name
	 * @return 
	 */
	public int validateName(@Param("parentId") Long parentId, @Param("id") Long id, @Param("name") String name);
	
	/**
	 * 根据计划品目查询计划品目ID
	 */
	List<Long> selectIDByCode(@Param("catalogCode") String catalogCode);
	List<Long> selectPIDByName(@Param("catalogCode") String catalogCode);
	
	List<PlatformCatalogs> selectByTreeNamesOrCode(@Param("treeNames") String treeNames,@Param("code") String code);
	
	/**
	 * 
	 * Description: 查询关联mallCatalogs一级品目id
	 * 
	 * @data 2019年6月10日
	 * @param 
	 * @return
	 */
	Long selectMallCatalogsIdByMid(@Param("mallCatalogsId") Long mallCatalogsId);
	
	/**
	 * 
	 * Description: 查询店铺品目树
	 * 
	 * @data 2019年9月9日
	 * @param 
	 * @return
	 */
	List<PlatformCatalogs> selectShopCatalogs(@Param("shopId") Long shopId);

	List<PlatformCatalogs> getByTreeDepth(@Param("treeDepth") int treeDepth);

	List<PlatformCatalogs> getOneLevelChildren(Long id);
} 