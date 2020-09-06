package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.RatingsAttr;
import www.tonghao.mall.core.ResultWrap;



/**
 * 好评度api结果类
 *
 */
public class ProductRatingsRes implements ResultWrap{
	private boolean success;
	private String desc;
	private List<RatingsAttr> result;
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
	public List<RatingsAttr> getResult() {
		return result;
	}
	public void setResult(List<RatingsAttr> result) {
		this.result = result;
	}
	
	
}
