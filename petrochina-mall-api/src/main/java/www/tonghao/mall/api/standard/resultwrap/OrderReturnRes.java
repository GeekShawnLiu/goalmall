package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.OrderReturnAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 订单退货信息
 *
 */
public class OrderReturnRes implements ResultWrap{
	private boolean success;
	private OrderReturnAttr result;
	private String desc;

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public OrderReturnAttr getResult() {
		return result;
	}
	public void setResult(OrderReturnAttr result) {
		this.result = result;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
