package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.core.ResultWrap;

public class GetInvoiceListRes implements ResultWrap {
	private boolean success;
	private Map<String, List<String>> result;
	private String desc;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, List<String>> getResult() {
		return result;
	}
	public void setResult(Map<String, List<String>> result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
