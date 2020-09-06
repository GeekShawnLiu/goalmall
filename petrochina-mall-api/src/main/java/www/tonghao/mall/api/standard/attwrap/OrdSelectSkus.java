package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;

public class OrdSelectSkus {
	private String sku;
	private int num;
	private BigDecimal price;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
