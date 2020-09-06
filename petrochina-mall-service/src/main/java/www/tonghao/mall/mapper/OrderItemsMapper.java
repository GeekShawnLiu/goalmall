package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.OrderItems;
import www.tonghao.service.common.base.BaseMapper;

public interface OrderItemsMapper extends BaseMapper<OrderItems> {
	
	/**
	 * 根据Id查找
	 * @param id
	 * @return
	 */
	OrderItems findById(Long id);
	
	/**
	 * 查询商品订单项
	 * @param productId
	 * @return
	 */
	List<OrderItems> findProductOrderItems(@Param("productId")Long productId);
	
	/**
	 * 
	 * Description: 条件查询订单明细
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	List<OrderItems> findByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 子订单详情
	 * 
	 * @data 2019年8月21日
	 * @param 
	 * @return
	 */
	List<OrderItems> findChildOrderItem(@Param("parentSn")Long parentSn, @Param("childSn")Long childSn);
	
	/**
	 * 
	 * Description: 商品统计
	 * 
	 * @data 2019年8月29日
	 * @param 
	 * @return
	 */
	List<OrderItems> productStatisticalList(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 根据订单和商品查询订单明细
	 * 
	 * @data 2019年8月30日
	 * @param 
	 * @return
	 */
	OrderItems selectByOrderAndProduct(@Param("orderId")Long orderId, @Param("productId")Long productId);
	
}