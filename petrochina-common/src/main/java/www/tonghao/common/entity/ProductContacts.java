package www.tonghao.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * 产品库存
 * @author developer001
 *
 */
@ApiModel(value="产品联系人")
public class ProductContacts implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductContacts(){}
	
	@ApiModelProperty(name = "类型 1：厂商；2：代理商")
	private int type;
	
	@ApiModelProperty(name = "代理商名称")
	private String agentName;
	
	@ApiModelProperty(name = "联系人姓名")
	private String name;
	
	@ApiModelProperty(name = "联系方式")
	private String mobile;//手机号
	
	private String phone;//电话
	
	private String email;//邮箱
	
	private String fax;//传真
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
}