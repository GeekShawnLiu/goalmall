package www.tonghao.mall.api.sn.attwrap;

/**
 * 苏宁商品价格
 * @author yyxx9
 *
 */
public class PriceSkus {
	private String skuId;		//sku
	private String price;		//价格
	private String snPrice;		//易购价
	private String discountRate;	//折扣率
	private String tax;			//税率
	private String taxprice;	//税额
	private String nakedprice;	//裸价
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTaxprice() {
		return taxprice;
	}
	public void setTaxprice(String taxprice) {
		this.taxprice = taxprice;
	}
	public String getNakedprice() {
		return nakedprice;
	}
	public void setNakedprice(String nakedprice) {
		this.nakedprice = nakedprice;
	}
	public String getSnPrice() {
		return snPrice;
	}
	public void setSnPrice(String snPrice) {
		this.snPrice = snPrice;
	}
	
}
