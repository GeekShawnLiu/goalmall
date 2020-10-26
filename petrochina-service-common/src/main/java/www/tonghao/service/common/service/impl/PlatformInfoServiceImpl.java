package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PlatformInfo;
import www.tonghao.service.common.mapper.PlatformInfoMapper;
import www.tonghao.service.common.service.PlatformInfoService;

import java.util.List;

@Service("platformInfoService")
public class PlatformInfoServiceImpl extends BaseServiceImpl<PlatformInfo> implements PlatformInfoService {

    @Autowired
    private PlatformInfoMapper platformInfoMapper;

    @Override
    public List<PlatformInfo> selectAllPlatformInfo() {
        Example example = new Example(PlatformInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<PlatformInfo> platformInfos = platformInfoMapper.selectByExample(example);
        return platformInfos;
    }
}
