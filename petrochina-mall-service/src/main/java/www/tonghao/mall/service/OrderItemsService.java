package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import www.tonghao.mall.entity.OrderItems;
import www.tonghao.service.common.base.BaseService;

public interface OrderItemsService extends BaseService<OrderItems> {

	/**
	 * 根据Id查找
	 * 
	 * @param id
	 * @return
	 */
	OrderItems findById(Long id);

	/**
	 * 查询商品订单项
	 * 
	 * @param productId
	 * @return
	 */
	List<OrderItems> findProductOrderItems(Long productId);

	/**
	 * 
	 * Description: 查询用户订单明细
	 * 
	 * @data 2019年6月26日
	 * @param
	 * @return
	 */
	List<OrderItems> findByMap(Map<String, Object> map);

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
	 * Description: 商品统计导出excel
	 * 
	 * @data 2019年8月29日
	 * @param
	 * @return
	 */
	void downloadProductStatistical(Map<String, Object> map, HttpServletResponse response);
	
	/**
	 * 
	 * Description: 根据订单和商品查询订单明细
	 * 
	 * @data 2019年8月30日
	 * @param 
	 * @return
	 */
	OrderItems selectByOrderAndProduct(Long orderId, Long productId);
}
