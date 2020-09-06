package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;

public class ReturnService {
	private OrdSelectSkus skus;
	private String order_service_id;
	private String order_service_type;
	private BigDecimal refund_price;
	private String order_service_step;
	private String approve_notes;
	
	public OrdSelectSkus getSkus() {
		return skus;
	}
	public void setSkus(OrdSelectSkus skus) {
		this.skus = skus;
	}
	public String getOrder_service_id() {
		return order_service_id;
	}
	public void setOrder_service_id(String order_service_id) {
		this.order_service_id = order_service_id;
	}
	public String getOrder_service_type() {
		return order_service_type;
	}
	public void setOrder_service_type(String order_service_type) {
		this.order_service_type = order_service_type;
	}
	public BigDecimal getRefund_price() {
		return refund_price;
	}
	public void setRefund_price(BigDecimal refund_price) {
		this.refund_price = refund_price;
	}
	public String getOrder_service_step() {
		return order_service_step;
	}
	public void setOrder_service_step(String order_service_step) {
		this.order_service_step = order_service_step;
	}
	public String getApprove_notes() {
		return approve_notes;
	}
	public void setApprove_notes(String approve_notes) {
		this.approve_notes = approve_notes;
	}
	
}
