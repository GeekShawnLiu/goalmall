package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;

//type=2 商品价格变更
public class MessagePrice implements Message {
   private String skuId;
   
   private int state;
   
   private BigDecimal price;
   
   private BigDecimal market_price;

public String getSkuId() {
	return skuId;
}

public void setSkuId(String skuId) {
	this.skuId = skuId;
}

public int getState() {
	return state;
}

public void setState(int state) {
	this.state = state;
}

public BigDecimal getPrice() {
	return price;
}

public void setPrice(BigDecimal price) {
	this.price = price;
}

public BigDecimal getMarket_price() {
	return market_price;
}

public void setMarket_price(BigDecimal market_price) {
	this.market_price = market_price;
}
   
}
