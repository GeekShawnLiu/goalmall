package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 订单电子发票信息
 */
@Table(name = "order_electronic_invoice")
public class OrderElectronicInvoice extends BaseEntity {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "invoice_img")
    private String invoiceImg;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceImg() {
        return invoiceImg;
    }

    public void setInvoiceImg(String invoiceImg) {
        this.invoiceImg = invoiceImg;
    }
}
