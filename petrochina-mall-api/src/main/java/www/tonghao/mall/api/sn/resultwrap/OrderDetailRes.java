package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.OrderDetailGetResponse.OrderItemList;
/**
 * 查询单个订单详细
 *
 */
public class OrderDetailRes implements ResultWrap{
	private boolean success;
	private String accountName;		//下单企业账号的用户名
	private String companyName;		//采购账号的企业名称
	private String createTime;		//订单创建时间（yyyy-MM-dd HH:mm:ss）
	private String orderAmt;		//订单总价
	private String orderId;			//苏宁订单号（唯一）
	private List<OrderItemList> orderItemList;	//订单详情
	private String receiverAddress;		//订单收货地址
	private String receiverTel;			//收货人联系电话
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<OrderItemList> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItemList> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
