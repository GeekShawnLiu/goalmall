package www.tonghao.mall.api.sn.resultwrap;


import www.tonghao.mall.api.sn.attwrap.OrderCreateAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 创建订单
 * @author yyxx9
 */
public class OrderCreateRes implements ResultWrap{
	private String error_code;
	private String error_msg;
	private boolean success;
	private OrderCreateAttr result;
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public OrderCreateAttr getResult() {
		return result;
	}
	public void setResult(OrderCreateAttr result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
