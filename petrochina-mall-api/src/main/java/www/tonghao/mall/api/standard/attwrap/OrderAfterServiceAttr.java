package www.tonghao.mall.api.standard.attwrap;

public class OrderAfterServiceAttr {
	private String order_id;
	private String page_total;
	private String page_per;
	private boolean hasNext;
	private ReturnService service;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPage_total() {
		return page_total;
	}
	public void setPage_total(String page_total) {
		this.page_total = page_total;
	}
	public String getPage_per() {
		return page_per;
	}
	public void setPage_per(String page_per) {
		this.page_per = page_per;
	}
	public ReturnService getService() {
		return service;
	}
	public void setService(ReturnService service) {
		this.service = service;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	
}
