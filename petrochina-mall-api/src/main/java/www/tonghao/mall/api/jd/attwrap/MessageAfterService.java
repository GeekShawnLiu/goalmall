package www.tonghao.mall.api.jd.attwrap;


//售后服务单状态变更 
public class MessageAfterService implements Message {

	private String orderId;
	
	private String pin;
	
	private String skuId;
	
	private String afsServiceId;
	
	//1：创建；2：审核不通过；3：审核取消；4：完成
	private int state;

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

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(String afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
}
