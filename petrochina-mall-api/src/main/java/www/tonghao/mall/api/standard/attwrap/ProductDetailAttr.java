package www.tonghao.mall.api.standard.attwrap;

import java.util.List;

/**
 * 商品api结果属性封装类
 *
 */
public class ProductDetailAttr {
	private String name;	//商品名称
	private String sku;		//商品sku
	private String model;	//型号
	private String weight;	//重量
	private String state;	//上下架状态
	private String unit;	//销售单位
	private String param;	//参数
	private String url;		//商品url
	private String brand_name;	//品牌名称
	private String image_path;	//主图url
	private String introduction;	//描述
	private String category;	//品目名称
	private String ware;	//笔记本主机 x1 电池 x1 电源适配器 x1 
	private String productArea;  //产地
	private String upc; //条形码
	private String service; //售后服务
	private List<Attributes> attributes; //标准参数
	private int saleActives; //是否是促销商品“0-不是促销产品， 1-是促销产品”
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWare() {
		return ware;
	}
	public void setWare(String ware) {
		this.ware = ware;
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
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public int getSaleActives() {
		return saleActives;
	}
	public void setSaleActives(int saleActives) {
		this.saleActives = saleActives;
	}
	
	
	public List<Attributes> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attributes> attributes) {
		this.attributes = attributes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result
				+ ((brand_name == null) ? 0 : brand_name.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((image_path == null) ? 0 : image_path.hashCode());
		result = prime * result
				+ ((introduction == null) ? 0 : introduction.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((param == null) ? 0 : param.hashCode());
		result = prime * result
				+ ((productArea == null) ? 0 : productArea.hashCode());
		result = prime * result + saleActives;
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((ware == null) ? 0 : ware.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDetailAttr other = (ProductDetailAttr) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (brand_name == null) {
			if (other.brand_name != null)
				return false;
		} else if (!brand_name.equals(other.brand_name))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (image_path == null) {
			if (other.image_path != null)
				return false;
		} else if (!image_path.equals(other.image_path))
			return false;
		if (introduction == null) {
			if (other.introduction != null)
				return false;
		} else if (!introduction.equals(other.introduction))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		if (productArea == null) {
			if (other.productArea != null)
				return false;
		} else if (!productArea.equals(other.productArea))
			return false;
		if (saleActives != other.saleActives)
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (upc == null) {
			if (other.upc != null)
				return false;
		} else if (!upc.equals(other.upc))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (ware == null) {
			if (other.ware != null)
				return false;
		} else if (!ware.equals(other.ware))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProductDetailAttr [name=" + name + ", sku=" + sku + ", model="
				+ model + ", weight=" + weight + ", state=" + state + ", unit="
				+ unit + ", param=" + param + ", url=" + url + ", brand_name="
				+ brand_name + ", image_path=" + image_path + ", introduction="
				+ introduction + ", category=" + category + ", ware=" + ware
				+ ", productArea=" + productArea + ", upc=" + upc
				+ ", service=" + service + ", attributes=" + attributes
				+ ", saleActives=" + saleActives + "]";
	}
	
	
}
