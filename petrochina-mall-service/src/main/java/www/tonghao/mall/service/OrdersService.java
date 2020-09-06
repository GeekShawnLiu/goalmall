package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import www.tonghao.mall.entity.OrderForm;
import www.tonghao.mall.entity.Orders;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.OrderPayPrice;
import www.tonghao.service.common.entity.Users;

public interface OrdersService extends BaseService<Orders> {
	
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
	
	List<Orders> getOrdersByDistinct(Long supplierId );
	
	/**
	 * 
	 * Description: 校验下单信息
	 * 
	 * @data 2019年7月10日
	 * @param 
	 * @return
	 */
	Map<String, Object> orderCheck(OrderForm orderForm);
	
	/**
	 * 
	 * Description: 确认下单
	 * 
	 * @data 2019年5月8日
	 * @param 
	 * @return
	 */
	Map<String, Object> confirmOrder(OrderForm orderForm);
	
	/**
	 * 创建订单
	 * @param orderlist
	 * @param orderForm
	 */
	Map<String, Object> create(List<Orders> orderlist,OrderForm orderForm);
	
	/**
	 * 电商订单确认
	 * @param orders
	 */
	void emallOrderConfirm(List<Orders> orders);
	
	/**
	 * 取消电商订单
	 * @param order
	 */
	boolean cancelEmallOrder(Orders order);
	
	/**
	 * 
	 * Description: 根据订单id查询物流
	 * 
	 * @data 2019年5月24日
	 * @param 
	 * @return
	 */
	List<Orders> getOrderTrackById(Long id);
	
	/**
	 * 
	 * Description: 订单扣款
	 * 
	 * @data 2019年7月9日
	 * @param 
	 * @return
	 */
	Map<String, Object> deductions(List<Orders> orderlist, OrderForm orderForm);
	
	/**
	 * 
	 * Description: 订单退款
	 * 
	 * @data 2019年7月10日
	 * @param 
	 * @return
	 */
	void refund(Orders orders);
	
	
	String payQRCode(Long orderMasterId,Long userId,String webSiteAddress) throws Exception;
	
	
	int updatePayStatus(String orderNo,String json,String resultCode);
	
	List<OrderPayPrice> getOrderPayPrice(Long orderMsterId,Long ordersCode);
	
	/**
	 * 
	 * Description: 15分钟未完成支付自动取消订单
	 * 
	 * @data 2019年7月15日
	 * @param 
	 * @return
	 */
	void timeToCancelOrders(List<Orders> orders);
	
	int payIntegral(Long orderId);
	
	String payQRCodePlatform(Long orderId, Long userId,String webSiteAddress) throws Exception;
	
	int updatePayStatusPlatform(String orderNo,String json);
	
	int cancelOrderByConfirmed(Long orderId,Users users);
	
	
	String payH5Price(Users users,String info,Long orderMasterId,String webSiteAddress);
	
	Map<String, Object> payWxPrice(Users users,Long orderMasterId, String webSiteAddress) throws Exception;
	
	
	String payWxOrAliQrcode(Long orderMasterId,Long userId,String webSiteAddress,Integer payCode,Integer payType,String ip) throws Exception;
    
	String platPayWxOrAliQrcode(Long orderId,Long userId,String webSiteAddress,Integer payCode,Integer payType,String ip) throws Exception;

	Map<String, String> mobileWxOrAliQrcode(Long orderMasterId,Long userId,String webSiteAddress,Integer payCode,Integer payType,String ip,String openId)throws Exception;
	Map<String, String> mobileWxOrAliQrcodeOne(Long orderId,Long userId,String webSiteAddress,Integer payCode,Integer payType,String ip,String openId)throws Exception;
	
	String mobileWxMwebOrAliQrcode(Long orderMasterId,Long userId,String webSiteAddress,Integer payCode,Integer payType,String ip)throws Exception;
	String mobileWxMwebOrAliQrcodeOne(Long orderId,Long userId,String webSiteAddress,Integer payCode,Integer payType,String ip)throws Exception;
	
	
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
	 * Description: 获取订单的物流信息 
	 * 
	 * @data 2019年8月21日
	 * @param 
	 * @return
	 */
	Orders getTrack(Orders orders);
	
	/**
	 * 
	 * Description: 获取子订单详情
	 * 
	 * @data 2019年8月21日
	 * @param 
	 * @return
	 */
	Orders getchildOrderView(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 第三方供应商手动确认订单
	 * 
	 * @data 2019年8月24日
	 * @param 
	 * @return
	 */
	Map<String, Object> supplierConfirmedOrder(Long supplierId, Long orderId);
	
	/**
	 * 
	 * Description: 删除订单
	 * 
	 * @data 2019年8月26日
	 * @param 
	 * @return
	 */
	Map<String, Object> deleteOrder(Long userId, Long orderId);
	
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
	 * Description: 下载订单统计表
	 * 
	 * @data 2019年8月29日
	 * @param 
	 * @return
	 */
	void downloadOrderStatistical(Map<String, Object> map, HttpServletResponse response);
}
