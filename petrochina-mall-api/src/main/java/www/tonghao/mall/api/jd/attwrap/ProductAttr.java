package www.tonghao.mall.api.jd.attwrap;

public class ProductAttr<T extends Product> {
  
	private T product;

	public T getProduct() {
		return product;
	}

	public void setProduct(T product) {
		this.product = product;
	}
}
