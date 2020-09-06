package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;

/**
 * 
 * Description: 退款明细
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceFinanceDetailInfoDTO {

	/**
	 * 退款方式
	 */
	private Integer refundWay;
	
	/**
	 * 退款方式名称
	 */
	private String refundWayName;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 状态名称
	 */
	private String statusName;
	
	/**
	 * 退款金额
	 */
	private BigDecimal refundPrice;
	
	/**
	 * 商品名称
	 */
	private String wareName;
	
	/**
	 * 商品编号
	 */
	private Integer wareId;

	public Integer getRefundWay() {
		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}

	public String getRefundWayName() {
		return refundWayName;
	}

	public void setRefundWayName(String refundWayName) {
		this.refundWayName = refundWayName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(BigDecimal refundPrice) {
		this.refundPrice = refundPrice;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Integer getWareId() {
		return wareId;
	}

	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}
}
