package www.tonghao.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.FloorPlatformCatalog;
import www.tonghao.mall.mapper.FloorPlatformCatalogMapper;
import www.tonghao.mall.service.FloorPlatformCatalogService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("mallFloorPlatformCatalogService")
public class FloorPlatformCatalogServiceImpl extends BaseServiceImpl<FloorPlatformCatalog> implements FloorPlatformCatalogService{
    
    @Autowired
    FloorPlatformCatalogMapper floorPlatformCatalogMapper;
    /**
     * 通过楼层Id查询
     * @param floorId
     * @return
     */
    public List<FloorPlatformCatalog> selectByFloorId(Long floorId){
        return floorPlatformCatalogMapper.selectByFloorId(floorId);
    }
    
    /**
     * 通过楼层Id删除
     */
    public int deleteByFloorId(Long floorId) {
        return floorPlatformCatalogMapper.deleteByFloorId(floorId);
    }
}
