package www.tonghao.mall.api.jd.attwrap;


/**
 * 订单拆分
 *
 */
public class MessagePOrder implements Message {
	//父订单id
    private String pOrder;

	public String getpOrder() {
		return pOrder;
	}

	public void setpOrder(String pOrder) {
		this.pOrder = pOrder;
	}
    
    
}
