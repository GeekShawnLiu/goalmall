package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

public class ProductSkusRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<String> skus;
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
	public List<String> getSkus() {
		return skus;
	}
	public void setSkus(List<String> skus) {
		this.skus = skus;
	}
	
}
