package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductStateRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<ProductStateAttr> list;
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
	public List<ProductStateAttr> getList() {
		return list;
	}
	public void setList(List<ProductStateAttr> list) {
		this.list = list;
	}
	
	
	
}
