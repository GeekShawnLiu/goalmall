package www.tonghao.mall.web.controller.purchaser;

import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.enums.FavorutesProductType;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.FavoritesProduct;
import www.tonghao.mall.service.CartItemsService;
import www.tonghao.mall.service.CartService;
import www.tonghao.mall.service.FavoritesProductService;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 收藏夹 - 商品
 * @author developer001
 *
 */
@Api(value="purchaserFavoritesProductController",description="采购人商品收藏夹")
@RestController
@RequestMapping("/purchaser/favorites_product")
public class FavoritesProductController {
	
	@Autowired
	private FavoritesProductService favoritesProductService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MallProductService mallProductService;
	
	@Autowired
	private CartItemsService cartItemsService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<FavoritesProduct> getPage(@ModelAttribute PageBean page, HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		
		Users user = UserUtil.getUser(request);
		FavoritesProduct entity = new FavoritesProduct();
		entity.setUserId(user.getId());
		List<FavoritesProduct> list = favoritesProductService.findListByEntity(entity );
		
		return new PageInfo<FavoritesProduct>(list);
	}
	
	@ApiOperation(value="添加",notes="添加api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="productId",value="商品ID",required=true,dataType="long"),
	})
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Map<String, Object> add(Long productId, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user==null){
			return ResultUtil.error("请先登录");
		}
		Example example = new Example(FavoritesProduct.class);
		Criteria critria = example.createCriteria();
		critria.andEqualTo("userId", user.getId());
		critria.andEqualTo("productId",productId);
		long count = favoritesProductService.selectCountByExample(example);
		if(count==0){
			FavoritesProduct favoritesProduct = new FavoritesProduct();
			favoritesProduct.setProductId(productId);
			favoritesProduct.setUserId(user.getId());
			favoritesProduct.setType(FavorutesProductType.mall);
			favoritesProduct.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			favoritesProductService.save(favoritesProduct);
		}
		return ResultUtil.success("");
	}
	
	@ApiOperation(value="移除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="long"),
	})
	@RequestMapping(value="/remove",method=RequestMethod.DELETE)
	public Map<String, Object> remove(Long id, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		List<FavoritesProduct> selectByPId = favoritesProductService.selectByPId(user.getId(), id);
		if(!Collections.isEmpty(selectByPId)){
			favoritesProductService.delete(selectByPId.get(0).getId());
		}
		return ResultUtil.success("");
	}
	
	/**
	 * 获取当前用户已收藏的商品ID列表
	 * @return
	 */
	@ApiOperation(value="获取当前用户已收藏的商品ID列表")
	@GetMapping(value="/get_current_pids")
	public List<Long> getCurrentPorductIds(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			return favoritesProductService.findUserProductIds(user.getId());
		}
		return new ArrayList<Long>();
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="long"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		FavoritesProduct favoritesProduct = favoritesProductService.selectByKey(id);
		if(favoritesProduct!=null&&favoritesProduct.getUserId().equals(user.getId())){
			favoritesProductService.delete(id);
		}
		return ResultUtil.success("");
	}
	
	@ApiOperation(value="批量添加购物车",notes="批量添加购物车api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "商品ID数组", required = true, dataType = "String"),
	})
	@RequestMapping(value = "/add_cart", method = RequestMethod.POST)
	public Map<String, Object> delete(String ids, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Map<String, Object> checkAddCart = cartService.checkAddCart(user, ids, 1, null);
		if(ResultUtil.isSuccess(checkAddCart)){
			cartService.addToCart(user, ids, 1, null);
		}
		return checkAddCart;
	}
	
	@ApiOperation(value="批量移除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "String"),
	})
	@RequestMapping(value="/batchRemove",method=RequestMethod.DELETE)
	public Map<String, Object> batchRemove(String ids, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			if(!StringUtils.isEmpty(ids)){
				String[] split = ids.split(",");
				for (String id : split) {
					favoritesProductService.delete(Long.parseLong(id));
				}
				return ResultUtil.success("");
			}else{
				return ResultUtil.error("请先选择商品");
			}
		}else{
			return ResultUtil.error("请先登录");
		}
	}
}
