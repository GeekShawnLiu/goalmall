package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.MessageAttr;
import www.tonghao.mall.core.ResultWrap;

/**
 * 消息接口
 *
 */
public class MessageRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<MessageAttr> attr;
	
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
	public List<MessageAttr> getAttr() {
		return attr;
	}
	public void setAttr(List<MessageAttr> attr) {
		this.attr = attr;
	}
	
	
}
