package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.OrderCancelReq;
import www.tonghao.mall.api.jd.resultwrap.OrderCancelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * 取消订单接口
 *
 */
public class OrderCancelApi extends AbstractBusinesApi<OrderCancelRes>{
	
	private static Log logger = LogFactory.getLog(OrderCancelApi.class);

	public OrderCancelApi(String order_id){
		super(new OrderCancelReq(order_id));
	}
	@Override
	protected OrderCancelRes resolver(String result) {
		logger.info("OrderCancelApi result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderCancelRes res = new OrderCancelRes();
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
