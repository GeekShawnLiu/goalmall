package www.tonghao.common.warpper;


import java.io.Serializable;
/**
 * 配送地址
 * @author yggc
 *
 */
public class DeliveryVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	/**
	 * 支持配送图标 1办公室 2楼下 3单位
	 */
	private Integer icon;
	
	public DeliveryVo(){}
	
	public DeliveryVo(Long id,String name){
		this.id = id;
		this.name = name;
	}
	
	public DeliveryVo(Long id,String name,Integer icon){
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