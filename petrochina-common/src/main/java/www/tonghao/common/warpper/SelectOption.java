package www.tonghao.common.warpper;

import java.io.Serializable;
/**
 * 下拉款项模型
 * @author developer001
 *
 */
public class SelectOption implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;
	private String label;
	
	public SelectOption(){}
	
	public SelectOption(Object value,String label){
		this.value = value;
		this.label = label;	
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}