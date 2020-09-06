package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.FloorBrand;
import www.tonghao.service.common.base.BaseService;

public interface FloorBrandService extends BaseService<FloorBrand>{
    /**
     * 通过楼层Id查询
     * @param floorId
     * @return
     */
    List<FloorBrand> selectByFloorId(Long floorId);
    
    /**
     * 通过楼层Id删除
     * @param floorId
     * @return
     */
    int deleteByFloorId(Long floorId);
}
