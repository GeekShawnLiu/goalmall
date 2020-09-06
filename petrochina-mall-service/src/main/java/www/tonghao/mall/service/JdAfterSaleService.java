package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.JdAfsWaybill;
import www.tonghao.service.common.entity.JdAfterSale;
import www.tonghao.service.common.entity.OrderSplitItem;
import www.tonghao.service.common.entity.Users;

public interface JdAfterSaleService extends BaseService<JdAfterSale>{

	/**
	 * 
	 * Description: 检查商品是否可进行售后
	 * 
	 * @data 2019年7月17日
	 * @param 
	 * @return
	 */
	public Map<String, Object> checkAfterSale(Long orderId, Long productId, Users user);
	
	/**
	 * 
	 * Description: 查询商品售后类型以及逆向配送方式
	 * 
	 * @data 2019年7月25日
	 * @param 
	 * @return
	 */
	public Map<String, Object> findAfsType(Long orderId, Long productId, Users user);
	
	/**
	 * 
	 * Description: 提交售后申请
	 * 
	 * @data 2019年7月17日
	 * @param 
	 * @return
	 */
	public Map<String, Object> createAfsApply(JdAfterSale jdAfterSale, Users user);
	
	/**
	 * 
	 * Description: 取消售后申请
	 * 
	 * @data 2019年7月17日
	 * @param 
	 * @return
	 */
	public Map<String, Object> cancelAfsApply(Long id);
	
	/**
	 * 
	 * Description: 售后信息详情
	 * 
	 * @data 2019年7月17日
	 * @param 
	 * @return
	 */
	public JdAfterSale view(Long id);
	
	/**
	 * 
	 * Description: 条件查询
	 * 
	 * @data 2019年7月18日
	 * @param 
	 * @return
	 */
	public List<JdAfterSale> findListByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 补充发运单信息
	 * 
	 * @data 2019年7月18日
	 * @param 
	 * @return
	 */
	public Map<String, Object> addWaybillInfo(JdAfsWaybill jdAfsWaybill, Users user);
	
	/**
	 * 
	 * Description: 查看发运单信息
	 * 
	 * @data 2019年7月23日
	 * @param 
	 * @return
	 */
	public JdAfsWaybill waybillView(Long jdAfsId);
	
	
	/**
	 * 
	 * Description: h5售后列表
	 * 
	 * @data 2019年7月18日
	 * @param 
	 * @return
	 */
	public List<JdAfterSale> findH5ListByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 根据订单明细查询拆单信息
	 * 
	 * @data 2019年8月12日
	 * @param 
	 * @return
	 */
	public OrderSplitItem selectByOrderItemId(Long orderId, Long productId, Long userId);
}
