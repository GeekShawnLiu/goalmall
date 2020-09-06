package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.InvoiceAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 订单取消api结果类
 *
 */
public class GetInvoiceListRes implements ResultWrap{
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private List<InvoiceAttr> result;
	
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
	public List<InvoiceAttr> isResult() {
		return result;
	}
	public void setResult(List<InvoiceAttr> result) {
		this.result = result;
	}
	
}
