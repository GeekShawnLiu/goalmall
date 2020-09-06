package www.tonghao.mall.api.stb.call;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.Order;
import www.tonghao.mall.api.stb.attwrap.OrderSkus;
import www.tonghao.mall.api.stb.attwrap.SelectOrderAttr;
import www.tonghao.mall.api.stb.reqwarp.SelectOrderReq;
import www.tonghao.mall.api.stb.resultwrap.SelectOrderRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class SelectOrderApi extends AbstractBusinesApi<SelectOrderRes> {
	private static Log logger = LogFactory.getLog(SelectOrderApi.class);
	private int isParent;//1 是父订单，0是子订单
	public SelectOrderApi(String supplierOrderId,int isParent) {
		super(new SelectOrderReq(supplierOrderId));
		this.isParent=isParent;
	}
	
	@Override
	protected SelectOrderRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		SelectOrderRes orderRes=new SelectOrderRes();
		orderRes.setSuccess(success);
		if(success) {
			SelectOrderAttr attr=new SelectOrderAttr(); 
			if(isParent==1) {//父订单
				JsonNode resultNode = rootNode.path("result");
				Integer z_type = resultNode.path("type").asInt();
				Integer z_submitState = resultNode.path("submitState").asInt();
				Integer z_orderState = resultNode.path("orderState").asInt();
				attr.setType(z_type);
				attr.setOrderState(z_orderState);
				attr.setSubmitState(z_submitState);
				JsonNode pOrder = resultNode.path("pOrder");//父订单
				Order p_order=new Order(); 
				String supplierOrderId = pOrder.path("supplierOrderId").asText();//父订单编号
				BigDecimal orderNakedPrice = new BigDecimal(pOrder.path("orderNakedPrice").asDouble());//订单裸价合计
				BigDecimal orderTaxPrice = new BigDecimal(pOrder.path("orderTaxPrice").asDouble());//订单税额合计
				BigDecimal orderPrice = new BigDecimal(pOrder.path("orderPrice").asDouble());//订单企业价合计
				p_order.setSupplierOrderId(supplierOrderId);
				p_order.setOrderNakedPrice(orderNakedPrice);
				p_order.setOrderTaxPrice(orderTaxPrice);
				p_order.setOrderPrice(orderPrice);
				
				JsonNode p_sku = pOrder.path("sku");//父订单编号
				List<OrderSkus> p_list_sku=new ArrayList<>();
				for (JsonNode jsonNode : p_sku) {
					OrderSkus orderSkus=new OrderSkus();
					BigDecimal taxprice = new BigDecimal(jsonNode.path("taxprice").asDouble());
					BigDecimal nakedprice = new BigDecimal(jsonNode.path("nakedprice").asDouble());
					BigDecimal bizPrice = new BigDecimal(jsonNode.path("bizPrice").asDouble());
					Integer num = jsonNode.path("num").asInt();
					String skuId = jsonNode.path("skuId").asText();
					orderSkus.setTaxprice(taxprice);
					orderSkus.setNakedprice(nakedprice);
					orderSkus.setBizPrice(bizPrice);
					orderSkus.setNum(num);
					orderSkus.setSkuId(skuId);
					p_list_sku.add(orderSkus);
				}
				p_order.setSku(p_list_sku);
				attr.setpOrder(p_order);
				
				JsonNode cOrder = resultNode.path("cOrder");//子订单
				List<Order> corder_list=new ArrayList<>();
				for (JsonNode corder_ : cOrder) {
					Order c_order=new Order();
					String cOrder_supplierOrderId = corder_.path("supplierOrderId").asText();//订单编号
					String cOrder_pOrder = corder_.path("pOrder").asText();//父订单编号
					BigDecimal cOrder_orderNakedPrice = new BigDecimal(corder_.path("orderNakedPrice").asDouble());//订单裸价合计
					BigDecimal cOrder_orderTaxPrice = new BigDecimal(corder_.path("orderTaxPrice").asDouble());//订单税额合计
					BigDecimal cOrder_orderPrice = new BigDecimal(corder_.path("orderPrice").asDouble());//订单企业价合计
					Integer cOrder_state = corder_.path("state").asInt();
					Integer cOrder_type = corder_.path("type").asInt();
					Integer cOrder_submitState = corder_.path("submitState").asInt();
					Integer cOrder_orderState = corder_.path("orderState").asInt();
					c_order.setSupplierOrderId(cOrder_supplierOrderId);
					c_order.setPorder(cOrder_pOrder);
					c_order.setOrderNakedPrice(cOrder_orderNakedPrice);
					c_order.setOrderTaxPrice(cOrder_orderTaxPrice);
					c_order.setOrderPrice(cOrder_orderPrice);
					c_order.setState(cOrder_state);
					c_order.setType(cOrder_type);
					c_order.setSubmitState(cOrder_submitState);
					c_order.setOrderState(cOrder_orderState);
					
					JsonNode c_sku = corder_.path("sku");//父订单编号
					List<OrderSkus> c_list_sku=new ArrayList<>();
					for (JsonNode jsonNode : c_sku) {
						OrderSkus orderSkus=new OrderSkus();
						BigDecimal taxprice = new BigDecimal(jsonNode.path("taxprice").asDouble());
						BigDecimal nakedprice = new BigDecimal(jsonNode.path("nakedprice").asDouble());
						BigDecimal bizPrice = new BigDecimal(jsonNode.path("bizPrice").asDouble());
						BigDecimal taxRate = new BigDecimal(jsonNode.path("taxRate").asDouble());
						Integer num = jsonNode.path("num").asInt();
						String skuId = jsonNode.path("skuId").asText();
						orderSkus.setTaxprice(taxprice);
						orderSkus.setNakedprice(nakedprice);
						orderSkus.setBizPrice(bizPrice);
						orderSkus.setNum(num);
						orderSkus.setSkuId(skuId);
						orderSkus.setTaxRate(taxRate);
						c_list_sku.add(orderSkus);
					}
					c_order.setSku(c_list_sku);
					corder_list.add(c_order);
				}
				attr.setcOrder(corder_list);
			}else {
				Order c_order=new Order();
				JsonNode resultNode = rootNode.path("result");
				Integer z_type = resultNode.path("type").asInt();
				Integer cOrder_state = resultNode.path("state").asInt();
				Integer z_submitState = resultNode.path("submitState").asInt();
				Integer z_orderState = resultNode.path("orderState").asInt();
				String supplierOrderId = resultNode.path("supplierOrderId").asText();//订单编号
				String cOrder_pOrder = resultNode.path("pOrder").asText();//父订单编号
				BigDecimal cOrder_orderNakedPrice = new BigDecimal(resultNode.path("orderNakedPrice").asDouble());//订单裸价合计
				BigDecimal cOrder_orderTaxPrice = new BigDecimal(resultNode.path("orderTaxPrice").asDouble());//订单税额合计
				BigDecimal cOrder_orderPrice = new BigDecimal(resultNode.path("orderPrice").asDouble());//订单企业价合计
				c_order.setSupplierOrderId(supplierOrderId);
				c_order.setPorder(cOrder_pOrder);
				c_order.setOrderNakedPrice(cOrder_orderNakedPrice);
				c_order.setOrderTaxPrice(cOrder_orderTaxPrice);
				c_order.setOrderPrice(cOrder_orderPrice);
				c_order.setState(cOrder_state);
				c_order.setType(z_type);
				c_order.setSubmitState(z_submitState);
				c_order.setOrderState(z_orderState);
				JsonNode c_sku = resultNode.path("sku");//父订单编号
				List<OrderSkus> c_list_sku=new ArrayList<>();
				for (JsonNode jsonNode : c_sku) {
					OrderSkus orderSkus=new OrderSkus();
					BigDecimal taxprice = new BigDecimal(jsonNode.path("taxprice").asDouble());
					BigDecimal nakedprice = new BigDecimal(jsonNode.path("nakedprice").asDouble());
					BigDecimal bizPrice = new BigDecimal(jsonNode.path("bizPrice").asDouble());
					BigDecimal taxRate = new BigDecimal(jsonNode.path("taxRate").asDouble());
					Integer num = jsonNode.path("num").asInt();
					String skuId = jsonNode.path("skuId").asText();
					orderSkus.setTaxprice(taxprice);
					orderSkus.setNakedprice(nakedprice);
					orderSkus.setBizPrice(bizPrice);
					orderSkus.setNum(num);
					orderSkus.setSkuId(skuId);
					orderSkus.setTaxRate(taxRate);
					c_list_sku.add(orderSkus);
				}
				c_order.setSku(c_list_sku);
				attr.setpOrder(c_order);
			}
			orderRes.setAttr(attr);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			orderRes.setDesc(descNode.asText());
		}
		return orderRes;
	}

}
