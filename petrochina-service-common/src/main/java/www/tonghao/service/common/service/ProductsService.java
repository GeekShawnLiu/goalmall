package www.tonghao.service.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.ProductsList;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;

public interface ProductsService extends BaseService<Products> {

	/**  
	 * <p>Title: updateIsDelete</p>  
	 * <p>Description: 更新删除状态</p>  
	 * @author Yml  
	 * @param id
	 * @param isDelete
	 * @return  
	 */  
	int updateIsDelete(Long id, Integer isDelete);

	/**  
	 * <p>Title: saveOrUpdate</p>  
	 * <p>Description: 保存或修改</p>  
	 * @author Yml  
	 * @param product
	 * @return  
	 */  
	HashMap<String, Object> saveOrUpdate(Products product);

	/**  
	 * <p>Title: find</p>  
	 * <p>Description: 查询</p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<Products> find(Map<String, Object> map);

	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: 根据主键id查询</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	Products getById(Long id);

	/**  
	 * <p>Title: updateStatus</p>  
	 * <p>Description: 更新商品状态</p>  
	 * @author Yml  
	 * @param id
	 * @param status
	 * @param userId 
	 * @return  
	 */  
	int updateStatus(Long id, Integer status, Long userId);

	/**  
	 * <p>Title: findMyAgentProducts</p>
	 * <p>Description: 查询我代理的商品</p>  
	 * @author YML 
	 * @param map
	 * @return 
	 */
	List<Products> findMyAgentProducts(Map<String, Object> map);

	/**  
	 * <p>Title: applyUpper</p>
	 * <p>Description: 商品申请上架</p>  
	 * @author YML 
	 * @param id 商品id
	 * @param mySiteCode 
	 * @param userId 当前操作人id
	 * @return 
	 */
	Map<String, Object> applyUpper(Long id, String mySiteCode, Long userId);

	/**  
	 * <p>Title: asynPolymerization</p>
	 * <p>Description: 异步执行商品聚合</p>  
	 * @author YML 
	 * @param productId 
	 */
	void asynPolymerization(Long productId);
	
	/**  
	 * <p>Title: asynPolymerization</p>
	 * <p>Description: 商品聚合</p>  
	 * @author YML 
	 * @param productId 
	 */
	Map<String, Object> polymerization(Long productId);


	
    Integer selectProductCount(Map<String, Object> map);
	
    
	List<Products> selectProductPage(Map<String, Object> map);
	
	/**
	 * 电商自动刷新下架状态
	 * @param supplier
	 * @param product
	 * @return null表示未下架 否则表示已下架
	 */
	public String emallAutoRefreshDownStatus(Suppliers supplier,Products product);

	/**  
	 * <p>Title: updateEmallProductsBySku</p>
	 * <p>Description: 更新电商商品信息</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku
	 * @return 
	 */
	Map<String, Object> updateEmallProductsBySku(Suppliers supplier, String sku);

	/**  
	 * <p>Title: setProductIsMarketable</p>
	 * <p>Description: 更新电商商品上下架</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	void setProductIsMarketable(Suppliers supplier, String sku);

	/**  
	 * <p>Title: saveProductImages</p>
	 * <p>Description: 更新电商商品图片</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	void saveProductImages(Suppliers supplier, String sku);

	/**  
	 * <p>Title: saveProductPrice</p>
	 * <p>Description: 更新电商商品价格</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	void setProductPrice(Suppliers supplier, String sku);

	/**  
	 * <p>Title: validate</p>
	 * <p>Description: 校验电商商品上下架状态</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	void validate(Suppliers supplier, String sku);

	/**  
	 * <p>Title: updateEmallProducts</p>
	 * <p>Description: 通过sku更新电商商品</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 * @return 
	 */
	Map<String, Object> updateEmallProducts(Suppliers supplier, String sku,Long catelogId,boolean is_catelog);

	/**  
	 * <p>Title: updateEmallProductsByCatalog</p>
	 * <p>Description: 通过品目更新电商商品</p>  
	 * @author YML 
	 * @param supplier
	 * @param catalogId 
	 * @return 
	 */
	Map<String, Object> updateEmallProductsByCatalog(Suppliers supplier, Long catalogId);

	/**  
	 * <p>Title: updateParameter</p>
	 * <p>Description: 维护商品参数</p>  
	 * @author YML 
	 * @param product
	 * @return 
	 */
	Map<String, Object> updateParameter(Products product);

	/**  
	 * <p>Title: findByCommoditiesId</p>
	 * <p>Description: 查询聚合的商品</p>  
	 * @author YML 
	 * @param map
	 * @return 
	 */
	List<Products> findByCommoditiesId(Map<String, Object> map);

	/**  
	 * <p>Title: getProductState</p>
	 * <p>Description: 获取电商商品最新上下架状态</p>  
	 * @author YML 
	 * @param sku
	 * @param emallCode 
	 */
	Boolean getProductState(String sku, String emallCode);
	
	/**
	 * 统计分析:根据商品查询
	 */
	List<Map<String, Object>> selectEmallProducts(Map<String, Object> map);

	/**
	 * 统计分析:电商分组查询
	 * @param supplierList
	 */
	List<Map<String, Object>> findByEmallSupplier(Map<String, Object> map);

	/**
	 * 统计分析:电商商品统计(品目品牌)
	 * @param supplierList
	 */
	List<Map<String, Object>> selectEmallBrand(@Param("supplierList") List<Long> supplierList);

	/**  
	 * <p>Title: findSupplierProducts</p>
	 * <p>Description:供应商商品管理查询 </p>  
	 * @author YML 
	 * @param map
	 * @return 
	 */
	List<Products> findSupplierProducts(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 商品审核
	 * 
	 * @data 2019年4月24日
	 * @param 
	 * @return
	 */
	int productAudit(UpperLowerHistory upperLowerHistory, Long userId);
	
	/**
	 * 
	 * Description: 更新非空字段  更新es索引
	 * 
	 * @data 2019年5月8日
	 * @param 
	 * @return
	 */
	@Override
	int updateNotNull(Products product);
	
	/**
	 * 
	 * Description: 修改商品点击量
	 * 
	 * @data 2019年5月17日
	 * @param 
	 * @return
	 */
	void updateHits(Products product);
	
	List<Products> selectChangePrice(Map<String, Object> map);
	
	
	int updatePrice(Products products);
	
	Map<String, Object> updateBatchPrice(ProductsList productList);
	
	int updateBatchPriceResult(Map<String, Object> map);
	
	List<Products> selectproductPutaway(Map<String, Object> map);
	
	
	int updateToUp(Long id);
	
	int updateToUpBatch(String ids);
	
	List<Products> productJdSku();
	
	Products selectBySku(String sku, Long supplierId);
}
