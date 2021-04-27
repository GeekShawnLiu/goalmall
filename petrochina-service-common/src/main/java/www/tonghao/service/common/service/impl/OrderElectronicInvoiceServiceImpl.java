package www.tonghao.service.common.service.impl;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.OrderElectronicInvoice;
import www.tonghao.service.common.service.OrderElectronicInvoiceService;

import java.util.List;

@Service("orderElectronicInvoiceService")
public class OrderElectronicInvoiceServiceImpl extends BaseServiceImpl<OrderElectronicInvoice> implements OrderElectronicInvoiceService {
    @Override
    public List<OrderElectronicInvoice> getByOrderId(Long orderId) {
        Example example = new Example(OrderElectronicInvoice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        List<OrderElectronicInvoice> list = this.selectByExample(example);
        return list;
    }
}
