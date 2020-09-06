package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.ImageResultAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 产品图片api结果类
 *
 */
public class ProductImageRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private List<ImageResultAttr> result;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<ImageResultAttr> getResult() {
		return result;
	}
	public void setResult(List<ImageResultAttr> result) {
		this.result = result;
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
	
	
}
