package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.api.jd.attwrap.FreightAttr;
import www.tonghao.mall.core.ResultWrap;

/**
 * 运费
 *
 */
public class FreightRes implements ResultWrap {
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private FreightAttr FreightAttr;
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
	public FreightAttr getFreightAttr() {
		return FreightAttr;
	}
	public void setFreightAttr(FreightAttr freightAttr) {
		FreightAttr = freightAttr;
	}
	
	
}
