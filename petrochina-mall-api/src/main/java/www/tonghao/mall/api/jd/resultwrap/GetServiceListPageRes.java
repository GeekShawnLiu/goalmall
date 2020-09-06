package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.api.jd.attwrap.ServiceListPageAttr;
import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * Description: 查询服务单概要接口返回数据
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetServiceListPageRes implements ResultWrap{

	private boolean success;

	private String resultMessage;

	private String resultCode;
	
	/**
	 * 售后服务单信息，订单有售后服务单时有值
	 */
	private ServiceListPageAttr result;

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

	public ServiceListPageAttr getResult() {
		return result;
	}

	public void setResult(ServiceListPageAttr result) {
		this.result = result;
	}
}
