package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class ParentOrderRepVO {
    private String jdOrderId;
    /**
     * 订单税费  
     */
    private BigDecimal orderTaxPrice;
    
    /**
     * 订单裸价
     */
    private BigDecimal orderNakedPrice;
    
    /**
     * 订单价格
     */
    private BigDecimal orderPrice;
    
    /**
     * 运费（合同有运费配置才会返回，否则不会返回该字段） 
     */
    private BigDecimal freight;
    
    private List<OrderSkuVO> sku;
    
    private Integer orderState;
    
    private Integer state;

	public String getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
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

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public List<OrderSkuVO> getSku() {
		return sku;
	}

	public void setSku(List<OrderSkuVO> sku) {
		this.sku = sku;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
    
}
