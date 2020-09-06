package www.tonghao.mall.api.standard.entity;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.CreOrdSku;

public class Returns {
	
	/**
	 * 电商订单号
	 */
    private String orderId;
    
    /**
     * skus
     */
    private List<CreOrdSku> skus;
    
    /**
     * 4：退货 5：换货 6：维修
     */
    private int type;
    
    /**
     * 是否有包装
     */
    private String packages;
    
    
    /**
     * 售后问题描述
     */
    private String desc;

    /**
     * 联系人
     */
    private String customerContactName;
    
    /**
     * 手机号
     */
    private String customerMobilePhone;
    
    /**
     * 联系电话
     */
    private String customerTel;
    
    /**
     * 省 id
     */
    private String returnProvince;
    /**
     * 市 id
     */
    private String returnCity;
    
    /**
     * 区 id
     */
    private String returnCounty;
    
    /**
     * 详细地址
     */
    private String returnPlace;
    
    
	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public List<CreOrdSku> getSkus() {
		return skus;
	}


	public void setSkus(List<CreOrdSku> skus) {
		this.skus = skus;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getPackages() {
		return packages;
	}


	public void setPackages(String packages) {
		this.packages = packages;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getCustomerContactName() {
		return customerContactName;
	}


	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}


	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}


	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}


	public String getCustomerTel() {
		return customerTel;
	}


	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}


	public String getReturnProvince() {
		return returnProvince;
	}


	public void setReturnProvince(String returnProvince) {
		this.returnProvince = returnProvince;
	}


	public String getReturnCity() {
		return returnCity;
	}


	public void setReturnCity(String returnCity) {
		this.returnCity = returnCity;
	}


	public String getReturnCounty() {
		return returnCounty;
	}


	public void setReturnCounty(String returnCounty) {
		this.returnCounty = returnCounty;
	}


	public String getReturnPlace() {
		return returnPlace;
	}


	public void setReturnPlace(String returnPlace) {
		this.returnPlace = returnPlace;
	}
    
    
    
}
