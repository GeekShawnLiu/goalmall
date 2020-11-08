package www.tonghao.service.common.mapper;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PortalArea;

import java.util.List;

public interface PortalAreaMapper extends BaseMapper<PortalArea> {
    List<PortalArea> getAllByPosition();
}
