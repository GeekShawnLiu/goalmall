package www.tonghao.common.warpper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * 产品库存
 * @author developer001
 *
 */
@ApiModel(value="产品库存")
public class ProductStock implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductStock(){}
	
	/**
	 * 库存状态
	 */
	@ApiModelProperty(name = "库存状态 0：无货；1：有货")
	private int state; //0：无货；1：有货
	
	private int num;//库存数量
	
	@ApiModelProperty(name = "库存描述")
	private String msg;//库存描述
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}