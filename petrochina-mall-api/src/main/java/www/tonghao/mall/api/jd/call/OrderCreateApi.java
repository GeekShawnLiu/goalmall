package www.tonghao.mall.api.jd.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.OrderCreateAttr;
import www.tonghao.mall.api.jd.attwrap.OrderDetailSkuRep;
import www.tonghao.mall.api.jd.entity.JdOrder;
import www.tonghao.mall.api.jd.reqwrap.OrderCreateReq;
import www.tonghao.mall.api.jd.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 * 下订单接口
 *
 */
public class OrderCreateApi extends AbstractBusinesApi<OrderCreateRes>{

	private static Log log = LogFactory.getLog(OrderCreateApi.class);
	
	public OrderCreateApi(JdOrder jdorder){
		super(new OrderCreateReq(jdorder));
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
			JsonNode jdOrderId_Node = resultNode.path("jdOrderId");
			JsonNode skuNode = resultNode.path("sku");
			JsonNode freight_Node = resultNode.path("freight");
			JsonNode orderPrice_Node = resultNode.path("orderPrice");
			JsonNode orderNakedPrice_Node = resultNode.path("orderNakedPrice");
			JsonNode orderTaxPrice_Node = resultNode.path("orderTaxPrice");
			
			OrderCreateAttr resultAttr = new OrderCreateAttr();
			resultAttr.setJdOrderId(jdOrderId_Node.asText());
			resultAttr.setOrderPrice(orderPrice_Node.decimalValue());
			resultAttr.setFreight(freight_Node.decimalValue());
			resultAttr.setOrderNakedPrice(orderNakedPrice_Node.decimalValue());
			resultAttr.setOrderTaxPrice(orderTaxPrice_Node.decimalValue());
			
			OrderDetailSkuRep skuAttr = null;
			List<OrderDetailSkuRep> skuList = Lists.newArrayList();
			for(JsonNode node:skuNode){
				skuAttr = new OrderDetailSkuRep();
				skuAttr.setSkuId(node.path("skuId").asText());
				skuAttr.setNum(node.path("num").asInt()); 
				skuAttr.setCategory(node.path("category").asText());
				skuAttr.setName(node.path("name").asText());
				skuAttr.setTax(node.path("tax").asInt());
				skuAttr.setPrice(node.path("price").decimalValue());
				skuAttr.setTaxPrice(node.path("taxPrice").decimalValue());
				skuAttr.setNakedPrice(node.path("nakedPrice").decimalValue());
				skuAttr.setType(node.path("type").asInt());
				skuAttr.setOid(node.path("oid").asInt());
				skuList.add(skuAttr);
			}
			resultAttr.setSkus(skuList);
			res.setResult(resultAttr);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
	
}
