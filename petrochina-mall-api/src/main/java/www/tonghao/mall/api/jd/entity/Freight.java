package www.tonghao.mall.api.jd.entity;

import java.util.List;

public class Freight {
	
  private List<Sku> skus;
	
  //一级地址
  private String province;
  //二级地址
  private String city;
  //三级地址
  private String county;
  //四级地址  (如果该地区有四级地址，则必须传递四级地址，没有四级地址则传 0) 
  private String town;
  //京东支付方式  (1：货到付款，2：邮局付款，4：余额支付，5：公司转账（公对公转账），7：网银钱包，101：金采支付)
  private int paymentType;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public List<Sku> getSkus() {
		return skus;
	}
	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	} 
  
  
}
