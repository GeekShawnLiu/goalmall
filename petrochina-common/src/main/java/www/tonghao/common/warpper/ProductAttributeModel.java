package www.tonghao.common.warpper;


import io.swagger.annotations.ApiModel;

import java.io.Serializable;
/**
 * 商品属性模型
 * @author developer001
 *
 */
@ApiModel(value="商品属性模型")
public class ProductAttributeModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductAttributeModel(){}
	
	private Long productId;
	
	/**
	 * 聚合商家数量
	 */
	private int aggregationNum;
	
	/**
	 * 是否节能
	 */
	private boolean isEnergyConservation;
	
	/**
	 * 是否环保
	 */
	private boolean isEnvironmentalProtection;
	
	/**
	 * 是否集采
	 */
	private boolean isCollect;
	
	private PayWayVo[] payWayArray;
	
	private DeliveryVo[] deliveryArray;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getAggregationNum() {
		return aggregationNum;
	}
	public void setAggregationNum(int aggregationNum) {
		this.aggregationNum = aggregationNum;
	}
	public boolean isEnergyConservation() {
		return isEnergyConservation;
	}
	public void setEnergyConservation(boolean isEnergyConservation) {
		this.isEnergyConservation = isEnergyConservation;
	}
	public boolean isEnvironmentalProtection() {
		return isEnvironmentalProtection;
	}
	public void setEnvironmentalProtection(boolean isEnvironmentalProtection) {
		this.isEnvironmentalProtection = isEnvironmentalProtection;
	}
	public boolean isCollect() {
		return isCollect;
	}
	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}
	public PayWayVo[] getPayWayArray() {
		return payWayArray;
	}
	public void setPayWayArray(PayWayVo[] payWayArray) {
		this.payWayArray = payWayArray;
	}
	public DeliveryVo[] getDeliveryArray() {
		return deliveryArray;
	}
	public void setDeliveryArray(DeliveryVo[] deliveryArray) {
		this.deliveryArray = deliveryArray;
	}
}