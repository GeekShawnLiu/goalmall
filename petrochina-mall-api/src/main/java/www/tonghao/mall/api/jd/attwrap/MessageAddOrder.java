package www.tonghao.mall.api.jd.attwrap;


//换新订单生成 
public class MessageAddOrder implements Message {

	//服务id
	private String afsServiceId;
	//新订单号
	private String orderId;
	public String getAfsServiceId() {
		return afsServiceId;
	}
	public void setAfsServiceId(String afsServiceId) {
		this.afsServiceId = afsServiceId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
