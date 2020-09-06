package www.tonghao.mall.api.standard.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.OrdSelectSkus;
import www.tonghao.mall.api.standard.attwrap.OrderAfterServiceAttr;
import www.tonghao.mall.api.standard.attwrap.ReturnService;
import www.tonghao.mall.api.standard.entity.Services;
import www.tonghao.mall.api.standard.reqwrap.OrderAfterServiceReq;
import www.tonghao.mall.api.standard.resultwrap.OrderAfterServiceRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 退换货查询接口
 *	
 */
public class OrderAfterServiceApi extends AbstractBusinesApi<OrderAfterServiceRes>{
	private static Log logger = LogFactory.getLog(OrderAfterServiceApi.class);
	public OrderAfterServiceApi(String emallCode,Services services){
		super(new OrderAfterServiceReq(emallCode,services));
	}
	@Override
	protected OrderAfterServiceRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderAfterServiceRes res = new OrderAfterServiceRes();
		res.setSuccess(success);
		if (success) {
			JsonNode resultNode = rootNode.path("result");
			JsonNode orderIdNode = resultNode.path("order_id");
			JsonNode pageTotalNode = resultNode.path("page_total");
			JsonNode pagePerNode = resultNode.path("page_per");
			JsonNode hasNextNode = resultNode.path("has_next");
			JsonNode serviceNode = resultNode.path("service");
			
			OrderAfterServiceAttr afterServiceresult = new OrderAfterServiceAttr();
			afterServiceresult.setOrder_id(orderIdNode.asText());
			afterServiceresult.setPage_total(pageTotalNode.asText());
			afterServiceresult.setPage_per(pagePerNode.asText());
			afterServiceresult.setHasNext(hasNextNode.asBoolean());
			ReturnService returnService = null;
			for(JsonNode node:serviceNode){
				returnService = new ReturnService();
				returnService.setOrder_service_id(node.path("order_service_id").asText());
				returnService.setOrder_service_type(node.path("order_service_type").asText());
				returnService.setRefund_price(node.path("refund_price").decimalValue());
				returnService.setOrder_service_step(node.path("order_service_step").asText());
				returnService.setApprove_notes(node.path("approve_notes").asText());
				
				JsonNode skusNode = node.path("skus");
				OrdSelectSkus ordSelectSkus = null;
				for(JsonNode skuNode:skusNode){
					ordSelectSkus = new OrdSelectSkus();
					ordSelectSkus.setSku(skuNode.path("sku").asText());
					ordSelectSkus.setNum(skuNode.path("num").asInt());
				}
				returnService.setSkus(ordSelectSkus);
			}
			afterServiceresult.setService(returnService);
			res.setResult(afterServiceresult);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.toString());
		}
		return res;
	}
	
}
