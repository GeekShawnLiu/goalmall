package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;



/**
 * 产品sku接口结果类
 * @author developer001
 *
 */
public class ProductSkusRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<String> result;
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
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
}
