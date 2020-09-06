package www.tonghao.mall.api.standard.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.OrdSelectSkus;
import www.tonghao.mall.api.standard.attwrap.OrderReturnAttr;
import www.tonghao.mall.api.standard.entity.Returns;
import www.tonghao.mall.api.standard.reqwrap.OrderReturnReq;
import www.tonghao.mall.api.standard.resultwrap.OrderReturnRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 退换货接口
 *	
 */
public class OrderReturnApi extends AbstractBusinesApi<OrderReturnRes>{

	private static Log log = LogFactory.getLog(OrderReturnApi.class);
	
	public OrderReturnApi(String emallCode,Returns returns){
		super(new OrderReturnReq(emallCode,returns));
	}
	@Override
	protected OrderReturnRes resolver(String result) {
		log.info("退货接口result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderReturnRes res = new OrderReturnRes();
		res.setSuccess(success);
		if (success) {
			JsonNode resultNode = rootNode.path("result");
			JsonNode orderIdNode = resultNode.path("order_id");
			JsonNode typeNode = resultNode.path("type");
			JsonNode skusNode = resultNode.path("skus");
			JsonNode orderServiceIdNode = resultNode.path("order_service_id");
			OrderReturnAttr returnResult = new OrderReturnAttr();
			returnResult.setOrder_id(orderIdNode.asText());
			returnResult.setType(typeNode.asText());
			returnResult.setOrder_service_id(orderServiceIdNode.asText());
			OrdSelectSkus sku = null;
			List<OrdSelectSkus> list=new ArrayList<OrdSelectSkus>();
			for(JsonNode node:skusNode){
				sku = new OrdSelectSkus();
				sku.setSku(node.path("sku").toString());
				sku.setPrice(node.path("price").decimalValue());
				sku.setNum(node.path("num").asInt());
				list.add(sku);
			}
			returnResult.setSkus(list);
			res.setResult(returnResult);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.toString());
		}
		return res;
	}
	
}
