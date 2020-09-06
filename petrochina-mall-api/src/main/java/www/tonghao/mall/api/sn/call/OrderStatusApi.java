package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderStatusReq;
import www.tonghao.mall.api.sn.resultwrap.OrderStatusRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.OrderStatusGetRequest;
import com.suning.api.entity.govbus.OrderStatusGetResponse;
import com.suning.api.entity.govbus.OrderStatusGetResponse.OrderItemInfoList;
import com.suning.api.exception.SuningApiException;

/**
 * 查看订单状态
 */
public class OrderStatusApi extends AbstractSnApi<OrderStatusRes>{

	private static Log logger = LogFactory.getLog(OrderStatusApi.class);
	
	private String orderId;
	
	public OrderStatusApi(String orderId){
		super(new OrderStatusReq());
		this.orderId = orderId;
	}
	
	@Override
	protected OrderStatusRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("OrderStatusApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderStatusRes res = new OrderStatusRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁查看订单状态错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode getOrderStatusNode = bodyNode.path("getOrderStatus");
			JsonNode orderIdNode = getOrderStatusNode.path("orderId");
			//订单状态。1:审核中; 2:待发货; 3:待收货; 4:已完成; 5:已取消; 6:已退货; 7:待处理; 8：审核不通过，订单已取消; 9：待支付
			JsonNode orderStatusNode = getOrderStatusNode.path("orderStatus");
			res.setOrderId(orderIdNode.asText());
			res.setOrderStatus(orderStatusNode.asText());
			JsonNode orderItemInfoNode = getOrderStatusNode.path("orderItemInfoList");
			List<OrderItemInfoList> orderItemInfoList = new ArrayList<OrderItemInfoList>();
			OrderItemInfoList orderItemInfo = null;
			for(JsonNode node : orderItemInfoNode){
				orderItemInfo = new OrderItemInfoList();
				JsonNode skuIdNode = node.path("skuId");				//商品编码
				JsonNode orderItemIdNode = node.path("orderItemId");	//订单行号
				//订单行状态码： 1:审核中; 2:待发货; 3:待收货; 4:已完成; 5:已取消; 6:已退货; 7:待处理; 8：审核不通过，订单已取消; 9：待支付
				JsonNode statusNameNode = node.path("statusName");		//订单行状态码
				orderItemInfo.setSkuId(skuIdNode.asText());
				orderItemInfo.setOrderItemId(orderItemIdNode.asText());
				orderItemInfo.setStatusName(statusNameNode.asText());
				orderItemInfoList.add(orderItemInfo);
			}
			res.setOrderItemInfoList(orderItemInfoList);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		OrderStatusGetRequest request = new OrderStatusGetRequest();
		request.setCheckParam(true);
		request.setOrderId(orderId);
		OrderStatusGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("查询订单状态失败！");
		}
		return response.getBody();
	}
	
}
