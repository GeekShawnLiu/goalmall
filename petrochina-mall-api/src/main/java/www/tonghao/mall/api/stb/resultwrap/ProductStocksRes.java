package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.api.stb.attwrap.ProductStocksAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductStocksRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<ProductStocksAttr> productStocksAttrs;
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
	public List<ProductStocksAttr> getProductStocksAttrs() {
		return productStocksAttrs;
	}
	public void setProductStocksAttrs(List<ProductStocksAttr> productStocksAttrs) {
		this.productStocksAttrs = productStocksAttrs;
	}
	
	
	
}
