package www.tonghao.mall.api.stb.resultwrap;

import www.tonghao.mall.core.ResultWrap;

public class MessageDelRes implements ResultWrap {
	private boolean success;
	private String desc;
	private Boolean result;
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
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
}
