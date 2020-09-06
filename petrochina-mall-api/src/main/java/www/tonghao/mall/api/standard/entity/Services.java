package www.tonghao.mall.api.standard.entity;

public class Services {

	private String orderServiceId;
	
	private String orderId;
	
	private int page;
	
	private int per;

	public String getOrderServiceId() {
		return orderServiceId;
	}

	public void setOrderServiceId(String orderServiceId) {
		this.orderServiceId = orderServiceId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPer() {
		return per;
	}

	public void setPer(int per) {
		this.per = per;
	}
	
}
