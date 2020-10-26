package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.PlatformInfo;

import java.util.List;

public interface PlatformInfoService extends BaseService<PlatformInfo> {

    List<PlatformInfo> selectAllPlatformInfo();
}
