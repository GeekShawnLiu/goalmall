package www.tonghao.mall.api.stb.call;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.CreateOrderAttr;
import www.tonghao.mall.api.stb.attwrap.OrderSkus;
import www.tonghao.mall.api.stb.entity.Order;
import www.tonghao.mall.api.stb.reqwarp.OrderCreateReq;
import www.tonghao.mall.api.stb.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class OrderCreateApi extends AbstractBusinesApi<OrderCreateRes> {
	private static Log logger = LogFactory.getLog(OrderCreateApi.class);
	
	public OrderCreateApi(Order order) {
		super(new OrderCreateReq(order));
	}
	
	@Override
	protected OrderCreateRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderCreateRes createRes=new OrderCreateRes();
		createRes.setSuccess(success);
		if(success) {
			CreateOrderAttr attr=new CreateOrderAttr(); 
			JsonNode resultNode = rootNode.path("result");
			String supplierOrderId=resultNode.path("supplierOrderId").asText();
			BigDecimal  orderNakedPrice=new BigDecimal(resultNode.path("orderNakedPrice").asText());
			BigDecimal orderTaxPriceTotal=new BigDecimal(resultNode.path("orderTaxPriceTotal").asText());
			BigDecimal orderPrice=new BigDecimal(resultNode.path("orderPrice").asText());
			attr.setSupplierOrderId(supplierOrderId);
			attr.setOrderNakedPrice(orderNakedPrice);
			attr.setOrderTaxPriceTotal(orderTaxPriceTotal);
			attr.setOrderPrice(orderPrice);
			JsonNode skus = resultNode.path("sku");
			List<OrderSkus> list=new ArrayList<>();
			for (JsonNode jsonNode : skus) {
				OrderSkus orderSkus=new OrderSkus();
				String skuId = jsonNode.path("skuId").asText();
				int num = jsonNode.path("num").asInt();
				BigDecimal nakedprice =new BigDecimal(jsonNode.path("nakedprice").asText());
				BigDecimal taxprice =new BigDecimal(jsonNode.path("taxprice").asText());
				BigDecimal taxRate =new BigDecimal(jsonNode.path("taxRate").asText());
				BigDecimal bizPrice =new BigDecimal(jsonNode.path("bizPrice").asText());
				BigDecimal nakedPriceTotal =new BigDecimal(jsonNode.path("nakedPriceTotal").asText());
				BigDecimal taxPriceTotal =new BigDecimal(jsonNode.path("taxPriceTotal").asText());
				BigDecimal bizPriceTotal =new BigDecimal(jsonNode.path("bizPriceTotal").asText());
				orderSkus.setSkuId(skuId);
				orderSkus.setNum(num);
				orderSkus.setNakedprice(nakedprice);
				orderSkus.setTaxprice(taxprice);
				orderSkus.setTaxRate(taxRate);
				orderSkus.setBizPrice(bizPrice);
				orderSkus.setNakedPriceTotal(nakedPriceTotal);
				orderSkus.setTaxPriceTotal(taxPriceTotal);
				orderSkus.setBizPriceTotal(bizPriceTotal);
				list.add(orderSkus);
			}
			attr.setSkus(list);
			createRes.setCreateOrderAttr(attr);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			createRes.setDesc(descNode.asText());
		}
		return createRes;
	}
	
}
