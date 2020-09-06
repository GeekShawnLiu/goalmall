package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreateAttr {
	private String mall_order_id;
	private List<CreOrdSku> sku;
	private BigDecimal orderPrice;
	private BigDecimal freight;
	public String getMall_order_id() {
		return mall_order_id;
	}
	public void setMall_order_id(String mall_order_id) {
		this.mall_order_id = mall_order_id;
	}
	public List<CreOrdSku> getSku() {
		return sku;
	}
	public void setSku(List<CreOrdSku> sku) {
		this.sku = sku;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
}
