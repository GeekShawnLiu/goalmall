package www.tonghao.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.Invoices;
import www.tonghao.mall.mapper.InvoicesMapper;
import www.tonghao.mall.service.InvoicesService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

/**
 * 发票
 * @author developer001
 *
 */
@Service("mallInvoicesService")
public class InvoicesServiceImpl extends BaseServiceImpl<Invoices> implements InvoicesService{
    
    @Autowired
    private InvoicesMapper invoiceMapper;
    @Override
    public Invoices findById(Long id) {
        return invoiceMapper.findById(id);
    }
	
}
