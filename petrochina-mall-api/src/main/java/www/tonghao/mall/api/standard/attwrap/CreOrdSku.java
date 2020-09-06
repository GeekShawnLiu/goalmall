package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;

public class CreOrdSku {
	private String sku;
	private int num;
	private BigDecimal price;
	/**
	 * 价格模式：1-协议价（默认）；2-团购模式； 3-特惠模式; 4-阶梯价模式 }
	 */
	private int mode;
	
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
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
