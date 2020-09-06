package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.MessageAttr;
import www.tonghao.mall.core.ResultWrap;

public class MessageRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private List<MessageAttr> result;
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
	public List<MessageAttr> getResult() {
		return result;
	}
	public void setResult(List<MessageAttr> result) {
		this.result = result;
	}
	
	
}
