package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.OrderCheckrAttr;
import www.tonghao.mall.core.ResultWrap;

public class OrderCheckrRes implements ResultWrap {
	private boolean success;
	private String desc;
	private OrderCheckrAttr result;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public OrderCheckrAttr getResult() {
		return result;
	}
	public void setResult(OrderCheckrAttr result) {
		this.result = result;
	}
	
	
}
