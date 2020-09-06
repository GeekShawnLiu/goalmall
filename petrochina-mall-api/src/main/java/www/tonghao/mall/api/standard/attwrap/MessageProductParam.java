package www.tonghao.mall.api.standard.attwrap;

//type=16 商品介绍及规格参数变更消息
public class MessageProductParam implements Message {

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
