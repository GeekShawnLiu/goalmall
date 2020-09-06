package www.tonghao.mall.api.standard.resultwrap;

import java.util.Map;

import www.tonghao.mall.core.ResultWrap;



/**
 * 二级地区api结果类
 *
 */
public class CityRes implements ResultWrap{
	private boolean success;
	private String desc;
	private Map<String,String> result; //"顺义区":"2812"
	
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
