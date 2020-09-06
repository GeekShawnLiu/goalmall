package www.tonghao.mall.service.impl;

import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.OrderLogs;
import www.tonghao.mall.service.OrderLogsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service(value="orderLogsService")
public class OrderLogsServiceImpl extends BaseServiceImpl<OrderLogs> implements OrderLogsService {

	@Override
	public void saveLog(String operator, String content, int type, long orderId) {
		OrderLogs log = new OrderLogs();
		log.setContent(content);
		log.setOperator(operator);
		log.setType((byte)type);
		log.setOrderId(orderId);
		save(log);
	}

}
