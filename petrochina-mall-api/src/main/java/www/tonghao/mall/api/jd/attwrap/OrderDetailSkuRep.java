package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;

public class OrderDetailSkuRep {

	private String skuId;
	
	/**
	 * 数量
	 */
	private int num;
	
	/**
	 * 类别
	 */
	private String category;
	
	/**
	 * 名称 
	 */
	private String name;
	
	/**
	 * 税种 
	 */
	private int tax;
	
	/**
	 * 价格
	 */
	private BigDecimal price;
	
	/**
	 * 税额 
	 */
	private BigDecimal taxPrice;
	
	/**
	 * 裸价
	 */
	private BigDecimal nakedPrice;
	
	/**
	 * 类别 0 普通、1 附件、2 赠品， 
	 */
	private int type;
	
	/**
	 * 父商品 ID，如果本身是主商品，则 oid 为 0
	 */
	private int oid;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}
	
	
	
	
}
