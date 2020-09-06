package www.tonghao.mall.api.sn.attwrap;

import com.suning.api.entity.govbus.ProdDetailGetResponse.ProdParams;

public class ProductDetailAttr {
	private String skuId;	//商品编码
	private String weight;	//重量
	private String image;	//主图地址
	private String brand;	//品牌
	private String model;	//型号
	private String name;	//商品名称
	private String productArea;	//产地
	private String upc;	//条形码
	private String saleUnit;	//销售单位
	private String category;	//类别
	private String packlisting;		//商品清单
	private String introduction;	//Html描述
	private ProdParams prodParams;	//包含主体参数、基本参数等
	private int state;	//上下架状态
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductArea() {
		return productArea;
	}
	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getSaleUnit() {
		return saleUnit;
	}
	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPacklisting() {
		return packlisting;
	}
	public void setPacklisting(String packlisting) {
		this.packlisting = packlisting;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public ProdParams getProdParams() {
		return prodParams;
	}
	public void setProdParams(ProdParams prodParams) {
		this.prodParams = prodParams;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	
}
