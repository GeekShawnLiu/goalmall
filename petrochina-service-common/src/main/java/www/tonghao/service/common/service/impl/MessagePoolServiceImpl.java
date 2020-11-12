package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.MessagePool;
import www.tonghao.service.common.entity.ProductQuotation;
import www.tonghao.service.common.mapper.MessagePoolMapper;
import www.tonghao.service.common.mapper.ProductQuotationMapper;
import www.tonghao.service.common.service.MessagePoolService;

import java.math.BigDecimal;
import java.util.Date;

@Service("messagePoolService")
public class MessagePoolServiceImpl extends BaseServiceImpl<MessagePool> implements MessagePoolService {

    @Autowired
    private ProductQuotationMapper productQuotationMapper;

    @Autowired
    private MessagePoolMapper messagePoolMapper;

    @Override
    public void addProductMessage(Long productQuotationId, Long type, Integer state) {
        ProductQuotation p = productQuotationMapper.selectByPrimaryKey(productQuotationId);
        if (p != null) {
            MessagePool messagePool = new MessagePool();
            messagePool.setIsDelete(0);
            messagePool.setCreatedAt(DateUtilEx.timeFormat(new Date()));
            messagePool.setPlatformCode(p.getPlatformInfoCode());
            messagePool.setSku(p.getSku());
            messagePool.setState(state);
            messagePool.setType(type);
            messagePool.setTypeId(p.getId());
            if (type == 2) {
                messagePool.setPrice(p.getProtocolPrice());
                messagePool.setMarketPrice(p.getPrice());
            }
            messagePoolMapper.insertSelective(messagePool);
        }
    }

    @Override
    public void addOrderMessage(Long orderId, String orderSn, String platformInfoCode, Long type, Integer state) {
        MessagePool messagePool = new MessagePool();
        messagePool.setIsDelete(0);
        messagePool.setCreatedAt(DateUtilEx.timeFormat(new Date()));
        messagePool.setPlatformCode(platformInfoCode);
        messagePool.setState(state);
        messagePool.setOrderSn(orderSn);
        messagePool.setType(type);
        messagePool.setTypeId(orderId);
        messagePoolMapper.insertSelective(messagePool);
    }
}
