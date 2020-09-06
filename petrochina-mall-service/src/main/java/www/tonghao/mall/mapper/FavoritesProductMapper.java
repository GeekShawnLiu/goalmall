package www.tonghao.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.FavoritesProduct;
import www.tonghao.service.common.base.BaseMapper;

public interface FavoritesProductMapper extends BaseMapper<FavoritesProduct> {
	
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
	 * Description: 根据商品id查询
	 * 
	 * @data 2019年5月7日
	 * @param 
	 * @return
	 */
	List<FavoritesProduct> selectByPId(@Param("userId")Long userId, @Param("productId")Long productId);
}