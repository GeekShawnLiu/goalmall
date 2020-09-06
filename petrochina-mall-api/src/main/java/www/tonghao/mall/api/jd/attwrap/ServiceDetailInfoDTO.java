package www.tonghao.mall.api.jd.attwrap;

/**
 * 
 * Description: 服务单商品明细
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceDetailInfoDTO {

	/**
	 * 商品编号
	 */
	private Integer wareId;
	
	/**
	 * 商品名称
	 */
	private String wareName;
	
	/**
	 * 商品品牌
	 */
	private String wareBrand;
	
	/**
	 * 明细类型：主商品(10), 赠品(20), 附件(30)，拍拍取主商品就可以
	 */
	private Integer afsDetailType;
	
	/**
	 * 附件描述
	 */
	private String wareDescribe;

	public Integer getWareId() {
		return wareId;
	}

	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public String getWareBrand() {
		return wareBrand;
	}

	public void setWareBrand(String wareBrand) {
		this.wareBrand = wareBrand;
	}

	public Integer getAfsDetailType() {
		return afsDetailType;
	}

	public void setAfsDetailType(Integer afsDetailType) {
		this.afsDetailType = afsDetailType;
	}

	public String getWareDescribe() {
		return wareDescribe;
	}

	public void setWareDescribe(String wareDescribe) {
		this.wareDescribe = wareDescribe;
	}
}
