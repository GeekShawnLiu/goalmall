package www.tonghao.mall.api.sn.resultwrap;

import www.tonghao.mall.core.ResultWrap;


/**
 * 取消预占订单结果集封装
 *
 */
public class OrderCancelRes implements ResultWrap{
	private boolean success;
	private String unCampSuccess;	//是否成功，Y或者N
	private String error_code;	//API错误码
	private String error_msg;	 //错误码中文描述
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
	public String getUnCampSuccess() {
		return unCampSuccess;
	}
	public void setUnCampSuccess(String unCampSuccess) {
		this.unCampSuccess = unCampSuccess;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
