package www.tonghao.mall.api.stb.attwrap;


public class ProductStateAttr {
	private String sku;
	private int state;//0:下架，1:上架
	
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
