package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.OrderAfterServiceAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 退换货查询api结果类
 *
 */
public class OrderAfterServiceRes implements ResultWrap{
	private boolean success;
	private OrderAfterServiceAttr result;
	private String desc;

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public OrderAfterServiceAttr getResult() {
		return result;
	}
	public void setResult(OrderAfterServiceAttr result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
