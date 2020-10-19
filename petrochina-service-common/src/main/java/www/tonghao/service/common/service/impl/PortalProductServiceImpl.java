package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PortalProduct;
import www.tonghao.service.common.mapper.PortalProductMapper;
import www.tonghao.service.common.service.PortalProductService;

import java.util.List;

@Service("portalProductService")
public class PortalProductServiceImpl extends BaseServiceImpl<PortalProduct> implements PortalProductService {

    @Autowired
    private PortalProductMapper portalProductMapper;

    public List<PortalProduct> getAllByPosition() {
        return portalProductMapper.getAllByPosition();
    }

    public Integer getMaxPosition() {
        return portalProductMapper.getMaxPosition();
    }
}
