package www.tonghao.mall.api.stb.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class Order {
	
  private String supplierOrderId;//史泰博订单编号
  private String porder;//父订单
  private BigDecimal orderNakedPrice;//订单裸价合计
  private BigDecimal orderTaxPrice;//订单税额合计
  private BigDecimal orderPrice;//订单企业价合计
  private Integer type;//2（订单类型2：子订单）,
  private Integer state;//:物流状态（ 0：新建  1：妥投   2：拒收 3:退货  4：换货 5：未收到货）
  private List<OrderSkus> sku;
  private Integer submitState;//0：未确认下单订单   1：确认下单订单
  private Integer orderState;//订单状态（ 0：取消订单  1：有效 2：挂起）,
public String getSupplierOrderId() {
	return supplierOrderId;
}
public void setSupplierOrderId(String supplierOrderId) {
	this.supplierOrderId = supplierOrderId;
}
public String getPorder() {
	return porder;
}
public void setPorder(String porder) {
	this.porder = porder;
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
public BigDecimal getOrderPrice() {
	return orderPrice;
}
public void setOrderPrice(BigDecimal orderPrice) {
	this.orderPrice = orderPrice;
}
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getState() {
	return state;
}
public void setState(Integer state) {
	this.state = state;
}
public List<OrderSkus> getSku() {
	return sku;
}
public void setSku(List<OrderSkus> sku) {
	this.sku = sku;
}
public Integer getSubmitState() {
	return submitState;
}
public void setSubmitState(Integer submitState) {
	this.submitState = submitState;
}
public Integer getOrderState() {
	return orderState;
}
public void setOrderState(Integer orderState) {
	this.orderState = orderState;
}
  
  
}
