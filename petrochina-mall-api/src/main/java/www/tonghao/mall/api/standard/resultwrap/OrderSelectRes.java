package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.OrdSelectResultAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 订单查询api结果类
 *
 */
public class OrderSelectRes implements ResultWrap{
	private boolean success;
	private OrdSelectResultAttr result;
	private String desc;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public OrdSelectResultAttr getResult() {
		return result;
	}
	public void setResult(OrdSelectResultAttr result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
