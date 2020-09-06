package www.tonghao.mall.api.standard.attwrap;

//type=10 代表订单取消
public class MessageOrderCancel implements Message {

	private String orderId;
	
	/**
	 * 1 取消，2 拒收,  
	 */
	private int state;
	
	private String remark;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
