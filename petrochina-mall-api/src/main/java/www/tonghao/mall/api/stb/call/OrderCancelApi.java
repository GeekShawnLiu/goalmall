package www.tonghao.mall.api.stb.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.reqwarp.OrderCancelReq;
import www.tonghao.mall.api.stb.resultwrap.OrderCancelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class OrderCancelApi  extends AbstractBusinesApi<OrderCancelRes>{
	private static Log logger = LogFactory.getLog(OrderCancelApi.class);
	public OrderCancelApi(String supplierOrderId) {
		super(new OrderCancelReq(supplierOrderId));
	}
	
	
	@Override
	protected OrderCancelRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderCancelRes cancelRes=new OrderCancelRes();
		cancelRes.setSuccess(success);
		if(success) {
			boolean resultNode = rootNode.path("result").asBoolean();
			cancelRes.setResult(resultNode);
		}else{
			JsonNode descNode = rootNode.path("resultMessage");
			cancelRes.setDesc(descNode.asText());
		}
		return cancelRes;
	}

}
