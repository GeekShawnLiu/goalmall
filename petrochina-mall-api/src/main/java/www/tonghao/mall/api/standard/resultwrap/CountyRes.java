package www.tonghao.mall.api.standard.resultwrap;

import java.util.Map;

import www.tonghao.mall.core.ResultWrap;



/**
 * 三级地区api结果类
 *
 */
public class CountyRes implements ResultWrap{
	private boolean success;
	private String desc;
	private Map<String,String> result; //"xxx街道":"2812"
	
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
