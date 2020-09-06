package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.FavoritesProduct;
import www.tonghao.service.common.base.BaseService;

public interface FavoritesProductService  extends BaseService<FavoritesProduct> {

	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<FavoritesProduct> findListByEntity(FavoritesProduct entity);
	
	/**
	 * 获取用户收藏的商品ID列表
	 * @param userId
	 * @return
	 */
	List<Long> findUserProductIds(Long userId);
	
	/**
	 * 
	 * Description: 根据用户id和商品id查询
	 * 
	 * @data 2019年5月7日
	 * @param 
	 * @return
	 */
	List<FavoritesProduct> selectByPId(Long userId, Long productId);
}
