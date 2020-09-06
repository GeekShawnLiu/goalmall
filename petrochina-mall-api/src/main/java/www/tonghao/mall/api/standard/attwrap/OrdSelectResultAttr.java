package www.tonghao.mall.api.standard.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class OrdSelectResultAttr {
	private String order_id;
	private BigDecimal total_price;
	private int state; //0 是新建  1是妥投完成  -1是拒收 4是退换货中
	private int submit_state;	//状态 0 未确认  1 已确认
	private int deliver_state;	//发货状态  0 未发货  1已发货
	private List<OrdSelectSkus> skus;
	private List<OrdSelectSkus> return_skus;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public BigDecimal getTotal_price() {
		return total_price;
	}
	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public List<OrdSelectSkus> getSkus() {
		return skus;
	}
	public void setSkus(List<OrdSelectSkus> skus) {
		this.skus = skus;
	}
	public List<OrdSelectSkus> getReturn_skus() {
		return return_skus;
	}
	public void setReturn_skus(List<OrdSelectSkus> return_skus) {
		this.return_skus = return_skus;
	}
	public int getSubmit_state() {
		return submit_state;
	}
	public void setSubmit_state(int submit_state) {
		this.submit_state = submit_state;
	}
	public int getDeliver_state() {
		return deliver_state;
	}
	public void setDeliver_state(int deliver_state) {
		this.deliver_state = deliver_state;
	}
	
}
