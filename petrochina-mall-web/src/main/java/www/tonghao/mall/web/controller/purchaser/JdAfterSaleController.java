package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.JdAfterSaleService;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.JdAfsWaybill;
import www.tonghao.service.common.entity.JdAfterSale;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/jdAfterSale")
public class JdAfterSaleController {

	@Autowired
	private OrderItemsService orderItemsService;

	@Autowired
	private JdAfterSaleService jdAfterSaleService;
	
	@Autowired
	private OrdersService ordersService;

	@ApiOperation(value = "jd售后列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="orderSn",value="订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sn",value="售后编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="type",value="售后类型：退货(10)、换货(20)、维修(30)",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="userType",value="用户类型 1 个人用户   2供应商    3运营人员",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public PageInfo<JdAfterSale> list(HttpServletRequest request, PageBean page, Integer userType, String orderSn, String sn, Integer type, Integer status) {
		Users user = UserUtil.getUser(request);
		if (user != null || userType != null) {
			PageHelper.startPage(page.getPage(), page.getRows());
			Map<String, Object> map = new HashMap<>();
			if(userType == 1){
				map.put("userId", user.getId());
			}else if(userType == 2){
				map.put("supplierId", user.getTypeId());
			}else if(userType == 3){
				//运营人员 查看所有
			}
			map.put("orderSn", orderSn);
			map.put("sn", sn);
			map.put("type", type);
			map.put("status", status);
			List<JdAfterSale> list = jdAfterSaleService.findListByMap(map);
			if(CollectionUtils.isNotEmpty(list)){
				list.forEach(a -> {
					OrderItems orderItems = orderItemsService.selectByKey(a.getOrderItemId());
					if(orderItems != null){
						a.setProductThumbnail(orderItems.getThumbnail());
						a.setProductPrice(orderItems.getPrice());
						a.setOrderId(orderItems.getOrderId());
						a.setProductId(orderItems.getProductId());
					}
				});
			}
			return new PageInfo<JdAfterSale>(list);
		}
		return null;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ApiOperation(value = "查询商品是否可进行售后", notes = "查询商品是否可进行售后")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "productId", value = "商品id", required = true, dataType = "int", paramType = "query"), 
	})
	public Map<String, Object> check(HttpServletRequest request, Long orderId, Long productId) {
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.checkAfterSale(orderId, productId, user);
		}
		return ResultUtil.error("未获取到登录信息");
	}
	
	@RequestMapping(value = "/getAfsType", method = RequestMethod.GET)
	@ApiOperation(value = "查询商品售后类型以及逆向配送方式", notes = "查询商品售后类型以及逆向配送方式")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "productId", value = "商品id", required = true, dataType = "int", paramType = "query"),
	})
	public Map<String, Object> getAfsType(HttpServletRequest request, Long orderId, Long productId) {
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.findAfsType(orderId, productId, user);
		}
		return ResultUtil.error("未获取到登录信息");
	}

	@ApiOperation(value = "获取订单明细")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "productId", value = "商品id", required = true, dataType = "int", paramType = "query"),
	})
	@RequestMapping(value = "/getOrderItem", method = RequestMethod.GET)
	public OrderItems getOrderItem(Long orderId, Long productId, HttpServletRequest request) {
		OrderItems orderItem = orderItemsService.selectByOrderAndProduct(orderId, productId);
		Orders order = ordersService.selectByKey(orderId);
		Users user = UserUtil.getUser(request);
		if (order != null && user != null) {
			orderItem.setOrder(order);
			return orderItem;
		}
		return null;
	}

	@ApiOperation(value = "提交售后申请")
	@RequestMapping(value = "/createAfsApply", method = RequestMethod.POST)
	public Map<String, Object> createAfsApply(HttpServletRequest request, @RequestBody JdAfterSale jdAfterSale) {
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.createAfsApply(jdAfterSale, user);
		}
		return ResultUtil.error("未获取到登录信息");
	}
	
	@ApiOperation(value="取消售后申请")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "业务ID", required = true, dataType = "int") 
	})
	@RequestMapping(value="/cancelAfsApply", method=RequestMethod.PUT)
	public Map<String, Object> cancelAfsApply(HttpServletRequest request, @RequestParam(name="id") Long id){
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.cancelAfsApply(id);
		}
		return ResultUtil.error("未获取到登录信息");
	}
	
	@ApiOperation(value="售后申请详情")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "售后记录ID", required = true, dataType = "int") 
	})
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public JdAfterSale view(HttpServletRequest request, Long id){
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.view(id);
		}
		return null;
	}
	
	@ApiOperation(value="补充发运单信息")
	@RequestMapping(value="/addWaybillInfo", method=RequestMethod.POST)
	public Map<String, Object> addWaybillInfo(HttpServletRequest request, @RequestBody JdAfsWaybill jdAfsWaybill){
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.addWaybillInfo(jdAfsWaybill, user);
		}
		return ResultUtil.error("未获取到登录信息");
	}
	
	@ApiOperation(value="查看发运单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "jdAfsId", value = "售后记录ID", required = true, dataType = "int") 
	})
	@RequestMapping(value="/waybillView", method=RequestMethod.GET)
	public JdAfsWaybill waybillView(HttpServletRequest request, Long jdAfsId){
		Users user = UserUtil.getUser(request);
		if (user != null) {
			return jdAfterSaleService.waybillView(jdAfsId);
		}
		return null;
	}
	
	@ApiOperation(value = "jd售后列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="sn",value="售后编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="售后状态：处理中(1)、已完成(4)",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value = "/h5_afsList", method = RequestMethod.GET)
	public PageInfo<JdAfterSale> afsList(HttpServletRequest request, PageBean page, String sn, Integer status) {
		Users user = UserUtil.getUser(request);
		if (user != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("sn", sn);
			map.put("status", status);
			PageHelper.startPage(page.getPage(), page.getRows());
			List<JdAfterSale> list = jdAfterSaleService.findH5ListByMap(map);
			PageInfo<JdAfterSale> pageInfo = new PageInfo<JdAfterSale>(list);
			if(pageInfo != null){
				List<JdAfterSale> list22 = pageInfo.getList();
				for(JdAfterSale a : list22){
					OrderItems orderItems = orderItemsService.selectByKey(a.getOrderItemId());
					if(orderItems != null){
						a.setProductThumbnail(orderItems.getThumbnail());
						a.setProductPrice(orderItems.getPrice());
						a.setOrderId(orderItems.getOrderId());
						a.setProductId(orderItems.getProductId());
					}
				}
			}
			return pageInfo;
		}
		return null;
	}
}
