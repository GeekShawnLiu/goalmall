package www.tonghao.service;

public interface ProductApiService {

    /**
     * 获取品目池编号
     *
     * @param platformCode
     * @return
     */
    String getPools(String platformCode);

    /**
     * 获取商品sku
     *
     * @param catalogId
     * @param platformCode
     * @return
     */
    String skus(String catalogId, String platformCode);

    /**
     * 商品详情
     *
     * @param sku
     * @param platformCode
     * @param productExtendAttributes
     * @return
     */
    String detail(String sku, String productExtendAttributes, String platformCode);

    /**
     * 上下架状态
     *
     * @param sku
     * @param platformCode
     * @return
     */
    String shelfStates(String sku, String platformCode);

    /**
     * 商品图片
     *
     * @param sku
     * @param platformCode
     * @return
     */
    String images(String sku, String platformCode);

    /**
     * 商品价格
     *
     * @param sku
     * @param platformCode
     * @return
     */
    String prices(String sku, String platformCode);

    /**
     * 商品库存
     *
     * @param sku
     * @param platformCode
     * @return
     */
    String stocks(String sku, String area, String platformCode);

    /**
     * 获取消息
     *
     * @param type         消息类型
     *                     type=2 商品价格变更
     *                     type=4 代表商品上下架变更消息
     *                     type=6 代表添加、删除商品池内的商品
     *                     type=16 商品介绍及规格参数变更消息
     *                     type=5 订单已完成
     *                     type=10 代表订单取消，不区分取消原因
     *                     type=12 配送单生成成功消息
     * @param platformCode 平台code
     * @return
     */
    String getMessagePool(String type, String platformCode);

    /**
     * 删除消息
     *
     * @param messageId
     * @param platformCode
     * @return
     */
    String delMessagePool(Long messageId, String platformCode);

    /**
     * 节能环保
     *
     * @param skus
     * @param platformCode
     * @return
     */
    String certificates(String skus, String platformCode);
}
