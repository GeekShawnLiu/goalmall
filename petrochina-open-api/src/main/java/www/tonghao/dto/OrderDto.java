package www.tonghao.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String yggc_order;

    private String sku;

    /**
     * 收货人
     */
    private String name;

    /**
     * 一级地址
     */
    private String province;

    /**
     * 二级地址
     */
    private String city;

    /**
     * 三级地址
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zip;

    /**
     * 座机号 (与mobile其中一个有值即可)
     */
    private String phone;

    /**
     * 手机号 （与phone其中一个有值即可）
     */
    private String mobile;

    /**
     * 邮箱（非必须）
     */
    private String email;

    /**
     * 备注（少于100字）（非必须）
     */
    private String remark;

    /**
     * 采购单位名称（非必须）
     */
    private String dep_name;

    /**
     * 发票抬头，个人或公司名称
     */
    private String invoice_title;

    /**
     * 1普通发票;2增值税发票；3 电子发票；默认为普通发票
     */
    private String invoice_type;

    /**
     * 纳税人识别号
     */
    private String invoice_org_code;

    /**
     * 增值票收票人（非必须）
     * 备注：当invoice_type=2 且时则此字段必填
     */
    private String invoice_name;

    /**
     * 注册电话（非必须）
     * 备注：当invoice_type=2 且时则此字段必填
     */
    private String invoice_phone;

    /**
     * 开户银行（非必须）
     * 备注：当invoice_type=2 且时则此字段必填
     */
    private String invoice_bank;

    /**
     * 开户行帐号（非必须）
     * 备注：当invoice_type=2 且时则此字段必填
     */
    private String invoice_bank_code;

    /**
     * 注册地址（非必须）
     * 备注：当invoice_type=2 且时则此字段必填
     */
    private String invoice_address;

    /**
     * 收票联系电话
     */
    private String invoice_mobile;

    /**
     * 收票地址
     */
    private String invoice_receipt_address;

    /**
     * 1：货到付款，2：集中支付， 3：在线支付， 4：支票 5：账期支付 6：先款后货
     */
    private String payment;

    /**
     * 订单金额（包含运费）
     */
    private BigDecimal order_price;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 订单模式：1-协议价（默认）)；2-团购模式； 3-特惠模式; 4-阶梯价模式
     */
    private int mode;

    public String getYggc_order() {
        return yggc_order;
    }

    public void setYggc_order(String yggc_order) {
        this.yggc_order = yggc_order;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getInvoice_org_code() {
        return invoice_org_code;
    }

    public void setInvoice_org_code(String invoice_org_code) {
        this.invoice_org_code = invoice_org_code;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public String getInvoice_phone() {
        return invoice_phone;
    }

    public void setInvoice_phone(String invoice_phone) {
        this.invoice_phone = invoice_phone;
    }

    public String getInvoice_bank() {
        return invoice_bank;
    }

    public void setInvoice_bank(String invoice_bank) {
        this.invoice_bank = invoice_bank;
    }

    public String getInvoice_bank_code() {
        return invoice_bank_code;
    }

    public void setInvoice_bank_code(String invoice_bank_code) {
        this.invoice_bank_code = invoice_bank_code;
    }

    public String getInvoice_address() {
        return invoice_address;
    }

    public void setInvoice_address(String invoice_address) {
        this.invoice_address = invoice_address;
    }

    public String getInvoice_mobile() {
        return invoice_mobile;
    }

    public void setInvoice_mobile(String invoice_mobile) {
        this.invoice_mobile = invoice_mobile;
    }

    public String getInvoice_receipt_address() {
        return invoice_receipt_address;
    }

    public void setInvoice_receipt_address(String invoice_receipt_address) {
        this.invoice_receipt_address = invoice_receipt_address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public BigDecimal getOrder_price() {
        return order_price;
    }

    public void setOrder_price(BigDecimal order_price) {
        this.order_price = order_price;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
