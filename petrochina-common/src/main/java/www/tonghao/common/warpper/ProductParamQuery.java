package www.tonghao.common.warpper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Set;
/**
 * 商品参数查询
 * @author developer001
 *
 */
@ApiModel(value="商品参数查询")
public class ProductParamQuery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductParamQuery(){}
	
	@ApiModelProperty(value="产品参数ID")
	private Long id;
	
	/*@ApiModelProperty(value="参数项ids")
	private Set<Long> paramItemIds;*/
	
	@ApiModelProperty(value="标准参数项的ids")
	private Set<String> standValueIds;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/*
	public Set<Long> getParamItemIds() {
		return paramItemIds;
	}
	public void setParamItemIds(Set<Long> paramItemIds) {
		this.paramItemIds = paramItemIds;
	}
	 */
	public Set<String> getStandValueIds() {
		return standValueIds;
	}
	public void setStandValueIds(Set<String> standValueIds) {
		this.standValueIds = standValueIds;
	}
}
