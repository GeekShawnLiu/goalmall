package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.OrderRefundItem;

public interface OrderRefundItemMapper extends BaseMapper<OrderRefundItem> {

	 public List<OrderRefundItem> getOrderFundItem(Map<String, Object> map);
	 
	 
	 public List<OrderRefundItem> selectOrderFundItem(Map<String, Object> map);
	
	
}
