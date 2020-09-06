package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 订单表单
 * @author developer001
 *
 */
@ApiModel(value="订单表单")
public class OrderForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 收货地址ID
	 */
	@ApiModelProperty(value="收货地址ID")
	private Long receiverAddressId; 
	
	/**
	 * 发票ID
	 */
	@ApiModelProperty(value="发票ID")
	private Long invoiceId;  
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark; 
	
	/**
	 * 用户
	 */
	@JsonIgnore
	private Users user;
	
	/**
	 * 地区
	 */
	@JsonIgnore
	private Areas area;
	
	/**
	 * 收货地址
	 */
	@JsonIgnore
	private ReceiverAddresses receiverAddress;
	
	/**
	 * 发票
	 */
	@JsonIgnore
	private Invoices invoice;
	
	
	/**
	 * 供应商支付方式 (key:供应商ID value:支付方式ID)
	 */
	/*@ApiModelProperty(value="供应商支付方式")
	private Map<Long,Long> supplierPayWayMap;*/
	
	@ApiModelProperty(value="支付方式ID  1积分支付  2个人支付   3混合支付")
	private Long paywayId;
	
	@ApiModelProperty(value="下单来源  1:pc  2:h5  3:小程序")
	private String orderSource;
	
	public Long getPaywayId() {
		return paywayId;
	}

	public void setPaywayId(Long paywayId) {
		this.paywayId = paywayId;
	}

	public Long getReceiverAddressId() {
		return receiverAddressId;
	}

	public void setReceiverAddressId(Long receiverAddressId) {
		this.receiverAddressId = receiverAddressId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Areas getArea() {
		return area;
	}

	public void setArea(Areas area) {
		this.area = area;
	}

	public ReceiverAddresses getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(ReceiverAddresses receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public Invoices getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoices invoice) {
		this.invoice = invoice;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
}
