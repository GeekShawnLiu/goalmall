package www.tonghao.common.warpper;

import java.io.Serializable;
/**
 * 支付方式
 * @author yggc
 *
 */
public class PayWayVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	/**
	 * 支持付款图标  1货到付款 2账期支付
	 */
	private Integer icon;
	
	public PayWayVo(){}
	
	public PayWayVo(Long id,String name){
		this.id = id;
		this.name = name;
	}
	
	public PayWayVo(Long id,String name,Integer icon){
		this.id = id;
		this.name = name;
		this.icon = icon;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIcon() {
		return icon;
	}
	public void setIcon(Integer icon) {
		this.icon = icon;
	}
}
