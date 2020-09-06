package www.tonghao.mall.api.jd.entity;

/**
 * 
 * Description: 售后
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AfsApply {

	/**
	 * 京东订单号 
	 * 必填
	 */
	private String jdOrderId;

	/**
	 * 售后类型：退货(10)、换货(20)、维修(30)
	 * 必填
	 */
	private String customerExpect;

	/**
	 * 产品问题描述，最多1000字符
	 * 非必填
	 */
	private String questionDesc;

	/**
	 * 是否需要检测报告
	 * 必填
	 */
	private Boolean isNeedDetectionReport;

	/**
	 * 问题描述图片.最多2000字符 支持多张图片，用逗号分隔（英文逗号）
	 * 非必填
	 */
	private String questionPic;

	/**
	 * 是否有包装
	 * 必填
	 */
	private Boolean isHasPackage;

	/**
	 * 包装描述：0 无包装 10 包装完整 20 包装破损
	 * 必填
	 */
	private String packageDesc;

	/**
	 * 客户信息实体
	 * 必填
	 */
	private AfterSaleCustomerDto asCustomerDto;

	/**
	 * 取件信息实体
	 * 必填
	 */
	private AfterSalePickwareDto asPickwareDto;

	/**
	 * 返件信息实体
	 * 必填
	 */
	private AfterSaleReturnwareDto asReturnwareDto;

	/**
	 * 申请单明细
	 * 必填
	 */
	private AfterSaleDetailDto asDetailDto;

	public String getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
	}

	public String getCustomerExpect() {
		return customerExpect;
	}

	public void setCustomerExpect(String customerExpect) {
		this.customerExpect = customerExpect;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public Boolean getIsNeedDetectionReport() {
		return isNeedDetectionReport;
	}

	public void setIsNeedDetectionReport(Boolean isNeedDetectionReport) {
		this.isNeedDetectionReport = isNeedDetectionReport;
	}

	public String getQuestionPic() {
		return questionPic;
	}

	public void setQuestionPic(String questionPic) {
		this.questionPic = questionPic;
	}

	public Boolean getIsHasPackage() {
		return isHasPackage;
	}

	public void setIsHasPackage(Boolean isHasPackage) {
		this.isHasPackage = isHasPackage;
	}

	public String getPackageDesc() {
		return packageDesc;
	}

	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}

	public AfterSaleCustomerDto getAsCustomerDto() {
		return asCustomerDto;
	}

	public void setAsCustomerDto(AfterSaleCustomerDto asCustomerDto) {
		this.asCustomerDto = asCustomerDto;
	}

	public AfterSalePickwareDto getAsPickwareDto() {
		return asPickwareDto;
	}

	public void setAsPickwareDto(AfterSalePickwareDto asPickwareDto) {
		this.asPickwareDto = asPickwareDto;
	}

	public AfterSaleReturnwareDto getAsReturnwareDto() {
		return asReturnwareDto;
	}

	public void setAsReturnwareDto(AfterSaleReturnwareDto asReturnwareDto) {
		this.asReturnwareDto = asReturnwareDto;
	}

	public AfterSaleDetailDto getAsDetailDto() {
		return asDetailDto;
	}

	public void setAsDetailDto(AfterSaleDetailDto asDetailDto) {
		this.asDetailDto = asDetailDto;
	}

	public AfsApply() {
		super();
	}

	public AfsApply(String jdOrderId, String customerExpect,
			String questionDesc, Boolean isNeedDetectionReport,
			String questionPic, Boolean isHasPackage, String packageDesc,
			AfterSaleCustomerDto asCustomerDto,
			AfterSalePickwareDto asPickwareDto,
			AfterSaleReturnwareDto asReturnwareDto,
			AfterSaleDetailDto asDetailDto) {
		super();
		this.jdOrderId = jdOrderId;
		this.customerExpect = customerExpect;
		this.questionDesc = questionDesc;
		this.isNeedDetectionReport = isNeedDetectionReport;
		this.questionPic = questionPic;
		this.isHasPackage = isHasPackage;
		this.packageDesc = packageDesc;
		this.asCustomerDto = asCustomerDto;
		this.asPickwareDto = asPickwareDto;
		this.asReturnwareDto = asReturnwareDto;
		this.asDetailDto = asDetailDto;
	}
}
