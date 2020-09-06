package www.tonghao.mall.api.sn.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderCancelReq;
import www.tonghao.mall.api.sn.resultwrap.OrderCancelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.RejectOrderDeleteRequest;
import com.suning.api.entity.govbus.RejectOrderDeleteResponse;
import com.suning.api.exception.SuningApiException;

/**
 * 取消预占订单
 */
public class OrderCancelApi extends AbstractSnApi<OrderCancelRes>{

	private static Log logger = LogFactory.getLog(OrderCancelApi.class);
	
	private String orderId;
	
	public OrderCancelApi(String orderId){
		super(new OrderCancelReq());
		this.orderId = orderId;
	}
	
	@Override
	protected OrderCancelRes resolver(DefaultSuningClient client) {
		String result = result(client);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderCancelRes res = new OrderCancelRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁取消预占订单错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode deleteRejectOrderNode = bodyNode.path("deleteRejectOrder");
			JsonNode unCampSuccessNode = deleteRejectOrderNode.path("unCampSuccess");
			if (unCampSuccessNode.asText().equals("N")) {
				res.setSuccess(false);
				JsonNode errorCodeNode = unCampSuccessNode.path("error_code");
				JsonNode errorMsgNode = unCampSuccessNode.path("error_msg");
				res.setError_code(errorCodeNode.asText());
				res.setError_msg(errorMsgNode.asText());
				return res;
			}else{
				res.setSuccess(true);
			}
			res.setUnCampSuccess(unCampSuccessNode.asText());
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		RejectOrderDeleteRequest request = new RejectOrderDeleteRequest();
		request.setCheckParam(true);
		request.setOrderId(orderId);
		RejectOrderDeleteResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("取消预占订单失败！");
		}
		return response.getBody();
	}
	
}
