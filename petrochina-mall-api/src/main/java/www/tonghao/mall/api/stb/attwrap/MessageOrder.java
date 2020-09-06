package www.tonghao.mall.api.stb.attwrap;

public class MessageOrder implements Message {
   private String porderId; //父订单号  拆单消息
   
   private String orderId; //订单号  妥投  取消消息
   
   private Integer state;//1是妥投，2是拒收


public String getPorderId() {
	return porderId;
}

public void setPorderId(String porderId) {
	this.porderId = porderId;
}

public String getOrderId() {
	return orderId;
}

public void setOrderId(String orderId) {
	this.orderId = orderId;
}

public Integer getState() {
	return state;
}

public void setState(Integer state) {
	this.state = state;
}
   
   
   
   
}
