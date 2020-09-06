package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "order_split_item")
public class OrderSplitItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 子订单id
	 */
	@Column(name = "child_order_id")
	private Long childOrderId;

	/**
	 * 父订单编号
	 */
	@Column(name = "order_sn")
	private String orderSn;

	/**
	 * 父订单电商订单号
	 */
	@Column(name = "order_emall_sn")
	private String orderEmallSn;

	/**
	 * 子订单单电商订单号
	 */
	@Column(name = "child_emall_sn")
	private String childEmallSn;

	/**
	 * 商品sku
	 */
	@Column(name = "sku")
	private String sku;

	/**
	 * 供应商id
	 */
	@Column(name = "supplier_id")
	private Long supplierId;

	/**
	 * 供应商名称
	 */
	@Column(name = "supplier_name")
	private String supplierName;

	/**
	 * 商品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 数量
	 */
	@Column(name = "num")
	private Integer num;

	/**
	 * 是否删除 0未删除，1删除
	 */
	@Column(name = "is_delete")
	private Integer isDelete;

	/**
	 * 支付方式 1积分支付 2个人支付 3混合支付
	 */
	@Transient
	private Long payId;

	public Long getChildOrderId() {
		return childOrderId;
	}

	public void setChildOrderId(Long childOrderId) {
		this.childOrderId = childOrderId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getOrderEmallSn() {
		return orderEmallSn;
	}

	public void setOrderEmallSn(String orderEmallSn) {
		this.orderEmallSn = orderEmallSn;
	}

	public String getChildEmallSn() {
		return childEmallSn;
	}

	public void setChildEmallSn(String childEmallSn) {
		this.childEmallSn = childEmallSn;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}
}
