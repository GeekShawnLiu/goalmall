package www.tonghao.mall.api.standard.attwrap;

//type=5 订单已完成
public class MessageOrderComplete implements Message {

	private String orderId;
	
	private int state;

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
	
	
	
}
