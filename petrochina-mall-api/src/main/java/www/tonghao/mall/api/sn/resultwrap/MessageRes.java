package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.api.sn.attwrap.MessageAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 获取消息
 *
 */
public class MessageRes implements ResultWrap{
	private String error_code;	//API异常码
	private String error_msg;	//异常码中文描述
	private List<MessageAttr> result;
	private boolean success;
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public List<MessageAttr> getResult() {
		return result;
	}
	public void setResult(List<MessageAttr> result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
