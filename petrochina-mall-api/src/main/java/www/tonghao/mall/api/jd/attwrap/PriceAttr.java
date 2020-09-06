package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;

public class PriceAttr {
	private String skuId;
	private BigDecimal price;//协议价
	private BigDecimal jdPrice;//市场价
	private BigDecimal marketPrice;
	private BigDecimal tax;
	private BigDecimal taxPrice;
	private BigDecimal nakedPrice;
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
	public BigDecimal getJdPrice() {
		return jdPrice;
	}
	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}
	public BigDecimal getNakedPrice() {
		return nakedPrice;
	}
	public void setNakedPrice(BigDecimal nakedPrice) {
		this.nakedPrice = nakedPrice;
	}
	
}
