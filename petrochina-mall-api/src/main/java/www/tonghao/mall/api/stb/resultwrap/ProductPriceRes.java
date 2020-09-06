package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductPriceRes implements ResultWrap  {
	private boolean success;
	private String desc;
	private List<ProductPriceAttr> attrs;
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
	public List<ProductPriceAttr> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<ProductPriceAttr> attrs) {
		this.attrs = attrs;
	}
	
}
