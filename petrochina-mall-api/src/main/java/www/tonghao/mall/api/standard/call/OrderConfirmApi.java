package www.tonghao.mall.api.standard.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.reqwrap.OrderConfirmReq;
import www.tonghao.mall.api.standard.resultwrap.OrderConfirmRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 确认订单接口
 *
 */
public class OrderConfirmApi extends AbstractBusinesApi<OrderConfirmRes>{
	private static Log logger = LogFactory.getLog(OrderConfirmApi.class);
	public OrderConfirmApi(String emallcode,String orderId){
		super(new OrderConfirmReq(emallcode, orderId));
	}
	@Override
	protected OrderConfirmRes resolver(String result) {
		logger.info("OrderCreateApi result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderConfirmRes res = new OrderConfirmRes();
		res.setSuccess(success);
		if(!success){
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.asText());
		}
		return res;
	}
	
}
