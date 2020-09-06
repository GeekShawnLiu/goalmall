package www.tonghao.mall.api.stb.attwrap;

public class Image {
	private String path;
	private int order;
	private int isPrimary;//1为主图，0为附图（主图只有一张）
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}
	
}
