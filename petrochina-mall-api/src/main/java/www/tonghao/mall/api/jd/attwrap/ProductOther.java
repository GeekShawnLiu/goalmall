package www.tonghao.mall.api.jd.attwrap;

public class ProductOther implements Product {
   private String sku; //sku
   
   private String weight; //重量
   
   private String imagePath; //主图地址
   
   
   private int state; //上下架状态(1上架  0下架) 
   
   private String brandName;//品牌
   
   private String name; //产品名称
   
   private String productArea; //产地
   
   private String upc; //条形码
   
   private String saleUnit; //销售单位 (个，箱，件，套，盒，这种商品单位)
   
   private String category; //类别
   
   private String eleGift;//京东自营礼品卡， 只有当 sku 为京东自营实物礼品卡的时候才有该字段 
   
   private String introduction; //详细介绍
   
   private String param; //规格参数
   
   private String wareQD; //包装清单
   
   private String shouhou; //移动商品售后
   
   private String appintroduce; //移动商品详情
   
   private String wxintroduction; //微信小程序商品详情
   private Integer is_factory_ship;// 是否厂商配送 1是 0否
   
   private String model; //型号
   

	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	
	public String getEleGift() {
		return eleGift;
	}
	
	public void setEleGift(String eleGift) {
		this.eleGift = eleGift;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getParam() {
		return param;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getWareQD() {
		return wareQD;
	}
	
	public void setWareQD(String wareQD) {
		this.wareQD = wareQD;
	}

	public String getShouhou() {
		return shouhou;
	}

	public void setShouhou(String shouhou) {
		this.shouhou = shouhou;
	}

	public String getAppintroduce() {
		return appintroduce;
	}

	public void setAppintroduce(String appintroduce) {
		this.appintroduce = appintroduce;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getWxintroduction() {
		return wxintroduction;
	}

	public void setWxintroduction(String wxintroduction) {
		this.wxintroduction = wxintroduction;
	}

	public Integer getIs_factory_ship() {
		return is_factory_ship;
	}

	public void setIs_factory_ship(Integer is_factory_ship) {
		this.is_factory_ship = is_factory_ship;
	}
   
   
   
   
}
