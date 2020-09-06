package www.tonghao.mall.mapper;

import www.tonghao.mall.entity.Floor;
import www.tonghao.service.common.base.BaseMapper;

public interface FloorMapper extends BaseMapper<Floor> {
    
    /**
     * 通过id查询
     * @param id
     * @return
     */
    Floor findById(Long id);
}