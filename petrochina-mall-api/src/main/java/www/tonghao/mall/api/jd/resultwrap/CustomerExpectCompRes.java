package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.CustomerExpectCompAttr;
import www.tonghao.mall.core.ResultWrap;

/**
 * 
 *根据订单号、商品编号查询支持的服务类型 
 */
public class CustomerExpectCompRes implements ResultWrap {
   
	private boolean success;
	private String resultMessage;
	private String resultCode;
	private List<CustomerExpectCompAttr> result;
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
	public List<CustomerExpectCompAttr> getResult() {
		return result;
	}
	public void setResult(List<CustomerExpectCompAttr> result) {
		this.result = result;
	}
	
	
	
	
}
