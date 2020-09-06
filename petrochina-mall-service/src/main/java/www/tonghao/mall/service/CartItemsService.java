package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.entity.CartItems;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Activity;

public interface CartItemsService extends BaseService<CartItems>{
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	CartItems findById(Long id);
	
	/**
	 * 根据购物车ID查询购物车项列表
	 * @param cartId
	 * @return
	 */
	List<CartItems> findListByCartId(Long cartId);
	
	/**
	 * 
	 * Description: 获取购物车积分消费信息
	 * 
	 * @data 2019年5月5日
	 * @param 
	 * @return
	 */
	List<Activity> findCartIntegral(Long userId);
	
	/**
	 * 
	 * Description: 查询用户购物车明细
	 * 
	 * @data 2019年7月17日
	 * @param 
	 * @return
	 */
	List<CartItems> findListByUserId(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询用户购物车所有选中的商品
	 * 
	 * @data 2019年9月18日
	 * @param 
	 * @return
	 */
	List<CartItems> selectCheckItemsByUser(Long userId);
}
