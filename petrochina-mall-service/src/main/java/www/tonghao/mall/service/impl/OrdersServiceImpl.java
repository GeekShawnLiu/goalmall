package www.tonghao.mall.service.impl;

import io.jsonwebtoken.lang.Collections;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.Constant;
import www.tonghao.common.constant.EmallConstant;
import www.tonghao.common.constant.InvoiceConstant;
import www.tonghao.common.entity.OrderTrack;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.pay.PayUtils;
import www.tonghao.common.pay.utils.RSA;
import www.tonghao.common.pay.wx.WXPayUtil;
import www.tonghao.common.pay.wx.WXpayConfig;
import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.mall.api.jd.call.TrackApi;
import www.tonghao.mall.api.jd.entity.JdOrder;
import www.tonghao.mall.api.jd.entity.OrderSku;
import www.tonghao.mall.api.jd.entity.SkuPrice;
import www.tonghao.mall.api.jd.resultwrap.TrackRes;
import www.tonghao.mall.api.standard.attwrap.CreOrdSku;
import www.tonghao.mall.api.standard.attwrap.OrderCreateAttr;
import www.tonghao.mall.api.standard.attwrap.TrackAttr;
import www.tonghao.mall.api.standard.call.OrderConfirmApi;
import www.tonghao.mall.api.stb.attwrap.CreateOrderAttr;
import www.tonghao.mall.api.stb.attwrap.OrderTrackAttr;
import www.tonghao.mall.api.stb.attwrap.Track;
import www.tonghao.mall.api.stb.call.ConfirmOrderApi;
import www.tonghao.mall.api.stb.call.OrderCancelApi;
import www.tonghao.mall.api.stb.call.OrderCreateApi;
import www.tonghao.mall.api.stb.call.OrderTrackApi;
import www.tonghao.mall.api.stb.entity.Order;
import www.tonghao.mall.api.stb.resultwrap.OrderCancelRes;
import www.tonghao.mall.api.stb.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.stb.resultwrap.OrderTrackRes;
import www.tonghao.mall.core.ApiCommon;
import www.tonghao.mall.entity.CartItems;
import www.tonghao.mall.entity.OrderForm;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.OrderMaster;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.mapper.CartItemsMapper;
import www.tonghao.mall.mapper.InvoicesMapper;
import www.tonghao.mall.mapper.OrderItemsMapper;
import www.tonghao.mall.mapper.OrderMasterMapper;
import www.tonghao.mall.mapper.OrdersMapper;
import www.tonghao.mall.mapper.ReceiverAddressesMapper;
import www.tonghao.mall.service.CartService;
import www.tonghao.mall.service.OrderLogsService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.mall.support.OrderHelper;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.IntegralConsume;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.OrderPayPrice;
import www.tonghao.service.common.entity.OrderRefundItem;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.AreasMapper;
import www.tonghao.service.common.mapper.IntegralConsumeMapper;
import www.tonghao.service.common.mapper.IntegralUserMapper;
import www.tonghao.service.common.mapper.OrderInvoiceMapper;
import www.tonghao.service.common.mapper.OrderPayPriceMapper;
import www.tonghao.service.common.mapper.OrderRefundItemMapper;
import www.tonghao.service.common.mapper.OrganizationMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.MappingAreaService;
import www.tonghao.service.common.service.ProductsService;

@Service("mallOrdersService")
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements OrdersService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrganizationMapper organizationMapper;

	@Autowired
	private CartItemsMapper cartItemsMapper;

	@Autowired
	private ReceiverAddressesMapper receiverAddressesMapper;

	@Autowired
	private AreasMapper areasMapper;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderHelper orderHelper;

	@Autowired
	private OrderMasterMapper orderMasterMapper;

	@Autowired
	private OrderItemsMapper orderItemsMapper;

	@Autowired
	private ProductsService productsService;

	@Autowired
	private IntegralUserService integralUserService;

	@Autowired
	private SuppliersMapper suppliersMapper;

	@Autowired
	private OrderLogsService orderLogsService;

	@Autowired
	private IntegralConsumeMapper integralConsumeMapper;
	
	@Autowired
	private IntegralUserMapper integralUserMapper;
	
	@Autowired
	private OrderPayPriceMapper OrderPayPriceMapper;
	
	@Autowired
	private OrderRefundItemMapper orderRefundItemMapper;
	
	@Autowired
	private OrderPayPriceMapper orderPayPriceMapper;
	
	@Autowired
	private MappingAreaService mappingAreaService;
	
	@Autowired
	private InvoicesMapper invoicesMapper;
	
	@Autowired
	private OrderInvoiceMapper orderInvoiceMapper;

	ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);

	@Override
	public List<Orders> findListByEntity(Orders entity) {
		return ordersMapper.findListByEntity(entity);
	}

	@Override
	public Orders findById(Long id) {
		return ordersMapper.findById(id);
	}

	@Override
	public Orders findBySn(String sn) {
		return ordersMapper.findBySn(sn);
	}

	@Override
	public List<Orders> getOrdersByDistinct(Long supplierId) {
		return ordersMapper.getOrdersByDistinct(supplierId);
	}

	@Override
	public Map<String, Object> orderCheck(OrderForm orderForm) {
		Users user = orderForm.getUser();
		if (user == null) {
			return ResultUtil.error("登录失效");
		}
		if (user.getType() == null || user.getType() != 1) {
			return ResultUtil.error("非采购人用户不允许采购");
		}
		Organization org = organizationMapper.selectByPrimaryKey(user.getDepId());
		if (org == null) {
			return ResultUtil.error("采购人组织机构异常，请联系管理人员!");
		}
		if (orderForm.getRemark() != null) {
			if (!ValidatorUtil.isValidLength(orderForm.getRemark(), 255, true)) {
				return ResultUtil.error("备注长度不能大于255");
			}
		}
		ReceiverAddresses receiverAddresses = receiverAddressesMapper.selectByPrimaryKey(orderForm.getReceiverAddressId());
		if (receiverAddresses == null) {
			return ResultUtil.error("请选择收货地址");
		}
		Areas area = areasMapper.selectByPrimaryKey(receiverAddresses.getAreaId());
		if (area == null) {
			return ResultUtil.error("收货地址异常");
		}
		orderForm.setArea(area);
		orderForm.setReceiverAddress(receiverAddresses);
		//发票
		/*Invoices invoices = invoicesMapper.selectByPrimaryKey(orderForm.getInvoiceId());
		if(invoices == null){
			return ResultUtil.error("请选择发票信息");
		}
		orderForm.setInvoice(invoices);*/
		List<CartItems> cartItemsList = cartItemsMapper.selectCheckItemsByUser(user.getId());
		//校验商品信息
		Map<String, Object> checkSkipMap = cartService.checkSkip(user, area.getId(), cartItemsList);
		if ("error".equals(checkSkipMap.get("status"))) {
			return checkSkipMap;
		}
		if (Collections.isEmpty(cartItemsList)) {
			return ResultUtil.error("请先选择商品");
		}
		// 活动商品标识
		boolean activityProductFlag = false;
		for (CartItems cartItems2 : cartItemsList) {
			if (cartItems2.getActivityId() != null) {
				activityProductFlag |= true;
			}
		}
		//根据积分是否足够判断支付状态
		if (activityProductFlag) {
			//活动商品
			boolean integralflag = true;
			List<Activity> findCartIntegral = cartItemsMapper.findCartIntegral(user.getId());
			for (Activity activity : findCartIntegral) {
				BigDecimal integralBalance = activity.getIntegralBalance();
				if (integralBalance.compareTo(new BigDecimal(0)) < 0) {
					integralflag &= false;
				}
			}
			orderForm.setPaywayId(integralflag ? 1L : 3L);
		} else {
			// 非活动商品 个人支付
			orderForm.setPaywayId(2L);
		}
		Map<String, Object> success = ResultUtil.success("");
		success.put("orderForm", orderForm);
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> confirmOrder(OrderForm orderForm) {
		Users user = orderForm.getUser();
		List<CartItems> cartItems = cartItemsMapper.selectCheckItemsByUser(user.getId());
		// 根据供应商拆单
		Map<String, Object> splitOrdersBySupplier = orderHelper.splitOrdersBySupplier(cartItems, orderForm, user);
		if (splitOrdersBySupplier.get("status").equals("error")) {
			return ResultUtil.error(splitOrdersBySupplier.get("message").toString());
		}
		List<Orders> orderlist = (List<Orders>)splitOrdersBySupplier.get("orderList");
		// 电商下单
		Map<String, Object> map_result = create(orderlist, orderForm);
		if (map_result.get("status").equals("error")) {
			return ResultUtil.error(map_result.get("message").toString());
		} else {
			List<Orders> validOrders = (List<Orders>) map_result.get("order");
			if (!CollectionUtils.isEmpty(validOrders)) {
				// 支付
				Map<String, Object> deductions = deductions(validOrders, orderForm);
				if (!ResultUtil.isSuccess(deductions)) {
					// 调用取消预下单接口
					emallOrderCancel(validOrders);
					// 退还已经扣除的积分
					for (Orders orders : validOrders) {
						refund(orders);
					}
					// 删除订单信息
					validOrders.forEach(vo -> {
						vo.setIsDelete(1);
						ordersMapper.updateByPrimaryKey(vo);
					});
					return ResultUtil.error("下单失败");
				}
				
				//积分支付 自动确认订单
				if(orderForm.getPaywayId() != null && orderForm.getPaywayId() == 1){
					if (!CollectionUtils.isEmpty(validOrders)) {
						emallOrderConfirm(validOrders);
					}
				}
				
				//混合支付或者个人支付  15分钟未完成支付  自动取消订单
				if(orderForm.getPaywayId() != null && (orderForm.getPaywayId() == 2 || orderForm.getPaywayId() == 3)){
					if (!CollectionUtils.isEmpty(validOrders)) {
						timeToCancelOrders(validOrders);
					}
				}
				
				// 删除选中的购物车项
				if (!Collections.isEmpty(cartItems)) {
					for (CartItems item : cartItems) {
						if (item.getIsChecked()) {
							cartItemsMapper.deleteByPrimaryKey(item.getId());
						}
					}
				}
			}
			Object masterId = map_result.get("masterId");
			Object payId = map_result.get("payId");
			Map<String, Object> success = ResultUtil.success("下单成功");
			success.put("masterId", masterId);
			success.put("payId", payId);
			return success;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> create(List<Orders> orderlist, OrderForm orderForm) {
		BigDecimal totalAmount = BigDecimalUtil.ZERO;
		Users user = orderForm.getUser();
		OrderMaster master = new OrderMaster();
		master.setUserId(user.getId());
		master.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		orderMasterMapper.insert(master);
		
		Long masterId = master.getId();
		
		// 供应商 —— 订单ID
		Map<Suppliers, Long> supplierOrderMap = new HashMap<Suppliers, Long>();
		
		//记录返回值
		Map<String, Object> map = new HashMap<String, Object>();
		//回写数据
		map.put("masterId", masterId);
		map.put("payId", orderForm.getPaywayId());
		// 有效订单
		List<Orders> validOrders = new ArrayList<Orders>();
		// 创建orders
		for (int i = 0; i < orderlist.size(); i++) {
			Orders order = orderlist.get(i);
			order.setMasterId(masterId);
			order.setOrdersState(OrderStatus.commit);
			order.setIsReceipt(0);
			order.setIsCancel(0);
			save(order);
			/*if(order.getId() != null && order.getOrderInvoice() != null){
				OrderInvoice orderInvoice = order.getOrderInvoice();
				orderInvoice.setOrderId(order.getId());
				orderInvoiceMapper.insertSelective(orderInvoice);
			}*/
			orderLogsService.saveLog(user.getRealName(), "订单已提交", 0, order.getId());

			Suppliers supplier = order.getSupplier();
			/*if (ApiCommon.checkCode(supplier.getCode())) {
				emallOrderMap.put(supplier, order.getId());
			}*/
			//记录供应商的订单
			supplierOrderMap.put(supplier, order.getId());
			
			List<OrderItems> orderItems = order.getItems();
			// 创建orderItems
			for (int j = 0; j < orderItems.size(); j++) {
				OrderItems item = orderItems.get(j);
				item.setPlanItemBudgetIn(BigDecimalUtil.ZERO);
				item.setPlanItemBudgetFinance(BigDecimalUtil.ZERO);
				item.setPlanItemBudgetSelf(BigDecimalUtil.ZERO);
				item.setOrderId(order.getId());
				item.setOrderSn(order.getSn());
				item.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				orderItemsMapper.insert(item);
			}
			totalAmount = totalAmount.add(order.getTotal());
		}
		master.setTotal(totalAmount);
		orderMasterMapper.updateByPrimaryKeySelective(master);

		//下单
		if(supplierOrderMap.size() > 0){
			Set<Entry<Suppliers, Long>> entrySet = supplierOrderMap.entrySet();
			boolean isSuccess = true;
			try {
				ther: for (Entry<Suppliers, Long> entry : entrySet) {
					Suppliers supplier = entry.getKey();
					String emallCode = supplier.getCode();
					Orders order = findById(entry.getValue());

					// 刷新商品销售量、库存
					for (OrderItems orderItem : order.getItems()) {
						refreshMallProduct(orderItem);
					}

					if (emallCode.equals(EmallConstant.MALL_JD_CODE)) {
						JdOrder jdOrder = getJdOrder(order,supplier);
						www.tonghao.mall.api.jd.call.OrderCreateApi api = new www.tonghao.mall.api.jd.call.OrderCreateApi(jdOrder);
						www.tonghao.mall.api.jd.resultwrap.OrderCreateRes call = api.call();
						if(call.isSuccess()){
							www.tonghao.mall.api.jd.attwrap.OrderCreateAttr result = call.getResult();
							order.setEmallSn(result.getJdOrderId());
							order.setPaidAmount(result.getOrderPrice());
							updateNotNull(order);
							validOrders.add(order);
							map.put("status", "success");
						}else{
							isSuccess &= false;
							map.put("status", "error");
							String errorMsg = call.getResultMessage();
							//错误情况处理
							if(errorMsg != null && errorMsg.indexOf("[") != -1 && errorMsg.indexOf("]") != -1 && errorMsg.indexOf("[") < errorMsg.indexOf("]")){
								//商品不支持开增票，不能下单
								String sku = errorMsg.substring(errorMsg.indexOf("[") + 1, errorMsg.indexOf("]"));
								Products selectBySku = productsService.selectBySku(sku, supplier.getId());
								if(selectBySku != null){
									errorMsg = errorMsg.replace(sku, selectBySku.getName());
								}
							}else if(errorMsg != null && errorMsg.indexOf(":") != -1){
								String sku = errorMsg.substring(errorMsg.indexOf(":") + 1, errorMsg.length());
								Products selectBySku = productsService.selectBySku(sku, supplier.getId());
								if(selectBySku != null){
									errorMsg = errorMsg.replace(sku, selectBySku.getName());
								}
							}
							map.put("message", supplier.getName() + "下单失败," + errorMsg);
							logger.error("{}接口调用异常,信息:{}", supplier.getName(), call.getResultMessage());
							break ther;
						}
					} else if (emallCode.equals(EmallConstant.MALL_SN_CODE)) {
						
					} else if (emallCode.equals(EmallConstant.MALL_STB_CODE)) {
						OrderCreateApi stbOrderCreateApi = new OrderCreateApi(getStbOrder(order, user, emallCode));
						OrderCreateRes call = stbOrderCreateApi.call();
						if (call.isSuccess()) {
							CreateOrderAttr createOrderAttr = call.getCreateOrderAttr();
							BigDecimal orderPrice = createOrderAttr.getOrderPrice();
							String supplierOrderId = call.getCreateOrderAttr().getSupplierOrderId();
							order.setEmallSn(supplierOrderId);
							order.setPaidAmount(orderPrice);
							updateNotNull(order);
							validOrders.add(order);
							map.put("status", "success");
						} else {
							isSuccess &= false;
							map.put("status", "error");
							map.put("message", supplier.getName() + "下单失败," + call.getDesc());
							logger.error("{}接口调用异常,信息:{}", supplier.getName(), call.getDesc());
							break ther;
						}
					} else if (emallCode.equals(EmallConstant.MALL_DL_CODE)) {
						www.tonghao.mall.api.standard.entity.Order standardOrder = getStandardOrder(order, supplier, user);
						www.tonghao.mall.api.standard.call.OrderCreateApi api = new www.tonghao.mall.api.standard.call.OrderCreateApi(emallCode, standardOrder);
						www.tonghao.mall.api.standard.resultwrap.OrderCreateRes res = api.call();
						if (res.isSuccess()) {
							OrderCreateAttr result = res.getResult();
							BigDecimal orderPrice = result.getOrderPrice();
							String supplierOrderId = result.getMall_order_id();
							order.setEmallSn(supplierOrderId);
							order.setPaidAmount(orderPrice);
							updateNotNull(order);
							validOrders.add(order);
							map.put("status", "success");
						} else {
							isSuccess &= false;
							map.put("status", "error");
							map.put("message", supplier.getName() + "下单失败," + res.getDesc());
							logger.error("{}接口调用异常,信息:{}", supplier.getName(), res.getDesc());
							break ther;
						}
					}else{
						//不是以上电商  全部按照本地供应商处理
						validOrders.add(order);
						map.put("status", "success");
					}
				}
				map.put("order", validOrders);
			} finally {
				if (!isSuccess) {
					// 调用取消预下单接口
					emallOrderCancel(validOrders);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
				}
			}
		}
		return map;
	}

	/**
	 * 刷新商品信息
	 * 
	 * @param item
	 */
	private void refreshMallProduct(OrderItems item) {
		Products product = item.getProduct();
		product.setSales(product.getSales() != null ? product.getSales() + item.getNum() : item.getNum());
		Integer stock = product.getStock() == null ? 0 : product.getStock();
		stock = stock - item.getNum();
		if (stock < 0) {
			stock = 0;
		}
		product.setStock(stock);
		Integer monthSales = product.getMonthSales();
		Integer monthSalesIndex = product.getMonthSalesIndex();
		Date date = new Date();
		Integer currMonthSalesIndex = DateUtilEx.getIndexMonth(date);
		if (monthSalesIndex == currMonthSalesIndex) {
			monthSales = monthSales + item.getNum();
		} else {
			monthSales = item.getNum();
			monthSalesIndex = currMonthSalesIndex;
		}
		product.setMonthSales(monthSales);
		product.setMonthSalesIndex(monthSalesIndex);
		productsService.updateNotNull(product);
	}

	/**
	 * 订单取消
	 * 
	 * @param orders
	 */
	private void emallOrderCancel(List<Orders> orders) {
		if (!CollectionUtils.isEmpty(orders)) {
			for (Orders order : orders) {
				Suppliers supplier = order.getSupplier();
				if(ApiCommon.checkCode(supplier.getCode())){
					//电商
					doCancelEmallOrder(order);
				}
				order.setOrdersState(OrderStatus.cancelled);
				updateNotNull(order);
				orderLogsService.saveLog("系统", "订单已取消", 4, order.getId());
			}
		}
	}

	private boolean doCancelEmallOrder(Orders order) {
		Suppliers supplier = order.getSupplier();
		String emallCode = supplier.getCode();
		if (ApiCommon.checkCode(emallCode) && StringUtils.isNotBlank(order.getEmallSn())) {
			logger.info(emallCode + "调用取消预下单接口");
			if (emallCode.equals(EmallConstant.MALL_JD_CODE)) {
				www.tonghao.mall.api.jd.resultwrap.OrderCancelRes call = new www.tonghao.mall.api.jd.call.OrderCancelApi(order.getEmallSn()).call();
				if(call.isSuccess()&&call.isResult()){
					return true;
				}
			} else if (emallCode.equals(EmallConstant.MALL_SN_CODE)) {
				
			} else if (emallCode.equals(EmallConstant.MALL_STB_CODE)) {
				OrderCancelApi api = new OrderCancelApi(order.getEmallSn());
				OrderCancelRes call = api.call();
				if (call.isSuccess() && call.getResult()) {
					return true;
				}
			} else if (emallCode.equals(EmallConstant.MALL_DL_CODE)) {
				www.tonghao.mall.api.standard.resultwrap.OrderCancelRes res = new www.tonghao.mall.api.standard.call.OrderCancelApi(emallCode, order.getEmallSn()).call();
				if (res.isSuccess()) {
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}

	@Override
	public void emallOrderConfirm(List<Orders> orders) {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		// 异步调用
		ses.schedule(new Runnable() {
			@Override
			public void run() {
				for (Orders item : orders) {
					Orders order = findById(item.getId());
					Suppliers supplier = order.getSupplier();
					String emallCode = supplier.getCode();

					if (order != null && order.getOrdersState() == OrderStatus.commit) {
						if (ApiCommon.checkCode(emallCode)
								&& StringUtils.isNotBlank(order.getEmallSn())) {
							if (emallCode.equals(EmallConstant.MALL_JD_CODE)) {
								new www.tonghao.mall.api.jd.call.OrderConfirmApi(order.getEmallSn()).call();
								order.setOrdersState(OrderStatus.confirmed);
								updateNotNull(order);
								orderLogsService.saveLog("系统", "订单已确认", 1, order.getId());
							} else if (emallCode.equals("suning")) {
								//new www.tonghao.mall.api.sn.call.OrderConfirmApi(order.getEmallSn()).call();
							} else if (emallCode.equals(EmallConstant.MALL_STB_CODE)) {
								new ConfirmOrderApi(item.getEmallSn()).call();
								order.setOrdersState(OrderStatus.confirmed);
								updateNotNull(order);
								orderLogsService.saveLog("系统", "订单已确认", 1, order.getId());
							} else if (emallCode.equals(EmallConstant.MALL_DL_CODE)) {
								new OrderConfirmApi(emallCode, item.getEmallSn()).call();
								order.setOrdersState(OrderStatus.confirmed);
								updateNotNull(order);
								orderLogsService.saveLog("系统", "订单已确认", 1, order.getId());
							}
						}else{
							order.setOrdersState(OrderStatus.confirmed);
							updateNotNull(order);
							orderLogsService.saveLog("系统", "订单已确认", 1, order.getId());
						}
					}
				}
			}
		}, Constant.ORDER_CONFIRM_WAIT_TIME, TimeUnit.MINUTES);
	}

	@Override
	public boolean cancelEmallOrder(Orders order) {
		boolean isSuccess = doCancelEmallOrder(order);
		if (isSuccess) {
			order.setOrdersState(OrderStatus.cancelled);
			updateNotNull(order);
			orderLogsService.saveLog("系统", "订单已取消", 4, order.getId());
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Description: jd订单信息
	 * 
	 * @data 2019年7月18日
	 * @param 
	 * @return
	 */
	private JdOrder getJdOrder(Orders order, Suppliers supplier){
		JdOrder jdOrder=new JdOrder();
		MappingArea mappingArea = mappingAreaService.getEmallMappingArea(order.getAreaId(), supplier.getCode());
		if(mappingArea != null){
			String[] mappingAreas = mappingArea.getMappingCode().split("_");
			String province = mappingAreas[0];
			String city = mappingAreas[1];
			String county = mappingAreas[2];
			String town = null;
			if(mappingAreas.length==3){
				town = "0";
			}else{
				town = mappingAreas[3];
			}
			jdOrder.setProvince(province);
			jdOrder.setCity(city);
			jdOrder.setCounty(county);
			jdOrder.setTown(town);
		}
		jdOrder.setThirdOrder(order.getSn());
		jdOrder.setName(order.getConsigneeName());
		jdOrder.setAddress(StringUtils.substringAfter(order.getAddr(), " "));
		jdOrder.setMobile(order.getMobile());
		jdOrder.setEmail(order.getEmail());
		jdOrder.setRemark(order.getRemark());
		jdOrder.setInvoiceState(2);
		jdOrder.setInvoiceType(InvoiceConstant.INVOICE_TYPE);
		jdOrder.setSelectedInvoiceTitle(5);
		jdOrder.setCompanyName(InvoiceConstant.COMPANY);
		jdOrder.setRegCode(InvoiceConstant.ID_CODE);
		jdOrder.setInvoiceContent(1);
		jdOrder.setPaymentType(101);
		jdOrder.setSubmitState(0);
		List<OrderSku> list2 = new ArrayList<>();
		List<SkuPrice> orderPriceSnap = new ArrayList<>();
		List<OrderItems> items = order.getItems();
		for(OrderItems item:items){
			OrderSku orderSku=new OrderSku();
			orderSku.setSkuId(item.getSn());
			orderSku.setPrice(item.getProtocolPrice());
			orderSku.setNum(item.getNum());
			orderSku.setbNeedAnnex(false);
			orderSku.setbNeedGift(false);
			list2.add(orderSku);
			
			SkuPrice price=new SkuPrice();
			price.setPrice(item.getProtocolPrice());
			price.setSkuId(item.getSn());
			orderPriceSnap.add(price);	
		}
		jdOrder.setSku(list2);
		jdOrder.setDoOrderPriceMode(1);
		jdOrder.setOrderPriceSnap(orderPriceSnap);
		return jdOrder;
	}

	/**
	 * 
	 * Description: 史泰博订单信息
	 * 
	 * @data 2019年5月28日
	 * @param
	 * @return
	 */
	public Order getStbOrder(Orders order, Users user, String supplierCode) {
		Order stb_order = new Order();
		stb_order.setThirdOrder(order.getSn());

		List<Map<String, String>> list = new ArrayList<>();
		order.getItems().forEach(item -> {
			Map<String, String> map = new HashMap<>();
			map.put("skuId", item.getProduct().getSku());
			map.put("num", item.getNum() + "");
			list.add(map);
		});
		stb_order.setSku(list);
		MappingArea selectOne = mappingAreaService.getEmallMappingArea(order.getAreaId(), supplierCode);
		if (selectOne != null) {
			String mappingCode = selectOne.getMappingCode();
			if (StringUtils.isNotEmpty(mappingCode)) {
				String[] split = mappingCode.split("_");
				if (split.length >= 3) {
					stb_order.setProvince(split[0]);
					stb_order.setCity(split[1]);
					stb_order.setCounty(split[2]);
				}
			}
		}
		ReceiverAddresses receiverAddresses = receiverAddressesMapper.selectByPrimaryKey(order.getAddressId());
		stb_order.setName(receiverAddresses.getConsigneeName());
		stb_order.setAddress(receiverAddresses.getAddr());
		stb_order.setZip(receiverAddresses.getZipCode());
		stb_order.setMobile(receiverAddresses.getMobile());
		stb_order.setEmail(receiverAddresses.getEmail());
		stb_order.setRemark(order.getRemark());
		stb_order.setCreatorName(user.getRealName());
		stb_order.setCreatorMobile(user.getMobile());
		stb_order.setCreatedTime(DateUtilEx.timeFormat(new Date()));
		stb_order.setInvoiceState("1");
		stb_order.setInvoiceType(3 == InvoiceConstant.INVOICE_TYPE ? "4" : InvoiceConstant.INVOICE_TYPE + "");
		stb_order.setCompanyName(InvoiceConstant.COMPANY);
		if ("2".equals(order.getInvoiceType())) {
			stb_order.setCompanyAdd(InvoiceConstant.ADDRESS);// 发票地址 非必填
			stb_order.setBillingPhone(InvoiceConstant.PHONE);// 注册电话 非必填
			stb_order.setTaxNo(InvoiceConstant.ID_CODE);
			stb_order.setBankName(InvoiceConstant.BANK);// 银行名称 非必填
			stb_order.setBankAccno(InvoiceConstant.BANK_ACCOUNT);// 银行账户 非必填
		}
		stb_order.setInvoiceContent("1");
		stb_order.setPayment("2");
		String customerName = user.getDepName();
		if (user != null && user.getDepId() != null) {
			Organization org = organizationMapper.selectByPrimaryKey(user.getDepId());
			if (org != null) {
				customerName = org.getName();
			}
		}
		stb_order.setCustomerName(customerName);
		return stb_order;
	}

	@Override
	public List<Orders> getOrderTrackById(Long id) {
		Example example = new Example(Orders.class);
		Criteria critria = example.createCriteria();
		critria.andEqualTo("isDelete", 0);
		critria.andEqualTo("parentId", id);
		List<Orders> childOrderList = ordersMapper.selectByExample(example);
		List<Orders> trackList = new ArrayList<Orders>();
		if (!CollectionUtils.isEmpty(childOrderList)) {
			// 存在子订单
			for (Orders o : childOrderList) {
				if (OrderStatus.completed.equals(o.getOrdersState()) || OrderStatus.cancelled.equals(o.getOrdersState())) {
					// 订单状态完成或取消直接获取保存的物流信息
					trackList.add(o);
				} else {
					trackList.add(getTrack(o));
				}
			}
		} else {
			// 不存在子订单
			Orders orders = ordersMapper.selectByPrimaryKey(id);
			if (OrderStatus.completed.equals(orders.getOrdersState()) || OrderStatus.cancelled.equals(orders.getOrdersState())) {
				// 订单状态完成或取消直接获取保存的物流信息
				trackList.add(orders);
			} else {
				trackList.add(getTrack(orders));
			}
		}
		return trackList;
	}

	/**
	 * 
	 * Description:获取订单的物流信息 
	 * 
	 * @data 2019年5月24日
	 * @param
	 * @return
	 */
	@Override
	public Orders getTrack(Orders orders) {
		if (orders != null && orders.getSupplierId() != null) {
			Suppliers suppliers = suppliersMapper.selectByPrimaryKey(orders.getSupplierId());
			if (suppliers != null && suppliers.getCode() != null) {
				String code = suppliers.getCode();
				if (EmallConstant.MALL_STB_CODE.equals(code)) {
					// 史泰博
					OrderTrackApi orderTrackApi = new OrderTrackApi(orders.getEmallSn());
					OrderTrackRes call = orderTrackApi.call();
					if (call.isSuccess()) {
						OrderTrackAttr orderTrackAttr = call.getOrderTrackAttr();
						List<Track> trackList = orderTrackAttr.getTrackList();
						if (!CollectionUtils.isEmpty(trackList)) {
							List<OrderTrack> OrderTrackList = new ArrayList<OrderTrack>();
							for (int i = trackList.size() - 1; i >= 0; i--) {
								Track track = trackList.get(i);
								OrderTrack e = new OrderTrack();
								e.setContent(track.getContent());
								e.setOperateTime(track.getMsgTime());
								e.setOperator(StringUtil.strIsEmpty(track.getOperator()) ? "" : track.getOperator());
								OrderTrackList.add(e);
						    }
							String json = JsonUtil.toJson(OrderTrackList);
							orders.setTrack(json);
							ordersMapper.updateByPrimaryKeySelective(orders);
							return orders;
						}
					}
				} else if (EmallConstant.MALL_DL_CODE.equals(code)) {
					www.tonghao.mall.api.standard.call.OrderTrackApi orderTrackApi = new www.tonghao.mall.api.standard.call.OrderTrackApi(code, orders.getEmallSn());
					www.tonghao.mall.api.standard.resultwrap.OrderTrackRes call = orderTrackApi.call();
					if (call.isSuccess()) {
						TrackAttr result = call.getResult();
						List<www.tonghao.mall.api.standard.attwrap.Track> trackList = result.getTrack();
						if (!CollectionUtils.isEmpty(trackList)) {
							List<OrderTrack> OrderTrackList = new ArrayList<OrderTrack>();
							for (int i = trackList.size() - 1; i >= 0; i--) {
								www.tonghao.mall.api.standard.attwrap.Track track = trackList.get(i);
								OrderTrack e = new OrderTrack();
								e.setContent(track.getContent());
								e.setOperateTime(track.getOperate_time());
								e.setOperator(StringUtil.strIsEmpty(track.getOperator()) ? "" : track.getOperator());
								OrderTrackList.add(e);
						    }
							String json = JsonUtil.toJson(OrderTrackList);
							orders.setTrack(json);
							ordersMapper.updateByPrimaryKeySelective(orders);
							return orders;
						}
					}
				}else if(EmallConstant.MALL_JD_CODE.equals(code)){
					TrackApi trackApi = new TrackApi(orders.getEmallSn());
					TrackRes call = trackApi.call();
					if(call.isSuccess()){
						www.tonghao.mall.api.jd.attwrap.TrackAttr result = call.getResult();
						List<www.tonghao.mall.api.jd.attwrap.Track> trackList = result.getOrderTrack();
						if (!CollectionUtils.isEmpty(trackList)) {
							List<OrderTrack> OrderTrackList = new ArrayList<OrderTrack>();
							for (int i = trackList.size() - 1; i >= 0; i--) {
								www.tonghao.mall.api.jd.attwrap.Track track = trackList.get(i);
								OrderTrack e = new OrderTrack();
								e.setContent(track.getContent());
								e.setOperateTime(track.getMsgTime());
								e.setOperator(StringUtil.strIsEmpty(track.getOperator()) ? "" : track.getOperator());
								OrderTrackList.add(e);
						    }
							String json = JsonUtil.toJson(OrderTrackList);
							orders.setTrack(json);
							ordersMapper.updateByPrimaryKeySelective(orders);
							return orders;
						}
					}
				}else {
					if(!StringUtils.isEmpty(orders.getLogisticsName())&&!StringUtils.isEmpty(orders.getLogisticsSn())){
						List<OrderTrack> OrderTrackList = new ArrayList<OrderTrack>();
						OrderTrack e = new OrderTrack();
						e.setContent("物流公司："+orders.getLogisticsName());
						OrderTrackList.add(e);
						e = new OrderTrack();
						e.setContent("物流单号："+orders.getLogisticsSn());
						OrderTrackList.add(e);
						String json = JsonUtil.toJson(OrderTrackList);
						orders.setTrack(json);
						return orders;
					}
				}
			}
		}
		orders.setTrack(null);
		return orders;
	}
	
	

	/**
	 * 得到标准电商订单实体类
	 * 
	 * @param order
	 * @param supplier
	 * @return
	 */
	private www.tonghao.mall.api.standard.entity.Order getStandardOrder(Orders order, Suppliers supplier, Users user) {
		List<CreOrdSku> creOrdSkus = new ArrayList<CreOrdSku>();
		CreOrdSku creOrdSku = null;
		List<OrderItems> items = order.getItems();
		for (OrderItems item : items) {
			creOrdSku = new CreOrdSku();
			creOrdSku.setMode(1);
			creOrdSku.setNum(item.getNum());
			creOrdSku.setSku(item.getSn());
			creOrdSku.setPrice(item.getProtocolPrice());
			creOrdSkus.add(creOrdSku);
		}
		www.tonghao.mall.api.standard.entity.Order standardOrder = new www.tonghao.mall.api.standard.entity.Order();
		standardOrder.setYggcOrder(order.getSn());
		standardOrder.setCreOrdSkus(creOrdSkus);
		standardOrder.setName(order.getConsigneeName());
		MappingArea selectOne = mappingAreaService.getEmallMappingArea(order.getAreaId(), supplier.getCode());
		if (selectOne == null) {
			logger.error("{}地址映射错误,areaId:{}", supplier.getName(), order.getAreaId());
		}
		String[] mappingAreas = selectOne.getMappingCode().split("_");
		String province = mappingAreas[0];
		String city = mappingAreas[1];
		String county = mappingAreas[2];

		standardOrder.setProvince(province);
		standardOrder.setCity(city);
		standardOrder.setCounty(county);
		standardOrder.setAddress(StringUtils.substringAfter(order.getAddr(), " "));
		standardOrder.setZip(StringUtils.isEmpty(order.getZipCode()) ? Constant.DEFALUT_ZIP_CODE : order.getZipCode());
		standardOrder.setPhone(order.getPhone());
		standardOrder.setMobile(order.getMobile());
		standardOrder.setEmail(order.getEmail());
		standardOrder.setRemark(order.getRemark());
		String customerName = user.getDepName();
		if (user != null && user.getDepId() != null) {
			Organization org = organizationMapper.selectByPrimaryKey(user.getDepId());
			if (org != null) {
				customerName = org.getName();
			}
		}
		standardOrder.setDepName(customerName);

		standardOrder.setInvoiceTitle(InvoiceConstant.ENTERPRISE);
		standardOrder.setInvoiceType(InvoiceConstant.INVOICE_TYPE);
		standardOrder.setInvoiceOrgCode(InvoiceConstant.ID_CODE);
		standardOrder.setInvoiceName(order.getConsigneeName());
		standardOrder.setInvoicePhone(InvoiceConstant.PHONE);
		standardOrder.setInvoiceBank(InvoiceConstant.BANK);
		standardOrder.setInvoiceBankCode(InvoiceConstant.BANK_ACCOUNT);
		
		/*if (emallInvoiceType == 2) {
			Invoices invoice = invoicesService.selectByKey(order.getInvoiceId());
			standardOrder.setInvoiceAddress(invoice.getAddress());
		}*/
		
		/*Integer emallPayway = null;
		if (paywayType == 1) {
			emallPayway = 1;
		} else if (paywayType == 2) {
			emallPayway = 2;
		}*/

		standardOrder.setPayment(2);
		standardOrder.setOrderPrice(order.getProtocolTotal());
		standardOrder.setFreight(order.getFreight());
		standardOrder.setMode(1);
		return standardOrder;
	}

	@Override
	public Map<String, Object> deductions(List<Orders> orderlist, OrderForm orderForm) {
		if(orderForm.getPaywayId() == null){
			return ResultUtil.error("支付方式有误");
		}
		Map<String, Object> resultMap = ResultUtil.success("");
		if (1 == orderForm.getPaywayId()) {
			// 积分支付
			Map<String, List<OrderItems>> splitActicityOrderMap = new HashMap<>();
			orderlist.forEach(o -> o.getItems().forEach(item -> {
				String key = item.getOrderId() + "_" + item.getActivityId();
				if (splitActicityOrderMap.get(key) == null) {
					List<OrderItems> list = new ArrayList<>();
					list.add(item);
					splitActicityOrderMap.put(key, list);
				} else {
					List<OrderItems> list = splitActicityOrderMap.get(key);
					list.add(item);
					splitActicityOrderMap.put(key, list);
				}
				//更新订单支付信息  记录每个订单 每种支付方式支付的金额
				Orders payOrder = new Orders();
				payOrder.setId(o.getId());
				payOrder.setPayMoney(new BigDecimal("0"));
				payOrder.setPayIntegral(o.getItemsPrice());
				ordersMapper.updateByPrimaryKeySelective(payOrder);
			}));
			//扣除积分
			Set<String> keySet = splitActicityOrderMap.keySet();
			for (String key : keySet) {
				BigDecimal itemTotal = BigDecimalUtil.ZERO;
				for (OrderItems v : splitActicityOrderMap.get(key)) {
					itemTotal = BigDecimalUtil.add(itemTotal, BigDecimalUtil.multiply(v.getPrice(), new BigDecimal(v.getNum()+"")));
				}
				String[] split = key.split("_");
				String subtractBalance = integralUserService.subtractBalance(orderForm.getUser().getId(), Long.parseLong(split[1]), Long.parseLong(split[0]), itemTotal);
				if ("error".equals(subtractBalance)) {
					resultMap = ResultUtil.error("扣除积分异常");
					break;
				}
			}
		} else if (3 == orderForm.getPaywayId()) {
			// 混合支付
			//记录每个活动剩余积分数量
			Map<Long, BigDecimal> integralBalanceMap = new HashMap<>();
			Map<String, List<OrderItems>> splitActicityOrderMap = new HashMap<>();
			orderlist.forEach(o -> o.getItems().forEach(item -> {
				//根据活动查询剩余积分
				if(integralBalanceMap.get(item.getActivityId()) == null){
					IntegralUser integralUser = integralUserMapper.findbalanceByUIdAndAId(o.getUserId(), item.getActivityId());
					if(integralUser != null){
						integralBalanceMap.put(integralUser.getActivityId(), integralUser.getBalance());
					}
				}
				//根据活动拆分订单信息
				String key = item.getOrderId() + "_" + item.getActivityId();
				if (splitActicityOrderMap.get(key) == null) {
					List<OrderItems> list = new ArrayList<>();
					list.add(item);
					splitActicityOrderMap.put(key, list);
				} else {
					List<OrderItems> list = splitActicityOrderMap.get(key);
					list.add(item);
					splitActicityOrderMap.put(key, list);
				}
			}));
			//记录还需支付的现金
			Map<Long, BigDecimal> moneyMap = new HashMap<>();
			//扣除积分
			Set<String> keySet = splitActicityOrderMap.keySet();
			for (String key : keySet) {
				BigDecimal itemTotal = BigDecimalUtil.ZERO;
				for (OrderItems v : splitActicityOrderMap.get(key)) {
					itemTotal = BigDecimalUtil.add(itemTotal, BigDecimalUtil.multiply(v.getPrice(), new BigDecimal(v.getNum()+"")));
				}
				String[] split = key.split("_");
				long orderId = Long.parseLong(split[0]);
				long activityId = Long.parseLong(split[1]);
				BigDecimal balance = integralBalanceMap.get(activityId);
				if(balance == null || BigDecimalUtil.compareTo(balance, BigDecimalUtil.ZERO) == 0){
					//没有积分余额 直接个人支付
					if(moneyMap.get(orderId) == null){
						moneyMap.put(orderId, itemTotal);
					}else{
						moneyMap.put(orderId, BigDecimalUtil.add(moneyMap.get(orderId), itemTotal));
					}
				}else if(BigDecimalUtil.compareTo(balance, itemTotal) >= 0){
					//积分余额足够
					String subtractBalance = integralUserService.subtractBalance(orderForm.getUser().getId(), Long.parseLong(split[1]), Long.parseLong(split[0]), itemTotal);
					if ("error".equals(subtractBalance)) {
						resultMap = ResultUtil.error("扣除积分异常");
						break;
					}
					//更新积分余额
					integralBalanceMap.put(activityId, BigDecimalUtil.subtract(balance, itemTotal));
				}else{
					//积分余额不足  先扣除积分  在走个人支付
					String subtractBalance = integralUserService.subtractBalance(orderForm.getUser().getId(), Long.parseLong(split[1]), Long.parseLong(split[0]), balance);
					if ("error".equals(subtractBalance)) {
						resultMap = ResultUtil.error("扣除积分异常");
						break;
					}
					//更新积分余额
					integralBalanceMap.put(activityId, BigDecimalUtil.ZERO);
					//记录需要个人支付的金额
					if(moneyMap.get(orderId) == null){
						moneyMap.put(orderId, BigDecimalUtil.subtract(itemTotal, balance));
					}else{
						BigDecimal b = moneyMap.get(orderId);
						moneyMap.put(orderId, BigDecimalUtil.add(b, BigDecimalUtil.subtract(itemTotal, balance)));
					}
				}
			}
			
			//更新订单支付信息  记录每个订单 每种支付方式支付的金额
			orderlist.forEach(o -> {
				Orders payOrder = new Orders();
				payOrder.setId(o.getId());
				BigDecimal money = moneyMap.get(o.getId());
				if(money == null){
					payOrder.setPayMoney(new BigDecimal("0"));
					payOrder.setPayIntegral(o.getItemsPrice());
				}else{
					payOrder.setPayMoney(money);
					payOrder.setPayIntegral(o.getItemsPrice().subtract(money));
				}
				if(payOrder.getPayMoney() != null && BigDecimalUtil.compareTo(payOrder.getPayMoney(), BigDecimalUtil.ZERO) == 0){
					payOrder.setPayId(1L);
					o.setPayId(1L);
				}
				ordersMapper.updateByPrimaryKeySelective(payOrder);
			});
		} else if (2 == orderForm.getPaywayId()) {
			//个人支付
			orderlist.forEach(o -> o.getItems().forEach(item -> {
				//更新订单支付信息  记录每个订单 每种支付方式支付的金额
				Orders payOrder = new Orders();
				payOrder.setId(o.getId());
				payOrder.setPayMoney(o.getItemsPrice());
				payOrder.setPayIntegral(new BigDecimal("0"));
				ordersMapper.updateByPrimaryKeySelective(payOrder);
			}));
		}
		return resultMap;
	}

	@Override
	public void refund(Orders orders) {
		Long payId = orders.getPayId();
		if(payId != null && (1 == payId || 3 == payId)){
			IntegralConsume integralConsume = new IntegralConsume();
			integralConsume.setOrderId(orders.getId());
			integralConsume.setUserId(orders.getUserId());
			integralConsume.setType(1);
			List<IntegralConsume> integralConsumeList = integralConsumeMapper.select(integralConsume);
			if (CollectionUtils.isNotEmpty(integralConsumeList)) {
				for (IntegralConsume ic : integralConsumeList) {
					integralUserService.addBalance(ic.getUserId(), ic.getActivityId(), ic.getOrderId(), ic.getIntegralNum(), "下单失败退还积分");
				}
			}
		}
	}

	@Override
	public String payQRCode(Long orderMasterId,Long userId,String webSiteAddress) throws Exception {
		Example example=new Example(Orders.class);
		Criteria orderCriteria = example.createCriteria();
		orderCriteria.andEqualTo("isDelete",0);
		orderCriteria.andEqualTo("ordersState","commit");
		orderCriteria.andEqualTo("masterId", orderMasterId);
		orderCriteria.andEqualTo("userId", userId);
		example.setOrderByClause(" id desc");
		List<Orders> orderList = ordersMapper.selectByExample(example); //根据父订单获取所有的订单
		if(!CollectionUtil.collectionIsEmpty(orderList)){
			BigDecimal price=BigDecimalUtil.ZERO;
			String order_code="";
			for (Orders orders : orderList) {
				if(BigDecimalUtil.compareTo(orders.getPayMoney(), BigDecimalUtil.ZERO)>0){
					order_code+=orders.getSn()+"_";
					price=BigDecimalUtil.add(price, orders.getPayMoney());
				}
			}
			if(order_code.length()>0){
				order_code=order_code.substring(0, order_code.length()-1);				
			}
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orderMasterId);
			opp.setOrdersCode(order_code);
			opp.setStatus(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
			String payParam=null;
			if(oppEntity==null){//判断 是不是已完成支付了
				payParam= payParam(orderMasterId, order_code, price,webSiteAddress);
			}
			return payParam;
		}
		return null;
	}
	
	
	public String payParam(Long orderMasterId,String OrderCode,BigDecimal price,String webSiteAddress) throws Exception{
		String url="";
		OrderPayPrice opp=new OrderPayPrice();
		opp.setOrderMasterId(orderMasterId);
		opp.setOrdersCode(OrderCode);
		opp.setPayType(1);
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		if(oppEntity==null){//判断有没有二维码记录
			Date date = new Date();
			String OutTradeNo = Long.toString(System.currentTimeMillis());
			Map<String, String> origMap = payParamMap(price, webSiteAddress, date,
					OutTradeNo);
			Orders orders_time=new Orders();
			orders_time.setMasterId(orderMasterId);
			List<Orders> os = ordersMapper.select(orders_time);
			String expireDate=""; //设置二维码过期时间
			if(!CollectionUtil.collectionIsEmpty(os)){
				Orders orders = os.get(0);
				if(orders!=null){
					Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
					Calendar nowTime = Calendar.getInstance();
					nowTime.setTime(parse);
					nowTime.add(Calendar.MINUTE, 14);
					nowTime.add(Calendar.SECOND, 40);
					expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
					origMap.put("OrderEndTime", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
				}
			}
			if(expireDate.length()==0){
				return null;
			}
			
			String gatewayPost = PayUtils.gatewayPost(origMap);
			if(gatewayPost!=null&&gatewayPost.length()>0){
				HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
				Map<String, String> sPara = PayUtils.paraFilter(result);
				System.out.println("预下订单原始值："+result);
				System.out.println("去掉关键参数后的值："+sPara);
				String prestr = PayUtils.createLinkString(sPara, false);
				System.out.println("拼接后的值："+prestr);
				boolean verify = RSA.verify(prestr, result.get("Sign").toString(), PayUtils.MERCHANT_PUBLIC_KEY, "UTF-8");
				String code = result.get("RetCode").toString();
				if(verify&&"SYSTEM_SUCCESS".equals(code)){
					OrderPayPrice record=new OrderPayPrice();
					record.setOrderMasterId(orderMasterId);
					record.setCode(OutTradeNo);
					record.setCjCode(result.get("InnerTradeNo").toString());
					record.setOrdersCode(OrderCode);
					record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
					record.setPrice(price);
					record.setStatus(0);
					record.setPayType(1);
					record.setReqJson(JsonUtil.toJson(origMap));
					record.setResultJson(gatewayPost);
					record.setExpireDate(expireDate);
					int resutNum= OrderPayPriceMapper.insertSelective(record);
					if(resutNum>0){
						 url= result.get("CodeUrl").toString();
					}
				}
			}
		}else{
			String gatewayPost = oppEntity.getResultJson();
			HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
			url= result.get("CodeUrl").toString();
		}
		return url;
	}

	private Map<String, String> payParamMap(BigDecimal price,
			String webSiteAddress, Date date, String OutTradeNo) {
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_one_code_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", PayUtils.USER_NO);	//生产环境测试商户号
		origMap.put("InputCharset", "UTF-8");
		origMap.put("TradeDate", new SimpleDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		/*origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");*/// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", OutTradeNo);//商户订单号
		origMap.put("MchId", PayUtils.USER_NO);	//生产环境测试商户号
		/*origMap.put("SubMchId", "");*/
		origMap.put("TradeType", "11");//交易类型（即时 11 担保 12）
		origMap.put("BusiType", "1");	//1动码，2固码
		/*origMap.put("DeviceInfo", "wx90192dels817xla0");*/
		origMap.put("Currency", "CNY");//默认CNY，暂只支持CNY
		origMap.put("TradeAmount", price+"");	// 动码时金额为必输
		origMap.put("GoodsName", OutTradeNo+"中油福瑞商城订单");
		origMap.put("TradeMemo", "中油福瑞商城订单");
		origMap.put("Subject", "中油福瑞商城订单");
		origMap.put("OrderStartTime",
				new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("NotifyUrl", webSiteAddress);
		return origMap;
	}

	

	private void confirmOrder(String no) {
		Orders findBySn = ordersMapper.findBySn(no);
		if(findBySn!=null&&OrderStatus.commit.equals(findBySn.getOrdersState())){
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(findBySn.getSupplierId());
			if(supplier!=null){
				if(EmallConstant.MALL_STB_CODE.equals(supplier.getCode())){
					new ConfirmOrderApi(findBySn.getEmallSn()).call();
				}else if(EmallConstant.MALL_JD_CODE.equals(supplier.getCode())){
					new www.tonghao.mall.api.jd.call.OrderConfirmApi(findBySn.getEmallSn()).call();
				}else if(EmallConstant.MALL_DL_CODE.equals(supplier.getCode())){
					new OrderConfirmApi(EmallConstant.MALL_DL_CODE, findBySn.getEmallSn()).call();
				}
				findBySn.setOrdersState(OrderStatus.confirmed);
				ordersMapper.updateByPrimaryKeySelective(findBySn);
			}
		}
	}

	@Override
	public List<OrderPayPrice> getOrderPayPrice(Long orderMsterId,Long ordersCode) {
		OrderPayPrice opp=new OrderPayPrice();
		if(orderMsterId!=null){
			Example example=new Example(Orders.class);
			Criteria orderCriteria = example.createCriteria();
			orderCriteria.andEqualTo("isDelete",0);
			orderCriteria.andEqualTo("masterId", orderMsterId);
			example.setOrderByClause(" id desc");
			List<Orders> orderList = ordersMapper.selectByExample(example);
			String code="";
			for (Orders orders : orderList) {
				code+=orders.getSn()+"_";
			}
			if(code.length()>0){
				code=code.substring(0, code.length()-1);
			}
			opp.setOrdersCode(code);
		}
		if(ordersCode!=null){
			Orders orders = ordersMapper.selectByPrimaryKey(ordersCode);
			if(orders!=null){
				opp.setOrdersCode(orders.getSn()+"");
			}
		}
		List<OrderPayPrice> list = OrderPayPriceMapper.select(opp);
		return list;
	}
	
	@Override
	public void timeToCancelOrders(List<Orders> orders) {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		// 异步调用
		ses.schedule(new Runnable() {
			@Override
			public void run() {
				for (Orders item : orders) {
					Orders order = findById(item.getId());
					if (order != null && order.getOrdersState() == OrderStatus.commit) {
						boolean isSuccess = doCancelEmallOrder(order);
						if (isSuccess) {
							//退还积分
							refund(order);
							//修改订单状态
							order.setOrdersState(OrderStatus.cancelled);
							updateNotNull(order);
							orderLogsService.saveLog("系统", "15未支付，系统自动取消订单", 4, order.getId());
						}
					}
				}
			}
		}, Constant.ORDER_CANCEL_WAIT_TIME, TimeUnit.MINUTES);
	}

	@Override
	public int payIntegral(Long orderId) {
		int resultNum=0;
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		if(orders!=null&&orders.getOrdersState()!=null&&orders.getOrdersState().equals(OrderStatus.commit)){
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(orders.getSupplierId());
		    if(supplier!=null&&supplier.getCode()!=null){
		    	if(EmallConstant.MALL_STB_CODE.equals(supplier.getCode())){
					new ConfirmOrderApi(orders.getEmallSn()).call();
				}else if(EmallConstant.MALL_JD_CODE.equals(supplier.getCode())){
					new www.tonghao.mall.api.jd.call.OrderConfirmApi(orders.getEmallSn()).call();
				}else if(EmallConstant.MALL_DL_CODE.equals(supplier.getCode())){
					new OrderConfirmApi(EmallConstant.MALL_DL_CODE, orders.getEmallSn()).call();
				}
		    	orders.setOrdersState(OrderStatus.confirmed);
		    	resultNum+=ordersMapper.updateByPrimaryKeySelective(orders);
		    }
		}
		return resultNum;
	}

	@Override
	public String payQRCodePlatform(Long orderId,Long userId, String webSiteAddress) throws Exception {
		String url="";
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		OrderPayPrice op=new OrderPayPrice();
		op.setOrderMasterId(orders.getMasterId());
		op.setOrdersCode(orders.getSn());
		op.setStatus(1);
		OrderPayPrice opr = OrderPayPriceMapper.selectOne(op); //判断是否支付完成
		if(opr==null){
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orders.getMasterId());
			opp.setOrdersCode(orders.getSn());
			opp.setPayType(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);//判断是否已经生成二维码
			if(oppEntity==null){
				Date date = new Date();
				String OutTradeNo = Long.toString(System.currentTimeMillis());
				Map<String, String> origMap = payParamMap(orders.getPayMoney(), webSiteAddress, date,
						OutTradeNo);
				
				String expireDate=""; //设置二维码过期时间
				if(orders!=null){
					Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
					Calendar nowTime = Calendar.getInstance();
					nowTime.setTime(parse);
					nowTime.add(Calendar.MINUTE, 14);
					nowTime.add(Calendar.SECOND, 40);
					expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
					origMap.put("OrderEndTime", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
				}
				if(expireDate.length()==0){
					return null;
				}
				String gatewayPost = PayUtils.gatewayPost(origMap);
				if(gatewayPost!=null&&gatewayPost.length()>0){
					HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
					Map<String, String> sPara = PayUtils.paraFilter(result);
					System.out.println("预下订单原始值："+result);
					System.out.println("去掉关键参数后的值："+sPara);
					String prestr = PayUtils.createLinkString(sPara, false);
					System.out.println("拼接后的值："+prestr);
					boolean verify = RSA.verify(prestr, result.get("Sign").toString(), PayUtils.MERCHANT_PUBLIC_KEY, "UTF-8");
					String code = result.get("RetCode").toString();
					if(verify&&"SYSTEM_SUCCESS".equals(code)){
						OrderPayPrice record=new OrderPayPrice();
						record.setOrderMasterId(orders.getMasterId());
						record.setCode(OutTradeNo);
						record.setCjCode(result.get("InnerTradeNo").toString());
						record.setOrdersCode(orders.getSn());
						record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
						record.setPrice(orders.getPayMoney());
						record.setStatus(0);
						record.setReqJson(JsonUtil.toJson(origMap));
						record.setResultJson(gatewayPost);
						record.setPayType(1);
						record.setExpireDate(expireDate);
						int resutNum= OrderPayPriceMapper.insertSelective(record);
						if(resutNum>0){
							url = result.get("CodeUrl").toString();
						}
					}
				}
			}else{
				String gatewayPost = oppEntity.getResultJson();
				HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
				url= result.get("CodeUrl").toString();
			}
		}
		return url;
	}

	
	@Override
	public int updatePayStatus(String orderNo,String json,String resultCode) {
		OrderPayPrice opp=new OrderPayPrice();
		opp.setCode(orderNo);
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		int resultNum=0;
		if(oppEntity!=null){
			oppEntity.setStatus(1);
			oppEntity.setPayCallBackJson(json);
			oppEntity.setCjCode(resultCode);
			resultNum= OrderPayPriceMapper.updateByPrimaryKeySelective(oppEntity);
			if(resultNum>0){
				String ordersCode = oppEntity.getOrdersCode();
				Long orderMasterId = oppEntity.getOrderMasterId();
				if(ordersCode!=null){
					Orders ord=new Orders();
					ord.setIsDelete(0);
					ord.setMasterId(orderMasterId);
					List<Orders> ordersList = ordersMapper.select(ord);
					String[] yggcOrderNo = ordersCode.split("_");
					for (Orders orders : ordersList) {
						boolean flg=false;
						for (String no : yggcOrderNo) {
							if(no.equals(orders.getSn())){
								flg=true;
								confirmOrder(no);
							}
						}
						if(!flg){
							confirmOrder(orders.getSn());
						}
					}
				}
			}
		}
		return resultNum;
	}
	@Override
	public int updatePayStatusPlatform(String orderNo,String json) {
		OrderPayPrice opp=new OrderPayPrice();
		opp.setCode(orderNo);
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		int resultNum=0;
		if(oppEntity!=null){
			oppEntity.setStatus(1);
			oppEntity.setPayCallBackJson(json);
			resultNum= OrderPayPriceMapper.updateByPrimaryKeySelective(oppEntity);
			if(resultNum>0){
				String ordersCode = oppEntity.getOrdersCode();
				if(ordersCode!=null){
					Orders ord=new Orders();
					ord.setIsDelete(0);
					ord.setSn(ordersCode);
					Orders orders = ordersMapper.selectOne(ord);
					if(orders!=null&&orders.getOrdersState()!=null&&orders.getOrdersState().equals(OrderStatus.commit)){
						confirmOrder(orders.getSn());
					}
				   
				}
			}
		}
		return 0;
	}

	@Override
	public int cancelOrderByConfirmed(Long orderId, Users users) {
		Orders orders=new Orders();
		orders.setId(orderId);
		orders.setUserId(users.getId());
		orders.setIsDelete(0);
		orders.setOrdersState(OrderStatus.confirmed);
		Orders order = ordersMapper.selectOne(orders);
		if(order!=null){
			Map<String, Object> map=new HashMap<>();
			map.put("orderSn", order.getSn());
			map.put("orderMasterId", order.getMasterId());
			List<OrderRefundItem> otfList = orderRefundItemMapper.selectOrderFundItem(map);
			if(!CollectionUtil.collectionIsEmpty(otfList)){
				return 2;
			}
			OrderRefundItem item=new OrderRefundItem();
			item.setPayId(order.getPayId());
			item.setOrderSn(order.getSn());
			item.setOrderTotal(order.getTotal());
			item.setCreateAt(DateUtilEx.timeFormat(new Date()));
			item.setUpdateAt(DateUtilEx.timeFormat(new Date()));
			item.setRefundUserId(order.getUserId());
			item.setRefundUserName(users.getRealName());
			item.setType(1);
			item.setOrderMasterId(order.getMasterId());
			item.setRefundStatus(0);
			if(order.getPayId()==2||order.getPayId()==3){//个人支付或者混合支付
				item.setRefundIntegralPrice(order.getPayIntegral());
				item.setRefundMoney(order.getPayMoney());
				Map<String,Object> paramMap=new HashMap<>();
				paramMap.put("ordersCode", order.getSn());
				paramMap.put("orderMasterId", order.getMasterId());
				paramMap.put("status", 1);
				List<OrderPayPrice> opp = orderPayPriceMapper.getOrderPayPriceByMap(paramMap);
				if(!CollectionUtil.collectionIsEmpty(opp)){
					OrderPayPrice orderPayPrice = opp.get(0);
					item.setCjCode(orderPayPrice.getCjCode());
					item.setOrderPayPriceId(orderPayPrice.getId());
				}
			}else if(order.getPayId()==1){//积分支付
				item.setRefundIntegralPrice(order.getPayIntegral());
				item.setRefundMoney(BigDecimalUtil.ZERO);
			}
			int insertSelective = orderRefundItemMapper.insertSelective(item);
			if(insertSelective>0){
				order.setIsCancel(1);
				ordersMapper.updateByPrimaryKeySelective(order);
				return insertSelective;
			}
		}
		return 0;
	}

	@Override
	public String payH5Price(Users users, String info,Long orderMasterId, String webSiteAddress) {
		Example example=new Example(Orders.class);
		Criteria orderCriteria = example.createCriteria();
		orderCriteria.andEqualTo("isDelete",0);
		orderCriteria.andEqualTo("ordersState","commit");
		orderCriteria.andEqualTo("masterId", orderMasterId);
		orderCriteria.andEqualTo("userId", users.getId());
		example.setOrderByClause(" id desc");
		List<Orders> orderList = ordersMapper.selectByExample(example);
		if(!CollectionUtil.collectionIsEmpty(orderList)){
			BigDecimal price=BigDecimalUtil.ZERO;
			String order_code="";
			for (Orders orders : orderList) {
				if(BigDecimalUtil.compareTo(orders.getPayMoney(), BigDecimalUtil.ZERO)>0){
					order_code+=orders.getSn()+"_";
					price=BigDecimalUtil.add(price, orders.getPayMoney());
				}
			}
			if(order_code.length()>0){
				order_code=order_code.substring(0, order_code.length()-1);				
			}
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orderMasterId);
			opp.setOrdersCode(order_code);
			opp.setStatus(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
			String payParam=null;
			if(oppEntity==null){
				payParam=payH5Param(orderMasterId, order_code, price,webSiteAddress,info);
			}
			return payParam;
		}
		return null;
	}
	private String payH5Param(Long orderMasterId,String order_code,BigDecimal price,String webSiteAddress,String info){
		OrderPayPrice opp=new OrderPayPrice();
		opp.setOrderMasterId(orderMasterId);
		opp.setOrdersCode(order_code);
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		if(oppEntity==null){
			Date date = new Date();
			String OutTradeNo = Long.toString(System.currentTimeMillis());
			Map<String, String> origMap = payH5ParamMap(price, webSiteAddress, date,
					OutTradeNo,info);
			String gatewayPost = PayUtils.gatewayPost(origMap);
			
			
			
			System.out.println(gatewayPost);
		}
		
		
		
		return null;
		
	}
	private Map<String, String> payH5ParamMap(BigDecimal price,
			String webSiteAddress, Date date, String OutTradeNo,String info) {
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_wx_wap_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", PayUtils.USER_NO);	//生产环境测试商户号
		origMap.put("InputCharset", "UTF-8");
		origMap.put("TradeDate", new SimpleDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		/*origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");*/// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", OutTradeNo);//商户订单号
		origMap.put("MchId", PayUtils.USER_NO);	//生产环境测试商户号
		/*origMap.put("SubMchId", "");*/
		origMap.put("TradeType", "11");//交易类型（即时 11 担保 12）
		
		origMap.put("AppId", "wx90192dels817xla0");//微信/支付宝给商户开通的标识
		
		origMap.put("DeviceInfo", info);//"iOS_SDK",AND_SDK
		//应用名 如果是用于苹果或安卓app应用中，传分别对应在AppStore和安卓市场中的应用名（如：王者荣耀）；如果是用于手机网站，传对应的网站名称（如：京东商城）
		origMap.put("MchAppName", "中油福瑞商城");
		//如果是用于苹果或安卓app应用中，传分别对应在AppStore应用唯一标识（如：com.tencent.wzryISO）和安卓传包名（如：com.tencent.tmgp.sgame）；如果是用于手机网站，传对应的网站首页URL必须是公网能正常访问的（如：https://m.jd.com）
		origMap.put("MchAppId", webSiteAddress);//应用标识
		origMap.put("Currency", "CNY");//默认CNY，暂只支持CNY
		origMap.put("TradeAmount", price+"");	// 金额为必输
		origMap.put("GoodsName", OutTradeNo+"中油福瑞商城订单");
		origMap.put("Subject", "中油福瑞商城订单");
		origMap.put("TradeMemo", "中油福瑞商城订单");
		origMap.put("OrderStartTime",
				new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(date);
		nowTime.add(Calendar.MINUTE, 15);
		origMap.put("OrderEndTime", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
		origMap.put("NotifyUrl", "");
		origMap.put("SpbillCreateIp", "");
		return origMap;
	}

	@Override
	public Map<String, Object> payWxPrice(Users users, Long orderMasterId,
			String webSiteAddress) throws Exception {
		Map<String, Object>  map=null;
		Example example=new Example(Orders.class);
		Criteria orderCriteria = example.createCriteria();
		orderCriteria.andEqualTo("isDelete",0);
		orderCriteria.andEqualTo("ordersState","commit");
		orderCriteria.andEqualTo("masterId", orderMasterId);
		orderCriteria.andEqualTo("userId", users.getId());
		example.setOrderByClause(" id desc");
		List<Orders> orderList = ordersMapper.selectByExample(example);
		if(!CollectionUtil.collectionIsEmpty(orderList)){
			BigDecimal price=BigDecimalUtil.ZERO;
			String order_code="";
			for (Orders orders : orderList) {
				if(BigDecimalUtil.compareTo(orders.getPayMoney(), BigDecimalUtil.ZERO)>0){
					order_code+=orders.getSn()+"_";
					price=BigDecimalUtil.add(price, orders.getPayMoney());
				}
			}
			if(order_code.length()>0){
				order_code=order_code.substring(0, order_code.length()-1);				
			}
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orderMasterId);
			opp.setOrdersCode(order_code);
			opp.setStatus(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
			if(oppEntity==null){
				map=payWxParam(orderMasterId, order_code, price,webSiteAddress);
			}
		}
		return map;
	}
	
	private Map payWxParam(Long orderMasterId,String order_code,BigDecimal price,String webSiteAddress) throws Exception{
		Map map=null;
		OrderPayPrice opp=new OrderPayPrice();
		opp.setOrderMasterId(orderMasterId);
		opp.setOrdersCode(order_code);
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		if(oppEntity==null){
			Date date = new Date();
			String OutTradeNo = Long.toString(System.currentTimeMillis());
			Map<String, String> origMap = payWxParamMap(price, webSiteAddress, date,
					OutTradeNo);
			String gatewayPost = PayUtils.gatewayPost(origMap);
			HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
			Map<String, String> sPara = PayUtils.paraFilter(result);
			System.out.println("预下订单原始值："+result);
			System.out.println("去掉关键参数后的值："+sPara);
			String prestr = PayUtils.createLinkString(sPara, false);
			System.out.println("拼接后的值："+prestr);
			boolean verify = RSA.verify(prestr, result.get("Sign").toString(), PayUtils.MERCHANT_PUBLIC_KEY, "UTF-8");
			String code = result.get("RetCode").toString();
			if(verify&&"SYSTEM_SUCCESS".equals(code)){
				OrderPayPrice record=new OrderPayPrice();
				record.setOrderMasterId(orderMasterId);
				record.setCode(OutTradeNo);
				record.setCjCode(result.get("InnerTradeNo").toString());
				record.setOrdersCode(order_code);
				record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
				record.setPrice(price);
				record.setStatus(0);
				record.setReqJson(JsonUtil.toJson(origMap));
				record.setResultJson(gatewayPost);
				int resutNum= OrderPayPriceMapper.insertSelective(record);
				if(resutNum>0){
					return result;
				}
			}
		}else{
			return JsonUtil.toObject(oppEntity.getResultJson(), HashMap.class);
		}
		return map;
		
	}
	
	private Map<String, String> payWxParamMap(BigDecimal price,
			String webSiteAddress, Date date, String OutTradeNo) {
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_wx_mini_program_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", PayUtils.USER_NO);//生产环境测试商户号
		origMap.put("InputCharset", PayUtils.charset);
		origMap.put("TradeDate", new SimpleDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(date));
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", OutTradeNo);
		origMap.put("MchId", PayUtils.USER_NO);
		origMap.put("TradeType", "11");
		origMap.put("AppId", "wxf8e96529a5c95f8e");//微信/支付宝标识
		origMap.put("BuyerPayCode", "ozFSO1u0v7mwILyKlP2EzdSsdueA");//付方支付ID
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "0.01");
		origMap.put("GoodsName", "中油福瑞商城订单");
		origMap.put("TradeMemo", "中油福瑞商城订单");
		origMap.put("Subject", "中油福瑞商城订单");
		origMap.put("OrderStartTime",
				new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(date);
		nowTime.add(Calendar.MINUTE, 15);
		origMap.put("OrderEndTime", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
		origMap.put("NotifyUrl", "");
		origMap.put("SpbillCreateIp", "192.168.1.1");
		return origMap;
	}
	
	/**
	 * orderMasterId 父订单id
	 * userId 用户id
	 * webSiteAddress 回调地址
	 * payCode 1 微信，2支付宝
	 * payType 移动端还是pc端 1 电脑支付，2移动端支付
	 */
	@Override
	public String payWxOrAliQrcode(Long orderMasterId, Long userId,
			String webSiteAddress,Integer payCode,Integer payType,String ip) throws Exception {
		Example example=new Example(Orders.class);
		Criteria orderCriteria = example.createCriteria();
		orderCriteria.andEqualTo("isDelete",0);
		orderCriteria.andEqualTo("ordersState","commit");
		orderCriteria.andEqualTo("masterId", orderMasterId);
		orderCriteria.andEqualTo("userId", userId);
		example.setOrderByClause(" id desc");
		List<Orders> orderList = ordersMapper.selectByExample(example); //根据父订单获取所有的订单
		if(!CollectionUtil.collectionIsEmpty(orderList)){
			BigDecimal price=BigDecimalUtil.ZERO;
			String order_code="";
			for (Orders orders : orderList) {
				if(BigDecimalUtil.compareTo(orders.getPayMoney(), BigDecimalUtil.ZERO)>0){
					order_code+=orders.getSn()+"_";
					price=BigDecimalUtil.add(price, orders.getPayMoney());
				}
			}
			if(order_code.length()>0){
				order_code=order_code.substring(0, order_code.length()-1);				
			}
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orderMasterId);
			opp.setOrdersCode(order_code);
			opp.setStatus(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
			String payParam=null;
			if(oppEntity==null){//判断 是不是已完成支付了
				payParam= payWxOrAliQrcodeParam(orderMasterId, order_code, price,webSiteAddress,payCode,payType,ip);
			}
			return payParam;
		}
		return null;
	}
		
	public String payWxOrAliQrcodeParam(Long orderMasterId,String OrderCode,BigDecimal price,String webSiteAddress,Integer payCode,Integer payType,String ip) throws Exception{
		String url="";
		OrderPayPrice opp=new OrderPayPrice();
		opp.setOrderMasterId(orderMasterId);
		opp.setOrdersCode(OrderCode);
		opp.setPayType(payType);//移动端还是pc端 1 电脑支付，2移动端支付
		opp.setPayCode(payCode);//1 微信，2支付宝
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		if(oppEntity==null){//判断有没有二维码记录
			String OutTradeNo= Long.toString(System.currentTimeMillis());//获取支付批次号
			Date date = new Date();
			if(payCode==1){
				Map<String, String> origMap = payWxOrAliQrcodeParamMap(price, webSiteAddress, date,
						OutTradeNo,payCode,ip,"NATIVE");
				Orders orders_time=new Orders();
				orders_time.setMasterId(orderMasterId);
				List<Orders> os = ordersMapper.select(orders_time);
				String expireDate=""; //设置二维码过期时间
				if(!CollectionUtil.collectionIsEmpty(os)){
					Orders orders = os.get(0);
					if(orders!=null){
						Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
						Calendar nowTime = Calendar.getInstance();
						nowTime.setTime(parse);
						nowTime.add(Calendar.MINUTE, 14);
						nowTime.add(Calendar.SECOND, 40);
						expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
						origMap.put("time_expire", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
					}
				}
				if(expireDate.length()==0){
					return null;
				}
				 origMap.put("sign", WXPayUtil.createSign(origMap, WXpayConfig.APIKEY, "MD5"));
				 String mapToXml = WXPayUtil.mapToXml(origMap);
		         Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
		         if(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5")){
			         String return_code = httpOrder.get("return_code");
			         String result_code = httpOrder.get("result_code");
		             if (null != httpOrder && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
		            	 OrderPayPrice record=new OrderPayPrice();
							record.setOrderMasterId(orderMasterId);
							record.setCode(OutTradeNo);
							record.setOrdersCode(OrderCode);
							record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
							record.setPrice(price);
							record.setStatus(0);
							record.setPayType(payType);
							record.setPayCode(payCode);
							record.setReqJson(JsonUtil.toJson(origMap));
							record.setResultJson(JsonUtil.toJson(httpOrder));
							record.setExpireDate(expireDate);
							int resutNum= OrderPayPriceMapper.insertSelective(record);
							if(resutNum>0){
								 url= httpOrder.get("code_url").toString();
							}
					 }
		         }
			}
		}else{
			String gatewayPost = oppEntity.getResultJson();
			HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
			url= result.get("code_url").toString();
		}
		return url;
	}
	
	private Map<String, String> payWxOrAliQrcodeParamMap(BigDecimal price,
			String webSiteAddress, Date date, String OutTradeNo,Integer payCode,String ip,String pay_type) {
		Map<String, String> origMap = new HashMap<String, String>();
		origMap.put("appid", WXpayConfig.APPID);//公众账号ID
		origMap.put("mch_id", WXpayConfig.WXPAYMENTACCOUNT);//商户号
		origMap.put("time_start", new SimpleDateFormat("yyyyMMddHHmmss").format(date));//商品描述
		origMap.put("body", "商品-订单支付");//商品描述
		origMap.put("nonce_str", WXPayUtil.generateNonceStr());
		origMap.put("notify_url", webSiteAddress);//通知地址
		origMap.put("out_trade_no", OutTradeNo);//订单号
		origMap.put("spbill_create_ip", ip);//终端ip
		origMap.put("trade_type", pay_type);//交易类型
		origMap.put("total_fee", String.valueOf(price.doubleValue()*100).split("\\.")[0]);//总金额
		//origMap.put("total_fee",1+"");
		return origMap;
	}

	/**
	 * orderId 父订单id
	 * userId 用户id
	 * webSiteAddress 回调地址
	 * payCode 1 微信，2支付宝
	 * payType 移动端还是pc端 1 电脑支付，2移动端支付
	 */
	@Override
	public String platPayWxOrAliQrcode(Long orderId, Long userId,
			String webSiteAddress, Integer payCode, Integer payType,String ip)
			throws Exception {
		String url="";
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		if(orders.getOrdersState().equals(OrderStatus.commit)){
			OrderPayPrice op=new OrderPayPrice();
			op.setOrderMasterId(orders.getMasterId());
			op.setOrdersCode(orders.getSn());
			op.setStatus(1);
			OrderPayPrice opr = OrderPayPriceMapper.selectOne(op); //判断是否支付完成
			if(opr==null){
				OrderPayPrice opp=new OrderPayPrice();
				opp.setOrderMasterId(orders.getMasterId());
				opp.setOrdersCode(orders.getSn());
				opp.setPayType(payType);//移动端还是pc端 1 电脑支付，2移动端支付
				opp.setPayCode(payCode);//1 微信，2支付宝
				OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
				if(oppEntity==null){//判断有没有二维码记录
					String OutTradeNo= Long.toString(System.currentTimeMillis());//获取支付批次号
					Date date = new Date();
					if(payCode==1){
						Map<String, String> origMap = payWxOrAliQrcodeParamMap(orders.getPayMoney(), webSiteAddress, date,
								OutTradeNo,payCode,ip,"NATIVE");
						String expireDate=""; //设置二维码过期时间
						Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
						Calendar nowTime = Calendar.getInstance();
						nowTime.setTime(parse);
						nowTime.add(Calendar.MINUTE, 14);
						nowTime.add(Calendar.SECOND, 40);
						expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
						origMap.put("time_expire", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
						origMap.put("sign", WXPayUtil.createSign(origMap, WXpayConfig.APIKEY, "MD5"));
						 String mapToXml = WXPayUtil.mapToXml(origMap);
				         Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
				         if(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5")){
					         String return_code = httpOrder.get("return_code");
					         String result_code = httpOrder.get("result_code");
				             if (null != httpOrder && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
				            	 OrderPayPrice record=new OrderPayPrice();
									record.setOrderMasterId(orders.getMasterId());
									record.setCode(OutTradeNo);
									record.setOrdersCode(orders.getSn());
									record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
									record.setPrice(orders.getPayMoney());
									record.setStatus(0);
									record.setPayType(payType);
									record.setPayCode(payCode);
									record.setReqJson(JsonUtil.toJson(origMap));
									record.setResultJson(JsonUtil.toJson(httpOrder));
									record.setExpireDate(expireDate);
									int resutNum= OrderPayPriceMapper.insertSelective(record);
									if(resutNum>0){
										 url= httpOrder.get("code_url").toString();
									}
				             }
				         }
					}
				}else{
					String gatewayPost = oppEntity.getResultJson();
					HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
					url= result.get("code_url").toString();
				}
			}
		}
		
		return url;
	}

	@Override
	public Map<String, String> mobileWxOrAliQrcode(Long orderMasterId,
			Long userId, String webSiteAddress, Integer payCode,
			Integer payType, String ip,String openId) throws Exception {
		Example example=new Example(Orders.class);
		Criteria orderCriteria = example.createCriteria();
		orderCriteria.andEqualTo("isDelete",0);
		orderCriteria.andEqualTo("ordersState","commit");
		orderCriteria.andEqualTo("masterId", orderMasterId);
		orderCriteria.andEqualTo("userId", userId);
		example.setOrderByClause(" id desc");
		List<Orders> orderList = ordersMapper.selectByExample(example); //根据父订单获取所有的订单
		if(!CollectionUtil.collectionIsEmpty(orderList)){
			BigDecimal price=BigDecimalUtil.ZERO;
			String order_code="";
			for (Orders orders : orderList) {
				if(BigDecimalUtil.compareTo(orders.getPayMoney(), BigDecimalUtil.ZERO)>0){
					order_code+=orders.getSn()+"_";
					price=BigDecimalUtil.add(price, orders.getPayMoney());
				}
			}
			if(order_code.length()>0){
				order_code=order_code.substring(0, order_code.length()-1);				
			}
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orderMasterId);
			opp.setOrdersCode(order_code);
			opp.setStatus(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
			String payParam=null;
			if(oppEntity==null){//判断 是不是已完成支付了
				return mobileWxOrAliQrcodeParam(orderMasterId, order_code, price,webSiteAddress,payCode,payType,ip,openId);
			}
		}
		return null;
	}
	public Map<String, String> mobileWxOrAliQrcodeParam(Long orderMasterId,String OrderCode,BigDecimal price,String webSiteAddress,Integer payCode,Integer payType,String ip,String openid) throws Exception{
		String url="";
		OrderPayPrice opp=new OrderPayPrice();
		opp.setOrderMasterId(orderMasterId);
		opp.setOrdersCode(OrderCode);
		opp.setPayType(payType);//移动端还是pc端 1 电脑支付，2移动端支付
		opp.setPayCode(payCode);//1 微信，2支付宝
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		if(oppEntity==null){//判断有没有支付记录
			String OutTradeNo= Long.toString(System.currentTimeMillis());//获取支付批次号
			Date date = new Date();
			if(payCode==1){
				Map<String, String> origMap = payWxOrAliQrcodeParamMap(price, webSiteAddress, date,
						OutTradeNo,payCode,ip,"JSAPI");
				origMap.put("openid", openid);
				Orders orders_time=new Orders();
				orders_time.setMasterId(orderMasterId);
				List<Orders> os = ordersMapper.select(orders_time);
				String expireDate=""; //设置二维码过期时间
				if(!CollectionUtil.collectionIsEmpty(os)){
					Orders orders = os.get(0);
					if(orders!=null){
						Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
						Calendar nowTime = Calendar.getInstance();
						nowTime.setTime(parse);
						nowTime.add(Calendar.MINUTE, 14);
						nowTime.add(Calendar.SECOND, 40);
						expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
						origMap.put("time_expire", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
					}
				}
				if(expireDate.length()==0){
					return null;
				}
				 origMap.put("sign", WXPayUtil.createSign(origMap, WXpayConfig.APIKEY, "MD5"));
				 String mapToXml = WXPayUtil.mapToXml(origMap);
		         Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
		         System.out.println(httpOrder);
		         if(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5")){
			         String return_code = httpOrder.get("return_code");
			         String result_code = httpOrder.get("result_code");
		             if (null != httpOrder && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
		            	 OrderPayPrice record=new OrderPayPrice();
							record.setOrderMasterId(orderMasterId);
							record.setCode(OutTradeNo);
							record.setOrdersCode(OrderCode);
							record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
							record.setPrice(price);
							record.setStatus(0);
							record.setPayType(payType);
							record.setPayCode(payCode);
							record.setReqJson(JsonUtil.toJson(origMap));
							record.setResultJson(JsonUtil.toJson(httpOrder));
							record.setExpireDate(expireDate);
							int resutNum= OrderPayPriceMapper.insertSelective(record);
							if(resutNum>0){
								return httpOrder;
							}
					 }
		         }
			}
		}else{
			String gatewayPost = oppEntity.getResultJson();
			HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
			return result;
		}
		return null;
	}

	@Override
	public Map<String, String> mobileWxOrAliQrcodeOne(Long orderId,
			Long userId, String webSiteAddress, Integer payCode,
			Integer payType, String ip,String openId) throws Exception{
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		if(orders.getOrdersState().equals(OrderStatus.commit)){
			OrderPayPrice op=new OrderPayPrice();
			op.setOrderMasterId(orders.getMasterId());
			op.setOrdersCode(orders.getSn());
			op.setStatus(1);
			OrderPayPrice opr = OrderPayPriceMapper.selectOne(op); //判断是否支付完成
			if(opr==null){
				OrderPayPrice opp=new OrderPayPrice();
				opp.setOrderMasterId(orders.getMasterId());
				opp.setOrdersCode(orders.getSn());
				opp.setPayType(payType);//移动端还是pc端 1 电脑支付，2移动端支付
				opp.setPayCode(payCode);//1 微信，2支付宝
				OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
				if(oppEntity==null){//判断有没有支付信息
					String OutTradeNo= Long.toString(System.currentTimeMillis());//获取支付批次号
					Date date = new Date();
					if(payCode==1){
						Map<String, String> origMap = payWxOrAliQrcodeParamMap(orders.getPayMoney(), webSiteAddress, date,
								OutTradeNo,payCode,ip,"JSAPI");
						origMap.put("openid", openId);
						
						Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
						Calendar nowTime = Calendar.getInstance();
						nowTime.setTime(parse);
						nowTime.add(Calendar.MINUTE, 14);
						nowTime.add(Calendar.SECOND, 40);
						String expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
						origMap.put("time_expire", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
							
						if(expireDate.length()==0){
							return null;
						}
						 origMap.put("sign", WXPayUtil.createSign(origMap, WXpayConfig.APIKEY, "MD5"));
						 String mapToXml = WXPayUtil.mapToXml(origMap);
				         Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
				         System.out.println(httpOrder);
				         if(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5")){
					         String return_code = httpOrder.get("return_code");
					         String result_code = httpOrder.get("result_code");
				             if (null != httpOrder && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
				            	 OrderPayPrice record=new OrderPayPrice();
									record.setOrderMasterId(orders.getMasterId());
									record.setCode(OutTradeNo);
									record.setOrdersCode(orders.getSn());
									record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
									record.setPrice(orders.getPayMoney());
									record.setStatus(0);
									record.setPayType(payType);
									record.setPayCode(payCode);
									record.setReqJson(JsonUtil.toJson(origMap));
									record.setResultJson(JsonUtil.toJson(httpOrder));
									record.setExpireDate(expireDate);
									int resutNum= OrderPayPriceMapper.insertSelective(record);
									if(resutNum>0){
										return httpOrder;
									}
							 }
				         }
					}
				}else{
					String gatewayPost = oppEntity.getResultJson();
					HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
					return result;
				}
			}
		}
		return null;
	}
	@Override
	public String mobileWxMwebOrAliQrcode(Long orderMasterId,
			Long userId, String webSiteAddress, Integer payCode,
			Integer payType, String ip) throws Exception {
		
		Example example=new Example(Orders.class);
		Criteria orderCriteria = example.createCriteria();
		orderCriteria.andEqualTo("isDelete",0);
		orderCriteria.andEqualTo("ordersState","commit");
		orderCriteria.andEqualTo("masterId", orderMasterId);
		orderCriteria.andEqualTo("userId", userId);
		example.setOrderByClause(" id desc");
		List<Orders> orderList = ordersMapper.selectByExample(example); //根据父订单获取所有的订单
		if(!CollectionUtil.collectionIsEmpty(orderList)){
			BigDecimal price=BigDecimalUtil.ZERO;
			String order_code="";
			for (Orders orders : orderList) {
				if(BigDecimalUtil.compareTo(orders.getPayMoney(), BigDecimalUtil.ZERO)>0){
					order_code+=orders.getSn()+"_";
					price=BigDecimalUtil.add(price, orders.getPayMoney());
				}
			}
			if(order_code.length()>0){
				order_code=order_code.substring(0, order_code.length()-1);				
			}
			OrderPayPrice opp=new OrderPayPrice();
			opp.setOrderMasterId(orderMasterId);
			opp.setOrdersCode(order_code);
			opp.setStatus(1);
			OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
			String payParam=null;
			if(oppEntity==null){//判断 是不是已完成支付了
				return mobileWxMwebOrAliQrcodeParam(orderMasterId, order_code, price,webSiteAddress,payCode,payType,ip);
			}
		}
		return null;
	}
	
	public String mobileWxMwebOrAliQrcodeParam(Long orderMasterId,String OrderCode,BigDecimal price,String webSiteAddress,Integer payCode,Integer payType,String ip) throws Exception{
		String url="";
		OrderPayPrice opp=new OrderPayPrice();
		opp.setOrderMasterId(orderMasterId);
		opp.setOrdersCode(OrderCode);
		opp.setPayType(payType);//移动端还是pc端 1 电脑支付，2移动端支付
		opp.setPayCode(payCode);//1 微信，2支付宝
		OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
		if(oppEntity==null){//判断有没有二维码记录
			String OutTradeNo= Long.toString(System.currentTimeMillis());//获取支付批次号
			Date date = new Date();
			if(payCode==1){
				Map<String, String> origMap = payWxOrAliQrcodeParamMap(price, webSiteAddress, date,
						OutTradeNo,payCode,ip,"MWEB");
				Orders orders_time=new Orders();
				orders_time.setMasterId(orderMasterId);
				List<Orders> os = ordersMapper.select(orders_time);
				String expireDate=""; //设置二维码过期时间
				if(!CollectionUtil.collectionIsEmpty(os)){
					Orders orders = os.get(0);
					if(orders!=null){
						Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
						Calendar nowTime = Calendar.getInstance();
						nowTime.setTime(parse);
						nowTime.add(Calendar.MINUTE, 14);
						nowTime.add(Calendar.SECOND, 40);
						expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
						origMap.put("time_expire", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
					}
				}
				if(expireDate.length()==0){
					return null;
				}
				 origMap.put("sign", WXPayUtil.createSign(origMap, WXpayConfig.APIKEY, "MD5"));
				 String mapToXml = WXPayUtil.mapToXml(origMap);
		         Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
		         System.out.println(httpOrder);
		         if(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5")){
			         String return_code = httpOrder.get("return_code");
			         String result_code = httpOrder.get("result_code");
		             if (null != httpOrder && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
		            	 OrderPayPrice record=new OrderPayPrice();
							record.setOrderMasterId(orderMasterId);
							record.setCode(OutTradeNo);
							record.setOrdersCode(OrderCode);
							record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
							record.setPrice(price);
							record.setStatus(0);
							record.setPayType(payType);
							record.setPayCode(payCode);
							record.setReqJson(JsonUtil.toJson(origMap));
							record.setResultJson(JsonUtil.toJson(httpOrder));
							record.setExpireDate(expireDate);
							int resutNum= OrderPayPriceMapper.insertSelective(record);
							if(resutNum>0){
								return httpOrder.get("mweb_url").toString();
							}
					 }
		         }
			}
		}else{
			String gatewayPost = oppEntity.getResultJson();
			HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
			return result.get("mweb_url").toString();
		}
		return null;
	}

	@Override
	public List<Orders> selectChildOrderByMap(Map<String, Object> map) {
		return ordersMapper.selectChildOrderByMap(map);
	}

	@Override
	public Orders getchildOrderView(Map<String, Object> map) {
		List<Orders> list = ordersMapper.selectChildOrderByMap(map);
		if(CollectionUtils.isNotEmpty(list)){
			Orders childOrder = list.get(0);
			if(childOrder != null){
				Orders orders = ordersMapper.selectByPrimaryKey(childOrder.getParentId());
				if(orders != null){
					orders.setItems(childOrder.getItems());
					orders.setParentSn(childOrder.getParentSn());
					orders.setSn(null);
					orders.setEmallSn(childOrder.getChildSn());
					orders.setOrdersState(childOrder.getOrdersState());
					return orders;
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> supplierConfirmedOrder(Long supplierId, Long orderId) {
		Suppliers supplier = suppliersMapper.selectByPrimaryKey(supplierId);
		Orders order = ordersMapper.selectByPrimaryKey(orderId);
		if(supplier != null && ApiCommon.checkCode(supplier.getCode()) && order != null){
			order.setOrdersState(OrderStatus.confirmed);
			updateNotNull(order);
			orderLogsService.saveLog(supplier.getName(), "订单已确认", 1, order.getId());
			return ResultUtil.success("订单已确认");
		}
		return ResultUtil.error("参数错误");
	}

	@Override
	public String mobileWxMwebOrAliQrcodeOne(Long orderId, Long userId,
			String webSiteAddress, Integer payCode, Integer payType, String ip)
			throws Exception {
		String url="";
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		if(orders.getOrdersState().equals(OrderStatus.commit)){
			OrderPayPrice op=new OrderPayPrice();
			op.setOrderMasterId(orders.getMasterId());
			op.setOrdersCode(orders.getSn());
			op.setStatus(1);
			OrderPayPrice opr = OrderPayPriceMapper.selectOne(op); //判断是否支付完成
			if(opr==null){
				OrderPayPrice opp=new OrderPayPrice();
				opp.setOrderMasterId(orders.getMasterId());
				opp.setOrdersCode(orders.getSn());
				opp.setPayType(payType);//移动端还是pc端 1 电脑支付，2移动端支付
				opp.setPayCode(payCode);//1 微信，2支付宝
				OrderPayPrice oppEntity = OrderPayPriceMapper.selectOne(opp);
				if(oppEntity==null){//判断有没有支付地址
					String OutTradeNo= Long.toString(System.currentTimeMillis());//获取支付批次号
					Date date = new Date();
					if(payCode==1){
						Map<String, String> origMap = payWxOrAliQrcodeParamMap(orders.getPayMoney(), webSiteAddress, date,
						OutTradeNo,payCode,ip,"MWEB");
						Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orders.getCreatedAt());
						Calendar nowTime = Calendar.getInstance();
						nowTime.setTime(parse);
						nowTime.add(Calendar.MINUTE, 14);
						nowTime.add(Calendar.SECOND, 40);
						String expireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime());
						origMap.put("time_expire", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
						if(expireDate.length()==0){
							return null;
						}
						 origMap.put("sign", WXPayUtil.createSign(origMap, WXpayConfig.APIKEY, "MD5"));
						 String mapToXml = WXPayUtil.mapToXml(origMap);
				         Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
				         System.out.println(httpOrder);
				         if(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5")){
					         String return_code = httpOrder.get("return_code");
					         String result_code = httpOrder.get("result_code");
				             if (null != httpOrder && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
				            	 OrderPayPrice record=new OrderPayPrice();
									record.setOrderMasterId(orders.getMasterId());
									record.setCode(OutTradeNo);
									record.setOrdersCode(orders.getSn());
									record.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
									record.setPrice(orders.getPayMoney());
									record.setStatus(0);
									record.setPayType(payType);
									record.setPayCode(payCode);
									record.setReqJson(JsonUtil.toJson(origMap));
									record.setResultJson(JsonUtil.toJson(httpOrder));
									record.setExpireDate(expireDate);
									int resutNum= OrderPayPriceMapper.insertSelective(record);
									if(resutNum>0){
										return httpOrder.get("mweb_url").toString();
									}
							 }
				         }
					}
				}else{
					String gatewayPost = oppEntity.getResultJson();
					HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
					return result.get("mweb_url").toString();
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> deleteOrder(Long userId, Long orderId) {
		Orders order = ordersMapper.selectByPrimaryKey(orderId);
		if(order != null && userId.equals(order.getUserId())){
			if(OrderStatus.cancelled.equals(order.getOrdersState()) || OrderStatus.completed.equals(order.getOrdersState())){
				Orders delOrder = new Orders();
				delOrder.setId(order.getId());
				delOrder.setIsDelete(1);
				int updateByPrimaryKeySelective = ordersMapper.updateByPrimaryKeySelective(delOrder);
				if(updateByPrimaryKeySelective > 0){
					return ResultUtil.error("删除成功");
				}else{
					return ResultUtil.error("删除失败");
				}
			}else{
				return ResultUtil.error("该订单无法删除");
			}
		}
		return ResultUtil.error("未查询到该订单");
	}

	@Override
	public List<Orders> orderStatisticalList(Map<String, Object> map) {
		List<Orders> orderStatisticalList = ordersMapper.orderStatisticalList(map);
		return orderStatisticalList;
	}

	@Override
	public void downloadOrderStatistical(Map<String, Object> map, HttpServletResponse response) {
		List<Orders> list = orderStatisticalList(map);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFFont createFont = workbook.createFont();
		createFont.setFontHeightInPoints((short) 12);// 设置字体

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT); // 内容居左
		cellStyle.setFont(createFont);

		XSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle1.setFont(createFont);
		
		XSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setAlignment(HorizontalAlignment.RIGHT);// 水平居右
		cellStyle2.setFont(createFont);

		XSSFSheet sheet = workbook.createSheet("订单统计");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(13, 4000);
		sheet.setColumnWidth(21, 4000);
		sheet.setColumnWidth(22, 4000);
		sheet.setColumnWidth(23, 4000);
		
		XSSFRow row0 = sheet.createRow(0);

		String[] title = { "序号", "组织机构", "用户名", "平台订单", "供应商名称", "电商订单",
				"电商子订单", "下单时间", "完成时间", "订单状态", "成本价", "售价", "毛利", "活动名称",
				"支付形式", "积分消费", "个人消费", "消费合计", "剩余积分", "是否包邮", "运费", "收件人姓名",
				"收件地址", "收件人电话" };
		for (int i = 0; i < title.length; i++) {
			XSSFCell title0 = row0.createCell(i);
			title0.setCellValue(title[i]);
			title0.setCellStyle(cellStyle1);
		}

		for (int i = 0; i < list.size(); i++) {
			Orders orders = list.get(i);
			XSSFRow row = sheet.createRow(i + 1);
			XSSFCell createCell0 = row.createCell(0);
			createCell0.setCellValue(i + 1);
			createCell0.setCellStyle(cellStyle1);
			//组织机构
			XSSFCell createCell1 = row.createCell(1);
			createCell1.setCellValue(orders.getPurchaserName());
			createCell1.setCellStyle(cellStyle);
			//用户名
			XSSFCell createCell2 = row.createCell(2);
			createCell2.setCellValue(orders.getUserName());
			createCell2.setCellStyle(cellStyle);
			//平台订单
			XSSFCell createCell3 = row.createCell(3);
			createCell3.setCellValue(orders.getSn());
			createCell3.setCellStyle(cellStyle);
			//供应商名称
			XSSFCell createCell4 = row.createCell(4);
			createCell4.setCellValue(orders.getSupplierName());
			createCell4.setCellStyle(cellStyle);
			//电商订单
			XSSFCell createCell5 = row.createCell(5);
			createCell5.setCellValue(orders.getEmallSn());
			createCell5.setCellStyle(cellStyle);
			//电商子订单
			XSSFCell createCell6 = row.createCell(6);
			createCell6.setCellValue(orders.getChildOrderEmallSns());
			createCell6.setCellStyle(cellStyle);
			//下单时间
			XSSFCell createCell7 = row.createCell(7);
			createCell7.setCellValue(orders.getCreatedAt());
			createCell7.setCellStyle(cellStyle);
			//完成时间
			XSSFCell createCell8 = row.createCell(8);
			createCell8.setCellValue(orders.getCompletedAt());
			createCell8.setCellStyle(cellStyle);
			//订单状态
			XSSFCell createCell9 = row.createCell(9);
			OrderStatus ordersState = orders.getOrdersState();
			if(ordersState.equals(OrderStatus.commit)){
				createCell9.setCellValue("已提交");
			}else if(ordersState.equals(OrderStatus.confirmed)){
				createCell9.setCellValue("待收货");
			}else if(ordersState.equals(OrderStatus.cancelled)){
				createCell9.setCellValue("已取消");
			}else if(ordersState.equals(OrderStatus.completed)){
				createCell9.setCellValue("已完成");
			}else{
				createCell9.setCellValue("");
			}
			createCell9.setCellStyle(cellStyle1);
			
			//成本价
			XSSFCell createCell10 = row.createCell(10);
			createCell10.setCellValue(orders.getProtocolTotal().setScale(2)+"");
			createCell10.setCellStyle(cellStyle2);
			//售价
			XSSFCell createCell11 = row.createCell(11);
			createCell11.setCellValue(orders.getTotal().setScale(2)+"");
			createCell11.setCellStyle(cellStyle2);
			//毛利
			XSSFCell createCell12= row.createCell(12);
			createCell12.setCellValue(orders.getProfit().setScale(2)+"");
			createCell12.setCellStyle(cellStyle2);
			//活动名称
			XSSFCell createCell13= row.createCell(13);
			createCell13.setCellValue(orders.getActivityNames());
			createCell13.setCellStyle(cellStyle);
			//支付形式
			XSSFCell createCell14= row.createCell(14);
			createCell14.setCellValue(orders.getPayName());
			createCell14.setCellStyle(cellStyle1);
			//积分消费
			XSSFCell createCell15= row.createCell(15);
			createCell15.setCellValue(orders.getPayIntegral().setScale(2)+"");
			createCell15.setCellStyle(cellStyle2);
			//个人消费
			XSSFCell createCell16= row.createCell(16);
			createCell16.setCellValue(orders.getPayMoney().setScale(2)+"");
			createCell16.setCellStyle(cellStyle2);
			//消费合计
			XSSFCell createCell17 = row.createCell(17);
			createCell17.setCellValue(orders.getTotal().setScale(2)+"");
			createCell17.setCellStyle(cellStyle2);
			//剩余积分
			XSSFCell createCell18= row.createCell(18);
			createCell18.setCellValue(orders.getBalanceIntegral().setScale(2)+"");
			createCell18.setCellStyle(cellStyle2);
			//是否包邮
			XSSFCell createCell19 = row.createCell(19);
			createCell19.setCellValue(BigDecimalUtil.compareTo(orders.getFreight(), new BigDecimal("0.00")) == 0 ? "是" : "否");
			createCell19.setCellStyle(cellStyle1);
			//运费
			XSSFCell createCell20 = row.createCell(20);
			createCell20.setCellValue(orders.getFreight().setScale(2)+"");
			createCell20.setCellStyle(cellStyle2);
			//收件人姓名
			XSSFCell createCell21 = row.createCell(21);
			createCell21.setCellValue(orders.getConsigneeName());
			createCell21.setCellStyle(cellStyle);
			//收件地址
			XSSFCell createCell22 = row.createCell(22);
			createCell22.setCellValue(orders.getAddr());
			createCell22.setCellStyle(cellStyle);
			//收件人电话
			XSSFCell createCell23 = row.createCell(23);
			createCell23.setCellValue(orders.getMobile());
			createCell23.setCellStyle(cellStyle1);
		}
		ServletOutputStream fileOut = null;
		try {
			fileOut = response.getOutputStream();
			String fileName = new String("订单统计".getBytes("UTF-8"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			fileOut = response.getOutputStream();
			workbook.write(fileOut);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
