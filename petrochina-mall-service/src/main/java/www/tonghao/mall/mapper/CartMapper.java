package www.tonghao.mall.mapper;

import www.tonghao.mall.entity.Cart;
import www.tonghao.service.common.base.BaseMapper;

public interface CartMapper extends BaseMapper<Cart> {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	Cart findById(Long id);
	
	/**
	 * 查询用户购物车
	 * @param userId
	 * @return
	 */
	Cart getUserCart(Long userId);
	
	/**
	 * 
	 * Description: 获取购物车数量
	 * 
	 * @data 2019年9月6日
	 * @param 
	 * @return
	 */
	Integer getCartCount(Long userId);
}