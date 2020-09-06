package www.tonghao.mall.service;

import www.tonghao.mall.entity.OrderLogs;
import www.tonghao.service.common.base.BaseService;

public interface OrderLogsService extends BaseService<OrderLogs> {

	/**
	 * 保存日志
	 * @param operator 操作人
	 * @param content 内容
	 * @param type	日志类型 0:已提交,1:已确认,2:已发货,3：已完成,4：已取消,5:已退货
	 * @param orderId 订单ID
	 */
	void saveLog(String operator,String content,int type,long orderId);
}
