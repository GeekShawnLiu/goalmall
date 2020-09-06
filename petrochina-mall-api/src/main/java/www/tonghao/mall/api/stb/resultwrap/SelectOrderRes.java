package www.tonghao.mall.api.stb.resultwrap;

import www.tonghao.mall.api.stb.attwrap.SelectOrderAttr;
import www.tonghao.mall.core.ResultWrap;

public class SelectOrderRes implements ResultWrap {
	private boolean success;
	private String desc;
	private SelectOrderAttr attr;
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
	public SelectOrderAttr getAttr() {
		return attr;
	}
	public void setAttr(SelectOrderAttr attr) {
		this.attr = attr;
	}
	
	
	
}
