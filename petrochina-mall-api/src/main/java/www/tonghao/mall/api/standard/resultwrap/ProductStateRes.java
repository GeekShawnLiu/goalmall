package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.StateResultAttr;
import www.tonghao.mall.core.ResultWrap;



/**
 * 产品状态api结果类
 */
public class ProductStateRes implements ResultWrap{
	private boolean success;
	private String desc;
	private List<StateResultAttr> result;
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
	public List<StateResultAttr> getResult() {
		return result;
	}
	public void setResult(List<StateResultAttr> result) {
		this.result = result;
	}
	
}
