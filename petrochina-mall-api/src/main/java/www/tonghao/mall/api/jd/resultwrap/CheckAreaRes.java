package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.CheckAreaAttr;
import www.tonghao.mall.core.ResultWrap;

public class CheckAreaRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private List<CheckAreaAttr> attr;
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
	public List<CheckAreaAttr> getAttr() {
		return attr;
	}
	public void setAttr(List<CheckAreaAttr> attr) {
		this.attr = attr;
	}

	
	
}
