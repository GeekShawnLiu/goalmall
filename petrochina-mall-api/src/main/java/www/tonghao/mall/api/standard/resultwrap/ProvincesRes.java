package www.tonghao.mall.api.standard.resultwrap;

import java.util.Map;

import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * 一级地区
 */
public class ProvincesRes implements ResultWrap {
	private boolean success;
	private String desc;
	private Map<String,String> result; //"北京":"1" 
	
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
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
	}
}
