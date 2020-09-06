package www.tonghao.mall.api.jd.attwrap;


/**
 * 商品上下架状态消息
 *
 */
public class MessageState implements Message {

	
	private String skuId;
	//1代表上架；0代表下架
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
