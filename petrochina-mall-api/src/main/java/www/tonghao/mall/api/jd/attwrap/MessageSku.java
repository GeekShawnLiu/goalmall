package www.tonghao.mall.api.jd.attwrap;


/**
 *  代表添加、删除商品池内商品 
 *
 */
public class MessageSku implements Message {

	private String skuId;
	
	/**
	 * 商品池编号
	 */
	private String page_num;
	
	//1 添加，2 删除
	private int state;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getPage_num() {
		return page_num;
	}

	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	} 
	
	
}
