package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PlatformCatalogMapping;

import java.util.List;

public interface PlatformCatalogMappingMapper extends BaseMapper<PlatformCatalogMapping> {

    /**
     * 根据平台code获取品目信息
     *
     * @param platformInfoCode
     * @return
     */
    List<String> selectByPlatformInfoCode(@Param("platformInfoCode") String platformInfoCode);

    /**
     * 查询品目映射信息
     *
     * @param catalogId
     * @param platformInfoCode
     * @return
     */
    PlatformCatalogMapping selectByCatalogId(@Param("catalogId") Long catalogId, @Param("platformInfoCode") String platformInfoCode);
}
