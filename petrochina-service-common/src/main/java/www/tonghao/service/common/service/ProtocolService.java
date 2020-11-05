package www.tonghao.service.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;

import java.util.List;
import java.util.Map;

public interface ProtocolService extends BaseService<Protocol> {

    List<Protocol> selectByMap(Map<String, Object> map);

    Map<String, Object> saveEntity(Protocol protocol);

    Map<String, Object> updateEntity(Protocol protocol);

    Map<String, Object> deleteEntity(Long id);

    Map<String, Object> updateStatus(Protocol protocol);

    /**
     * 获取商品列表
     *
     * @param page
     * @param productName
     * @param sku
     * @param protocolId
     * @return
     */
    PageInfo<Products> getProductPage(PageBean page, String productName, String sku, Long protocolId);

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
     * @param productName
     * @param sku
     * @param protocolId
     * @param status
     * @return
     */
    PageInfo<ProductQuotation> selectProtocolProductList(PageBean page, String productName, String sku, Long protocolId, Integer status);

    /**
     * 修改协议商品信息
     *
     * @param productQuotation
     * @return
     */
    Map<String, Object> updateProtocolProductInfo(ProductQuotation productQuotation);
}
