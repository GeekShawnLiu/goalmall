package www.tonghao.service.common.service;

import com.github.pagehelper.PageInfo;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;

import java.math.BigDecimal;
import java.util.Map;

public interface ProductQuotationService extends BaseService<ProductQuotation> {

    /**
     * 修改商品状态
     *
     * @param productQuotation
     * @return
     */
    Map<String, Object> updateStatus(ProductQuotation productQuotation);

    /**
     * 修改商品库存信息
     *
     * @param productQuotation
     * @return
     */
    Map<String, Object> updateStock(ProductQuotation productQuotation);

    /**
     * 批量修改状态
     *
     * @param ids
     * @return
     */
    Map<String, Object> batchUpdateStatus(String ids, Integer status);

    /**
     * 修改商品价格
     *
     * @param productQuotation
     * @return
     */
    Map<String, Object> updateProtocolPrice(ProductQuotation productQuotation);

    /**
     * 移除协议商品
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteProtocolProduct(Long id);

    /**
     * 获取待添加商品列表
     *
     * @param page
     * @param map
     * @return
     */
    PageInfo<Products> getProductPage(PageBean page, Map<String, Object> map);

    /**
     * 添加协议商品关联信息
     *
     * @param protocol
     * @return
     */
    Map<String, Object> addProtocolProduct(Protocol protocol);

    /**
     * 查询协议关联的商品列表
     *
     * @param page
     * @param map
     * @return
     */
    PageInfo<ProductQuotation> selectProtocolProductList(PageBean page, Map<String, Object> map);
}
