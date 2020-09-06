package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;

/**  
* <p>Title: InvoiceAttr</p>  
* <p>Description: 发票信息（BizInvoiceActualBillICRespVo）</p>  
* @author YML  
* @date 2018年12月19日  
*/ 
public class InvoiceAttr {
	
	private String ivcCode;//发票代码
	
	private String ivcNo;//发票号
	
	private Integer ivcType;//发票类型
	
	private String ivcContentType;//开票内容
	
	private String ivcContentName;//开票内容名称
	
	private String ivcTitle;//发票抬头
	
	private Integer iflag;//发票类别（1.正数；2.负数）
	
	private String businessId;//订单号
	
	private String invoiceTime;//开票日期
	
	private BigDecimal totalPrice;//开票金额,订单在本发票中的总金额
	
	private BigDecimal totalTaxPrice;//税额
	
	private String blueIsn;//红票对应蓝票序列号
	
	private String isn;//发票ISN
	
	private String remark;//备注
	
	private BigDecimal taxRate;//税率
	
	private String fileUrl;//电子票下载地址
	
	private Integer yn;//作废标识(1,有效；0.作废)

	public String getIvcCode() {
		return ivcCode;
	}

	public void setIvcCode(String ivcCode) {
		this.ivcCode = ivcCode;
	}

	public String getIvcNo() {
		return ivcNo;
	}

	public void setIvcNo(String ivcNo) {
		this.ivcNo = ivcNo;
	}

	public Integer getIvcType() {
		return ivcType;
	}

	public void setIvcType(Integer ivcType) {
		this.ivcType = ivcType;
	}

	public String getIvcContentType() {
		return ivcContentType;
	}

	public void setIvcContentType(String ivcContentType) {
		this.ivcContentType = ivcContentType;
	}

	public String getIvcContentName() {
		return ivcContentName;
	}

	public void setIvcContentName(String ivcContentName) {
		this.ivcContentName = ivcContentName;
	}

	public String getIvcTitle() {
		return ivcTitle;
	}

	public void setIvcTitle(String ivcTitle) {
		this.ivcTitle = ivcTitle;
	}

	public Integer getIflag() {
		return iflag;
	}

	public void setIflag(Integer iflag) {
		this.iflag = iflag;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getTotalTaxPrice() {
		return totalTaxPrice;
	}

	public void setTotalTaxPrice(BigDecimal totalTaxPrice) {
		this.totalTaxPrice = totalTaxPrice;
	}

	public String getBlueIsn() {
		return blueIsn;
	}

	public void setBlueIsn(String blueIsn) {
		this.blueIsn = blueIsn;
	}

	public String getIsn() {
		return isn;
	}

	public void setIsn(String isn) {
		this.isn = isn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
}
