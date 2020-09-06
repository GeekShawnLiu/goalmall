package www.tonghao.mall.api.jd.attwrap;

/**
 * 
 * Description: 售后地址信息实体
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceAftersalesAddressInfoDTO {

	/**
	 * 售后地址
	 */
	private String address;
	
	/**
	 * 售后电话
	 */
	private String tel;
	
	/**
	 * 售后联系人
	 */
	private String linkMan;
	
	/**
	 * 售后邮编
	 */
	private String postCode;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
