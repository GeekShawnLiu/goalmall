package www.tonghao.common.warpper;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 价格趋势
 * @author developer001
 *
 */
@ApiModel(value="价格趋势")
public class PriceTrend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PriceTrend(){}
	
	public PriceTrend(String month,BigDecimal price){
		this.month = month;
		this.price = price;
	}
	
	/**
	 * 开始价格
	 */
	@ApiModelProperty(value="月份")
	private String month;
	
	/**
	 * 结束价格 为0标识无限大
	 */
	@ApiModelProperty(value="价格")
	private BigDecimal price;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
