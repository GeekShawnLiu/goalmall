package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.core.ResultWrap;


/**
 * 订单取消api结果类
 *
 */
public class OrderCancelRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private boolean result;
	
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
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
}
