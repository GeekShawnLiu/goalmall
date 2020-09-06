package www.tonghao.mall.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import www.tonghao.mall.entity.Cart;
import www.tonghao.mall.entity.CartItems;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Users;

public interface CartService extends BaseService<Cart>{
	
	/**
	 * 新增
	 * @param cart
	 * @return
	 */
	Cart add(Cart cart);
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	Cart findById(Long id);

	/**
	 * 得到用户购物车
	 * @param userId
	 * @return
	 */
	Cart getUserCart(Long userId);
	
	/**
	 * 删除订单选择项
	 * @param cart
	 */
	void deleteOrderChecked(Cart cart);
	
	/**
	 * 清空购物车
	 * @param cart
	 */
	void truncate(Cart cart);
	
	/**
	 * 
	 * Description: 获取当前用户的购物车明细
	 * 
	 * @data 2019年4月30日
	 * @param 
	 * @return
	 */
	LinkedHashMap<String, Map<String, List<CartItems>>> getUserCartItems(Long userId, Integer isChecked);
	
	/**
	 * 
	 * Description: 购物车检查跳转
	 * 
	 * @data 2019年5月3日
	 * @param 
	 * @return
	 */
	Map<String, Object> checkSkip(Users user, Long areaId, List<CartItems> cartItemsList);
	
	/**
	 * 
	 * Description: 查询购物车项
	 * 
	 * @data 2019年5月8日
	 * @param 
	 * @return
	 */
	List<CartItems> findListByUserId(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 商品添加购物车校验     批量校验时num为1
	 * 
	 * @data 2019年8月5日
	 * @param 
	 * @return
	 */
	Map<String, Object> checkAddCart(Users user, String ids, int num, Long activityId);
	
	/**
	 * 
	 * Description: 商品加入购物车（可批量）
	 * 
	 * @data 2019年8月5日
	 * @param 
	 * @return
	 */
	void addToCart(Users user, String ids, int num, Long activityId);
	
	/**
	 * 
	 * Description: 获取购物车商品数量
	 * 
	 * @data 2019年9月6日
	 * @param 
	 * @return
	 */
	Integer getCartCount(Long userId);
}
