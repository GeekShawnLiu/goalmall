package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;

public class OrderSkuVO {

	private String skuId;
	
	private String name;
	
	private int num;
	
	/**
	 * 商品类型   0 普通、1 附件、2 赠品 
	 */
	private int type;
	
	private String category;
	//商品价格
	private BigDecimal price;
	//商品裸价 
	private BigDecimal nakedPrice;
	//商品税费 
	private BigDecimal taxPrice;
	//oid 为主商品 skuid，如果本身是主商品，则 oid 为 0 
	private int oid;
	//商品税种
	private int tax;
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getNakedPrice() {
		return nakedPrice;
	}
	public void setNakedPrice(BigDecimal nakedPrice) {
		this.nakedPrice = nakedPrice;
	}
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}
	
	
}
