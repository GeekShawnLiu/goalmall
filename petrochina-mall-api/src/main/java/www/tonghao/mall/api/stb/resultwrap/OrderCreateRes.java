package www.tonghao.mall.api.stb.resultwrap;

import www.tonghao.mall.api.stb.attwrap.CreateOrderAttr;
import www.tonghao.mall.core.ResultWrap;

public class OrderCreateRes implements ResultWrap  {
	private boolean success;
	private String desc;
	private CreateOrderAttr createOrderAttr;
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
	public CreateOrderAttr getCreateOrderAttr() {
		return createOrderAttr;
	}
	public void setCreateOrderAttr(CreateOrderAttr createOrderAttr) {
		this.createOrderAttr = createOrderAttr;
	}
	
	
}
