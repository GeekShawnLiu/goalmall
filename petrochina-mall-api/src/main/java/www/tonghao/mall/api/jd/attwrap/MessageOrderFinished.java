package www.tonghao.mall.api.jd.attwrap;


/**
 * 
 *订单已妥投
 */
public class MessageOrderFinished implements Message{
   private String orderId;
   
   //1 是妥投，2 是拒收
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
