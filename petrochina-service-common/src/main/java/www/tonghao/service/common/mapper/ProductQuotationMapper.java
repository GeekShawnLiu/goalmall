package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.entity.Products;

import java.util.List;
import java.util.Map;

public interface ProductQuotationMapper extends BaseMapper<ProductQuotation> {

    /**
     * 查询商品池
     *
     * @param catalogId
     * @param platformCode
     * @return
     */
    List<String> selectSkuPool(@Param("catalogId") String catalogId, @Param("platformCode") String platformCode);

    /**
     * 查询商品信息
     *
     * @param sku
     * @param platformCode
     * @return
     */
    ProductQuotation selectBySku(@Param("sku") String sku, @Param("platformCode") String platformCode);

    /**
     * 根据sku批量查询
     *
     * @param skus
     * @param platformCode
     * @return
     */
    List<ProductQuotation> selectBySkus(@Param("skus") String[] skus, @Param("platformCode") String platformCode);

    /**
     * 根据商品id查询商品信息
     *
     * @param productId
     * @param platformCode
     * @return
     */
    ProductQuotation selectByProductId(@Param("productId") Long productId, @Param("platformCode") String platformCode);

    /**
     * 查询协议关联商品
     *
     * @param map
     * @return
     */
    List<ProductQuotation> selectByProtocolId(Map<String, Object> map);

    /**
     * 协议添加商品列表筛选
     *
     * @param map
     * @return
     */
    List<Products> selectAddProtocolProductList(Map<String, Object> map);
}
