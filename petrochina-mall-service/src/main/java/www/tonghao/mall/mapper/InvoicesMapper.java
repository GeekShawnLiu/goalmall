package www.tonghao.mall.mapper;

import www.tonghao.mall.entity.Invoices;
import www.tonghao.service.common.base.BaseMapper;

public interface InvoicesMapper extends BaseMapper<Invoices> {
    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Invoices findById(Long id);
}