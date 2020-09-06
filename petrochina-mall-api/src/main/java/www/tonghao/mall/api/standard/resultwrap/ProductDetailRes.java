package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.ProductDetailAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 产品详情api结果类
 *
 */
public class ProductDetailRes implements ResultWrap{
	private boolean success;
	private String desc;
	private ProductDetailAttr result;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public ProductDetailAttr getResult() {
		return result;
	}
	public void setResult(ProductDetailAttr result) {
		this.result = result;
	}
	
}
