package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.api.jd.attwrap.CompatibleServiceDetailDTO;
import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * Description: 查询服务单明细接口返回数据
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetServiceDetailInfoRes implements ResultWrap{

	private boolean success;

	private String resultMessage;

	private String resultCode;
	
	/**
	 * 售后服务单详细信息，售后服务单存在时有值
	 */
	private CompatibleServiceDetailDTO result;

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

	public CompatibleServiceDetailDTO getResult() {
		return result;
	}

	public void setResult(CompatibleServiceDetailDTO result) {
		this.result = result;
	}
}
