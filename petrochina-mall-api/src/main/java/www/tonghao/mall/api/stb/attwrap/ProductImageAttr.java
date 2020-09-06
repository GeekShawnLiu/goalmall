package www.tonghao.mall.api.stb.attwrap;

import java.util.List;

public class ProductImageAttr {
	private String sku;
	private List<Image> images;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	
}
