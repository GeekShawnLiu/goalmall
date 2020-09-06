package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderTrackReq;
import www.tonghao.mall.api.sn.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.OrderlogistnewGetRequest;
import com.suning.api.entity.govbus.OrderlogistnewGetRequest.OrderItemIds;
import com.suning.api.entity.govbus.OrderlogistnewGetResponse.OrderLogistics;
import com.suning.api.entity.govbus.OrderlogistnewGetResponse.PackageIds;
import com.suning.api.exception.SuningApiException;

/**
 * 获取订单物流详情
 */
public class OrderTrackApi extends AbstractSnApi<OrderTrackRes>{

	private static Log logger = LogFactory.getLog(OrderTrackApi.class);
	
	private String orderId;
	private List<OrderItemIds> orderItemIds;
	
	public OrderTrackApi(String orderId,List<OrderItemIds> orderItemIds){
		super(new OrderTrackReq());
		this.orderId = orderId;
		this.orderItemIds = orderItemIds;
	}
	
	@Override
	protected OrderTrackRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("OrderTrackApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		JsonNode errorNode = successNode.findPath("sn_error");
		OrderTrackRes res = new OrderTrackRes();
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取订单物流信息错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode getOrderlogistnewNode = bodyNode.path("getOrderlogistnew");
			
			JsonNode isPackageNode = getOrderlogistnewNode.path("isPackage");	//Y：有包裹信息；N：暂无包裹信息
			JsonNode orderIdNode = getOrderlogistnewNode.path("orderId");		//订单号
			res.setIsPackage(isPackageNode.asText());
			res.setOrderId(orderIdNode.asText());
			
			JsonNode packageIdsNode = getOrderlogistnewNode.path("packageIds");
			List<PackageIds> packageIds = new ArrayList<PackageIds>();
			PackageIds packageId = null;
			for(JsonNode node : packageIdsNode){
				packageId = new PackageIds();
				JsonNode packageIdNode = node.path("packageId");
				JsonNode receiveTimeNode = node.path("receiveTime");
				JsonNode shippingTimeNode = node.path("shippingTime");
				packageId.setPackageId(packageIdNode.asText());
				packageId.setReceiveTime(receiveTimeNode.asText());
				packageId.setShippingTime(shippingTimeNode.asText());
				
				JsonNode orderItemIdsNode = node.path("orderItemIds");
				List<com.suning.api.entity.govbus.OrderlogistnewGetResponse.OrderItemIds> orderItemIdList 
									= new ArrayList<com.suning.api.entity.govbus.OrderlogistnewGetResponse.OrderItemIds>();
				com.suning.api.entity.govbus.OrderlogistnewGetResponse.OrderItemIds orderItemIds = null;
				for(JsonNode ItemIdNode : orderItemIdsNode){
					orderItemIds = new com.suning.api.entity.govbus.OrderlogistnewGetResponse.OrderItemIds();
					JsonNode orderItemIdNode = ItemIdNode.path("orderItemId");		//订单行号
					JsonNode skuIdNode = ItemIdNode.path("skuId");		//商品编码
					orderItemIds.setOrderItemId(orderItemIdNode.asText());
					orderItemIds.setSkuId(skuIdNode.asText());
					orderItemIdList.add(orderItemIds);
				}
				packageId.setOrderItemIds(orderItemIdList);
				
				JsonNode orderLogisticsNode = node.path("orderLogistics");
				List<OrderLogistics> orderLogistics = new ArrayList<OrderLogistics>();
				OrderLogistics orderLogistic = null;
				for(JsonNode orderLogisticNode : orderLogisticsNode){
					orderLogistic = new OrderLogistics();
					JsonNode operateTimeNode = orderLogisticNode.path("operateTime");		//操作时间
					JsonNode operateStateNode = orderLogisticNode.path("operateState");		//物流状态
					orderLogistic.setOperateTime(operateTimeNode.asText());
					orderLogistic.setOperateState(operateStateNode.asText());
					orderLogistics.add(orderLogistic);
				}
				packageId.setOrderLogistics(orderLogistics);
				packageIds.add(packageId);
			}
			res.setPackageIds(packageIds);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		OrderlogistnewGetRequest request = new OrderlogistnewGetRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setOrderItemIds(orderItemIds);
		request.setOrderId(orderId);
		com.suning.api.entity.govbus.OrderlogistnewGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取订单物流详情失败！");
		}
		return response.getBody();
	}
	
}
