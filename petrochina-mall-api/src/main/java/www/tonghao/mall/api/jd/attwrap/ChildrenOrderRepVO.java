package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class ChildrenOrderRepVO {

	private String jdOrderId;
	//物流状态 0 是新建  1 是妥投   2 是拒收 
	private int state;
	//订单类型   1 是父订单   2 是子订单 
	private int type; 
	//订单税费
	private BigDecimal orderTaxPrice;
	
	//订单裸价 
	private BigDecimal orderNakedPrice;
	
	//订单价格 
	private BigDecimal orderPrice;
	 /**
     * 运费（合同有运费配置才会返回，否则不会返回该字段） 
     */
    private BigDecimal freight;
	
	private List<OrderSkuVO> sku;
	
	//父订单号 
	private String pOrder;
	
	//订单状态  0 为取消订单  1 为有效 
	private int orderState;
	
	/**
	 * 京东状态，查询参数中包含 queryExts=jdOrderState 
	*1.新单 
	*2.等待支付 
	**3.等待支付确认 
	*4.延迟付款确认 
	*5.订单暂停
	*6.店长最终审核 
	*7.等待打印 
	*8.等待出库 
	*9.等待打包 
	*10.等待发货 
	*11.自提途中 
	*12.上门提货 
	*13.自提退货 
	*14.确认自提 
	*16.等待确认收货 
	*17.配送退货 
	*18.货到付款确认 
	*19.已完成 
	*21.收款确认 (货到付款订单，配送员配送完成，拿到款项之后等待交给财务对账时候的订单状态，等待订单完成即可)
	*22.锁定 
	*29.等待三方出库 
	*30.等待三方发货 
	*31.等待三方发货完成 
*xx.xx(未来不定时增加类别)
	 */
	private int jdOrderState;
	
	//0 为未确认下单订单   1 为确认下单订单 
	private int submitState;
	

	public String getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BigDecimal getOrderTaxPrice() {
		return orderTaxPrice;
	}

	public void setOrderTaxPrice(BigDecimal orderTaxPrice) {
		this.orderTaxPrice = orderTaxPrice;
	}

	public BigDecimal getOrderNakedPrice() {
		return orderNakedPrice;
	}

	public void setOrderNakedPrice(BigDecimal orderNakedPrice) {
		this.orderNakedPrice = orderNakedPrice;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public List<OrderSkuVO> getSku() {
		return sku;
	}

	public void setSku(List<OrderSkuVO> sku) {
		this.sku = sku;
	}

	public String getpOrder() {
		return pOrder;
	}

	public void setpOrder(String pOrder) {
		this.pOrder = pOrder;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public int getJdOrderState() {
		return jdOrderState;
	}

	public void setJdOrderState(int jdOrderState) {
		this.jdOrderState = jdOrderState;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public int getSubmitState() {
		return submitState;
	}

	public void setSubmitState(int submitState) {
		this.submitState = submitState;
	}
	
	
	
	
}
