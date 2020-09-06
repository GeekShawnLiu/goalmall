package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.FloorPlatformCatalog;
import www.tonghao.service.common.base.BaseService;

public interface FloorPlatformCatalogService extends BaseService<FloorPlatformCatalog>{
    /**
     * 通过楼层Id查询
     * @param floorId
     * @return
     */
    List<FloorPlatformCatalog> selectByFloorId(Long floorId);
    
    /**
     * 通过楼层Id删除
     * @param floorId
     * @return
     */
    int deleteByFloorId(Long floorId);
}
