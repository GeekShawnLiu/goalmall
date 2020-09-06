package www.tonghao.mall.api.sn.attwrap;

/**
 * 订单消息接口
 *
 */
public class MessageOrder implements Message {
	
	private String orderNo;
	
	//1.实时创建成功 ,2.预占成功,3.确认预占 ,4.取消预占 , 5.异常订单取消 
	private int status;
	
	private int type;

	private String id;
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
