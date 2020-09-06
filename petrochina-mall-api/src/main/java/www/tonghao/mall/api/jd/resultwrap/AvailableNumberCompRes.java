package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.core.ResultWrap;

/**
 * 是否可以售后
 *
 */
public class AvailableNumberCompRes implements ResultWrap {

	private boolean success;
	private String resultMessage;
	private String resultCode;
	private int result;
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
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	
}
