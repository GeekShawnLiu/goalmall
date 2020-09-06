package www.tonghao.mall.service;

import www.tonghao.mall.entity.Invoices;
import www.tonghao.service.common.base.BaseService;

public interface InvoicesService  extends BaseService<Invoices> {
    /**
     * 根据ID查找
     * @param id
     * @return
     */
    Invoices findById(Long id);
}
