package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.service.common.entity.ProductEvaluation;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.ProductEvaluationService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * Description: 商品评价控制类
 * 
 * @version 2019年6月26日
 * @since JDK1.8
 */
@Api(value="productEvaluationController", description="商品评价api")
@RestController
@RequestMapping("/productEvaluation")
public class ProductEvaluationController {

	@Autowired
	private ProductEvaluationService productEvaluationService;
	
	@Autowired
	private OrderItemsService orderItemsService;
	
	@Autowired
	private ProductsService productsService;
	
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/productList", method=RequestMethod.GET)
	public PageInfo<OrderItems> productList(@ModelAttribute PageBean page, Integer isEvaluation, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Users user = UserUtil.getUser(request);
		if(user != null){
			PageHelper.startPage(page.getPage(), page.getRows());
			map.put("userId", user.getId());
			map.put("isEvaluation", isEvaluation);
			List<OrderItems> list = orderItemsService.findByMap(map);
			return new PageInfo<OrderItems>(list);
		}
		return null;
	}
	
	@ApiOperation(value="新增评价",notes="新增评价api")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody ProductEvaluation productEvaluation, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user == null){
			return ResultUtil.error("用户未登录");
		}
		Map<String, Object> save = productEvaluationService.save(productEvaluation, user);
		if("success".equals(save.get("status"))){
			//更新订单明细评价状态
			OrderItems orderItems = new OrderItems();
			orderItems.setId(productEvaluation.getItemId());
			orderItems.setIsEvaluation(1);
			orderItemsService.updateNotNull(orderItems);
		}
		return save;
	}
	
	@ApiOperation(value="查看评价信息详情",notes="查看评价信息详情api")
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public ProductEvaluation view(Long itemId, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			OrderItems orderItems = orderItemsService.selectByKey(itemId);
			if(orderItems != null && orderItems.getProductId() != null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productId", orderItems.getProductId());
				map.put("itemId", orderItems.getId());
				map.put("userId", user.getId());
				return productEvaluationService.productEvaluationView(map);
			}
		}
		return null;
	}
	
	@ApiOperation(value="获取商品所有的评价信息",notes="查看评价信息详情api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="productId",value="商品id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="level",value="评价等级  0全部  1好评  2中评  3差评",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getProductEvaluationList", method=RequestMethod.GET)
	public PageInfo<ProductEvaluation> getProductEvaluationList(@ModelAttribute PageBean page, Long productId, Integer level){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		if(level != null && level != 0){
			map.put("level", level);
		}
		List<ProductEvaluation> list = productEvaluationService.productEvaluationList(map);
		return new PageInfo<ProductEvaluation>(list);
	}
	
	@ApiOperation(value="查看商品信息",notes="查看商品信息api")
	@RequestMapping(value="/productView", method=RequestMethod.GET)
	public Products productView(Long productId, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			return productsService.selectByKey(productId);
		}
		return null;
	}
	
	@RequestMapping(value="/getProductRate", method=RequestMethod.GET)
	public ProductEvaluation getProductRate(Long productId){
		ProductEvaluation productRate = productEvaluationService.getProductRate(productId);
		return productRate;
	}
	
}
