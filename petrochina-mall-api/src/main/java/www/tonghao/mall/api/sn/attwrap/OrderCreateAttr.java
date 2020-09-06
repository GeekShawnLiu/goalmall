package www.tonghao.mall.api.sn.attwrap;

import java.math.BigDecimal;
import java.util.List;

import com.suning.api.entity.govbus.MixpayorderAddResponse.Skus;

public class OrderCreateAttr {
	private String orderId;
	private List<Skus> skus;
	//商品金额
	private BigDecimal amount;
	//运费
	private BigDecimal freight;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<Skus> getSkus() {
		return skus;
	}
	public void setSkus(List<Skus> skus) {
		this.skus = skus;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	
}
