package www.tonghao.mall.api.standard.call;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.OrdSelectResultAttr;
import www.tonghao.mall.api.standard.attwrap.OrdSelectSkus;
import www.tonghao.mall.api.standard.reqwrap.OrderSelectReq;
import www.tonghao.mall.api.standard.resultwrap.OrderSelectRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 * 查询贵方订单信息接口
 *	
 */
public class OrderSelectApi extends AbstractBusinesApi<OrderSelectRes>{
	
	private static Log log = LogFactory.getLog(OrderSelectApi.class);

	public OrderSelectApi(String emallcode,String orderId){
		super(new OrderSelectReq(emallcode, orderId));
	}
	
	@Override
	protected OrderSelectRes resolver(String result) {
		log.info("OrderSelectApi result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderSelectRes res = new OrderSelectRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			JsonNode orderIdNode = resultNode.path("order_id");
			JsonNode totalPriceNode = resultNode.path("total_price");
			JsonNode skusNode = resultNode.path("skus");
			JsonNode stateNode = resultNode.path("state");
			JsonNode submitStateNode = resultNode.path("submit_state");
			JsonNode deliverStateNode = resultNode.path("deliver_state");
			JsonNode returnSkusNode = resultNode.path("return_skus");
			
			OrdSelectResultAttr resultAttr = new OrdSelectResultAttr();
			resultAttr.setOrder_id(orderIdNode.asText());
			resultAttr.setState(stateNode.asInt());
			resultAttr.setSubmit_state(submitStateNode.asInt());
			resultAttr.setDeliver_state(deliverStateNode.asInt());
			resultAttr.setTotal_price(totalPriceNode.decimalValue());
			
			OrdSelectSkus sku = null;
			List<OrdSelectSkus> skusList = Lists.newArrayList();
			for(JsonNode node:skusNode){
				sku = new OrdSelectSkus();
				sku.setSku(node.path("sku").asText());
				sku.setPrice(node.path("price").decimalValue());
				sku.setNum(node.path("num").asInt());
				skusList.add(sku);
			}
			List<OrdSelectSkus> return_skus = Lists.newArrayList();
			for(JsonNode node:returnSkusNode){
				sku = new OrdSelectSkus();
				sku.setSku(node.path("sku").toString());
				sku.setPrice(node.path("price").decimalValue());
				sku.setNum(node.path("num").asInt());
				return_skus.add(sku);
			}
			resultAttr.setSkus(skusList);
			resultAttr.setReturn_skus(return_skus );
			res.setResult(resultAttr);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.toString());
		}
		return res;
	}
	
}
