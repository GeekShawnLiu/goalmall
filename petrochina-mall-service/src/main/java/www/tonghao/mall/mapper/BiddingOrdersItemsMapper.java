package www.tonghao.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.BiddingOrdersItems;
import www.tonghao.service.common.base.BaseMapper;

public interface BiddingOrdersItemsMapper extends BaseMapper<BiddingOrdersItems> {
	
		List<BiddingOrdersItems>  getByOrderId(@Param("orderId")Long orderId);
		Long selectByOrderId(@Param("orderId")Long orderId);
		BiddingOrdersItems selectMaxTypeNum(@Param("orderId")Long orderId);
		List<Long> selectItems(@Param("orderId")Long orderId);
	
}