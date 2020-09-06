package www.tonghao.service.common.entity;

import java.io.Serializable;
import java.util.List;

public class ProductsList implements Serializable {
	private List<Products> productList;

	public List<Products> getProductList() {
		return productList;
	}

	public void setProductList(List<Products> productList) {
		this.productList = productList;
	}
}
