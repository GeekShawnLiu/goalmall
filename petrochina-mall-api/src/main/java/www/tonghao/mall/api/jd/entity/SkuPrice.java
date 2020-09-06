package www.tonghao.mall.api.jd.entity;

import java.math.BigDecimal;

public class SkuPrice {

	private String skuId;
	
	private BigDecimal price;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
