package www.tonghao.mall.warpper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import www.tonghao.mall.entity.MallProducts;

/**
 * 相似商品
 * @author developer001
 *
 */
@ApiModel(value="相似商品")
public class SimilarProduct implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="相似配置商品列表")
	private List<MallProducts> similarConfigureProducts;
	
	@ApiModelProperty(value="相似价格商品列表")
	private List<MallProducts> similarPriceProducts;
	
	
	public List<MallProducts> getSimilarConfigureProducts() {
		return similarConfigureProducts;
	}
	public void setSimilarConfigureProducts(List<MallProducts> similarConfigureProducts) {
		this.similarConfigureProducts = similarConfigureProducts;
	}
	public List<MallProducts> getSimilarPriceProducts() {
		return similarPriceProducts;
	}
	public void setSimilarPriceProducts(List<MallProducts> similarPriceProducts) {
		this.similarPriceProducts = similarPriceProducts;
	}
	
	@ApiModelProperty(value="是否相似配置和价格的商品都为空")
	public boolean isEmpty() {
		return (CollectionUtils.isEmpty(getSimilarConfigureProducts())&&CollectionUtils.isEmpty(getSimilarPriceProducts()));
	}
	
	@ApiModelProperty(value="是否相似配置的商品为空")
	public boolean isSimilarConfigureEmpty() {
		return CollectionUtils.isEmpty(getSimilarConfigureProducts());
	}
	
	@ApiModelProperty(value="是否相似价格的商品为空")
	public boolean isSimilarPriceEmpty() {
		return CollectionUtils.isEmpty(getSimilarPriceProducts());
	}
	
}
