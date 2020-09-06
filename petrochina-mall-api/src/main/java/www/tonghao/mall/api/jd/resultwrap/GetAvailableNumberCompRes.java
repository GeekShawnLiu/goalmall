package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * Description: 查询可售后商品接口返回数据
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetAvailableNumberCompRes implements ResultWrap {

	private boolean success;
	
	private String resultMessage;
	
	private String resultCode;
	
	/**
	 * 可申请时返回可申请数量
	 */
	private Integer result;

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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
}
