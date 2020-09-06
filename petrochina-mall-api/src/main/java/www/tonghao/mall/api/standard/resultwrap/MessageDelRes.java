package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.core.ResultWrap;

/**
 * 删除消息
 *
 */
public class MessageDelRes implements ResultWrap{
	private boolean success;
	private String desc;
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
	
}
