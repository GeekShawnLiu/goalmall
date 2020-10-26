package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Products;

public interface ProductsMapper extends BaseMapper<Products> {

    List<Products> find(Map<String, Object> map);

    Products getById(@Param("id") Long id);

    /**
     * 条件查询供应商协议内的商品列表
     *
     * @param entity
     * @return
     */
    List<Products> findSupplierProtocolListByEntity(Products entity);


    /**
     * <p>Title: findMyAgentProducts</p>
     * <p>Description: 我代理的商品</p>
     *
     * @param map
     * @return
     * @author YML
     */
    List<Products> findMyAgentProducts(Map<String, Object> map);

    List<Products> selectProductByBrandModel(@Param("brand") String brand, @Param("model") String model, @Param("catalogId") String catalogId);

    List<Products> findBrandByIds(Map<String, Object> map);


    Integer selectProductCount(Map<String, Object> map);

    List<Products> selectProductPage(Map<String, Object> map);

    /**
     * <p>Description: 根据商品Id查找商品</p>
     *
     * @author mys
     * @date 2019年1月8日
     */
    List<Products> selectProduct(@Param("pId") List<Long> pId);

    /**
     * <p>Title: findByCommoditiesId</p>
     * <p>Description: 查询聚合的商品列表</p>
     *
     * @param map
     * @return
     * @author YML
     */
    List<Products> findByCommoditiesId(Map<String, Object> map);

    /**
     * 获得车辆的所有品牌
     *
     * @return
     */
    List<String> selectCarBrand(Map<String, Object> map);

    /**
     * 获得车辆特定品目下的供应商
     *
     * @return
     */
    List<Long> selectCarSupplier(Long catalogId);

    /**
     * 查找定点商品数量
     */
    Integer findFixProduct();


    /**
     * 统计分析:根据商品查询
     *
     * @return
     */
    List<Map<String, Object>> selectEmallProducts(Map<String, Object> map);

    /**
     * 统计分析:电商分组查询
     *
     * @param supplierList
     */
    List<Map<String, Object>> findByEmallSupplier(Map<String, Object> map);

    /**
     * 统计分析:电商商品统计(品目品牌)
     *
     * @param supplierList
     */
    List<Map<String, Object>> selectEmallBrand(@Param("supplierList") List<Long> supplierList);

    /**
     * <p>Title: findSupplierProducts</p>
     * <p>Description: 供应商商品查询</p>
     *
     * @param map
     * @return
     * @author YML
     */
    List<Products> findSupplierProducts(Map<String, Object> map);

    /**
     * @Description:活动添加商品列表
     * @date 2019年4月29日
     */
    List<Products> getActivityProductsList(Map<String, Object> map);

    /**
     * Description: 获取活动商品
     *
     * @param
     * @return
     * @data 2019年5月1日
     */
    List<Products> selectActivityProducts(Map<String, Object> map);

    /**
     * Description: 刷新商品点击量
     *
     * @param
     * @return
     * @data 2019年5月17日
     */
    void updateHits(Products entity);


    List<Products> selectChangePrice(Map<String, Object> map);


    List<Products> selectproductPutaway(Map<String, Object> map);

    List<Products> productJdSku();

    /**
     * Description: 查询子订单商品列表
     *
     * @param
     * @return
     * @data 2019年8月21日
     */
    List<Products> selectListByChildOrder(@Param("childOrderId") Long childOrderId);

    /**
     * Description:	条件查询
     *
     * @param
     * @return
     * @data 2019年8月27日
     */
    List<Products> selectByMap(Map<String, Object> map);

    /**
     * 根据品目查询所有的sku
     *
     * @param catalogId
     * @return
     */
    List<String> selectSkuByCatalogId(@Param("catalogId") Long catalogId);

    /**
     * 根据sku查询商品详情
     *
     * @param sku
     * @return
     */
    Products selectBySku(@Param("sku") String sku);

    /**
     * 根据sku批量查询
     *
     * @param skus
     * @return
     */
    List<Products> selectBySkus(@Param("skus") String[] skus);
}