package www.tonghao.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.FavoritesProduct;
import www.tonghao.mall.mapper.FavoritesProductMapper;
import www.tonghao.mall.service.FavoritesProductService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

/**
 * 收藏夹-商品
 * @author developer001
 *
 */
@Service("mallFavoritesProductService")
public class FavoritesProductServiceImpl extends BaseServiceImpl<FavoritesProduct> implements FavoritesProductService{

	@Autowired
	private FavoritesProductMapper favoritesProductMapper;
	
	@Override
	public List<FavoritesProduct> findListByEntity(FavoritesProduct entity) {
		return favoritesProductMapper.findListByEntity(entity);
	}

	@Override
	public List<Long> findUserProductIds(Long userId) {
		return favoritesProductMapper.findUserProductIds(userId);
	}

	@Override
	public List<FavoritesProduct> selectByPId(Long userId, Long productId) {
		return favoritesProductMapper.selectByPId(userId, productId);
	}
	
}
