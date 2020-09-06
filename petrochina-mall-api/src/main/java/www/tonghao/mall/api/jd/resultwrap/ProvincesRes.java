package www.tonghao.mall.api.jd.resultwrap;

import java.util.Map;

import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * 一级地区
 */
public class ProvincesRes implements ResultWrap {
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private Map<String,String> result; //"北京":"1" 
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
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
	
}
