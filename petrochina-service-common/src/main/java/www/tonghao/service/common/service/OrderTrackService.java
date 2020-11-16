package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.OrderTrack;

import java.util.List;

public interface OrderTrackService extends BaseService<OrderTrack> {

    @Override
    List<OrderTrack> selectByExample(Object example);

    List<OrderTrack> getByOrderId(Long orderId);
}
