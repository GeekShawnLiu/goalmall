package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.PortalProduct;

import java.util.List;

public interface PortalProductService extends BaseService<PortalProduct> {

    List<PortalProduct> getAllByPosition();

    Integer getMaxPosition();

}
