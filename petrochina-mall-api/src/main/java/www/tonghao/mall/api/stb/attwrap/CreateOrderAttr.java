package www.tonghao.mall.api.stb.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderAttr {

	private String supplierOrderId;
	private BigDecimal orderNakedPrice;//订单裸价合计
	private BigDecimal orderTaxPriceTotal;//订单税额合计
	private BigDecimal orderPrice;//订单企业价合计
	private List<OrderSkus> skus;
	
	public List<OrderSkus> getSkus() {
		return skus;
	}
	public void setSkus(List<OrderSkus> skus) {
		this.skus = skus;
	}
	public String getSupplierOrderId() {
		return supplierOrderId;
	}
	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}
	public BigDecimal getOrderNakedPrice() {
		return orderNakedPrice;
	}
	public void setOrderNakedPrice(BigDecimal orderNakedPrice) {
		this.orderNakedPrice = orderNakedPrice;
	}
	public BigDecimal getOrderTaxPriceTotal() {
		return orderTaxPriceTotal;
	}
	public void setOrderTaxPriceTotal(BigDecimal orderTaxPriceTotal) {
		this.orderTaxPriceTotal = orderTaxPriceTotal;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	
	
}
