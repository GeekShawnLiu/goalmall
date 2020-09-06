package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.AccessTokenResultAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 返回结果封装-获取token
 * @author developer001
 *
 */
public class AccessTokenRes implements ResultWrap{
	private boolean success;
	private String desc;
	private AccessTokenResultAttr result;
	
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


	public AccessTokenResultAttr getResult() {
		return result;
	}

	public void setResult(AccessTokenResultAttr result) {
		this.result = result;
	}

}
