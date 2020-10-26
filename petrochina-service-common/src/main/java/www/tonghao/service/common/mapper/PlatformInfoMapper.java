package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PlatformInfo;

public interface PlatformInfoMapper extends BaseMapper<PlatformInfo> {

    PlatformInfo selectByPlatformCode(@Param("platformCode") String platformCode);
}
