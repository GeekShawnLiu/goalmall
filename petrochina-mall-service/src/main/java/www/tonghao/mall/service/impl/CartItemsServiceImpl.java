package www.tonghao.mall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.entity.CartItems;
import www.tonghao.mall.mapper.CartItemsMapper;
import www.tonghao.mall.service.CartItemsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Activity;

@Service(value="mallCartItemsService")
public class CartItemsServiceImpl extends BaseServiceImpl<CartItems> implements CartItemsService {

	@Autowired
	private CartItemsMapper cartItemsMapper;
	
	@Override
	public CartItems findById(Long id) {
		return cartItemsMapper.findById(id);
	}

	@Override
	public List<CartItems> findListByCartId(Long cartId) {
		return cartItemsMapper.findListByCartId(cartId);
	}

	@Override
	public List<Activity> findCartIntegral(Long userId) {
		return cartItemsMapper.findCartIntegral(userId);
	}

	@Override
	public List<CartItems> findListByUserId(Map<String, Object> map) {
		return cartItemsMapper.findListByUserId(map);
	}

	@Override
	public List<CartItems> selectCheckItemsByUser(Long userId) {
		return cartItemsMapper.selectCheckItemsByUser(userId);
	}
}
