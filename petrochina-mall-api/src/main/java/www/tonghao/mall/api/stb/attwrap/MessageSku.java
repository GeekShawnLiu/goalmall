package www.tonghao.mall.api.stb.attwrap;

public class MessageSku implements Message {
   private String skuId;
   
   private int state;

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
   
   
}
