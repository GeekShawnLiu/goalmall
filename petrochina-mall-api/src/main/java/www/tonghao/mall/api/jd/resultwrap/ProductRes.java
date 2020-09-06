package www.tonghao.mall.api.jd.resultwrap;

import www.tonghao.mall.api.jd.attwrap.ProductAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductRes implements ResultWrap {
	private boolean success;
	private String resultMessage;
	private String resultCode;
	
	private ProductAttr result;

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

	public ProductAttr getResult() {
		return result;
	}

	public void setResult(ProductAttr result) {
		this.result = result;
	}
	
	
	
}
