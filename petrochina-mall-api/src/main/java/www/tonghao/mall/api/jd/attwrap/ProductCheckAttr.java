package www.tonghao.mall.api.jd.attwrap;

public class ProductCheckAttr {

	private String skuId;
	
	private String name;
	
	//是否可售，1：是，0：否
	private int saleState;
	
	//是否可开增票，1：支持，0：不支持
	private int isCanVAT;
	
	//是否支持7天无理由退货，1：是，0：否
	private int is7ToReturn;
	//无理由退货类型：0,1,2,3,4,5,6,7,8
	//0、3：不支持7天无理由退货；
	//1、5、8或null：支持7天无理由退货；
	//2：支持90天无理由退货；
	//4、7：支持15天无理由退货；
	//6：支持30天无理由退货；
	private int noReasonToReturn;
	/**
	 * 无理由退货文案类型：
		null：文案空；
		0：文案空；
		1：支持7天无理由退货；
		2：支持7天无理由退货（拆封后不支持）；
		3：支持7天无理由退货（激活后不支持）
		4：支持7天无理由退货（使用后不支持）；
		5：支持7天无理由退货（安装后不支持）；
		12：支持15天无理由退货；
		13：支持15天无理由退货（拆封后不支持）；
		14：支持15天无理由退货（激活后不支持）；
		15：支持15天无理由退货（使用后不支持）；
		16：支持15天无理由退货（安装后不支持）；
		22：支持30天无理由退货；
		23：支持30天无理由退货（安装后不支持）；
		24：支持30天无理由退货（拆封后不支持）；
		25：支持30天无理由退货（使用后不支持）；
		26：支持30天无理由退货（激活后不支持）；
		（提示客户取到其他枚举值，无效）
	 */
	
	private int thwa;
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSaleState() {
		return saleState;
	}
	public void setSaleState(int saleState) {
		this.saleState = saleState;
	}
	public int getIsCanVAT() {
		return isCanVAT;
	}
	public void setIsCanVAT(int isCanVAT) {
		this.isCanVAT = isCanVAT;
	}
	public int getIs7ToReturn() {
		return is7ToReturn;
	}
	public void setIs7ToReturn(int is7ToReturn) {
		this.is7ToReturn = is7ToReturn;
	}
	public int getNoReasonToReturn() {
		return noReasonToReturn;
	}
	public void setNoReasonToReturn(int noReasonToReturn) {
		this.noReasonToReturn = noReasonToReturn;
	}
	public int getThwa() {
		return thwa;
	}
	public void setThwa(int thwa) {
		this.thwa = thwa;
	}
	
	
}
