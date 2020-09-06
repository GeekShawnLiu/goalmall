package www.tonghao.mall.api.stb.attwrap;

import java.math.BigDecimal;

public class OrderSkus {

	private String skuId;
	
	private Integer num;
	
	private BigDecimal nakedprice;//商品裸价
	
	private BigDecimal taxprice;//商品税价
	
	private BigDecimal taxRate;//商品税率
	
	private BigDecimal bizPrice;//商品企业价
	
	private BigDecimal nakedPriceTotal;//商品裸价合计
	
	private BigDecimal taxPriceTotal;//商品税额合计
	
	private BigDecimal bizPriceTotal;//商品企业价合计

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getNakedprice() {
		return nakedprice;
	}

	public void setNakedprice(BigDecimal nakedprice) {
		this.nakedprice = nakedprice;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getBizPrice() {
		return bizPrice;
	}

	public void setBizPrice(BigDecimal bizPrice) {
		this.bizPrice = bizPrice;
	}

	public BigDecimal getNakedPriceTotal() {
		return nakedPriceTotal;
	}

	public void setNakedPriceTotal(BigDecimal nakedPriceTotal) {
		this.nakedPriceTotal = nakedPriceTotal;
	}

	public BigDecimal getTaxPriceTotal() {
		return taxPriceTotal;
	}

	public void setTaxPriceTotal(BigDecimal taxPriceTotal) {
		this.taxPriceTotal = taxPriceTotal;
	}

	public BigDecimal getBizPriceTotal() {
		return bizPriceTotal;
	}

	public void setBizPriceTotal(BigDecimal bizPriceTotal) {
		this.bizPriceTotal = bizPriceTotal;
	}
	
}
