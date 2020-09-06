package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.api.jd.attwrap.OrderCreateAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 订单创建api结果类
 */
public class OrderCreateRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private OrderCreateAttr result;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public OrderCreateAttr getResult() {
		return result;
	}
	public void setResult(OrderCreateAttr result) {
		this.result = result;
	}
	
		
}
