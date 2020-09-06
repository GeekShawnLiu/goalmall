package www.tonghao.platform.service;

import www.tonghao.platform.entity.SupplierScale;
import www.tonghao.service.common.base.BaseService;


public interface SupplierScaleService extends BaseService<SupplierScale> {

	public int saveOrUpdate(SupplierScale scale);
}
