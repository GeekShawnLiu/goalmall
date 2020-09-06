package www.tonghao.service.common.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.pay.PayUtils;
import www.tonghao.common.pay.utils.RSA;
import www.tonghao.common.pay.wx.WXPayUtil;
import www.tonghao.common.pay.wx.WXpayConfig;
import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.AfterSaleOrders;
import www.tonghao.service.common.entity.AfterSaleOrdersItem;
import www.tonghao.service.common.entity.JdAfterSale;
import www.tonghao.service.common.entity.OrderPayPrice;
import www.tonghao.service.common.entity.OrderRefundItem;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.AfterSaleMapper;
import www.tonghao.service.common.mapper.AfterSaleOrdersItemMapper;
import www.tonghao.service.common.mapper.AfterSaleOrdersMapper;
import www.tonghao.service.common.mapper.JdAfterSaleMapper;
import www.tonghao.service.common.mapper.OrderPayPriceMapper;
import www.tonghao.service.common.mapper.OrderRefundItemMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.service.common.entity.JobOrderItems;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.mapper.JobOrderItemsMapper;
import www.tonghao.service.common.mapper.JobOrdersMapper;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.OrderRefundItemService;

@Service("orderRefundItemService")
public class OrderRefundItemServiceImpl extends BaseServiceImpl<OrderRefundItem> implements OrderRefundItemService {

	@Autowired
	private OrderRefundItemMapper orderRefundItemMapper;
	
	@Autowired
	private OrderPayPriceMapper orderPayPriceMapper;
	
	@Autowired
	private JdAfterSaleMapper jdAfterSaleMapper;
	
	@Autowired
	private AfterSaleOrdersMapper afterSaleOrdersMapper;
	
	@Autowired
	private AfterSaleOrdersItemMapper afterSaleOrdersItemMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private AfterSaleMapper afterSaleMapper;
	@Autowired
	private IntegralUserService integralUserService;
	@Autowired
	private JobOrdersMapper jobOrdersMapper;
	@Autowired
	private JobOrderItemsMapper jobOrderItemsMapper;
	
	@Override
	public List<OrderRefundItem> getOrderFundItem(Map<String, Object> map) {
		return orderRefundItemMapper.getOrderFundItem(map);
	}

	/*@SuppressWarnings("all")
	@Override
	public int refund(Long id,String webSiteAddress,Users user) throws Exception {
		OrderRefundItem ori = orderRefundItemMapper.selectByPrimaryKey(id);
		if(ori!=null){
			if(ori.getPayId()==2||ori.getPayId()==3){//个人支付，混合支付
				OrderPayPrice opp = orderPayPriceMapper.selectByPrimaryKey(ori.getOrderPayPriceId());
				Date date=new Date();
				Map<String, String> payParamMap = payParamMap(ori.getRefundMoney(),webSiteAddress, date, opp.getCode(), opp.getCjCode());
			    String gatewayPost = PayUtils.gatewayPost(payParamMap);
			    if(gatewayPost!=null&&gatewayPost.length()>0){
			    	HashMap result = JsonUtil.toObject(gatewayPost, HashMap.class);
					Map<String, String> sPara = PayUtils.paraFilter(result);
					System.out.println("原始值："+result);
					System.out.println("去掉关键参数后的值："+sPara);
					String prestr = PayUtils.createLinkString(sPara, false);
					System.out.println("拼接后的值："+prestr);
					boolean verify = RSA.verify(prestr, result.get("Sign").toString(), PayUtils.MERCHANT_PUBLIC_KEY, "UTF-8");
					if(verify){
						String status = result.get("Status").toString();
						ori.setOperationId(user.getId());
						ori.setRefundReqParam(JsonUtil.toJson(payParamMap));
						ori.setRefundResParam(gatewayPost);
						if("S".equals(status.toUpperCase())||"P".equals(status.toUpperCase())){
							ori.setRefundCode(result.get("OrderTrxid").toString());
							ori.setRefundStatus(1);
							int orderRefundItem=orderRefundItemMapper.updateByPrimaryKeySelective(ori);
							return orderRefundItem;
						}else if("F".equals(status.toUpperCase())){
							ori.setRefundStatus(3);
							return orderRefundItemMapper.updateByPrimaryKeySelective(ori);
						}
					}
			    }
			}
			if(ori.getPayId()==1){//积分支付
				Example example=new Example(JobOrders.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("sn", ori.getOrderSn());
				List<JobOrders> order_list = jobOrdersMapper.selectByExample(example);
				if(!CollectionUtil.collectionIsEmpty(order_list)){
					JobOrders jobOrders = order_list.get(0);
					Example item_example=new Example(JobOrderItems.class);
					Criteria item_createCriteria = item_example.createCriteria();
					item_createCriteria.andEqualTo("orderId", jobOrders.getId());
					List<JobOrderItems> item_order = jobOrderItemsMapper.selectByExample(item_example);
					if(!CollectionUtil.collectionIsEmpty(item_order)){
						JobOrderItems jobOrderItems = item_order.get(0);
						String addBalance = integralUserService.addBalance(ori.getRefundUserId(), jobOrderItems.getActivityId(), jobOrders.getId(), ori.getRefundIntegralPrice(), "手动退还积分");
						if("success".equals(addBalance)){
							ori.setRefundStatus(2);
							ori.setOperationId(user.getId());
							return orderRefundItemMapper.updateByPrimaryKeySelective(ori);
						}
					}
				}
				
			}
		}
		return 0;
	}*/
	
	@SuppressWarnings("all")
	@Override
	public int refund(Long id,String webSiteAddress,Users user) throws Exception {
		OrderRefundItem ori = orderRefundItemMapper.selectByPrimaryKey(id);
		if(ori!=null){
			if(ori.getPayId()==2||ori.getPayId()==3){//个人支付，混合支付
				OrderPayPrice opp = orderPayPriceMapper.selectByPrimaryKey(ori.getOrderPayPriceId());
				Date date=new Date();
				 Map<String, String>  payParamMap= refundWxOrAliParamMap(ori.getCjCode(), opp.getPrice(), ori.getRefundMoney(), webSiteAddress);
				 payParamMap.put("sign", WXPayUtil.createSign(payParamMap, WXpayConfig.APIKEY, "MD5"));
				 Map<String, String> refundApihttp = WXPayUtil.refundApihttp(WXPayUtil.mapToXml(payParamMap));
		         if(WXPayUtil.isSignatureValid(refundApihttp, WXpayConfig.APIKEY, "MD5")){
			         String result_code = refundApihttp.get("result_code");
					 ori.setOperationId(user.getId());
					 ori.setRefundReqParam(JsonUtil.toJson(payParamMap));
					 ori.setRefundResParam(JsonUtil.toJson(refundApihttp));
			         if ("SUCCESS".equals(result_code)) {
			        	ori.setRefundCode(refundApihttp.get("refund_id").toString());
						ori.setRefundStatus(1);
		             }else{
		            	 ori.setRefundStatus(3);
		             }
			         int updateByPrimaryKeySelective = orderRefundItemMapper.updateByPrimaryKeySelective(ori);
			         if(updateByPrimaryKeySelective>0){
			        	Example example=new Example(JobOrders.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("sn", ori.getOrderSn());
						List<JobOrders> jo = jobOrdersMapper.selectByExample(example);
						if(!CollectionUtil.collectionIsEmpty(jo)){
							JobOrders jobOrders = jo.get(0);
							jobOrders.setIsCancel(2);
							jobOrdersMapper.updateByPrimaryKeySelective(jobOrders);
						}
			         }
			         return updateByPrimaryKeySelective;
		         }
			}
			if(ori.getPayId()==1){//积分支付
				Example example=new Example(JobOrders.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("sn", ori.getOrderSn());
				List<JobOrders> order_list = jobOrdersMapper.selectByExample(example);
				if(!CollectionUtil.collectionIsEmpty(order_list)){
					JobOrders jobOrders = order_list.get(0);
					Example item_example=new Example(JobOrderItems.class);
					Criteria item_createCriteria = item_example.createCriteria();
					item_createCriteria.andEqualTo("orderId", jobOrders.getId());
					List<JobOrderItems> item_order = jobOrderItemsMapper.selectByExample(item_example);
					if(!CollectionUtil.collectionIsEmpty(item_order)){
						JobOrderItems jobOrderItems = item_order.get(0);
						String addBalance = integralUserService.addBalance(ori.getRefundUserId(), jobOrderItems.getActivityId(), jobOrders.getId(), ori.getRefundIntegralPrice(), "手动退还积分");
						if("success".equals(addBalance)){
							ori.setRefundStatus(2);
							ori.setOperationId(user.getId());
							int updateByPrimaryKeySelective = orderRefundItemMapper.updateByPrimaryKeySelective(ori);
							if(updateByPrimaryKeySelective>0){
								jobOrders.setIsCancel(2);
								jobOrdersMapper.updateByPrimaryKeySelective(jobOrders);
							}
							return updateByPrimaryKeySelective;
						}
					}
				}
				
			}
		}
		return 0;
	}
	/*private Map<String, String> payParamMap(BigDecimal price,
			String webSiteAddress, Date date, String orderSn,String cjCode) {
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put("Service", "nmg_api_refund");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", PayUtils.USER_NO);// 畅捷支付分配的商户号
		origMap.put("InputCharset", PayUtils.charset);// 字符集
		origMap.put("TradeDate",  new SimpleDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(date));
		origMap.put("Memo", "退款申请");
		// 2.2 业务参数
		origMap.put("TrxId", cjCode);// 订单号
		origMap.put("OriTrxId", orderSn);// 原有支付请求订单号
		origMap.put("TrxAmt", price+"");// 退款金额
		origMap.put("NotifyUrl", webSiteAddress);// 异步通知地址
		return origMap;
	}*/
	
	private Map<String, String> refundWxOrAliParamMap(String transaction_id,BigDecimal total_fee,BigDecimal refund_fee,String notify_url){
        Map<String, String> origMap = new HashMap<String, String>();
        origMap.put("appid", WXpayConfig.APPID);//公众账号ID
        origMap.put("mch_id", WXpayConfig.WXPAYMENTACCOUNT);//商户号
        origMap.put("nonce_str", WXPayUtil.generateNonceStr());
        origMap.put("transaction_id",transaction_id);
        origMap.put("total_fee", String.valueOf(total_fee.doubleValue()*100).split("\\.")[0]);
        origMap.put("refund_fee",String.valueOf(refund_fee.doubleValue()*100).split("\\.")[0]);
        origMap.put("notify_url",notify_url);
        origMap.put("out_refund_no",new Date().getTime()+"");
        return origMap;
    }

	@Override
	public Map<String, Object> afterSaleRefund(Long afsId, String type) {
		AfterSaleOrders afterSaleOrders = null;
		AfterSaleOrdersItem afterSaleOrdersItem = null;
		//退货
		Integer num = null;
		//本次退款明细
		OrderRefundItem orderRefundItem = new OrderRefundItem();
		if(type != null && type.equals("1")){
			//jd售后
			JdAfterSale jdAfterSale = jdAfterSaleMapper.selectByPrimaryKey(afsId);
			if(jdAfterSale == null){
				return ResultUtil.error("未找到售后信息");
			}
			num = jdAfterSale.getProductNum();
			afterSaleOrders = afterSaleOrdersMapper.selectByPrimaryKey(jdAfterSale.getOrderId());
			afterSaleOrdersItem = afterSaleOrdersItemMapper.selectByPrimaryKey(jdAfterSale.getOrderItemId());
			//判断当前售后申请是否记录过
			Example exampleOrder = new Example(OrderRefundItem.class);
			Criteria criteriaOrder = exampleOrder.createCriteria();
			criteriaOrder.andEqualTo("afsId", afsId);
			criteriaOrder.andEqualTo("afsType", 1);
			List<OrderRefundItem> selectByExample = orderRefundItemMapper.selectByExample(exampleOrder);
			if(CollectionUtils.isNotEmpty(selectByExample)){
				return ResultUtil.error("当前售后已经申请过退款");
			}
			orderRefundItem.setAfsId(afsId);
			orderRefundItem.setAfsType(1);
		}else{
			//其他售后
			AfterSale afterSale = afterSaleMapper.selectByPrimaryKey(afsId);
			if(afterSale == null){
				return ResultUtil.error("未找到售后信息");
			}
			num = afterSale.getProductNum();
			afterSaleOrdersItem = afterSaleOrdersItemMapper.selectByPrimaryKey(afterSale.getOrderItmesId());
			if(afterSaleOrdersItem == null || afterSaleOrdersItem.getOrderId() ==null){
				return ResultUtil.error("未找到订单信息");
			}
			afterSaleOrders = afterSaleOrdersMapper.selectByPrimaryKey(afterSaleOrdersItem.getOrderId());
			//判断当前售后申请是否记录过
			Example exampleOrder = new Example(OrderRefundItem.class);
			Criteria criteriaOrder = exampleOrder.createCriteria();
			criteriaOrder.andEqualTo("afsId", afsId);
			criteriaOrder.andEqualTo("afsType", 2);
			List<OrderRefundItem> selectByExample = orderRefundItemMapper.selectByExample(exampleOrder);
			if(CollectionUtils.isNotEmpty(selectByExample)){
				return ResultUtil.error("当前售后已经申请过退款");
			}
			orderRefundItem.setAfsId(afsId);
			orderRefundItem.setAfsType(2);
		}
		if(afterSaleOrders == null || afterSaleOrdersItem == null || num == null || afterSaleOrders.getPayId() == null){
			return ResultUtil.error("未找到订单信息");
		}
		Users user = usersMapper.selectByPrimaryKey(afterSaleOrders.getUserId());
		if(user == null){
			return ResultUtil.error("未找到采购人信息");
		}
		
		//获取当前订单已经退还的金额
		BigDecimal refundIntegralPriceTotal = BigDecimalUtil.ZERO;
		BigDecimal refundMoneyTotal = BigDecimalUtil.ZERO;
		Example example = new Example(OrderRefundItem.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("orderSn", afterSaleOrders.getSn());
		createCriteria.andEqualTo("refundUserId", user.getId());
		List<OrderRefundItem> orderRefundItemList = orderRefundItemMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(orderRefundItemList)){
			for (OrderRefundItem orderRefundItem22 : orderRefundItemList) {
				refundIntegralPriceTotal = BigDecimalUtil.add(refundIntegralPriceTotal, orderRefundItem22.getRefundIntegralPrice() == null ? BigDecimalUtil.ZERO : orderRefundItem22.getRefundIntegralPrice());
				refundMoneyTotal = BigDecimalUtil.add(refundMoneyTotal, orderRefundItem22.getRefundMoney() == null ? BigDecimalUtil.ZERO : orderRefundItem22.getRefundMoney());
			}
		}
		
		orderRefundItem.setPayId(afterSaleOrders.getPayId());
		orderRefundItem.setCreateAt(DateUtilEx.timeFormat(new Date()));
		orderRefundItem.setType(2);
		orderRefundItem.setOrderSn(afterSaleOrders.getSn());
		orderRefundItem.setOrderTotal(afterSaleOrders.getTotal());
		orderRefundItem.setRefundUserId(user.getId());
		orderRefundItem.setRefundUserName(user.getRealName());
		orderRefundItem.setOrderMasterId(afterSaleOrders.getMasterId());
		orderRefundItem.setRefundStatus(0);
		if(afterSaleOrders.getPayId() == 1){
			//积分支付
			orderRefundItem.setRefundIntegralPrice(BigDecimalUtil.multiply(afterSaleOrdersItem.getPrice(), new BigDecimal(num+"")));
			orderRefundItem.setRefundMoney(BigDecimalUtil.ZERO);
		}else if(afterSaleOrders.getPayId() == 2){
			//个人支付
			orderRefundItem.setRefundMoney(BigDecimalUtil.multiply(afterSaleOrdersItem.getPrice(), new BigDecimal(num+"")));
			orderRefundItem.setRefundIntegralPrice(BigDecimalUtil.ZERO);
		}else if(afterSaleOrders.getPayId() == 3){
			//混合支付   优先退款
			//本次需要退的总金额
			BigDecimal refundPrice = BigDecimalUtil.multiply(afterSaleOrdersItem.getPrice(), new BigDecimal(num+""));
			//支付的钱
			BigDecimal payMoney = afterSaleOrders.getPayMoney() == null ? BigDecimalUtil.ZERO : afterSaleOrders.getPayMoney();
			//本次需要退的金额
			BigDecimal refundIntegralPrice = BigDecimalUtil.ZERO;
			BigDecimal refundMoney = BigDecimalUtil.ZERO;
			if(BigDecimalUtil.compareTo(refundMoneyTotal, payMoney) < 0){
				//钱没有退完  此次还需要退钱
				BigDecimal subtract = BigDecimalUtil.subtract(payMoney, refundMoneyTotal);
				if(BigDecimalUtil.compareTo(refundPrice, subtract) <= 0){
					//剩余的钱足够  此次继续全部退钱
					refundMoney = refundPrice;
				}else{
					refundMoney = subtract;
					refundIntegralPrice = BigDecimalUtil.subtract(refundPrice, subtract);
				}
			}else{
				//钱已经退完   直接退积分
				refundIntegralPrice = refundPrice;
			}
			orderRefundItem.setRefundIntegralPrice(refundIntegralPrice);
			orderRefundItem.setRefundMoney(refundMoney);
		}
		BigDecimal refundTotal = BigDecimalUtil.add(refundIntegralPriceTotal, refundMoneyTotal);
		if(BigDecimalUtil.compareTo(BigDecimalUtil.add(BigDecimalUtil.add(refundTotal, orderRefundItem.getRefundIntegralPrice()), orderRefundItem.getRefundMoney()), afterSaleOrders.getTotal()) > 0){
			return ResultUtil.error("退款金额大于支付总金额");
		}
		int insertSelective = orderRefundItemMapper.insertSelective(orderRefundItem);
		if(insertSelective > 0){
			return ResultUtil.success("");
		}else{
			return ResultUtil.error("添加退款记录异常");
		}
	}
}
