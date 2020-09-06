package www.tonghao.mall.api.jd.entity;

import java.math.BigDecimal;

public class OrderSku {

	private String skuId;
	
	private int num;
	
    //bNeedAnnex 表示是否需要附件true， 不需要false
	private boolean bNeedAnnex;
	//bNeedGift 表示是否需要赠品，
	//如果需要增品 bNeedGift 请给 true,建议该参数都给 true,但如果实在不需要赠品可以给 false; 
	private boolean bNeedGift;
	
	private BigDecimal price;

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

	public boolean isbNeedAnnex() {
		return bNeedAnnex;
	}

	public void setbNeedAnnex(boolean bNeedAnnex) {
		this.bNeedAnnex = bNeedAnnex;
	}

	public boolean isbNeedGift() {
		return bNeedGift;
	}

	public void setbNeedGift(boolean bNeedGift) {
		this.bNeedGift = bNeedGift;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	
}
