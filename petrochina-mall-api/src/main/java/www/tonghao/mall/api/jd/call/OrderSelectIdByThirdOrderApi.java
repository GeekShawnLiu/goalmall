package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.OrderSelectIdByThirdOrderReq;
import www.tonghao.mall.api.jd.resultwrap.OrderSelectIdByThirdOrderRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *  订单反查接口
 *
 */
public class OrderSelectIdByThirdOrderApi extends AbstractBusinesApi<OrderSelectIdByThirdOrderRes> {
	private static Log log = LogFactory.getLog(OrderSelectIdByThirdOrderApi.class);
	
	public OrderSelectIdByThirdOrderApi(String orderId){
		super(new OrderSelectIdByThirdOrderReq(orderId));
	}
	@Override
	protected OrderSelectIdByThirdOrderRes resolver(String result) {
		log.info("OrderSelectIdByThirdOrderApi result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderSelectIdByThirdOrderRes byThirdOrderRes=new OrderSelectIdByThirdOrderRes();
		byThirdOrderRes.setSuccess(success);
		JsonNode resultMessageNode = rootNode.path("resultMessage");
		JsonNode resultCodeNode = rootNode.path("resultCode");
		JsonNode result_CodeNode = rootNode.path("result");
		byThirdOrderRes.setResultCode(resultCodeNode.asText());
		byThirdOrderRes.setResultMessage(resultMessageNode.asText());
		byThirdOrderRes.setResult(result_CodeNode.asText());
		return byThirdOrderRes;
	}

}
