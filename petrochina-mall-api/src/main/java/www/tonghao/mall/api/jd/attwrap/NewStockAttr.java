package www.tonghao.mall.api.jd.attwrap;

public class NewStockAttr {
    private String skuId;
    
    //配送地址 id 
    private String areaId;
    
    //库存状态编号 33,39,40,36,34 
    private int stockStateId;
    
    /**
     * 库存状态描述 
	*33 有货 现货-下单立即发货 
	*39有货 在途-正在内部配货，预计 2~6 天到达本仓库 
	*40有货 可配货-下单后从有货仓库配货 
	*36 预订 
	*34 无货 
	*99 无货开预定，此时stockStateDesc返回的数值代表预计到货天数，并且该功能需要依赖合同上有无货开预定权限的用户，到货周期略长，谨慎采用该功能。
     */
    private String stockStateDesc;
    
    /**
     * 当库存小于 50 时展示真实库存数量(如果商品是 厂商直送商品，无论是否有货都返回-1)；
	*当库存大于等于 50 时小于100时，剩余数量 -1 未知；
	*当库存在100以上不允许查，系统都直接返回-1。
	*库存小于[1,50)时，可以返回是否有货以及数量；库存在[50,100)是可以返回是否有货，不能返回数量；库存在[100,+∞)时，不能返回是否有货，且不返回数量
     */
    private int remainNum;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public int getStockStateId() {
		return stockStateId;
	}

	public void setStockStateId(int stockStateId) {
		this.stockStateId = stockStateId;
	}

	public String getStockStateDesc() {
		return stockStateDesc;
	}

	public void setStockStateDesc(String stockStateDesc) {
		this.stockStateDesc = stockStateDesc;
	}

	public int getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}
    
    
}
