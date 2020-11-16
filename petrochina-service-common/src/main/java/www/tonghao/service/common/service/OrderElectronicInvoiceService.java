package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.OrderElectronicInvoice;

import java.util.List;

public interface OrderElectronicInvoiceService extends BaseService<OrderElectronicInvoice> {

    List<OrderElectronicInvoice> getByOrderId(Long orderId);
}
