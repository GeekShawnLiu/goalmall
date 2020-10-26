package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.OrderTrack;

import java.util.List;

public interface OrderTrackMapper extends BaseMapper<OrderTrack> {

    List<OrderTrack> selectByOrderId(@Param("orderId") Long orderId);
}
