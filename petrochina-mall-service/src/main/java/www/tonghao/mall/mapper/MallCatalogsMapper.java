package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.MallCatalogs;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PlatformCatalogs;

public interface MallCatalogsMapper extends BaseMapper<MallCatalogs> {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	MallCatalogs findById(long id);

	/**
	 * 根据条件查询列表
	 * @param entity
	 * @return
	 */
	List<MallCatalogs> findListByEntity(MallCatalogs entity);
	
	/**
	 * 查询所有可用品目
	 * @return
	 */
	List<MallCatalogs> findAllUsableList();
	
	/**
	 * 供应商商城品目
	 * @param supplierId
	 * @return
	 */
	List<MallCatalogs> getSupplierCatalogsList(@Param("supplierId")Long supplierId);
	
	/**
	 * 查询所有可用竞价品目
	 * @return
	 */
	List<MallCatalogs> findAllUsableBiddingList();
	
	/**
	 * 查询商城品目的竞价平台品目
	 * @param mallCatalogId
	 * @return
	 */
	List<PlatformCatalogs> findBidingPlatformCatalogsByMcid(@Param("mallCatalogId")Long mallCatalogId);
	
	/**
	 * 
	 * Description: 获取活动详情页商品一级品目信息
	 * 
	 * @data 2019年5月30日
	 * @param 
	 * @return
	 */
	List<MallCatalogs> getMallCatalogsByActivity(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询有上架商品的一级品目信息
	 * 
	 * @data 2019年6月23日
	 * @param 
	 * @return
	 */
	List<MallCatalogs> selectMallCatalogsByProducts();
}