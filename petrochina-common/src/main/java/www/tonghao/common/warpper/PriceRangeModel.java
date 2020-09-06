package www.tonghao.common.warpper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * 价格区间模型
 * @author developer001
 *
 */
@ApiModel(value="价格区间模型")
public class PriceRangeModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PriceRangeModel(){}
	
	public PriceRangeModel(int begin,int end){
		this.begin = begin;
		this.end = end;
	}
	
	/**
	 * 开始价格
	 */
	@ApiModelProperty(value="开始价格")
	private int begin;
	
	/**
	 * 结束价格 为0标识无限大
	 */
	@ApiModelProperty(value="结束价格 为0标识无限大")
	private int end;
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
}