package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.OrderRefundItem;
import www.tonghao.service.common.entity.Users;

public interface OrderRefundItemService extends BaseService<OrderRefundItem> {
	public List<OrderRefundItem> getOrderFundItem(Map<String, Object> map);
	
	
	public int refund(Long id,String webSiteAddress,Users users) throws Exception;
	
	/**
	 * 
	 * Description: 售后完成 退还支付金额
	 * 
	 * @data 2019年7月29日
	 * @param	afsId 售后id
	 * 			type  售后类型  1：jd售后    其他：其他售后
	 * @return
	 */
	Map<String, Object> afterSaleRefund(Long afsId, String type);
}
