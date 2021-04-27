package www.tonghao.service.common.mapper;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PortalProduct;

import java.util.List;

public interface PortalProductMapper extends BaseMapper<PortalProduct> {

    List<PortalProduct> getAllByPosition();

    Integer getMaxPosition();
}
