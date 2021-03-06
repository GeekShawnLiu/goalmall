package www.tonghao.mall.web.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;



@RestController
@RequestMapping("/admin/order")
@Api(value="管理员订单",description="管理员订单Api")
public class AdminOrderController {
	
	@Autowired
	private OrdersService ordersService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="dateRange",value="时间范围(all|month_3|year_1)",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="订单状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="contractStatus",value="合同状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sn",value="订单号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="emallSn", value = "电商订单号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name="productName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="consigneeName",value="收货人",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="userName",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name = "supplierName", value = "供应商名称", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "userMobile", value = "登录人手机号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "mobile", value = "收货人手机号", required = false, dataType = "string", paramType = "query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Orders> getPage(@ModelAttribute PageBean page
			,String dateRange,String status,String contractStatus, String emallSn, String supplierName
			,String sn,String productName,String consigneeName, HttpServletRequest request, String userName, String userMobile, String mobile){
		Users user = UserUtil.getUser(request);
		List<Orders> list =null;
		if(user!=null){
			PageHelper.startPage(page.getPage(), page.getRows());
			Orders entity = new Orders();
			Map<String, Object> queryFilter = Maps.newHashMap();
			String dateRangeStart = null;
			String dateRangeEnd = null;
			if(StringUtils.isNotEmpty(dateRange)){
				Date date = new Date();
				if(dateRange.equals("month_3")){
					dateRangeStart = DateUtilEx.timeFormat(DateUtils.addMonths(date, -3));
				}else if(dateRange.equals("year_1")){
					dateRangeStart = DateUtilEx.timeFormat(DateUtils.addYears(date, -1));
				}
				dateRangeEnd = DateUtilEx.timeFormat(date);
				queryFilter.put("dateRangeStart", dateRangeStart);
				queryFilter.put("dateRangeEnd", dateRangeEnd);
			}
			queryFilter.put("productName", productName);
			entity.setQueryfilter(queryFilter);
			entity.setOrdersState(EnumUtils.getEnum(OrderStatus.class, status));
			entity.setSn(sn);
			entity.setEmallSn(emallSn);
			entity.setConsigneeName(consigneeName);
			entity.setSupplierName(supplierName);
			//entity.setIsDelete(0);
			entity.setUserName(userName);
			entity.setUserMobile(userMobile);
			entity.setMobile(mobile);
			list= ordersService.findListByEntity(entity);
			for (Orders orders : list) {
				//判断是否包含子订单
				Example childExample = new Example(Orders.class);
				Criteria childCritria = childExample.createCriteria();
				childCritria.andEqualTo("isDelete", 0);
				childCritria.andEqualTo("parentId", orders.getId());
				List<Orders> childOrderList = ordersService.selectByExample(childExample);
				if(CollectionUtils.isNotEmpty(childOrderList)){
					orders.setIsHavaChild(1);
				}else{
					orders.setIsHavaChild(0);
				}
			}
		}
		return new PageInfo<Orders>(list);
	}
	@ApiOperation(value="根据编号查询",notes="查询单条api")
	@GetMapping(value="/getBySn")
	public Orders getBySn(String sn, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Orders order = ordersService.findBySn(sn);
		if(user!=null){
			order.setTrackList(ordersService.getOrderTrackById(order.getId()));
			return order;
		}
		return null;
	}
	@ApiOperation(value="查询供应商已完成订单",notes="查询供应商已完成订单api")
	@GetMapping(value="/getSupplierOrder")
	public Map<String, Integer> getSupplierOrder(HttpServletRequest request){
		Map<String, Integer> map=new HashMap<String, Integer>();
		Users user = UserUtil.getUser(request);
		if(user!=null){
			Example example=new Example(Orders.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("ordersState", "completed");
			createCriteria.andIsNull("parentId");
			List<Orders> orderList = ordersService.selectByExample(example);
			if(!CollectionUtils.isEmpty(orderList)){
				map.put("completed", orderList.size());
			}else{
				map.put("completed", 0);
			}
		}
		return map;
	}
	
	@ApiOperation(value="条件分页查询所有子订单列表", notes="查询所有子订单列表api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name = "parentSn", value = "父订单平台编号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "parentEmallSn", value = "父订单电商编号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "childSn", value = "子订单编号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "loginName", value = "登录名", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "userName", value = "姓名", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "userMobile", value = "用户手机号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "receiveName", value = "收货人", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "receiveMobile", value = "收货人手机号", required = false, dataType = "string", paramType = "query"),
	})
	@RequestMapping(value="getChildOrderPage", method=RequestMethod.GET)
	public PageInfo<Orders> getChildOrderPage(@ModelAttribute PageBean page, HttpServletRequest request, String parentSn, String childSn, 
			String loginName, String userName, String userMobile, String parentEmallSn,
			String receiveName, String receiveMobile){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Map<String, Object> map = new HashMap<>();
			map.put("parentSn", parentSn);
			map.put("childSn", childSn);
			map.put("userName", userName);
			map.put("userMobile", userMobile);
			map.put("parentEmallSn", parentEmallSn);
			map.put("loginName", loginName);
			map.put("receiveName", receiveName);
			map.put("receiveMobile", receiveMobile);
			PageHelper.startPage(page.getPage(), page.getRows());
			List<Orders> list = ordersService.selectChildOrderByMap(map);
			return new PageInfo<Orders>(list);
		}
		return null;
	}
	
	@ApiOperation(value="根据父订单查询子订单列表", notes="根据父订单查询子订单列表api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "父订单id", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "childSn", value = "子订单编号", required = false, dataType = "string", paramType = "query"),
	})
	@RequestMapping(value="getchildOrderByOrderId", method=RequestMethod.GET)
	public PageInfo<Orders> getchildOrderByOrderId(@ModelAttribute PageBean page, HttpServletRequest request, Long orderId, String childSn){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Example example = new Example(Orders.class);
			Criteria critria = example.createCriteria();
			critria.andEqualTo("isDelete", 0);
			critria.andEqualTo("parentId", orderId);
			List<Orders> childOrderList = ordersService.selectByExample(example);
			if(CollectionUtils.isNotEmpty(childOrderList)){
				Map<String, Object> map = new HashMap<>();
				map.put("orderId", orderId);
				map.put("childSn", childSn);
				PageHelper.startPage(page.getPage(), page.getRows());
				List<Orders> list = ordersService.selectChildOrderByMap(map);
				return new PageInfo<Orders>(list);
			}
		}
		return null;
	}
	
	@ApiOperation(value="获取订单物流信息", notes="获取订单物流信息api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
	})
	@RequestMapping(value="getOrdertrack", method=RequestMethod.GET)
	public String getOrdertrack(HttpServletRequest request, Long orderId){
		Users user = UserUtil.getUser(request);
		Orders orders = ordersService.selectByKey(orderId);
		if(user != null && orders != null){
			if (OrderStatus.completed.equals(orders.getOrdersState()) || OrderStatus.cancelled.equals(orders.getOrdersState())) {
				// 订单状态完成或取消直接获取保存的物流信息
				return orders.getTrack();
			} else {
				Orders track = ordersService.getTrack(orders);
				if(track != null){
					return track.getTrack();
				}
			}
		}
		return null;
	}
	
	@ApiOperation(value="获取子订单详情", notes="获取子订单详情api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "childOrderId", value = "子订单id", required = true, dataType = "int", paramType = "query"),
	})
	@RequestMapping(value="getchildOrderView", method=RequestMethod.GET)
	public Orders getchildOrderView(HttpServletRequest request, Long childOrderId){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Map<String, Object> map = new HashMap<>();
			map.put("childOrderId", childOrderId);
			return ordersService.getchildOrderView(map);
		}
		return null;
	}
}
