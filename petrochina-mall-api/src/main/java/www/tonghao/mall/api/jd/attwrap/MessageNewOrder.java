package www.tonghao.mall.api.jd.attwrap;

//新订单消息 
public class MessageNewOrder implements Message {

	private String orderId;
	
	//京东账号
	private String pin;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
}
