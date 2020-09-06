package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.OrderPayPrice;

public interface OrderPayPriceMapper extends BaseMapper<OrderPayPrice> {
   List<OrderPayPrice> getOrderPayPriceByMap(Map<String, Object> map);
}
