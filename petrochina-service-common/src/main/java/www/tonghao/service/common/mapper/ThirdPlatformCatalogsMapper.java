package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ThirdPlatformCatalogs;

import java.util.List;

public interface ThirdPlatformCatalogsMapper extends BaseMapper<ThirdPlatformCatalogs> {

    List<String> selectByPlatformInfoCode(@Param("platformInfoCode") String platformInfoCode);
}
