package www.tonghao.mall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.tonghao.common.entity.ProductContacts;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;

public interface MallProductsMapper extends BaseMapper<MallProducts> {
	
	MallProducts getById(@Param("id") Long id);
	
	/**
	 * 查询汽车商品
	 * @param id
	 * @return
	 */
	MallProducts getCarById(@Param("id") Long id);
	
	/**
	 * 条件查询供应商协议内的商品列表
	 * @param entity
	 * @return
	 */
	List<MallProducts> findSupplierProtocolListByEntity(Products entity);
	
	/**
	 * 条件查询供应商协议内特惠商品列表
	 * @param entity
	 * @return
	 */
	List<MallProducts> findSupplierProtocolBenefitListByEntity(Products entity);
	
	/**
	 * 条件查询供应商协议内的聚合商品数量
	 * @param entity
	 * @return
	 */
	int countSupplierProtocolAggregationListByEntity(Products entity);
	
	/**
	 * 条件查询供应商协议内的聚合商品列表
	 * @param entity
	 * @return
	 */
	List<MallProducts> findSupplierProtocolAggregationListByEntity(Products entity);
	
	
	/**
	 * 条件查询供应商协议内的最大商品价格
	 * @param entity
	 * @return
	 */
	BigDecimal getSupplierProtocolMaxPriceByEntity(Products entity);
	
	/**
	 * 条件查询协议内商品品牌列表
	 * @param entity
	 * @return
	 */
	List<Brand> getSupplierProtocolBrands(Products entity);
	
	/**
	 * 条件查询协议内商品供应商列表
	 * @param entity
	 * @return
	 */
	List<Suppliers> getSupplierProtocolSuppliers(Products entity);
	
	/**
	 * 条件查询协议内商品参数
	 * @param entity
	 * @return
	 */
	List<ProductParameter> getSupplierProtocolProductParameters(Products entity);
	
	
	
	/**
     * 获取产品主要参数的Ids
     * @param id
     * @return
     */
    Set<Long> getKeyProductParameterIds(Long id);
    
    /**
     * 查询同配置商品
     * @param catalogId
     * @param getKeyProductParameterIds
     * @param count
     * @return
     */
    List<MallProducts> findCurrConfigureList(Map<String, Object> map);
    
    /**
     * 查询同价格商品
     * @param catalogId
     * @param beginPrice
     * @param endPrice
     * @param count
     * @return
     */
    List<MallProducts> findCurrPriceList(Map<String, Object> map);
    
    /**
     * 查询汽车同价格商品
     * @param catalogId
     * @param beginPrice
     * @param endPrice
     * @param count
     * @return
     */
    List<MallProducts> findCarCurrPriceList(@Param("currPid")Long currPid,@Param("catalogId")Long catalogId,
    		@Param("beginPrice")BigDecimal beginPrice,
    		@Param("endPrice")BigDecimal endPrice,
    		@Param("count")Integer count);
    
    /**
     * 
     * Description: 获取联系人列表
     * 
     * @data 2019年4月28日
     * @param 
     * @return
     */
	List<ProductContacts> getContacts(
    		@Param("catalogId")Long catalogId, 
    		@Param("supplierId")Long supplierId, 
    		@Param("protocolId")Long protocolId);
}