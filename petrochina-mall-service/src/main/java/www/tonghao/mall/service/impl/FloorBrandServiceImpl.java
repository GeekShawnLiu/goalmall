package www.tonghao.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.FloorBrand;
import www.tonghao.mall.mapper.FloorBrandMapper;
import www.tonghao.mall.service.FloorBrandService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("mallFloorBrandService")
public class FloorBrandServiceImpl extends BaseServiceImpl<FloorBrand> implements FloorBrandService{
    
    @Autowired
    FloorBrandMapper floorBrandMapper;
    /**
     * 通过楼层Id查询
     * @param floorId
     * @return
     */
    public List<FloorBrand> selectByFloorId(Long floorId) {
        return floorBrandMapper.selectByFloorId(floorId);
    }
    
    /**
     * 通过楼层Id删除
     */
    public int deleteByFloorId(Long floorId) {
        return floorBrandMapper.deleteByFloorId(floorId);
    }
    
}
