package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderDetailReq;
import www.tonghao.mall.api.sn.resultwrap.OrderDetailRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.OrderDetailGetRequest;
import com.suning.api.entity.govbus.OrderDetailGetResponse;
import com.suning.api.entity.govbus.OrderDetailGetResponse.OrderItemList;
import com.suning.api.exception.SuningApiException;

/**
 * 查询单个订单详细
 */
public class OrderDetailApi extends AbstractSnApi<OrderDetailRes>{

	private static Log logger = LogFactory.getLog(OrderDetailApi.class);
	
	private String orderId;
	
	public OrderDetailApi(String orderId){
		super(new OrderDetailReq());
		this.orderId = orderId;
	}
	
	@Override
	protected OrderDetailRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("OrderDetailApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderDetailRes res = new OrderDetailRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁查询单个订单信息错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode orderDetailNode = bodyNode.path("getOrderDetail");
			
			JsonNode createTimeNode = orderDetailNode.path("createTime");
			JsonNode accountNameNode = orderDetailNode.path("accountName");
			JsonNode receiverTelNode = orderDetailNode.path("receiverTel");
			JsonNode companyNameNode = orderDetailNode.path("companyName");
			JsonNode receiverAddressNode = orderDetailNode.path("receiverAddress");
			JsonNode orderIdNode = orderDetailNode.path("orderId");
			JsonNode orderAmtNode = orderDetailNode.path("orderAmt");
			res.setCreateTime(createTimeNode.asText());
			res.setAccountName(accountNameNode.asText());
			res.setReceiverTel(receiverTelNode.asText());
			res.setCompanyName(companyNameNode.asText());
			res.setReceiverAddress(receiverAddressNode.asText());
			res.setOrderId(orderIdNode.asText());
			res.setOrderAmt(orderAmtNode.asText());
			
			JsonNode orderItemListNode = orderDetailNode.path("orderItemList");
			List<OrderItemList> orderItemLists = new ArrayList<OrderItemList>();
			OrderItemList orderItemList = null;
			for(JsonNode node : orderItemListNode){
				orderItemList = new OrderItemList();
				JsonNode brandNameNode = node.path("brandName");
				JsonNode commdtyCodeNode = node.path("commdtyCode");
				JsonNode commdtyNameNode = node.path("commdtyName");
				JsonNode hopeArriveTimeNode = node.path("hopeArriveTime");
				JsonNode orderItemIdNode = node.path("orderItemId");
				JsonNode skuAmtNode = node.path("skuAmt");
				JsonNode skuNumNode = node.path("skuNum");
				JsonNode unitPriceNode = node.path("unitPrice");
				orderItemList.setBrandName(brandNameNode.asText());
				orderItemList.setCommdtyCode(commdtyCodeNode.asText());
				orderItemList.setCommdtyName(commdtyNameNode.asText());
				orderItemList.setHopeArriveTime(hopeArriveTimeNode.asText());
				orderItemList.setOrderItemId(orderItemIdNode.asText());
				orderItemList.setSkuAmt(skuAmtNode.asText());
				orderItemList.setSkuNum(skuNumNode.asText());
				orderItemList.setUnitPrice(unitPriceNode.asText());
				orderItemLists.add(orderItemList);
			}
			res.setOrderItemList(orderItemLists);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		OrderDetailGetRequest request = new OrderDetailGetRequest();
		request.setCheckParam(true);
		request.setOrderId(orderId);
		OrderDetailGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("查询订单详情失败！");
		}
		return response.getBody();
	}
	
}
