package www.tonghao.mall.api.jd.attwrap;

/**
 * 
 * Description: 售后服务单
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AfsServicebyCustomerPinAttr {

	/**
	 * 服务单号
	 */
	private Integer afsServiceId;

	/**
	 * 服务类型码：退货(10)、换货(20)、维修(30)
	 */
	private Integer customerExpect;

	/**
	 * 服务类型名称
	 */
	private String customerExpectName;

	/**
	 * 服务单申请时间 格式为yyyy-MM-dd HH:mm:ss
	 */
	private String afsApplyTime;

	/**
	 * 订单号
	 */
	private Long orderId;

	/**
	 * 商品编号
	 */
	private Long wareId;

	/**
	 * 商品名称
	 */
	private String wareName;

	/**
	 * 服务单环节： 申请阶段(10),审核不通过(20),客服审核(21),商家审核(22),京东收货(31),商家收货(32)
	 * 京东处理(33),商家处理(34), 用户确认(40),完成(50), 取消(60);
	 */
	private Integer afsServiceStep;

	/**
	 * 服务单环节名称：申请阶段,客服审核,商家审核,京东收货,商家收货, 京东处理,商家处理, 用户确认,完成, 取消;
	 */
	private String afsServiceStepName;

	/**
	 * 是否可取消: 0代表否，1代表是
	 */
	private Integer cancel;

	public Integer getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(Integer afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public Integer getCustomerExpect() {
		return customerExpect;
	}

	public void setCustomerExpect(Integer customerExpect) {
		this.customerExpect = customerExpect;
	}

	public String getCustomerExpectName() {
		return customerExpectName;
	}

	public void setCustomerExpectName(String customerExpectName) {
		this.customerExpectName = customerExpectName;
	}

	public String getAfsApplyTime() {
		return afsApplyTime;
	}

	public void setAfsApplyTime(String afsApplyTime) {
		this.afsApplyTime = afsApplyTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Integer getAfsServiceStep() {
		return afsServiceStep;
	}

	public void setAfsServiceStep(Integer afsServiceStep) {
		this.afsServiceStep = afsServiceStep;
	}

	public String getAfsServiceStepName() {
		return afsServiceStepName;
	}

	public void setAfsServiceStepName(String afsServiceStepName) {
		this.afsServiceStepName = afsServiceStepName;
	}

	public Integer getCancel() {
		return cancel;
	}

	public void setCancel(Integer cancel) {
		this.cancel = cancel;
	}
}
