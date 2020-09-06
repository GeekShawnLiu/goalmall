package www.tonghao.service.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name="order_pay_price")
public class OrderPayPrice extends BaseEntity {

	
	/**
	 * 支付code
	 */
	@Column(name = "code")
	private String code;
	
	/**
	 * 订单号 多个用_隔开
	 */
	@Column(name = "orders_code")
	private String ordersCode;
	
	@Column(name = "create_at")
	private String createAt;
	
	/**
	 * 支付金额
	 */
	@Column(name = "price")
	private BigDecimal price;
	
	/**
	 * 状态  0 预下单  1已经支付
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 父订单id
	 */
	@Column(name = "order_master_id")
	private Long orderMasterId;
	
	/**
	 * 预下订单请求返回值
	 */
	@Column(name = "result_json")
	private String resultJson;
	
	/**
	 * 预下订单请求值
	 */
	@Column(name = "req_json")
	private String reqJson;
	
	/**
	 * 畅捷平台支付单号
	 */
	@Column(name = "cj_code")
	private String cjCode;
	
	/**
	 * 支付成功返回值
	 */
	@Column(name = "pay_call_back_json")
	private String payCallBackJson;
	
	/**
	 * 1 电脑支付，2移动端支付
	 */
	@Column(name = "pay_type")
	private Integer payType;
	
	/**
	 * 支付过期时间
	 */
	@Column(name = "expire_date")
	private String expireDate;
	
	/**
	 * 1 微信，2支付宝
	 */
	@Column(name = "pay_code")
	private Integer payCode;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrdersCode() {
		return ordersCode;
	}

	public void setOrdersCode(String ordersCode) {
		this.ordersCode = ordersCode;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getOrderMasterId() {
		return orderMasterId;
	}

	public void setOrderMasterId(Long orderMasterId) {
		this.orderMasterId = orderMasterId;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}

	public String getReqJson() {
		return reqJson;
	}

	public void setReqJson(String reqJson) {
		this.reqJson = reqJson;
	}

	public String getCjCode() {
		return cjCode;
	}

	public void setCjCode(String cjCode) {
		this.cjCode = cjCode;
	}

	public String getPayCallBackJson() {
		return payCallBackJson;
	}

	public void setPayCallBackJson(String payCallBackJson) {
		this.payCallBackJson = payCallBackJson;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getPayCode() {
		return payCode;
	}

	public void setPayCode(Integer payCode) {
		this.payCode = payCode;
	} 
	
	
	
	
}
