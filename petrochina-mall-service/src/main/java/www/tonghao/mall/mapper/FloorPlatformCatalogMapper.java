package www.tonghao.mall.mapper;

import java.util.List;

import www.tonghao.mall.entity.FloorPlatformCatalog;
import www.tonghao.service.common.base.BaseMapper;

public interface FloorPlatformCatalogMapper extends BaseMapper<FloorPlatformCatalog> {
    
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
    
    /**
     * 获取商城的平台品目
     * @param floorId
     * @return
     */
    List<FloorPlatformCatalog> findMallByFloorId(Long floorId);
}