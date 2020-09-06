package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.core.ResultWrap;

public class OrderSelectIdByThirdOrderRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private String result;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
