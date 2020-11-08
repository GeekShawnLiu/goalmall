package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PortalArea;
import www.tonghao.service.common.mapper.PortalAreaMapper;
import www.tonghao.service.common.service.PortalAreaService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("portalAreaService")
public class PortalAreaServiceImpl extends BaseServiceImpl<PortalArea> implements PortalAreaService {

    @Autowired
    private PortalAreaMapper portalAreaMapper;

    @Override
    public List<PortalArea> getAllBySort() {
        return portalAreaMapper.getAllByPosition();
    }

    @Override
    public Map<String, Object> saveOrUpdate(PortalArea portalArea) {
        int edit = 0;
        portalArea.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        if (portalArea.getId() != null) {
            edit = this.updateNotNull(portalArea);
        }else {
            portalArea.setCreatedAt(DateUtilEx.timeFormat(new Date()));
            edit = this.save(portalArea);
        }
        return ResultUtil.resultMessage(edit,"");
    }
}
