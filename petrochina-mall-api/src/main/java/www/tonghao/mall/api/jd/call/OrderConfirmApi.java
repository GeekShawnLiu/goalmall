package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.OrderConfirmReq;
import www.tonghao.mall.api.jd.resultwrap.OrderConfirmRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 确认订单接口
 *
 */
public class OrderConfirmApi extends AbstractBusinesApi<OrderConfirmRes>{
	private static Log logger = LogFactory.getLog(OrderConfirmApi.class);
	public OrderConfirmApi(String orderId){
		super(new OrderConfirmReq(orderId));
	}
	@Override
	protected OrderConfirmRes resolver(String result) {
		logger.info("OrderConfirmApi result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderConfirmRes res = new OrderConfirmRes();
		res.setSuccess(success);
		JsonNode resultMessageNode = rootNode.path("resultMessage");
		JsonNode resultCodeNode = rootNode.path("resultCode");
		JsonNode result_CodeNode = rootNode.path("result");
		res.setResultCode(resultCodeNode.asText());
		res.setResultMessage(resultMessageNode.asText());
		res.setResult(result_CodeNode.asBoolean());
		return res;
	}
	
}
