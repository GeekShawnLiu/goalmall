package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: 补单信息
 * 
 * @version 2019年6月24日
 * @since JDK1.8
 */
@Table(name="supplement_order")
public class SupplementOrder extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="订单号")
    @Column(name = "sn")
	private String sn;
	
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    @ApiModelProperty(value="记录当前操作人id")
    @Column(name = "user_id")
    private Long userId;
    
    @ApiModelProperty(value="删除标识 默认0：未删除，1：删除")
    @Column(name = "is_delete")
    private Integer isDelete;
    
    @ApiModelProperty(value="用户名")
    @Column(name = "user_name")
    private String userName;
    
	@ApiModelProperty(value="收货人")
    @Column(name = "consignee_name")
    private String consigneeName;
	
    @ApiModelProperty(value="收货地址")
    @Column(name = "consignee_addr")
    private String consigneeAddr;
    
    @ApiModelProperty(value="电话")
    @Column(name = "mobile")
    private String mobile;
    
    @ApiModelProperty(value="快递名称")
    @Column(name = "courier_name")
    private String courierName;
    
    @ApiModelProperty(value="快递编号")
    @Column(name = "courier_sn")
    private String courierSn;
    
    @ApiModelProperty(value="电商订单编号")
    @Column(name = "electricity_order_sn")
    private String electricityOrderSn;
    
    @ApiModelProperty(value="供应商id")
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @ApiModelProperty(value="商品总价")
    @Column(name = "price_total")
    private BigDecimal priceTotal;
    
    /**
     * 补单商品明细
     */
    @Transient
    private List<SupplementOrderItem> supplementOrderItemList;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierSn() {
		return courierSn;
	}

	public void setCourierSn(String courierSn) {
		this.courierSn = courierSn;
	}

	public String getElectricityOrderSn() {
		return electricityOrderSn;
	}

	public void setElectricityOrderSn(String electricityOrderSn) {
		this.electricityOrderSn = electricityOrderSn;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<SupplementOrderItem> getSupplementOrderItemList() {
		return supplementOrderItemList;
	}

	public void setSupplementOrderItemList(
			List<SupplementOrderItem> supplementOrderItemList) {
		this.supplementOrderItemList = supplementOrderItemList;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}
}
