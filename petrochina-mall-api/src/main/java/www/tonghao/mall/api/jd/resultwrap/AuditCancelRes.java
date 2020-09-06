package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * Description: 取消服务单接口返回数据
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AuditCancelRes implements ResultWrap{

	private boolean success;

	private String resultMessage;

	private String resultCode;

	/**
	 * true成功 false失败
	 */
	private boolean result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
