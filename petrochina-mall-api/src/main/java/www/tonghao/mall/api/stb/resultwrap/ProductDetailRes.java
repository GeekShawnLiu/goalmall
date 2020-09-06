package www.tonghao.mall.api.stb.resultwrap;

import www.tonghao.mall.api.stb.attwrap.ProductDetailAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductDetailRes implements ResultWrap {
	private boolean success;
	private String desc;
	private ProductDetailAttr attr;
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
	public ProductDetailAttr getAttr() {
		return attr;
	}
	public void setAttr(ProductDetailAttr attr) {
		this.attr = attr;
	}
	
}
