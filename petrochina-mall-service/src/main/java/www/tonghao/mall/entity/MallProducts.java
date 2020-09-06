package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import www.tonghao.service.common.entity.Products;

public class MallProducts extends Products{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="官网商品链接")
	@Transient
	private String emallUrl;
	
	@ApiModelProperty(value="节约金额")
	public BigDecimal getReduceAmount(){
		if(getMarketPrice()!=null&&getPrice()!=null){
			return getMarketPrice().subtract(getPrice());
		}	
		return null;
	}
	
	/**
	 * 是否允许直购
	 * @return
	 */
	public boolean isAllowPurchase(){
		if(StringUtils.isNotEmpty(getSku())){
			return true;
		}
		return false;
	}

	public String getEmallUrl() {
		return emallUrl;
	}

	public void setEmallUrl(String emallUrl) {
		this.emallUrl = emallUrl;
	}
}
