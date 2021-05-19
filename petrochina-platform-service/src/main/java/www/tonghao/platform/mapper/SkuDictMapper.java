package www.tonghao.platform.mapper;

import www.tonghao.platform.entity.SkuDict;
import www.tonghao.service.common.base.BaseMapper;

public interface SkuDictMapper extends BaseMapper<SkuDict> {
    int updateSerialNum();
}
