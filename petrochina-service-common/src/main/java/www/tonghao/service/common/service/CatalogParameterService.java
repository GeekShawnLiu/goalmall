package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.CatalogParameter;

import java.util.List;

public interface CatalogParameterService extends BaseService<CatalogParameter> {

    /**
     * 根据品目id删除
     * @param catalogId
     * @return
     */
    int deleteByCatalogId(Long catalogId);

    List<CatalogParameter> getByPlatformCatalogId(Long catalogId);
}
