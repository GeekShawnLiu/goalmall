package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.tonghao.common.entity.ProductContacts;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.common.warpper.ProductAttributeModel;
import www.tonghao.common.warpper.ProductStock;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.entity.ProductCompareRow;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;

public interface MallProductService extends BaseService<MallProducts> {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	MallProducts selectByKey(Long id);
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	MallProducts findById(Long id);
	
	/**
	 * 查询汽车商品
	 * @param id
	 * @return
	 */
	MallProducts findCarById(Long id);
	
	/**
	 * 查询商品列表
	 * @param entity
	 * @return
	 */
	List<MallProducts> findListByEntity(Products entity);
	
	/**
	 * 查询特惠商品
	 * @param entity
	 * @return
	 */
	List<MallProducts> findBenefitProductsByEntity(Products entity);
	
	/**
	 * 获得商品列表的品牌
	 * @param entity
	 * @return
	 */
	List<Brand> getQueryResultBrands(Products entity);
	
	/**
	 * 获得商品列表的供应商
	 * @param entity
	 * @return
	 */
	List<Suppliers> getQueryResultSuppliers(Products entity);
	
	/**
	 * 获取价格区间
	 */
	List<PriceRangeModel> getPriceRanges(Products entity);
	
	/**
	 * 获取商品列表的参数
	 * @param entity
	 * @return
	 */
	List<ProductParameter> getQueryResultParameters(Products entity);
	
	/**
	 * 得到产品主要参数 参数项的Ids
	 * @param productParameters
	 * @return
	 */
	Set<Long> getKeyProductParameterItemIds(List<ProductParameter> productParameters);
	
	/**
	 * 条件查询供应商协议内的聚合商品列表
	 * @param entity
	 * @return
	 */
	List<MallProducts> findSupplierProtocolAggregationListByEntity(Products entity);
	
	/**
	 * 
	 * @param mallProduct
	 * @return
	 */
	ProductAttributeModel getProductAttributeModel(MallProducts mallProduct);
	
	/**
	 * 统计商品聚合数量
	 * @param mallProduct
	 * @return
	 */
	int getAggregationNum(MallProducts mallProduct);
	
	/**
	 * 判断是否集采品目
	 * @param platformCatalogs
	 * @return
	 */
	boolean isCollect(PlatformCatalogs platformCatalogs);
	
	/**
	 * 商品比对
	 * @param pids
	 * @return
	 */
	List<ProductCompareRow> getProductCompareRows(Long[] pids);
	
	/**
	 * 填充电商商品的url链接、官网价并且自动刷新下架状态
	 * @param mallProduct
	 * @return
	 */
	MallProducts fillEmallUrlAndPriceAndRefreshState(MallProducts mallProduct);
	
	/**
	 * 获得产品库存
	 * @param product 产品
	 * @param areaId 地区ID
	 * @param num 占用库存数量
	 * @return
	 */
	ProductStock getProductStock(Products product,Long areaId,int num);
	/**
	 * 获取联系人列表
	 * @param catalogId
	 * @param supplierId
	 * @param protocolId
	 * @return
	 */
	List<ProductContacts> getContacts(Long catalogId,Long supplierId,Long protocolId);
	
	 /**
	  * 查询同配置商品
	  * @param currPid 当前商品ID
	  * @param catalogId 品目
	  * @param getKeyProductParameterItemIds 主要参数的参数项IDS
	  * @param count 查询数量
	  * @return
	  */
    List<MallProducts> findCurrConfigureList(Map<String, Object> map);
    
    /**
     * 查询同价格商品
     * @param currPid 当前商品ID
     * @param catalogId 品目
     * @param beginPrice 起始价格
     * @param endPrice 结束价格
     * @param count 查询数量
     * @return
     */
    List<MallProducts> findCurrPriceList(Map<String, Object> map);
   
    /**
     * 获取商品参数
     * @param productId
     * @return
     */
    List<ProductParameter> getRelationByProductId(@Param("productId") Long productId);
    
    /**
     * 
     * Description: 校验电商商品上下架状态
     * 
     * @data 2019年6月25日
     * @param 
     * @return
     */
    MallProducts vaProductPutawayStatus(MallProducts mallProduct);
    
    /**
     * 
     * Description: 查询商品区域购买限制
     * 
     * @data 2019年8月5日
     * @param 
     * @return
     */
    Map<String, Object> checkAreaLimit(MallProducts product, Long areaId);
}
