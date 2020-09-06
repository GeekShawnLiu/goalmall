package www.tonghao.mall.api.sn.attwrap;

/**
 * 商品上下架
 *
 */
public class MessageState implements Message {

	private String sku;
	
	// 1.上架 ;2.下架;0.添加;3.修改;4.删除
	private int status;
	
	private String categoryId;
	
	private String time;
	
	private int type;

	private String id;
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
