package www.tonghao.mall.api.standard.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.reqwrap.OrderCancelReq;
import www.tonghao.mall.api.standard.resultwrap.OrderCancelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * 取消订单接口
 *
 */
public class OrderCancelApi extends AbstractBusinesApi<OrderCancelRes>{
	
	private static Log logger = LogFactory.getLog(OrderCancelApi.class);

	public OrderCancelApi(String emallcode,String order_id){
		super(new OrderCancelReq(emallcode,order_id));
	}
	@Override
	protected OrderCancelRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderCancelRes res = new OrderCancelRes();
		res.setSuccess(success);
		if(!success){
			JsonNode desc = rootNode.path("desc");
			res.setDesc(desc.asText());
		}
		return res;
	}
	
}
