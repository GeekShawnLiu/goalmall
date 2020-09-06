package www.tonghao.mall.api.sn.resultwrap;

import www.tonghao.mall.api.sn.attwrap.ProductDetailAttr;
import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.ProdDetailGetResponse.ProdParams;
/**
 * 苏宁商品详情封装结果集
 */
public class ProductDetailRes implements ResultWrap{
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	private boolean success;
	private ProductDetailAttr result;
	
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public ProductDetailAttr getResult() {
		return result;
	}
	public void setResult(ProductDetailAttr result) {
		this.result = result;
	}
	
	
}
