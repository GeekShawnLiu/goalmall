package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.CatalogParameter;
import www.tonghao.service.common.mapper.CatalogParameterMapper;
import www.tonghao.service.common.service.CatalogParameterService;

import java.util.List;

@Service("catalogParameterService")
public class CatalogParameterServiceImpl extends BaseServiceImpl<CatalogParameter> implements CatalogParameterService {

    @Autowired
    private CatalogParameterMapper catalogParameterMapper;

    @Override
    public int deleteByCatalogId(Long catalogId) {

        return catalogParameterMapper.deleteByCatalogId(catalogId);
    }

    public List<CatalogParameter> getByPlatformCatalogId(Long catalogId) {
        return catalogParameterMapper.getByPlatformCatalogId(catalogId);
    }
}
