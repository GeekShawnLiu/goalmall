package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreateAttr {
	private String jdOrderId; //京东订单号
	private BigDecimal freight; //运费（合同有运费配置才会返回，否则不会返回该字段）
	private BigDecimal orderPrice; //价格
	private BigDecimal orderNakedPrice;	//订单裸价 
	private BigDecimal orderTaxPrice;	//订单税额 
	private List<OrderDetailSkuRep> skus;//商品信息 
	public String getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public BigDecimal getOrderNakedPrice() {
		return orderNakedPrice;
	}
	public void setOrderNakedPrice(BigDecimal orderNakedPrice) {
		this.orderNakedPrice = orderNakedPrice;
	}
	public BigDecimal getOrderTaxPrice() {
		return orderTaxPrice;
	}
	public void setOrderTaxPrice(BigDecimal orderTaxPrice) {
		this.orderTaxPrice = orderTaxPrice;
	}
	public List<OrderDetailSkuRep> getSkus() {
		return skus;
	}
	public void setSkus(List<OrderDetailSkuRep> skus) {
		this.skus = skus;
	}
	
	
	
	
}
