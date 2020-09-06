package www.tonghao.service.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name="order_refund_item")
public class OrderRefundItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付类型  1 积分支付 2 个人支付3混合支付
	 */
	@Column(name = "pay_id")
	private Long payId;
	
	/**
	 * 退款积分
	 */
	@Column(name = "refund_integral_price")
	private BigDecimal refundIntegralPrice;
	
	/**
	 * 退款金额
	 */
	@Column(name = "refund_money")
	private BigDecimal refundMoney;
	
	/**
	 * 订单号
	 */
	@Column(name = "order_sn")
	private String orderSn;
	
	/**
	 * 订单总金额
	 */
	@Column(name = "order_total")
	private BigDecimal orderTotal;
	
	/**
	 * 类型 1售前，2 售后
	 */
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "create_at")
	private String createAt;
	
	@Column(name = "update_at")
	private String updateAt;
	
	/**
	 * 退款人id
	 */
	@Column(name = "refund_user_id")
	private Long refundUserId;
	
	/**
	 * 退款人名称
	 */
	@Column(name = "refund_user_name")
	private String refundUserName;
	
	/**
	 * 操作人id
	 */
	@Column(name = "operation_id")
	private Long operationId;
	
	/**
	 * 畅捷支付单号  此单号只有 混合支付和个人支付需要填写
	 */
	@Column(name = "cj_code")
	private String cjCode;
	
	/**
	 * 畅捷支付id  此单号只有 混合支付和个人支付需要填写
	 */
	@Column(name = "order_pay_price_id")
	private Long orderPayPriceId;
	
	/**
	 * 父订单id
	 */
	@Column(name = "order_master_id")
	private Long orderMasterId;
	
	
	@Transient
	private String orderSate;
	
	/**
	 * 0 未退款， 1退款处理中，2 退款成功 3 退款失败 ,4 拒绝退款
	 */
	@Column(name = "refund_status")
	private Integer refundStatus;
	
	/**
	 * 退款请求参数
	 */
	@Column(name = "refund_req_param")
	private String refundReqParam;
	
	/**
	 * 退款接收参数
	 */
	@Column(name = "refund_res_param")
	private String refundResParam;
	
	/**
	 * 退款消息参数
	 */
	@Column(name = "cj_json")
	private String cjJson;
	
	/**
	 * 退款流水号
	 */
	@Column(name = "refund_code")
	private String refundCode;
	
	@Transient
	private String supplierName;
	
	/**
	 * 售后记录id
	 */
	@Column(name = "afs_id")
	private Long afsId;
	
	/**
	 * 售后类型  1 jd   2其他
	 */
	@Column(name = "afs_type")
	private Integer afsType;
	
	/**
	 * 拒绝退款意见
	 */
	@Column(name = "advice")
	private String advice;
	
	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public BigDecimal getRefundIntegralPrice() {
		return refundIntegralPrice;
	}

	public void setRefundIntegralPrice(BigDecimal refundIntegralPrice) {
		this.refundIntegralPrice = refundIntegralPrice;
	}

	public BigDecimal getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public Long getRefundUserId() {
		return refundUserId;
	}

	public void setRefundUserId(Long refundUserId) {
		this.refundUserId = refundUserId;
	}

	public String getRefundUserName() {
		return refundUserName;
	}

	public void setRefundUserName(String refundUserName) {
		this.refundUserName = refundUserName;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public String getCjCode() {
		return cjCode;
	}

	public void setCjCode(String cjCode) {
		this.cjCode = cjCode;
	}

	public Long getOrderPayPriceId() {
		return orderPayPriceId;
	}

	public void setOrderPayPriceId(Long orderPayPriceId) {
		this.orderPayPriceId = orderPayPriceId;
	}

	public Long getOrderMasterId() {
		return orderMasterId;
	}

	public void setOrderMasterId(Long orderMasterId) {
		this.orderMasterId = orderMasterId;
	}

	public String getOrderSate() {
		return orderSate;
	}

	public void setOrderSate(String orderSate) {
		this.orderSate = orderSate;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundReqParam() {
		return refundReqParam;
	}

	public void setRefundReqParam(String refundReqParam) {
		this.refundReqParam = refundReqParam;
	}

	public String getRefundResParam() {
		return refundResParam;
	}

	public void setRefundResParam(String refundResParam) {
		this.refundResParam = refundResParam;
	}

	public String getCjJson() {
		return cjJson;
	}

	public void setCjJson(String cjJson) {
		this.cjJson = cjJson;
	}

	public String getRefundCode() {
		return refundCode;
	}

	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public Long getAfsId() {
		return afsId;
	}

	public void setAfsId(Long afsId) {
		this.afsId = afsId;
	}

	public Integer getAfsType() {
		return afsType;
	}

	public void setAfsType(Integer afsType) {
		this.afsType = afsType;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	
}
