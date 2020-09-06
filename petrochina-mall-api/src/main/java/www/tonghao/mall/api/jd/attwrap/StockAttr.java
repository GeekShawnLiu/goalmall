package www.tonghao.mall.api.jd.attwrap;

public class StockAttr {
	
	/**
	 * 33 有货 现货-下单立即发货 
	*39有货 在途-正在内部配货，预计 2~6 天到达本仓库 
	*40有货 可配货-下单后从有货仓库配货 
	*36 预订 
	*34 无货 
	 */
  private int state;
  
  private String area;
  
  /**
   * 库存状态描述  
   * 有货 现货；有
   *货 在途
   */
  private String desc;
  
  private String sku;

	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
  
  
  
}
