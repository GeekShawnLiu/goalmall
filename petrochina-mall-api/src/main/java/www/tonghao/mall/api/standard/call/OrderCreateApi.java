package www.tonghao.mall.api.standard.call;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.CreOrdSku;
import www.tonghao.mall.api.standard.attwrap.OrderCreateAttr;
import www.tonghao.mall.api.standard.entity.Order;
import www.tonghao.mall.api.standard.reqwrap.OrderCreateReq;
import www.tonghao.mall.api.standard.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 * 下订单接口
 *
 */
public class OrderCreateApi extends AbstractBusinesApi<OrderCreateRes>{

	private static Log log = LogFactory.getLog(OrderCreateApi.class);
	
	public OrderCreateApi(String emallCode,Order order){
		super(new OrderCreateReq(emallCode,order));
	}
	@Override
	protected OrderCreateRes resolver(String result) {
		log.info("OrderCreateApi result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderCreateRes res = new OrderCreateRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			JsonNode mallOrderIdNode = resultNode.path("mall_order_id");
			JsonNode skuNode = resultNode.path("sku");
			JsonNode orderPriceNode = resultNode.path("orderPrice");
			JsonNode freightNode = resultNode.path("freight");
			
			OrderCreateAttr resultAttr = new OrderCreateAttr();
			resultAttr.setMall_order_id(mallOrderIdNode.asText());
			resultAttr.setOrderPrice(orderPriceNode.decimalValue());
			resultAttr.setFreight(freightNode.decimalValue());
			
			CreOrdSku skuAttr = null;
			List<CreOrdSku> skuList = Lists.newArrayList();
			for(JsonNode node:skuNode){
				skuAttr = new CreOrdSku();
				skuAttr.setNum(node.path("num").asInt());
				skuAttr.setPrice(node.path("price").decimalValue());
				skuAttr.setSku(node.path("sku").asText());
				skuList.add(skuAttr);
			}
			resultAttr.setSku(skuList);
			res.setResult(resultAttr);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.asText());
		}
		return res;
	}
	
}
