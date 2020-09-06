package www.tonghao.mall.api.jd.entity;

/**
 * 
 * Description: 京东售后 返件信息实体
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AfterSaleReturnwareDto {

	/**
	 * 返件方式：自营配送(10),第三方配送(20);
	 * 必填
	 */
	private String returnwareType;

	/**
	 * 返件省
	 * 必填
	 */
	private String returnwareProvince;

	/**
	 * 返件市
	 * 必填
	 */
	private String returnwareCity;

	/**
	 * 返件县
	 * 必填
	 */
	private String returnwareCounty;

	/**
	 * 返件乡镇
	 * 必填
	 */
	private String returnwareVillage;

	/**
	 * 返件街道地址，最多500字符
	 * 必填
	 */
	private String returnwareAddress;

	public String getReturnwareType() {
		return returnwareType;
	}

	public void setReturnwareType(String returnwareType) {
		this.returnwareType = returnwareType;
	}

	public String getReturnwareProvince() {
		return returnwareProvince;
	}

	public void setReturnwareProvince(String returnwareProvince) {
		this.returnwareProvince = returnwareProvince;
	}

	public String getReturnwareCity() {
		return returnwareCity;
	}

	public void setReturnwareCity(String returnwareCity) {
		this.returnwareCity = returnwareCity;
	}

	public String getReturnwareCounty() {
		return returnwareCounty;
	}

	public void setReturnwareCounty(String returnwareCounty) {
		this.returnwareCounty = returnwareCounty;
	}

	public String getReturnwareVillage() {
		return returnwareVillage;
	}

	public void setReturnwareVillage(String returnwareVillage) {
		this.returnwareVillage = returnwareVillage;
	}

	public String getReturnwareAddress() {
		return returnwareAddress;
	}

	public void setReturnwareAddress(String returnwareAddress) {
		this.returnwareAddress = returnwareAddress;
	}

	public AfterSaleReturnwareDto() {
		super();
	}

	public AfterSaleReturnwareDto(String returnwareType,
			String returnwareProvince, String returnwareCity,
			String returnwareCounty, String returnwareVillage,
			String returnwareAddress) {
		super();
		this.returnwareType = returnwareType;
		this.returnwareProvince = returnwareProvince;
		this.returnwareCity = returnwareCity;
		this.returnwareCounty = returnwareCounty;
		this.returnwareVillage = returnwareVillage;
		this.returnwareAddress = returnwareAddress;
	}
}
