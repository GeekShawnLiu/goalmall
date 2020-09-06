package www.tonghao.mall.api.sn.resultwrap;

import www.tonghao.mall.core.ResultWrap;


/**
 * 删除消息接口结果集
 *
 */
public class MessageDeleteRes implements ResultWrap{
	private String isDelSuccess;	//是否删除成功
	private String error_code;		//API异常码
	private String error_msg;		//异常码中文描述
	private boolean success;
	public String getIsDelSuccess() {
		return isDelSuccess;
	}
	public void setIsDelSuccess(String isDelSuccess) {
		this.isDelSuccess = isDelSuccess;
	}
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
