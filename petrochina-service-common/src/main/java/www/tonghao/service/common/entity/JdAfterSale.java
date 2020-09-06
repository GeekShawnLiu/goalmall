package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: jd售后信息表
 * 
 * @version 2019年7月17日
 * @since JDK1.8
 */
@Table(name = "jd_after_sale")
public class JdAfterSale extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 删除标识 0：未删除，1：删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 售后编号
     */
    @Column(name = "sn")
    private String sn;
    
    /**
     * 当前申请人id
     */
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "product_sku")
    private String productSku;
    
    @Column(name = "product_name")
    private String productName;

    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "order_item_id")
    private Long orderItemId;
    
    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    private String orderSn;

    /**
     * 电商订单编号
     */
    @Column(name = "order_emall_sn")
    private String orderEmallSn;

    /**
     * 售后类型：退货(10)、换货(20)、维修(30)
     */
    private Integer type;

    /**
     * 商品数量
     */
    @Column(name = "product_num")
    private Integer productNum;

    /**
     * 提交原因
     */
    private Integer reason;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 是否有包装 包装描述：0 无包装 10 包装完整 20 包装破损
     */
    @Column(name = "is_has_package")
    private Integer isHasPackage;

    /**
     * 申请凭据  是否需要检测报告
     */
    @Column(name = "application_credentials")
    private Integer applicationCredentials;

    /**
     * 取件方式：4上门取件； 7 客户送货；40客户发货
     */
    @Column(name = "pickware_type")
    private Integer pickwareType;

    /**
     * 取件开始时间
     */
    @Column(name = "pickware_start_time")
    private String pickwareStartTime;

    /**
     * 取件结束时间
     */
    @Column(name = "pickware_end_time")
    private String pickwareEndTime;

    /**
     * 取件地址  地址id
     */
    @Column(name = "pickware_address_id")
    private Long pickwareAddressId;

    /**
     * 取件地址名称
     */
    @Column(name = "pickware_address_name")
    private String pickwareAddressName;

    /**
     * 取件详细地址
     */
    @Column(name = "pickware_detailed_address")
    private String pickwareDetailedAddress;
    
    /**
     * 返件方式：自营配送(10),第三方配送(20);
     */
    @Column(name = "returnware_type")
    private Integer returnwareType;

    /**
     * 返件地址id
     */
    @Column(name = "returnware_address_id")
    private Long returnwareAddressId;

    /**
     * 返件地址名称
     */
    @Column(name = "returnware_address_name")
    private String returnwareAddressName;

    /**
     * 返件详细地址
     */
    @Column(name = "returnware_detailed_address")
    private String returnwareDetailedAddress;

    /**
     * 客户姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 客户手机号
     */
    @Column(name = "user_mobile")
    private String userMobile;
    
    /**
     * 状态  	1：创建	
     * 		2：审核不通过
     * 		3：审核取消
     * 		4：完成
     */
    @Column(name = "status")
    private Integer status;
    
    /**
     * 状态描述
     */
    @Column(name = "status_message")
    private String statusMessage;
    
    /**
     *  服务单号
     */
    @Column(name = "afs_service_id")
    private String afsServiceId;
    
    /**
     *  支付方式 1积分支付   2在线支付   3混合支付
     */
    @Column(name = "pay_id")
    private Long payId;
    
    /**
     * 退款
     */
    @Column(name = "return_price")
    private BigDecimal returnPrice;
    
    /**
     * 发运单状态  0不需要填写  1需要填写   2已经填写  
     */
    @Column(name = "waybill_status")
    private Integer waybillStatus;
    
    /**
     * 服务单是否允许操作   0不允许    1允许取消   2允许填写或者修改客户发货信息  3都允许
     */
    @Column(name = "allow_operations")
    private Integer allowOperations;
    
    /**
     * 售后地址（填写发运单使用）
     */
    @Column(name = "address")
    private String address;
    
    /**
     * 售后电话（填写发运单使用）
     */
    @Column(name = "tel")
    private String tel;
    
    /**
     * 售后联系人（填写发运单使用）
     */
    @Column(name = "link_man")
    private String linkMan;
    
    /**
     * 售后邮编（填写发运单使用）
     */
    @Column(name = "post_code")
    private String postCode;

    @ApiModelProperty(value="商品缩略图")
    @Transient
    private String productThumbnail;
    
    @ApiModelProperty(value="商品价格")
    @Transient
    private BigDecimal productPrice;
    
    @ApiModelProperty(value="售后类型  1京东  2其他")
    @Transient
    private Integer afsType;
    
    /**
     * 附件信息
     */
    @Transient
    private List<JdAfterSaleFile> jdAfterSaleFileList;

    /**
     * @return created_at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取删除标识 0：未删除，1：删除
     *
     * @return is_delete - 删除标识 0：未删除，1：删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置删除标识 0：未删除，1：删除
     *
     * @param isDelete 删除标识 0：未删除，1：删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取当前申请人id
     *
     * @return user_id - 当前申请人id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置当前申请人id
     *
     * @param userId 当前申请人id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return product_id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单编号
     *
     * @return order_sn - 订单编号
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 设置订单编号
     *
     * @param orderSn 订单编号
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取电商订单编号
     *
     * @return order_emall_sn - 电商订单编号
     */
    public String getOrderEmallSn() {
        return orderEmallSn;
    }

    /**
     * 设置电商订单编号
     *
     * @param orderEmallSn 电商订单编号
     */
    public void setOrderEmallSn(String orderEmallSn) {
        this.orderEmallSn = orderEmallSn;
    }

    /**
     * 获取售后类型：退货(10)、换货(20)、维修(30)
     *
     * @return type - 售后类型：退货(10)、换货(20)、维修(30)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置售后类型：退货(10)、换货(20)、维修(30)
     *
     * @param type 售后类型：退货(10)、换货(20)、维修(30)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取商品数量
     *
     * @return product_num - 商品数量
     */
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * 设置商品数量
     *
     * @param productNum 商品数量
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    /**
     * 获取提交原因
     *
     * @return reason - 提交原因
     */
    public Integer getReason() {
        return reason;
    }

    /**
     * 设置提交原因
     *
     * @param reason 提交原因
     */
    public void setReason(Integer reason) {
        this.reason = reason;
    }

    /**
     * 获取问题描述
     *
     * @return description - 问题描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置问题描述
     *
     * @param description 问题描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取是否有包装 包装描述：0 无包装 10 包装完整 20 包装破损
     *
     * @return is_has_package - 是否有包装 包装描述：0 无包装 10 包装完整 20 包装破损
     */
    public Integer getIsHasPackage() {
        return isHasPackage;
    }

    /**
     * 设置是否有包装 包装描述：0 无包装 10 包装完整 20 包装破损
     *
     * @param isHasPackage 是否有包装 包装描述：0 无包装 10 包装完整 20 包装破损
     */
    public void setIsHasPackage(Integer isHasPackage) {
        this.isHasPackage = isHasPackage;
    }

    /**
     * 获取申请凭据  是否需要检测报告
     *
     * @return application_credentials - 申请凭据  是否需要检测报告
     */
    public Integer getApplicationCredentials() {
        return applicationCredentials;
    }

    /**
     * 设置申请凭据  是否需要检测报告
     *
     * @param applicationCredentials 申请凭据  是否需要检测报告
     */
    public void setApplicationCredentials(Integer applicationCredentials) {
        this.applicationCredentials = applicationCredentials;
    }

    /**
     * 获取取件方式：4上门取件； 7 客户送货；40客户发货
     *
     * @return pickware_type - 取件方式：4上门取件； 7 客户送货；40客户发货
     */
    public Integer getPickwareType() {
        return pickwareType;
    }

    /**
     * 设置取件方式：4上门取件； 7 客户送货；40客户发货
     *
     * @param pickwareType 取件方式：4上门取件； 7 客户送货；40客户发货
     */
    public void setPickwareType(Integer pickwareType) {
        this.pickwareType = pickwareType;
    }

    /**
     * 获取取件开始时间
     *
     * @return pickware_start_time - 取件开始时间
     */
    public String getPickwareStartTime() {
        return pickwareStartTime;
    }

    /**
     * 设置取件开始时间
     *
     * @param pickwareStartTime 取件开始时间
     */
    public void setPickwareStartTime(String pickwareStartTime) {
        this.pickwareStartTime = pickwareStartTime;
    }

    /**
     * 获取取件结束时间
     *
     * @return pickware_end_time - 取件结束时间
     */
    public String getPickwareEndTime() {
        return pickwareEndTime;
    }

    /**
     * 设置取件结束时间
     *
     * @param pickwareEndTime 取件结束时间
     */
    public void setPickwareEndTime(String pickwareEndTime) {
        this.pickwareEndTime = pickwareEndTime;
    }

    /**
     * 获取取件地址  地址id
     *
     * @return pickware_address_id - 取件地址  地址id
     */
    public Long getPickwareAddressId() {
        return pickwareAddressId;
    }

    /**
     * 设置取件地址  地址id
     *
     * @param pickwareAddressId 取件地址  地址id
     */
    public void setPickwareAddressId(Long pickwareAddressId) {
        this.pickwareAddressId = pickwareAddressId;
    }

    /**
     * 获取取件地址名称
     *
     * @return pickware_address_name - 取件地址名称
     */
    public String getPickwareAddressName() {
        return pickwareAddressName;
    }

    /**
     * 设置取件地址名称
     *
     * @param pickwareAddressName 取件地址名称
     */
    public void setPickwareAddressName(String pickwareAddressName) {
        this.pickwareAddressName = pickwareAddressName;
    }

    /**
     * 获取取件详细地址
     *
     * @return pickware_detailed_address - 取件详细地址
     */
    public String getPickwareDetailedAddress() {
        return pickwareDetailedAddress;
    }

    /**
     * 设置取件详细地址
     *
     * @param pickwareDetailedAddress 取件详细地址
     */
    public void setPickwareDetailedAddress(String pickwareDetailedAddress) {
        this.pickwareDetailedAddress = pickwareDetailedAddress;
    }

    /**
     * 获取返件地址id
     *
     * @return returnware_address_id - 返件地址id
     */
    public Long getReturnwareAddressId() {
        return returnwareAddressId;
    }

    /**
     * 设置返件地址id
     *
     * @param returnwareAddressId 返件地址id
     */
    public void setReturnwareAddressId(Long returnwareAddressId) {
        this.returnwareAddressId = returnwareAddressId;
    }

    /**
     * 获取返件地址名称
     *
     * @return returnware_address_name - 返件地址名称
     */
    public String getReturnwareAddressName() {
        return returnwareAddressName;
    }

    /**
     * 设置返件地址名称
     *
     * @param returnwareAddressName 返件地址名称
     */
    public void setReturnwareAddressName(String returnwareAddressName) {
        this.returnwareAddressName = returnwareAddressName;
    }

    /**
     * 获取返件详细地址
     *
     * @return returnware_detailed_address - 返件详细地址
     */
    public String getReturnwareDetailedAddress() {
        return returnwareDetailedAddress;
    }

    /**
     * 设置返件详细地址
     *
     * @param returnwareDetailedAddress 返件详细地址
     */
    public void setReturnwareDetailedAddress(String returnwareDetailedAddress) {
        this.returnwareDetailedAddress = returnwareDetailedAddress;
    }

    /**
     * 获取客户姓名
     *
     * @return user_name - 客户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置客户姓名
     *
     * @param userName 客户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取客户手机号
     *
     * @return user_mobile - 客户手机号
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * 设置客户手机号
     *
     * @param userMobile 客户手机号
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public List<JdAfterSaleFile> getJdAfterSaleFileList() {
		return jdAfterSaleFileList;
	}

	public void setJdAfterSaleFileList(List<JdAfterSaleFile> jdAfterSaleFileList) {
		this.jdAfterSaleFileList = jdAfterSaleFileList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Integer getReturnwareType() {
		return returnwareType;
	}

	public void setReturnwareType(Integer returnwareType) {
		this.returnwareType = returnwareType;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(String afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}
	
	public BigDecimal getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(BigDecimal returnPrice) {
		this.returnPrice = returnPrice;
	}

	public Integer getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(Integer waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	public Integer getAllowOperations() {
		return allowOperations;
	}

	public void setAllowOperations(Integer allowOperations) {
		this.allowOperations = allowOperations;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProductThumbnail() {
		return productThumbnail;
	}

	public void setProductThumbnail(String productThumbnail) {
		this.productThumbnail = productThumbnail;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getAfsType() {
		return afsType;
	}

	public void setAfsType(Integer afsType) {
		this.afsType = afsType;
	}
	
}