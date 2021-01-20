package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.Constant;
import www.tonghao.common.SysLog;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.Cart;
import www.tonghao.mall.entity.CartItems;
import www.tonghao.mall.service.CartItemsService;
import www.tonghao.mall.service.CartService;
import www.tonghao.mall.service.PayWayService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.ActivityService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.utils.UserUtil;

@Api(value = "mallCartController", description = "商城购物车")
@RestController(value = "mallCartController")
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemsService cartItemsService;

	@Autowired
	private PayWayService payWayService;

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private ActivityService activityService;

	@SysLog(opt = "查看购物车")
	@ApiOperation(value = "我的购物车")
	@RequestMapping(method = RequestMethod.GET)
	public LinkedHashMap<String, Map<String, List<CartItems>>> cart(HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if(user != null){
			return cartService.getUserCartItems(user.getId(), null);
		}
		return null;
	}

	/**
	 * 获取购物车里商品数量
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取购物车里商品数量")
	@RequestMapping(value = "/count_cart_items", method = RequestMethod.GET)
	public int countCartItems(HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return cartService.getCartCount(user.getId());
		}
		return 0;
	}

	/**
	 * 加入购物车
	 * 
	 * @param entity
	 * @param request
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "加入购物车")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pid", value = "商品ID", required = true, dataType = "int"),
		@ApiImplicitParam(name = "num", value = "数量", required = true, dataType = "int"), 
		@ApiImplicitParam(name = "activityId", value = "活动ID", required = false, dataType = "int"),
	})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> add(@RequestParam(required = true) long pid, @RequestParam(required = true) int num, HttpServletRequest request, Long activityId) {
		Users user = UserUtil.getUser(request);
		Map<String, Object> checkAddCart = cartService.checkAddCart(user, pid+"", num, activityId);
		if(ResultUtil.isSuccess(checkAddCart)){
			cartService.addToCart(user, pid+"", num, activityId);
		}
		return checkAddCart;
	}

	@ApiOperation(value = "编辑购物车")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "购物车项ID", required = true, dataType = "int"),
		@ApiImplicitParam(name = "num", value = "数量", required = true, dataType = "int"), 
	})
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Map<String, Object> editNum(@RequestParam(required = true) long id, @RequestParam(required = true) int num, HttpServletRequest request) {
		if (num < 1 || num > Constant.MAX_PRODUCT_CART_COUNT) {
			return ResultUtil.error("购买数量限制");
		}
		Users user = UserUtil.getUser(request);
		if (user == null) {
			return ResultUtil.error("登录失效,请重新登录");
		}
		Cart cart = cartService.getUserCart(user.getId());
		if (cart == null) {
			return ResultUtil.error("购物车不存在");
		}
		List<CartItems> cartItems = cart.getCartItems();
		if (cartItems == null) {
			return ResultUtil.error("购物车为空");
		}
		CartItems cartItem = null;
		for (CartItems item : cartItems) {
			if (item.getId().equals(id)) {
				cartItem = item;
				break;
			}
		}
		if (cartItem == null) {
			return ResultUtil.error("购物车项不存在");
		}
		cartItem.setNum(num);
		cartItem.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		cartItemsService.updateNotNull(cartItem);
		return ResultUtil.success("");
	}

	@ApiOperation(value = "复选框改变")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "item_ids", value = "购物车项ID数组", required = true, dataType = "int"),
		@ApiImplicitParam(name = "is_checked", value = "是否选中", required = true, dataType = "boolean"), 
	})
	@RequestMapping(value = "/cart_change", method = RequestMethod.PUT)
	public Map<String, Object> cartChange(@RequestParam(value = "item_ids[]")Long[] item_ids, Boolean is_checked, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if (user == null) {
			return ResultUtil.error("登录失效,请重新登录");
		}
		Cart cart = cartService.getUserCart(user.getId());
		if (cart == null) {
			return ResultUtil.error("购物车不存在");
		}
		List<CartItems> cartItems = cart.getCartItems();
		if (cartItems == null) {
			return ResultUtil.error("购物车为空");
		}
		if (item_ids != null && item_ids.length > 0 && is_checked != null) {
			for (CartItems item : cartItems) {
				for (Long id : item_ids) {
					if (item.getId().equals(id) && item.getIsChecked() != is_checked) {
						item.setIsChecked(is_checked);
						item.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
						cartItemsService.updateNotNull(item);
					}
				}
			}
		}
		return ResultUtil.success("");
	}

	@ApiOperation(value = "删除购物车项")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "购物车项ID数组", required = true, dataType = "long"), 
	})
	@RequestMapping(value = "/delete_item", method = RequestMethod.PUT)
	public Map<String, Object> delete_item(@RequestParam(name = "ids[]", required = true) Long[] ids, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if (user == null) {
			return ResultUtil.error("登录失效,请重新登录");
		}
		Cart cart = cartService.getUserCart(user.getId());
		if (cart == null) {
			return ResultUtil.error("购物车不存在");
		}
		List<CartItems> cartItems = cart.getCartItems();
		if (cartItems == null) {
			return ResultUtil.error("购物车为空");
		}
		for (Long id : ids) {
			CartItems cartItem = cartItemsService.findById(id);
			if (cartItem != null && cartItem.getCartId().equals(cart.getId())) {
				cartItemsService.delete(cartItem.getId());
			}
		}
		return ResultUtil.success("");
	}

	@ApiOperation(value = "清空购物车")
	@RequestMapping(value = "/clear", method = RequestMethod.DELETE)
	public Map<String, Object> clear(HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if (user == null) {
			return ResultUtil.error("登录失效,请重新登录");
		}
		Cart cart = cartService.getUserCart(user.getId());
		if (cart == null) {
			return ResultUtil.error("购物车不存在");
		}
		cartService.truncate(cart);
		return ResultUtil.success("");
	}

	@ApiOperation(value = "检查跳转")
	@GetMapping(value = "/check_skip")
	public Map<String, Object> checkSkip(HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		if (user == null) {
			return ResultUtil.error("登录失效,请重新登录");
		}
		if (user.getType() == null || user.getType() != 1 || user.getType() !=6) {
			return ResultUtil.error("非采购人或管理员用户不允许采购");
		}
		List<CartItems> cartItemsList = cartItemsService.selectCheckItemsByUser(user.getId());
		return cartService.checkSkip(user, null, cartItemsList);
	}
	
	@ApiOperation(value = "订单购物车")
	@RequestMapping(value = "/order_cart", method = RequestMethod.GET)
	public Map<String, Map<String, List<CartItems>>> order_cart(HttpServletRequest request, ModelMap model) {
		Users user = UserUtil.getUser(request);
		if(user != null){
			return cartService.getUserCartItems(user.getId(), 1);
		}
		return null;
	}
	

}
