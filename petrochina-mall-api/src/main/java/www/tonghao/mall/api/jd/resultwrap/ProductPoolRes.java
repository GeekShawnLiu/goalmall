package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.ProductPoolResultAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductPoolRes implements ResultWrap {
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private List<ProductPoolResultAttr> result;
	
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
	public List<ProductPoolResultAttr> getResult() {
		return result;
	}
	public void setResult(List<ProductPoolResultAttr> result) {
		this.result = result;
	}
	
	
}
