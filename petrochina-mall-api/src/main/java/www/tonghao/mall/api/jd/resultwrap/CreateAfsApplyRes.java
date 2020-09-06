package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * Description: 申请售后接口返回数据
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class CreateAfsApplyRes implements ResultWrap{

	private boolean success;

	private String resultMessage;

	/**
	 * 0 成功
	 */
	private String resultCode;
	
	private String result;

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
