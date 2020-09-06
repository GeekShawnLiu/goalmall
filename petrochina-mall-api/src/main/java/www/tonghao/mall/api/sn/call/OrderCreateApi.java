package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.attwrap.OrderCreateAttr;
import www.tonghao.mall.api.sn.entity.Order;
import www.tonghao.mall.api.sn.reqwrap.OrderCreateReq;
import www.tonghao.mall.api.sn.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.MixpayorderAddRequest;
import com.suning.api.entity.govbus.MixpayorderAddResponse;
import com.suning.api.entity.govbus.MixpayorderAddRequest.Sku;
import com.suning.api.entity.govbus.MixpayorderAddRequest.SpecialVatTicket;
import com.suning.api.entity.govbus.MixpayorderAddResponse.Skus;
import com.suning.api.exception.SuningApiException;

/**
 * 创建订单接口
 *
 */
public class OrderCreateApi extends AbstractSnApi<OrderCreateRes> {
	private static Log logger = LogFactory.getLog(CategoryGetApi.class);
	
	private Order order;
	public OrderCreateApi(Order order){
		super(new OrderCreateReq());
		this.order=order;
	}
	
	@Override
	protected OrderCreateRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("OrderCreateApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderCreateRes res = new OrderCreateRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁创建订单错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			OrderCreateAttr attr=new OrderCreateAttr();
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode mixpayorderNode = bodyNode.path("addMixpayorder");
			JsonNode orderIdNode = mixpayorderNode.path("orderId");	//苏宁订单号
			JsonNode amountNode = mixpayorderNode.path("amount");	//商品总金额
			JsonNode freightNode = mixpayorderNode.path("freight");	//运费
			attr.setOrderId(orderIdNode.asText());
			attr.setAmount(amountNode.decimalValue());
			attr.setFreight(freightNode.decimalValue());
			
			JsonNode skusNode = mixpayorderNode.path("skus");
			List<Skus> skus = new ArrayList<Skus>();
			Skus sku = null;
			for(JsonNode node : skusNode){
				sku = new Skus();
				JsonNode skuIdNode = node.path("skuId");	//商品编码
				JsonNode orderItemIdNode = node.path("orderItemId");	//苏宁订单行号（唯一）
				JsonNode arriveDataNode = node.path("arriveData");	//预计送达时间（yyyy-MM-dd HH:mm:ss）
				JsonNode numNode = node.path("num");	//商品数量
				JsonNode packageNumberNode = node.path("packageNumber");	//套餐商品编码
				sku.setSkuId(skuIdNode.asText());
				sku.setOrderItemId(orderItemIdNode.asText());
				sku.setArriveData(arriveDataNode.asText());
				sku.setNum(numNode.asText());
				sku.setPackageNumber(packageNumberNode.asText());
				skus.add(sku);
			}
			attr.setSkus(skus);
			res.setResult(attr);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		MixpayorderAddRequest request = new MixpayorderAddRequest();
		request.setCheckParam(true);	//api入参校验逻辑开关
		request.setTradeNo(order.getTradeNo());
		request.setCompanyCustNo(order.getCompanyCustNo());
		request.setSku(order.getSku());
		request.setReceiverName(order.getReceiverName());
		request.setTelephone(order.getTelephone());
		request.setMobile(order.getMobile());
		request.setEmail(order.getEmail());
		
		request.setProvinceId(order.getProvinceId());	
		request.setCityId(order.getCityId());
		request.setCountyId(order.getCountyId());
		request.setAddress(order.getAddress());	//详细地址(不能多于80汉字)
		request.setRemark(order.getRemark());	//备注(不能多于100个汉字)
		request.setZip(order.getZip());	//邮编(6位数字)
		request.setInvoiceState(order.getInvoiceState());	//是否开发票【1=开，0=不开】
		
		request.setInvoiceType(order.getInvoiceType()); //发票类型【1=增值发票 2=普通发票 4-电子发票  6=统一增票  9-统一电子发票】
		request.setInvoiceTitle(order.getInvoiceTitle());	//发票抬头
		request.setInvoiceContent(order.getInvoiceContent());  //发票内容【1:明细，3：电脑配件，19:耗材，22：办公用品】
		
		
		SpecialVatTicket specialVatTicket=new SpecialVatTicket();
		specialVatTicket.setTaxNo(order.getTaxNo());
		
		if("1".equals(order.getInvoiceType())){
			specialVatTicket.setRegTel(order.getRegTel());	//增票注册电话（发票类型为1时必传）
			specialVatTicket.setRegAccount(order.getRegAccount());	//增票银行注册账户（发票类型为1时必传）
			specialVatTicket.setRegAdd(order.getRegAdd()); 	//增票注册地址（发票类型为1时必传）
			specialVatTicket.setCompanyName(order.getCompanyName());	//增票注册公司名称（发票类型为1时必传）
			specialVatTicket.setRegBank(order.getRegBank());
		}
		if("1".equals(order.getInvoiceType())||"3".equals(order.getInvoiceType())){
			specialVatTicket.setConsigneeName(order.getConsigneeName());	//收票件人姓名（发票类型为1/3时必传）
			specialVatTicket.setConsigneeAddress(order.getConsigneeAddress());	//收票件人地址（发票类型为1/3时必传）
			specialVatTicket.setConsigneeMobileNum(order.getConsigneeMobileNum());	//收票件人电话（发票类型为1/3时必传）
		}
		request.setSpecialVatTicket(specialVatTicket);
		request.setPayment(order.getPayment());	//支付方式：01-在线支付-易付宝；03-货到付款现金；04-货到付款POS；08-账期；09-预付款；11-第三方支付；020-在线支付-网银
		request.setAmount(order.getAmount());	//订单总金额
		request.setOrderType(order.getOrderType());	//订单类型：0-实时型订单； 1-预占型订单（默认：预占型）
		request.setServFee(order.getServFee());	//运费
		MixpayorderAddResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("创建订单失败！");
		}
		return response.getBody();
	}

}
