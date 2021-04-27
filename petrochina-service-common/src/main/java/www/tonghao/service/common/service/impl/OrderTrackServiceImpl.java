package www.tonghao.service.common.service.impl;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.OrderTrack;
import www.tonghao.service.common.service.OrderTrackService;

import java.util.List;

@Service("orderTrackService")
public class OrderTrackServiceImpl extends BaseServiceImpl<OrderTrack> implements OrderTrackService {

    @Override
    public List<OrderTrack> getByOrderId(Long orderId) {
        Example example = new Example(OrderTrack.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        List<OrderTrack> orderTracks = this.selectByExample(example);
        return orderTracks;
    }
}
