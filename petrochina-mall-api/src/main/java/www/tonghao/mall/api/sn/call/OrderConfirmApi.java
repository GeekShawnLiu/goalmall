package www.tonghao.mall.api.sn.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderConfirmReq;
import www.tonghao.mall.api.sn.resultwrap.OrderConfirmRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.MixconfirmorderAddRequest;
import com.suning.api.entity.govbus.MixconfirmorderAddResponse;
import com.suning.api.exception.SuningApiException;

/**
 * 确认订单
 */
public class OrderConfirmApi extends AbstractSnApi<OrderConfirmRes>{

	private static Log logger = LogFactory.getLog(OrderConfirmApi.class);
	
	private String orderId;
	
	public OrderConfirmApi(String orderId){
		super(new OrderConfirmReq());
		this.orderId = orderId;
	}
	
	@Override
	protected OrderConfirmRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("OrderConfirmApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderConfirmRes res = new OrderConfirmRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁确认订单接口错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode confirmorderNode = bodyNode.path("addMixconfirmorder");
			JsonNode campSuccessNode = confirmorderNode.path("campSuccess");
			res.setCampSuccess(campSuccessNode.asText());
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		MixconfirmorderAddRequest request = new MixconfirmorderAddRequest();
		request.setCheckParam(true);
		request.setOrderId(orderId);
		MixconfirmorderAddResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("确认订单失败！");
		}
		return response.getBody();
	}
	
}
