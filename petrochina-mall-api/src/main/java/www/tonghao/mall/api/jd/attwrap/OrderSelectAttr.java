package www.tonghao.mall.api.jd.attwrap;

import java.util.List;

public class OrderSelectAttr {
	private ParentOrderRepVO pOrder;

	private List<ChildrenOrderRepVO> cOrder;

	// 订单类型 1 是父订单 2 是子订单
	private int type;
	// 0 为未确认下单订单 1 为确认下单订单
	private int submitState;
	// 订单状态 0 为取消订单 1 为有效
	private int orderState;

	public List<ChildrenOrderRepVO> getcOrder() {
		return cOrder;
	}

	public void setcOrder(List<ChildrenOrderRepVO> cOrder) {
		this.cOrder = cOrder;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubmitState() {
		return submitState;
	}

	public void setSubmitState(int submitState) {
		this.submitState = submitState;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public ParentOrderRepVO getpOrder() {
		return pOrder;
	}

	public void setpOrder(ParentOrderRepVO pOrder) {
		this.pOrder = pOrder;
	}

}
