package www.tonghao.mall.api.jd.entity;

/**
 * 
 * Description: 京东售后 客户信息实体
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AfterSaleCustomerDto {

	/**
	 * 联系人，最多50字符
	 * 必填
	 */
	private String customerContactName;

	/**
	 * 联系电话，最多50字符
	 * 必填
	 */
	private String customerTel;

	/**
	 * 手机号，最多50字符
	 * 必填
	 */
	private String customerMobilePhone;

	/**
	 * Email
	 * 非必填
	 */
	private String customerEmail;

	/**
	 * 邮编
	 * 非必填
	 */
	private String customerPostcode;

	public String getCustomerContactName() {
		return customerContactName;
	}

	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPostcode() {
		return customerPostcode;
	}

	public void setCustomerPostcode(String customerPostcode) {
		this.customerPostcode = customerPostcode;
	}

	public AfterSaleCustomerDto() {
		super();
	}

	public AfterSaleCustomerDto(String customerContactName, String customerTel,
			String customerMobilePhone, String customerEmail,
			String customerPostcode) {
		super();
		this.customerContactName = customerContactName;
		this.customerTel = customerTel;
		this.customerMobilePhone = customerMobilePhone;
		this.customerEmail = customerEmail;
		this.customerPostcode = customerPostcode;
	}
}
