package www.tonghao.mall.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品比较行
 * @author developer001
 *
 */
@ApiModel(value="商品比较行")
public class ProductCompareRow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("行名称")
	private String name;
	
	@ApiModelProperty("列集合")
	private List<ProductCompareCell> cells;
	
	@ApiModelProperty("是否不同")
	private Boolean isDiff;
	
	public ProductCompareRow(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductCompareCell> getCells() {
		return cells;
	}

	public void setCells(List<ProductCompareCell> cells) {
		this.cells = cells;
	}

	public Boolean isDiff() {
		if(this.isDiff==null&&getCells()!=null){
			String uniqueValue = null;
			for(ProductCompareCell pc:getCells()){
				if(uniqueValue==null){
					uniqueValue = pc.getValue()!=null?pc.getValue():"";
				}
				
				if(!pc.getValue().equalsIgnoreCase(uniqueValue)){
					return true;
				}
			}
			return false;
		}
		return isDiff;
	}

	public void setDiff(Boolean isDiff) {
		this.isDiff = isDiff;
	}
	
}
