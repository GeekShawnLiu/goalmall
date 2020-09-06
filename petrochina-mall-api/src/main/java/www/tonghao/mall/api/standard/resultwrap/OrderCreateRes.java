package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.OrderCreateAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 订单创建api结果类
 *
 */
public class OrderCreateRes implements ResultWrap{
	private boolean success;
	private String desc;
	private OrderCreateAttr result;
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
	public OrderCreateAttr getResult() {
		return result;
	}
	public void setResult(OrderCreateAttr result) {
		this.result = result;
	}
		
}
