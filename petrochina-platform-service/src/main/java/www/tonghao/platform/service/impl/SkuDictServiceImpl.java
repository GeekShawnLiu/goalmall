package www.tonghao.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.tonghao.platform.entity.SkuDict;
import www.tonghao.platform.mapper.SkuDictMapper;
import www.tonghao.platform.service.SkuDictService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service(value="skuDictService")
public class SkuDictServiceImpl extends BaseServiceImpl<SkuDict> implements SkuDictService {

    @Autowired
    private SkuDictMapper skuDictMapper;

    @Override
    public int updateSerialNum() {
        return skuDictMapper.updateSerialNum();
    }
}
