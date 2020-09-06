package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.SysLog;
import www.tonghao.common.constant.RedisKeyConstant;
import www.tonghao.common.constant.SmSConstant;
import www.tonghao.common.enums.DealContractStatus;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.HttpClient;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.SelectOptionBuilder;
import www.tonghao.common.warpper.SelectOption;
import www.tonghao.config.OrderStatusConfig;
import www.tonghao.mall.core.ApiCommon;
import www.tonghao.mall.entity.OrderForm;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.CartService;
import www.tonghao.mall.service.InvoicesService;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.mall.service.OrderLogsService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.mall.service.PayWayService;
import www.tonghao.mall.service.ReceiverAddressesService;
import www.tonghao.mall.support.OrderHelper;
import www.tonghao.service.common.entity.OrderPayPrice;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.SystemSetting;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.OrganizationService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.SystemSettingService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

/**
 * 我的订单
 * 
 * @author developer001
 *
 */
@Api(value = "purchaserOrdersController", description = "采购人订单")
@RestController
@RequestMapping("/purchaser/orders")
@Scope("prototype")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ReceiverAddressesService receiverAddressesService;

	@Autowired
	private AreasService areasService;

	@Autowired
	private InvoicesService invoicesService;

	@Autowired
	private OrderHelper orderHelper;

	@Autowired
	private ProductsService productsService;

	@Autowired
	private MallProductService mallProductService;

	@Autowired
	private PayWayService payWayService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private OrderLogsService orderLogsService;
	
	@Autowired
	private OrderStatusConfig orderStatusConfig;
	
	@Autowired
	private IntegralUserService integralUserService;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Value("${webSiteAddress}")
	private String webSiteAddress;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private SystemSettingService systemSettingService;

	@ApiOperation(value = "分页查询", notes = "分页查询api")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "dateRange", value = "时间范围(all|month_3|year_1)", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "订单状态", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contractStatus", value = "合同状态", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sn", value = "订单号", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emallSn", value = "电商订单号", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplierName", value = "供应商名称", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "productName", value = "商品名称", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "consigneeName", value = "收货人", required = false, dataType = "string", paramType = "query"), })
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public PageInfo<Orders> getPage(@ModelAttribute PageBean page,
			String dateRange, String status, String contractStatus, String sn, String emallSn, String supplierName,
			String productName, String consigneeName, HttpServletRequest request) {
		PageHelper.startPage(page.getPage(), page.getRows());
		Orders entity = new Orders();
		Map<String, Object> queryFilter = Maps.newHashMap();
		String dateRangeStart = null;
		String dateRangeEnd = null;
		if (StringUtils.isNotEmpty(dateRange)) {
			Date date = new Date();
			if (dateRange.equals("month_3")) {
				dateRangeStart = DateUtilEx.timeFormat(DateUtils.addMonths(date, -3));
			} else if (dateRange.equals("year_1")) {
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
		Users user = UserUtil.getUser(request);
		entity.setUserId(user.getId());
		entity.setIsDelete(0);
		List<Orders> list = ordersService.findListByEntity(entity);
		if(!CollectionUtil.collectionIsEmpty(list)){
			for (Orders orders : list) {
				Example example=new Example(Orders.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("masterId", orders.getMasterId());
				List<Orders> order_list = ordersService.selectByExample(example);
				int i=0;
				if(!CollectionUtil.collectionIsEmpty(order_list)){
					for (Orders ord : order_list) {
						if(ord.getPayId()==1){
							i++;
						}
					}
				}
				if(order_list.size()==i){
					orders.setIsPay(0);
				}else{
					orders.setIsPay(1);
				}
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

	@ApiOperation(value = "根据编号查询", notes = "查询单条api")
	@GetMapping(value = "/getBySn")
	public Orders getBySn(String sn, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		Orders order = ordersService.findBySn(sn);
		if (order != null && order.getUserId().equals(user.getId())) {
			order.setTrackList(ordersService.getOrderTrackById(order.getId()));
			return order;
		}
		return null;
	}

	@ApiOperation(value = "电子发票", notes = "电子发票api")
	@ApiImplicitParams({ @ApiImplicitParam(name = "sn", value = "订单号", required = false, dataType = "string", paramType = "query"), })
	@RequestMapping(value = "/eInvoice", method = RequestMethod.GET)
	public Map<String, Object> eInvoice(String sn, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		Orders order = ordersService.findBySn(sn);
		if (order != null && order.getUserId().equals(user.getId())) {
			Suppliers supplier = order.getSupplier();
			if (supplier == null) {
				return ResultUtil.error("供应商信息获取失败");
			}
			if (ApiCommon.checkCode(supplier.getCode())
					&& StringUtils.isNotEmpty(order.getEmallSn())) {
				if (supplier.getCode().equals("jd")) {
					/*www.tonghao.mall.api.jd.call.GetInvoiceListApi api = new www.tonghao.mall.api.jd.call.GetInvoiceListApi(order.getEmallSn());
					www.tonghao.mall.api.jd.resultwrap.GetInvoiceListRes res = api.call();
					if (res.isSuccess() && res.isResult() != null && res.isResult().size() > 0) {
						return ResultUtil.success(res.isResult().get(0).getFileUrl());
					}*/
				} else if (supplier.getCode().equals("suning")) {
					/*return ResultUtil.error("苏宁暂不支持");*/
				} else if (supplier.getCode().equals("stb")) {
					
				} else {
					/*GetInvoiceListApi api = new GetInvoiceListApi(supplier.getCode(), order.getEmallSn());
					GetInvoiceListRes res = api.call();
					if (res.isSuccess() && res.getResult() != null && res.getResult() != null && !res.getResult().isEmpty()) {
						Set<Entry<String, List<String>>> entrySet = res.getResult().entrySet();
						for (Entry<String, List<String>> entry : entrySet) {
							if (entry.getValue() != null && entry.getValue().size() > 0) {
								return ResultUtil.success(entry.getValue().get(0).replaceAll("\"", ""));
							}
						}
					}*/
				}
			} else {
				return ResultUtil.error("非电商订单");
			}
		}
		return ResultUtil.error("操作失败");
	}

	/**
	 * 
	 * Description: 校验下单信息
	 * 
	 * @data 2019年7月11日
	 * @param 
	 * @return
	 */
	@SysLog(opt = "校验下单信息")
	@ApiOperation(value = "校验下单信息")
	@PostMapping(value = "/orderCheck")
	public Map<String, Object> orderCheck(OrderForm orderForm, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		orderForm.setUser(user);
		//校验下单信息
		Map<String, Object> orderCheck = ordersService.orderCheck(orderForm);
		if(ResultUtil.isSuccess(orderCheck)){
			orderForm = (OrderForm) orderCheck.get("orderForm");
			orderCheck.put("paywayId", orderForm.getPaywayId());
		}
		return orderCheck;
	}
	
	@SysLog(opt = "确认下单")
	@ApiOperation(value = "确认下单")
	@PostMapping(value = "/docreate")
	public Map<String, Object> doCreate(OrderForm orderForm, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		orderForm.setUser(user);
		//校验下单信息
		Map<String, Object> orderCheck = ordersService.orderCheck(orderForm);
		if(!ResultUtil.isSuccess(orderCheck)){
			return orderCheck;
		}
		orderForm = (OrderForm) orderCheck.get("orderForm");
		
		//获取是否开启混合支付  判断是否可以提交订单
		if(!systemSettingService.openMixturePay()){
			if(orderForm.getPaywayId() == null || orderForm.getPaywayId() == 2 || orderForm.getPaywayId() == 3){
				return ResultUtil.error("您的积分不足，订单提交失败！");
			}
		}
		
		//当前用户下单状态存入redis  控制重复下单
		String token = request.getHeader("token");
		boolean setNx = redisDao.setNx(RedisKeyConstant.CREATEORDER_USER+token+user.getId(), RedisKeyConstant.CREATEORDER_USER+token+user.getId());
		if(setNx){
			Map<String, Object> confirmOrder = null;
			try {
				confirmOrder = ordersService.confirmOrder(orderForm);
			} catch (Exception e) {
				e.printStackTrace();
				confirmOrder = ResultUtil.error("下单失败");
			} finally{
				redisDao.deleteKey(RedisKeyConstant.CREATEORDER_USER+token+user.getId());
			}
			return confirmOrder;
		}else{
			return ResultUtil.error("订单已提交，请勿重复下单！");
		}
	}
	
	@SysLog(opt = "取消下单")
	@ApiOperation(value = "取消", notes = "取消api")
	@ApiImplicitParams({ @ApiImplicitParam(name = "sn", value = "订单号", required = false, dataType = "string", paramType = "query"), })
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public Map<String, Object> cancel(String sn, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		Orders order = ordersService.findBySn(sn);
		if (order != null && order.getUserId().equals(user.getId()) && OrderStatus.commit.equals(order.getOrdersState())) {
			boolean isSuccess = true;
			if(ApiCommon.checkCode(order.getSupplier().getCode())){
				//电商
				isSuccess = ordersService.cancelEmallOrder(order);
			}else{
				isSuccess=true;
			}
			if (isSuccess) {
				ordersService.refund(order);
				return ResultUtil.success("取消订单成功");
			} else {
				return ResultUtil.error("取消失败");
			}
		}
		return ResultUtil.error("操作失败");
	}

	@ApiOperation(value = "统计数量", notes = "统计数量api")
	@ApiImplicitParams({ @ApiImplicitParam(name = "status", value = "订单状态", required = false, dataType = "string", paramType = "query"), })
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public long count(String status, HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		Example example = new Example(Orders.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", user.getId());
		if (StringUtils.isNotEmpty(status)) {
			criteria.andEqualTo("ordersState", EnumUtils.getEnum(OrderStatus.class, status));
		}
		criteria.andIsNull("parentId");
		return ordersService.selectCountByExample(example);
	}

	@ApiOperation(value = "获取下拉选项", notes = "获取下拉选项api")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "类型(1:订单状态;2:合同状态)", required = false, dataType = "int", paramType = "query"), })
	@RequestMapping(value = "/getselectOptions", method = RequestMethod.GET)
	public List<SelectOption> getSelectOptions(Integer type) {
		if (type != null) {
			List<SelectOption> optionList = new ArrayList<SelectOption>();
			if (type == 1) {
				OrderStatus[] enumConstants = OrderStatus.class.getEnumConstants();
				SelectOption selectOption = null;
				for(int i=0; i<enumConstants.length; i++){
					String label = orderStatusConfig.getOrderstatus().get(enumConstants[i].name());
					selectOption = new SelectOption(enumConstants[i].name(),label);
					optionList.add(selectOption);
				}
				return optionList;
			} else if (type == 2) {
				SelectOptionBuilder spb = new SelectOptionBuilder();
				return spb.enumToList(DealContractStatus.class);
			}
		}
		return null;
	}
	
	
	@ApiOperation(value = "获取支付倒计时", notes = "获取支付倒计时api")
	@RequestMapping(value = "/getExpireDate", method = RequestMethod.GET)
	public Map<String, Object> getExpireDate(Long orderMasterId,Long orderId,HttpServletRequest request) {
		if(orderMasterId!=null){
			Example example=new Example(Orders.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("masterId",orderMasterId);
			List<Orders> ordersList = ordersService.selectByExample(example);
			if(!CollectionUtil.collectionIsEmpty(ordersList)){
				Orders orders = ordersList.get(0);
				Date dates=null;
				String format="";
				try {
					dates=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
					Calendar nowTime = Calendar.getInstance();
					nowTime.setTime(dates);
					nowTime.add(Calendar.MINUTE, 15);
					format= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(nowTime.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				return ResultUtil.success(format);//正常状态
			}
		}
		if(orderId!=null){
			Orders ods = ordersService.selectByKey(orderId);
			Date dates=null;
			String format="";
			try {
				dates=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ods.getCreatedAt());
				Calendar nowTime = Calendar.getInstance();
				nowTime.setTime(dates);
				nowTime.add(Calendar.MINUTE, 15);
				format= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(nowTime.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return ResultUtil.success(format);//正常状态
		}
		return ResultUtil.error("获取失败");
	}
	
	@ApiOperation(value = "获取是否支付成功结果", notes = "获取是否支付成功结果api")
	@RequestMapping(value = "/getPayResult", method = RequestMethod.GET)
	public Map<String, Object> getSelectOptions(Long orderMasterId,Long orderId,HttpServletRequest request) {
		Users user = UserUtil.getUser(request);
		List<OrderPayPrice> orderPayPrice_list = ordersService.getOrderPayPrice(orderMasterId,orderId);
		System.out.println(orderPayPrice_list);
		if(!CollectionUtil.collectionIsEmpty(orderPayPrice_list)){
			boolean flg=false;
			for (OrderPayPrice orderPayPrice2 : orderPayPrice_list) {
				if(orderPayPrice2.getStatus()==1){
					flg=true;
				}
			}
			if(flg){
				return ResultUtil.success("1");//支付成功
			}else{
				flg=false;
				String ordersCode = orderPayPrice_list.get(0).getOrdersCode();
				if(!StringUtils.isEmpty(ordersCode)){
					String[] split = ordersCode.split("_");
					for (String sn : split) {
						Orders ods = ordersService.findBySn(sn);
						if(ods!=null&&!ods.getOrdersState().equals(OrderStatus.commit)){
							flg=true;
							break;
						}
					}
				}
				if(!flg){
					return ResultUtil.success("2");//正常状态
				}else{
					return ResultUtil.success("3");//支付异常
				}
			}
		}else{
		    return ResultUtil.success("2");//正常状态
			
		}
		
	}
	
	
	@ApiOperation(value = "积分去支付", notes = "积分去支付api")
	@RequestMapping(value = "/payIntegral", method = RequestMethod.GET)
	public Map<String, Object> getSelectOptions(Long orderId) {
		if(orderId!=null){
			int payResult = ordersService.payIntegral(orderId);
			if(payResult>0){
				return ResultUtil.success("支付成功");
			}
		}
		return ResultUtil.error("支付失败");
	}
	
	
	@ApiOperation(value = "待收货状态取消订单", notes = "待收货状态取消订单api")
	@RequestMapping(value = "/cancelOrderByConfirmed", method = RequestMethod.GET)
	public Map<String, Object> cancelOrderByConfirmed(Long orderId,HttpServletRequest request) {
		if(orderId!=null){
			Users user = UserUtil.getUser(request);
			int save = ordersService.cancelOrderByConfirmed(orderId, user);
			if(save==1){//订单取消成功
				Example example=new Example(SystemSetting.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("code", "operating_tel");
				List<SystemSetting> ss = systemSettingService.selectByExample(example);
				if(!CollectionUtil.collectionIsEmpty(ss)){
					Orders orders = ordersService.selectByKey(orderId);
					SystemSetting systemSetting = ss.get(0);
					if(!StringUtils.isEmpty(systemSetting.getSetValue())){
						Map<String, String> map = new HashMap<>();
						Long userId = orders.getUserId();
						Users ordersUser = usersService.selectByKey(userId);
						String username = "";
						if(ordersUser != null){
							username = ordersUser.getRealName();
						}
						String create_time=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
						String sn=orders.getSn();
						String payName=orders.getPayName();
						String order_create=orders.getCreatedAt();
						map.put("phoneNumbers", systemSetting.getSetValue());
						map.put("params", username+","+create_time+","+sn+","+payName+","+order_create);
						map.put("template", SmSConstant.ORDER_REFUND_PRICE_TEMPLATE_ID);
						HttpClient.doGet(SmSConstant.SMSURL, map);
						
					}
				}
				return ResultUtil.error("1");
			}else if(save==2){//订单已取消
				return ResultUtil.error("2");
			}
		}
		return ResultUtil.error("0");//订单取消异常
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
				map.put("userId", user.getId());
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
			map.put("userId", user.getId());
			return ordersService.getchildOrderView(map);
		}
		return null;
	}
	
	@ApiOperation(value="删除订单", notes="删除订单api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
	})
	@RequestMapping(value="deleteOrder", method=RequestMethod.DELETE)
	public Map<String, Object> deleteOrder(HttpServletRequest request, Long orderId){
		Users user = UserUtil.getUser(request);
		if(user != null){
			return ordersService.deleteOrder(user.getId(), orderId);
		}
		return ResultUtil.error("用户未登录");
	}
}
