package www.tonghao.mall.mapper;

import java.util.List;

import www.tonghao.mall.entity.FloorBrand;
import www.tonghao.service.common.base.BaseMapper;

public interface FloorBrandMapper extends BaseMapper<FloorBrand> {
    
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