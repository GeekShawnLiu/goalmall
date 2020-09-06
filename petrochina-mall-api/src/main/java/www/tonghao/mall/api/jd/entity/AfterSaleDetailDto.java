package www.tonghao.mall.api.jd.entity;

/**
 * 
 * Description: 京东售后 申请单明细
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AfterSaleDetailDto {

	/**
	 * 商品编号
	 * 必填
	 */
	private String skuId;

	/**
	 * 商品申请数量
	 * 必填
	 */
	private Integer skuNum;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public AfterSaleDetailDto() {
		super();
	}

	public AfterSaleDetailDto(String skuId, Integer skuNum) {
		super();
		this.skuId = skuId;
		this.skuNum = skuNum;
	}
}
