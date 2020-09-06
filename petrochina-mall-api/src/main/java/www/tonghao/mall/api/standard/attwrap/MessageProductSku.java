package www.tonghao.mall.api.standard.attwrap;

//type=6 代表添加、删除商品池内的商品
public class MessageProductSku implements Message {

	private String skuId;
	
	/**
	 * 1添加，2删除
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
