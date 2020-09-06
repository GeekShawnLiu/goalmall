package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.api.jd.attwrap.AccessTokenResultAttr;
import www.tonghao.mall.core.ResultWrap;


public class AccessTokenRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private AccessTokenResultAttr result;
	
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
	public AccessTokenResultAttr getResult() {
		return result;
	}
	public void setResult(AccessTokenResultAttr result) {
		this.result = result;
	}
}
