package www.tonghao.mall.api.jd.entity;

import java.math.BigDecimal;
import java.util.Map;

public class ExtContent {
	/**
	 * 是否由京东代C客户收款：1：是；0：否
     *由京东代收货款
	 */
   private int paymentType;
   
   /**
    * 订单商品在C客户系统的单价:
	*如："{"107810":"50.30","181818":"22.30"}"
	*107810：京东SkuId---50.30：商品在C客户价格
	*181818：京东SkuId---22.30：商品在C客户价格
    */
   private Map<String, BigDecimal> skus;
   
   private BigDecimal orderPrice;

public int getPaymentType() {
	return paymentType;
}

public void setPaymentType(int paymentType) {
	this.paymentType = paymentType;
}

public Map<String, BigDecimal> getSkus() {
	return skus;
}

public void setSkus(Map<String, BigDecimal> skus) {
	this.skus = skus;
}

public BigDecimal getOrderPrice() {
	return orderPrice;
}

public void setOrderPrice(BigDecimal orderPrice) {
	this.orderPrice = orderPrice;
}
   
   
}
