package www.tonghao.mall.api.stb.attwrap;

import java.util.List;

public class SelectOrderAttr {
	private Integer type;//1（订单类型1：父订单）,
	private Integer submitState;//0：未确认下单订单   1：确认下单订单
	private Integer orderState;//订单状态（ 0：取消订单  1：有效 2：挂起）,
	
	private Order pOrder;//父订单
	
	private List<Order> cOrder;//子订单

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Order getpOrder() {
		return pOrder;
	}

	public void setpOrder(Order pOrder) {
		this.pOrder = pOrder;
	}

	public List<Order> getcOrder() {
		return cOrder;
	}

	public void setcOrder(List<Order> cOrder) {
		this.cOrder = cOrder;
	}
	
	
	
}
