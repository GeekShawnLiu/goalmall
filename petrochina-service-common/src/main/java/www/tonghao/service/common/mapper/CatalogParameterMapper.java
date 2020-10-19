package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.CatalogParameter;

import java.util.List;

public interface CatalogParameterMapper extends BaseMapper<CatalogParameter> {
    /**
     * 根据品目id删除
     * @param catalogId
     * @return
     */
    int deleteByCatalogId(@Param("catalogId") Long catalogId);

    List<CatalogParameter> getByPlatformCatalogId(@Param("catalogId") Long catalogId);
}
