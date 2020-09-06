package www.tonghao.mall.api.jd.attwrap;

/**
 * 
 * Description: 服务单追踪信息
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceTrackInfoDTO {

	/**
	 * 服务单号
	 */
	private Integer afsServiceId;
	
	/**
	 * 追踪标题
	 */
	private String title;
	
	/**
	 * 追踪内容
	 */
	private String context;
	
	/**
	 * 提交时间：格式为yyyy-MM-dd HH:mm:ss
	 */
	private String createDate;
	
	/**
	 * 操作人昵称
	 */
	private String createName;
	
	/**
	 * 操作人账号
	 */
	private String createPin;

	public Integer getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(Integer afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreatePin() {
		return createPin;
	}

	public void setCreatePin(String createPin) {
		this.createPin = createPin;
	}
}
