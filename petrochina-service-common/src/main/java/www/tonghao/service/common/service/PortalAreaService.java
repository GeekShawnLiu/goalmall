package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.PortalArea;

import java.util.List;
import java.util.Map;

public interface PortalAreaService extends BaseService<PortalArea> {

    List<PortalArea> getAllBySort();

    Map<String,Object> saveOrUpdate(PortalArea portalArea);
}
