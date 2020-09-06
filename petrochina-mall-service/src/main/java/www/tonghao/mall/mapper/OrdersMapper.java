package www.tonghao.mall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.mall.entity.Orders;
import www.tonghao.service.common.base.BaseMapper;

public interface OrdersMapper extends BaseMapper<Orders> {
	
	/**
	 * 根据条件查询列表
	 * @param entity
	 * @return
	 */
	List<Orders> findListByEntity(Orders entity);
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	Orders findById(Long id);
	
	/**
	 * 根据编号查找
	 * @param sn
	 * @return
	 */
	Orders findBySn(String sn);
	/**
	 * 
	
	* <p>Description: 统计超市竞价的成交金额</p>  
	
	* @author mys  
	
	* @date 2019年1月14日
	 */
	BigDecimal countMallTotal();
	
	
	List<Orders> getOrdersByDistinct(@Param("supplierId")Long supplierId);
	
	/**
	 * 根据状态查询未删除订单列表
	 * @param orderStatus
	 * @return
	 */
	List<Orders> findValidListByStatus(@Param("orderStatus")OrderStatus orderStatus);
	
	/**
	 * 
	 * Description: 条件查询子订单列表信息
	 * 
	 * @data 2019年8月21日
	 * @param 
	 * @return
	 */
	List<Orders> selectChildOrderByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询所有子订单
	 * 
	 * @data 2019年8月29日
	 * @param 
	 * @return
	 */
	List<Orders> selectChildOrder(@Param("id")Long id);
	
	/**
	 * 
	 * Description: 订单统计
	 * 
	 * @data 2019年8月29日
	 * @param 
	 * @return
	 */
	List<Orders> orderStatisticalList(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 商品统计关联查询订单信息
	 * 
	 * @data 2019年8月29日
	 * @param 
	 * @return
	 */
	Orders orderStatisticalOne(Long id);
}