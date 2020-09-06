package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.OrderStatusGetResponse.OrderItemInfoList;

/**
 * 查询订单状态结果集封装
 *
 */
public class OrderStatusRes implements ResultWrap{
	private String orderId;	//订单号
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	private String orderStatus;	/*订单状态。1:审核中; 2:待发货; 3:待收货; 4:已完成; 5:已取消;6:已退货; 7:待处理; 8：审核不通过，订单已取消; 9：待支付*/
	private boolean success;
	private List<OrderItemInfoList> orderItemInfoList;	//订单行数组
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<OrderItemInfoList> getOrderItemInfoList() {
		return orderItemInfoList;
	}
	public void setOrderItemInfoList(List<OrderItemInfoList> orderItemInfoList) {
		this.orderItemInfoList = orderItemInfoList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
