package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.OrderInvoice;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;

@ApiModel(value = "订单")
public class Orders extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String sn;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    @Column(name = "orders_state")
    private OrderStatus ordersState;

    /**
     * 收获地址ID
     */
    @ApiModelProperty(value = "收获地址ID")
    @Column(name = "address_id")
    private Long addressId;

    /**
     * 地区ID
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    @Column(name = "consignee_name")
    private String consigneeName;

    /**
     * 收货详细地址
     */
    @ApiModelProperty(value = "收货详细地址")
    private String addr;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 收货人手机号
     */
    @ApiModelProperty(value = "收货人手机号")
    private String mobile;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "收货人电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 商品总金额
     */
    @ApiModelProperty(value = "商品总金额")
    @Column(name = "items_price")
    private BigDecimal itemsPrice;

    /**
     * 邮费
     */
    @ApiModelProperty(value = "邮费")
    private BigDecimal freight;

    /**
     * 订单总金额(含快递等费用)
     */
    @ApiModelProperty(value = "订单总金额(含快递等费用)")
    private BigDecimal total;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 发票类型
     */
    @ApiModelProperty(value = "发票类型")
    @Column(name = "invoice_type")
    private String invoiceType;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    @Column(name = "invoice_title")
    private String invoiceTitle;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty(value = "纳税人识别号")
    @Column(name = "invoice_tax_code")
    private String invoiceTaxCode;

    /**
     * 发票注册电话
     */
    @ApiModelProperty(value = "发票注册电话")
    @Column(name = "invoice_phone")
    private String invoicePhone;

    /**
     * 发票开户行
     */
    @ApiModelProperty(value = "发票开户行")
    @Column(name = "invoice_bank")
    private String invoiceBank;

    /**
     * 发票开户行账号
     */
    @ApiModelProperty(value = "发票开户行账号")
    @Column(name = "invoice_bank_account")
    private String invoiceBankAccount;

    /**
     * 计划ID
     */
    @Column(name = "plan_id")
    private Long planId;

    /**
     * 计划编号
     */
    @Column(name = "plan_sn")
    private String planSn;

    /**
     * 发票ID
     */
    @ApiModelProperty(value = "发票ID")
    @Column(name = "invoice_id")
    private Long invoiceId;

    /**
     * 主订单ID
     */
    @ApiModelProperty(value = "主订单ID")
    @Column(name = "master_id")
    private Long masterId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 电商订单编号
     */
    @ApiModelProperty(value = "电商订单编号")
    @Column(name = "emall_sn")
    private String emallSn;

    /**
     * 是否软删除 0:否1:删除
     */
    @ApiModelProperty(value = "是否软删除 0:否1:删除")
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 电商父订单id
     */
    @ApiModelProperty(value = "电商父订单id")
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间")
    @Column(name = "completed_at")
    private String completedAt;
    /**
     * 采购单位
     */
    @Column(name = "purchaser_name")
    private String purchaserName;

    /**
     * 采购地区编码
     */
    @Column(name = "purchaser_region_code")
    private String purchaserRegionCode;

    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式")
    @Column(name = "payway")
    private String payway;

    /**
     * 付款方式ID
     */
    @ApiModelProperty(value = "付款方式ID")
    @Column(name = "pay_id")
    private Long payId;

    @Transient
    @ApiModelProperty(value = "支付方式名称   1积分支付  2个人支付   3混合支付")
    private String payName;

    /**
     * 付款金额
     */
    @ApiModelProperty(value = "付款金额")
    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    /**
     * 1 电商，2协议供应商
     */
    @ApiModelProperty(value = "1 电商，2协议供应商")
    @Column(name = "order_type")
    private Integer orderType;

    /**
     * 是否生成验收单 0未生成 1生成
     */
    @ApiModelProperty(value = "是否生成验收单 0未生成 1生成")
    @Column(name = "accept_status")
    private Integer acceptStatus;

    /**
     * 是否生成合同0未生成，1已生成
     */
    @ApiModelProperty(value = "是否生成合同0未生成，1已生成")
    @Column(name = "contract_status")
    private Integer contractStatus;

    @ApiModelProperty(value = "计划原金额")
    @Column(name = "plan_amount")
    private BigDecimal planAmount;

    @ApiModelProperty(value = "计划可用金额")
    @Column(name = "plan_balance")
    private BigDecimal planBalance;

    @Column(name = "plan_uuid")
    private String planUuid;

    @Column(name = "contract_id")
    private Long contractId; //成交合同ID

    @Column(name = "contract_url")
    private String contractUrl; //成交合同url

    @Column(name = "project_code")
    private String projectCode;//计划项目编号

    @Column(name = "track")
    private String track; //物流  存OrderTrack的json数组

    @Transient
    private Users user;

    @Transient
    private OrderInvoice orderInvoice;

    /**
     * 对账状态 0 未对账 1已对账
     */
    @Column(name = "bill_status")
    private Integer billStatus;

    /**
     * 协议总金额
     */
    @Column(name = "protocol_total")
    private BigDecimal protocolTotal;

    /**
     * 混合支付时  支付积分数量
     */
    @Column(name = "pay_integral")
    private BigDecimal payIntegral;

    /**
     * 混合支付时  个人支付数量
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;


    /**
     * 主订单
     */
    @Transient
    private OrderMaster master;

    /*
     * 订单项i
     */
    @Transient
    private List<OrderItems> items;

    /**
     * 供应商
     */
    @Transient
    private Suppliers supplier;

    /**
     * 物流信息
     */
    @Transient
    private List<Orders> trackList;

    @Transient
    private String userName;

    /**
     * 批次支付类型
     */
    @Transient
    private Integer isPay;

    /**
     * 用户手机号
     */
    @Transient
    private String userMobile;

    /**
     * 父订单平台编号
     */
    @Transient
    private String parentSn;

    /**
     * 父订单电商编号
     */
    @Transient
    private String parentEmallSn;

    /**
     * 子订单编号
     */
    @Transient
    private String childSn;

    /**
     * 登录名
     */
    @Transient
    private String loginName;

    /**
     * 是否包含子订单   1包含   0未包含
     */
    @Transient
    private Integer isHavaChild;

    @ApiModelProperty(value = "下单来源  1:pc  2:h5  3:小程序")
    @Column(name = "order_source")
    private String orderSource;

    @ApiModelProperty(value = "物流名称（仅本地供应商）")
    @Column(name = "logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "物流编号（仅本地供应商）")
    @Column(name = "logistics_sn")
    private String logisticsSn;

    @Transient
    @ApiModelProperty(value = "毛利")
    private BigDecimal profit;

    @Transient
    @ApiModelProperty(value = "活动")
    private List<Activity> activityList;

    @Transient
    @ApiModelProperty(value = "子订单")
    private List<Orders> childOrderList;

    @Transient
    @ApiModelProperty(value = "电商子订单编号")
    private String childOrderEmallSns;

    @Transient
    @ApiModelProperty(value = "活动名称")
    private String activityNames;

    @Transient
    @ApiModelProperty(value = "剩余积分")
    private BigDecimal balanceIntegral;

    @Transient
    private List<IntegralUser> integralUserList;
    @Column(name = "receipt_at")
    private String receiptAt; //接单时间本地供应商使用）

    @Column(name = "is_receipt")
    private Integer isReceipt; //是否接单  0 未接单，1已接单本地供应商使用）

    @Column(name = "is_cancel")
    private Integer isCancel; //是否取消申请 2 取消申请通过，3取消驳回  1 提交 0 未提交 （本地供应商使用）

    @Column(name = "logistics_type")
    private Integer logisticsType; // 物流方式：1：供应商自行送货 2：快递 3：其他

    @Column(name = "platform_code")
    private String platformCode; // 下单平台code

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取订单号
     *
     * @return sn - 订单号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置订单号
     *
     * @param sn 订单号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取订单状态
     *
     * @return orders_state - 订单状态
     */
    public OrderStatus getOrdersState() {
        return ordersState;
    }

    /**
     * 设置订单状态
     *
     * @param ordersState 订单状态
     */
    public void setOrdersState(OrderStatus ordersState) {
        this.ordersState = ordersState;
    }

    /**
     * 获取地址ID
     *
     * @return address_id - 地址ID
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * 设置地址ID
     *
     * @param addressId 地址ID
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取收货人
     *
     * @return consignee_name - 收货人
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * 设置收货人
     *
     * @param consigneeName 收货人
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    /**
     * 获取收货详细地址
     *
     * @return addr - 收货详细地址
     */
    public String getAddr() {
        return addr;
    }

    /**
     * 设置收货详细地址
     *
     * @param addr 收货详细地址
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * 获取邮编
     *
     * @return zip_code - 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取收货人手机号
     *
     * @return mobile - 收货人手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置收货人手机号
     *
     * @param mobile 收货人手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取收货人电话
     *
     * @return phone - 收货人电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置收货人电话
     *
     * @param phone 收货人电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取商品总金额
     *
     * @return items_price - 商品总金额
     */
    public BigDecimal getItemsPrice() {
        return itemsPrice;
    }

    /**
     * 设置商品总金额
     *
     * @param itemsPrice 商品总金额
     */
    public void setItemsPrice(BigDecimal itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    /**
     * 获取邮费
     *
     * @return freight - 邮费
     */
    public BigDecimal getFreight() {
        return freight;
    }

    /**
     * 设置邮费
     *
     * @param freight 邮费
     */
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    /**
     * 获取订单总金额(含快递等费用)
     *
     * @return total - 订单总金额(含快递等费用)
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * 设置订单总金额(含快递等费用)
     *
     * @param total 订单总金额(含快递等费用)
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * 获取供应商ID
     *
     * @return supplier_id - 供应商ID
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商ID
     *
     * @param supplierId 供应商ID
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取发票类型
     *
     * @return invoice_type - 发票类型
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型
     *
     * @param invoiceType 发票类型
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 获取发表标题
     *
     * @return invoice_title - 发表标题
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * 设置发表标题
     *
     * @param invoiceTitle 发表标题
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    /**
     * 获取纳税人识别号
     *
     * @return invoice_tax_code - 纳税人识别号
     */
    public String getInvoiceTaxCode() {
        return invoiceTaxCode;
    }

    /**
     * 设置纳税人识别号
     *
     * @param invoiceTaxCode 纳税人识别号
     */
    public void setInvoiceTaxCode(String invoiceTaxCode) {
        this.invoiceTaxCode = invoiceTaxCode;
    }

    /**
     * 获取发票注册电话
     *
     * @return invoice_phone - 发票注册电话
     */
    public String getInvoicePhone() {
        return invoicePhone;
    }

    /**
     * 设置发票注册电话
     *
     * @param invoicePhone 发票注册电话
     */
    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone;
    }

    /**
     * 获取发票开户行
     *
     * @return invoice_bank - 发票开户行
     */
    public String getInvoiceBank() {
        return invoiceBank;
    }

    /**
     * 设置发票开户行
     *
     * @param invoiceBank 发票开户行
     */
    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }

    /**
     * 获取发票开户行账号
     *
     * @return invoice_bank_account - 发票开户行账号
     */
    public String getInvoiceBankAccount() {
        return invoiceBankAccount;
    }

    /**
     * 设置发票开户行账号
     *
     * @param invoiceBankAccount 发票开户行账号
     */
    public void setInvoiceBankAccount(String invoiceBankAccount) {
        this.invoiceBankAccount = invoiceBankAccount;
    }


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    /**
     * 获取发票ID
     *
     * @return invoice_id - 发票ID
     */
    public Long getInvoiceId() {
        return invoiceId;
    }

    /**
     * 设置发票ID
     *
     * @param invoiceId 发票ID
     */
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * 获取主订单ID
     *
     * @return master_id - 主订单ID
     */
    public Long getMasterId() {
        return masterId;
    }

    /**
     * 设置主订单ID
     *
     * @param masterId 主订单ID
     */
    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    /**
     * 获取供应商名称
     *
     * @return supplier_name - 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置供应商名称
     *
     * @param supplierName 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取电商订单编号
     *
     * @return emall_sn - 电商订单编号
     */
    public String getEmallSn() {
        return emallSn;
    }

    /**
     * 设置电商订单编号
     *
     * @param emallSn 电商订单编号
     */
    public void setEmallSn(String emallSn) {
        this.emallSn = emallSn;
    }

    /**
     * 获取是否软删除 0:否1:删除
     *
     * @return is_delete - 是否软删除 0:否1:删除
     */
    public Integer isDelete() {
        return isDelete;
    }

    /**
     * 设置是否软删除 0:否1:删除
     *
     * @param isDelete 是否软删除 0:否1:删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取电商父订单id
     *
     * @return parent_id - 电商父订单id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置电商父订单id
     *
     * @param parentId 电商父订单id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取完成时间
     *
     * @return completed_at - 完成时间
     */
    public String getCompletedAt() {
        return completedAt;
    }

    /**
     * 设置完成时间
     *
     * @param completedAt 完成时间
     */
    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    /**
     * 设置采购单位
     *
     * @return purchaserName
     */
    public String getPurchaserName() {
        return purchaserName;
    }

    /**
     * 获取采购单位
     *
     * @param purchaserName
     */
    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserRegionCode() {
        return purchaserRegionCode;
    }

    public void setPurchaserRegionCode(String purchaserRegionCode) {
        this.purchaserRegionCode = purchaserRegionCode;
    }

    /**
     * 获取付款方式
     *
     * @return payway
     */
    public String getPayway() {
        return payway;
    }

    /**
     * 设置付款方式
     *
     * @param payway
     */
    public void setPayway(String payway) {
        this.payway = payway;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public OrderMaster getMaster() {
        return master;
    }

    public void setMaster(OrderMaster master) {
        this.master = master;
    }

    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }


    public Integer getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Integer acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public String getPlanSn() {
        return planSn;
    }

    public void setPlanSn(String planSn) {
        this.planSn = planSn;
    }

    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    public BigDecimal getPlanBalance() {
        return planBalance;
    }

    public void setPlanBalance(BigDecimal planBalance) {
        this.planBalance = planBalance;
    }

    public String getPlanUuid() {
        return planUuid;
    }

    public void setPlanUuid(String planUuid) {
        this.planUuid = planUuid;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }

    /**
     * 预算内金额
     *
     * @return
     */
    public BigDecimal getTotalBudgetIn() {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItems> items = getItems();
        if (items != null) {
            for (OrderItems item : items) {
                if (item.getPlanItemBudgetIn() != null) {
                    total = total.add(item.getPlanItemBudgetIn());
                }
            }
        }
        return total;
    }

    /**
     * 财政金额
     *
     * @return
     */
    public BigDecimal getTotalBudgetFinance() {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItems> items = getItems();
        if (items != null) {
            for (OrderItems item : items) {
                if (item.getPlanItemBudgetFinance() != null) {
                    total = total.add(item.getPlanItemBudgetFinance());
                }
            }
        }
        return total;
    }

    /**
     * 自筹金额
     *
     * @return
     */
    public BigDecimal getTotalBudgetSelf() {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItems> items = getItems();
        if (items != null) {
            for (OrderItems item : items) {
                if (item.getPlanItemBudgetSelf() != null) {
                    total = total.add(item.getPlanItemBudgetSelf());
                }
            }
        }
        return total;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public List<Orders> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Orders> trackList) {
        this.trackList = trackList;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getProtocolTotal() {
        return protocolTotal;
    }

    public void setProtocolTotal(BigDecimal protocolTotal) {
        this.protocolTotal = protocolTotal;
    }

    public BigDecimal getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(BigDecimal payIntegral) {
        this.payIntegral = payIntegral;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayName() {
        if (getPayId() == null) {
            return null;
        } else if (1 == getPayId()) {
            return "积分支付";
        } else if (2 == getPayId()) {
            return "个人支付";
        } else if (3 == getPayId()) {
            return "混合支付";
        } else {
            return null;
        }
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public OrderInvoice getOrderInvoice() {
        return orderInvoice;
    }

    public void setOrderInvoice(OrderInvoice orderInvoice) {
        this.orderInvoice = orderInvoice;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getParentSn() {
        return parentSn;
    }

    public void setParentSn(String parentSn) {
        this.parentSn = parentSn;
    }

    public String getChildSn() {
        return childSn;
    }

    public void setChildSn(String childSn) {
        this.childSn = childSn;
    }

    public Integer getIsHavaChild() {
        return isHavaChild;
    }

    public void setIsHavaChild(Integer isHavaChild) {
        this.isHavaChild = isHavaChild;
    }

    public String getParentEmallSn() {
        return parentEmallSn;
    }

    public void setParentEmallSn(String parentEmallSn) {
        this.parentEmallSn = parentEmallSn;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsSn() {
        return logisticsSn;
    }

    public void setLogisticsSn(String logisticsSn) {
        this.logisticsSn = logisticsSn;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Orders> getChildOrderList() {
        return childOrderList;
    }

    public void setChildOrderList(List<Orders> childOrderList) {
        this.childOrderList = childOrderList;
    }

    public String getChildOrderEmallSns() {
        return childOrderEmallSns;
    }

    public void setChildOrderEmallSns(String childOrderEmallSns) {
        this.childOrderEmallSns = childOrderEmallSns;
    }

    public String getActivityNames() {
        return activityNames;
    }

    public void setActivityNames(String activityNames) {
        this.activityNames = activityNames;
    }

    public BigDecimal getBalanceIntegral() {
        return balanceIntegral;
    }

    public void setBalanceIntegral(BigDecimal balanceIntegral) {
        this.balanceIntegral = balanceIntegral;
    }

    public List<IntegralUser> getIntegralUserList() {
        return integralUserList;
    }

    public void setIntegralUserList(List<IntegralUser> integralUserList) {
        this.integralUserList = integralUserList;
    }

    public String getReceiptAt() {
        return receiptAt;
    }

    public void setReceiptAt(String receiptAt) {
        this.receiptAt = receiptAt;
    }

    public Integer getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(Integer isReceipt) {
        this.isReceipt = isReceipt;
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}