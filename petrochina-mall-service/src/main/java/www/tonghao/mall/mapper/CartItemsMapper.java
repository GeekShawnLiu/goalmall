package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.CartItems;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Activity;

public interface CartItemsMapper extends BaseMapper<CartItems> {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	CartItems findById(Long id);
	
	/**
	 * 查询购物车项
	 * @param cartId
	 * @return
	 */
	List<CartItems> findListByCartId(Long cartId);
	
	/**
	 * 
	 * Description: 根据用户查询购物车项
	 * 
	 * @data 2019年4月30日
	 * @param 
	 * @return
	 */
	List<CartItems> findListByUserId(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 获取购物车积分消费信息
	 * 
	 * @data 2019年5月5日
	 * @param 
	 * @return
	 */
	List<Activity> findCartIntegral(@Param("userId")Long userId);
	
	/**
	 * 
	 * Description: 查询购物车所有的选中项
	 * 
	 * @data 2019年9月18日
	 * @param 
	 * @return
	 */
	List<CartItems> selectCheckItemsByUser(@Param("userId")Long userId);
}