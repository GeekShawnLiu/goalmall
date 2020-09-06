package www.tonghao.mall.api.jd.attwrap;

public class CheckAreaAttr {
  private String skuId;
  
  private boolean isAreaRestrict;//true 代表区域受限 false 区域不受限

	public String getSkuId() {
		return skuId;
	}
	
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public boolean isAreaRestrict() {
		return isAreaRestrict;
	}
	
	public void setAreaRestrict(boolean isAreaRestrict) {
		this.isAreaRestrict = isAreaRestrict;
	}
  
  
  
}
