package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;

public class PriceAttr {
	private String sku;
	private BigDecimal price;//协议价
	private BigDecimal mall_price;//市场价
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMall_price() {
		return mall_price;
	}
	public void setMall_price(BigDecimal mall_price) {
		this.mall_price = mall_price;
	}
	
}
