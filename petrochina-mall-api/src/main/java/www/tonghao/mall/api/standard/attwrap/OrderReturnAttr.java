package www.tonghao.mall.api.standard.attwrap;

import java.util.List;

public class OrderReturnAttr {
	private String order_id;	//订单编号
	private String type;	//4：退货 5：换货 6：维修
	private List<OrdSelectSkus> skus;
	private String order_service_id;	//退换货的服务号id
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrder_service_id() {
		return order_service_id;
	}
	public void setOrder_service_id(String order_service_id) {
		this.order_service_id = order_service_id;
	}
	public List<OrdSelectSkus> getSkus() {
		return skus;
	}
	public void setSkus(List<OrdSelectSkus> skus) {
		this.skus = skus;
	}
	
	
}
