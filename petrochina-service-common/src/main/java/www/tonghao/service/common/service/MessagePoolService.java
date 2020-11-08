package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.MessagePool;

import java.math.BigDecimal;

public interface MessagePoolService extends BaseService<MessagePool> {

    /**
     * 商品消息
     * type=2 商品价格变更
     * type=4 代表商品上下架变更消息
     * type=6 代表添加、删除商品池内的商品
     * type=16 商品介绍及规格参数变更消息
     * type=5 订单已完成
     * type=10 代表订单取消，不区分取消原因
     * type=12 配送单生成成功消息
     * <p>
     * state
     * type = 4 : 1上架 2下架
     * type = 6 : 1添加 2删除
     * type = 10 : 1取消 2拒收
     */
    void addProductMessage(Long productQuotationId, Long type, Integer state);
}
