package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.api.stb.attwrap.ProductImageAttr;
import www.tonghao.mall.core.ResultWrap;

public class ProductImageRes  implements ResultWrap {
	private boolean success;
	private String desc;
	private List<ProductImageAttr> imagesList;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<ProductImageAttr> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<ProductImageAttr> imagesList) {
		this.imagesList = imagesList;
	}
	
	
}
