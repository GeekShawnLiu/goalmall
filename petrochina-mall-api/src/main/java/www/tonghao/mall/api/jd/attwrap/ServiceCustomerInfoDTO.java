package www.tonghao.mall.api.jd.attwrap;

/**
 * 
 * Description: 客户信息实体
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceCustomerInfoDTO {

	/**
	 * 客户京东账号
	 */
	private String customerPin;

	/**
	 * 用户昵称
	 */
	private String customerName;

	/**
	 * 服务单联系人
	 */
	private String customerContactName;

	/**
	 * 联系电话
	 */
	private String customerTel;

	/**
	 * 手机号
	 */
	private String customerMobilePhone;

	/**
	 * 电子邮件地址
	 */
	private String customerEmail;

	/**
	 * 邮编
	 */
	private String customerPostcode;

	public String getCustomerPin() {
		return customerPin;
	}

	public void setCustomerPin(String customerPin) {
		this.customerPin = customerPin;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

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
}
