package www.tonghao.mall.api.jd.attwrap;


public class StateResultAttr {
	private String sku;
	private int state;//1：上架 0：下架
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
