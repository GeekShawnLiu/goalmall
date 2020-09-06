package www.tonghao.mall.api.jd.entity;

/**
 * 
 * Description: 京东售后 取件信息实体
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AfterSalePickwareDto {

	/**
	 * 取件方式：4上门取件； 7 客户送货；40客户发货
	 * 必填
	 */
	private String pickwareType;

	/**
	 * 取件省
	 * 必填
	 */
	private String pickwareProvince;

	/**
	 * 取件市
	 * 必填
	 */
	private String pickwareCity;

	/**
	 * 取件县
	 * 必填
	 */
	private String pickwareCounty;

	/**
	 * 取件乡镇
	 * 必填
	 */
	private String pickwareVillage;

	/**
	 * 取件街道地址
	 * 取件方式为4（上门取件）时必填，最多500字符
	 * 
	 */
	private String pickwareAddress;

	public String getPickwareType() {
		return pickwareType;
	}

	public void setPickwareType(String pickwareType) {
		this.pickwareType = pickwareType;
	}

	public String getPickwareProvince() {
		return pickwareProvince;
	}

	public void setPickwareProvince(String pickwareProvince) {
		this.pickwareProvince = pickwareProvince;
	}

	public String getPickwareCity() {
		return pickwareCity;
	}

	public void setPickwareCity(String pickwareCity) {
		this.pickwareCity = pickwareCity;
	}

	public String getPickwareCounty() {
		return pickwareCounty;
	}

	public void setPickwareCounty(String pickwareCounty) {
		this.pickwareCounty = pickwareCounty;
	}

	public String getPickwareVillage() {
		return pickwareVillage;
	}

	public void setPickwareVillage(String pickwareVillage) {
		this.pickwareVillage = pickwareVillage;
	}

	public String getPickwareAddress() {
		return pickwareAddress;
	}

	public void setPickwareAddress(String pickwareAddress) {
		this.pickwareAddress = pickwareAddress;
	}

	public AfterSalePickwareDto() {
		super();
	}

	public AfterSalePickwareDto(String pickwareType, String pickwareProvince,
			String pickwareCity, String pickwareCounty, String pickwareVillage,
			String pickwareAddress) {
		super();
		this.pickwareType = pickwareType;
		this.pickwareProvince = pickwareProvince;
		this.pickwareCity = pickwareCity;
		this.pickwareCounty = pickwareCounty;
		this.pickwareVillage = pickwareVillage;
		this.pickwareAddress = pickwareAddress;
	}
}
