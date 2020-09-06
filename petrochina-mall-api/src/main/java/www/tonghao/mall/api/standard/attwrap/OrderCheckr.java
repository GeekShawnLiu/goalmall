package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;

public class OrderCheckr {
  private String orderId;
  
  /**
   * 状态 0 是新建 1 是妥投完成 -1 是拒收 4 是退换货中
   */
  private int state;
  
  /**
   * 开发票状态 0 为未开， 1 为已开
   */
  private int invoiceState;
  
  private BigDecimal totalPrice;

public String getOrderId() {
	return orderId;
}

public void setOrderId(String orderId) {
	this.orderId = orderId;
}

public int getState() {
	return state;
}

public void setState(int state) {
	this.state = state;
}

public int getInvoiceState() {
	return invoiceState;
}

public void setInvoiceState(int invoiceState) {
	this.invoiceState = invoiceState;
}

public BigDecimal getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(BigDecimal totalPrice) {
	this.totalPrice = totalPrice;
}
  
  
  
}
