package www.tonghao.mall.api.jd.attwrap;

/**
 * 
 * Description: 客户发货信息实体
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceExpressInfoDTO {

	/**
	 * 服务单号
	 */
	private Integer afsServiceId;
	
	/**
	 * 运费
	 */
	private String freightMoney;
	
	/**
	 * 快递公司名称
	 */
	private String expressCompany;
	
	/**
	 * 客户发货日期：格式为yyyy-MM-dd HH:mm:ss
	 */
	private String deliverDate;
	
	/**
	 * 快递单号
	 */
	private String expressCode;

	public Integer getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(Integer afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public String getFreightMoney() {
		return freightMoney;
	}

	public void setFreightMoney(String freightMoney) {
		this.freightMoney = freightMoney;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
}
