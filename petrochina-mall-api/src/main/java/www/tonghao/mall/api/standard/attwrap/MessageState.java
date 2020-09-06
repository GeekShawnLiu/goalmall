package www.tonghao.mall.api.standard.attwrap;

//type=4 代表商品上下架变更消息
public class MessageState implements Message {

	private String skuId;
	
	/**
	 * 1 上架，2下架
	 */
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
