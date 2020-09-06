package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.api.stb.attwrap.ProductPoolAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductPoolRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<ProductPoolAttr> productPoolAttrs;
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
	public List<ProductPoolAttr> getProductPoolAttrs() {
		return productPoolAttrs;
	}
	public void setProductPoolAttrs(List<ProductPoolAttr> productPoolAttrs) {
		this.productPoolAttrs = productPoolAttrs;
	}
	
	
	
}
