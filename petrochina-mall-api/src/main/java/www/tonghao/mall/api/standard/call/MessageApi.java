package www.tonghao.mall.api.standard.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.MessageAttr;
import www.tonghao.mall.api.standard.attwrap.MessageOrderCancel;
import www.tonghao.mall.api.standard.attwrap.MessageOrderComplete;
import www.tonghao.mall.api.standard.attwrap.MessagePrice;
import www.tonghao.mall.api.standard.attwrap.MessageProductParam;
import www.tonghao.mall.api.standard.attwrap.MessageProductSku;
import www.tonghao.mall.api.standard.attwrap.MessageState;
import www.tonghao.mall.api.standard.reqwrap.MessageReq;
import www.tonghao.mall.api.standard.resultwrap.MessageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *消息接口
 * type=2 商品价格变更
	* type=4 代表商品上下架变更消息
	* type=5 订单已完成
	* type=6 代表添加、删除商品池内的商品
	* type=10 代表订单取消，不区分取消原因
	* type=16 商品介绍及规格参数变更消息
 */
public class MessageApi extends AbstractBusinesApi<MessageRes> {

	private static Log logger = LogFactory.getLog(CatalogPoolApi.class);
	public MessageApi(String emallCode,int type){
		super(new MessageReq(emallCode, type));
	}
	
	@Override
	protected MessageRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		MessageRes messageRes=new MessageRes();
		messageRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			MessageAttr attr=null;
			MessagePrice messagePrice=null;
			MessageState messageState=null;
			MessageOrderComplete complete=null;
			MessageProductSku messageProductSku=null;
			MessageOrderCancel cancel=null;
			MessageProductParam messageProductParam=null;
			List<MessageAttr> attrs=new ArrayList<MessageAttr>();
			for (JsonNode jsonNode : resultNode) {
				attr=new MessageAttr();
				JsonNode id_path = jsonNode.path("id");
				JsonNode type_path = jsonNode.path("type");
				JsonNode time_path = jsonNode.path("time");
				attr.setId(id_path.asText());
				attr.setType(type_path.asInt());
				attr.setTime(time_path.asText());
				JsonNode result_path = jsonNode.path("result");
				if(type_path.asInt()==2){//* type=2 商品价格变更
					messagePrice(attr, result_path);
				}else if(type_path.asInt()==4){
					messageState(attr, result_path);
				}else if(type_path.asInt()==5){//type=5 订单已完成
					complete(attr, result_path);
				}else if(type_path.asInt()==6){// type=6 代表添加、删除商品池内的商品
					messageProductSku(attr, result_path);
				}else if(type_path.asInt()==10){// type=10 代表订单取消
					cancel(attr, result_path);
				}else if(type_path.asInt()==16){//type=16 商品介绍及规格参数变更消息
					messageProductParam(attr, result_path);
				}
				attrs.add(attr);
			}
			messageRes.setAttr(attrs);
		}else{
			JsonNode desc = rootNode.path("desc");
			messageRes.setDesc(desc.asText());
		}
		return messageRes;
	}

	private void messageProductParam(MessageAttr attr, JsonNode result_path) {
		MessageProductParam messageProductParam;
		messageProductParam=new MessageProductParam();
		JsonNode skuId_path=result_path.path("skuId");
		JsonNode state_path=result_path.path("state");
		messageProductParam.setSkuId(skuId_path.asText());
		messageProductParam.setState(state_path.asInt());
		attr.setMessage(messageProductParam);
	}

	private void cancel(MessageAttr attr, JsonNode result_path) {
		MessageOrderCancel cancel;
		cancel=new MessageOrderCancel();
		JsonNode orderId_path=result_path.path("orderId");
		JsonNode state_path=result_path.path("state");
		JsonNode remark_path=result_path.path("remark");
		cancel.setOrderId(orderId_path.asText());
		cancel.setState(state_path.asInt());
		cancel.setRemark(remark_path.asText());
		attr.setMessage(cancel);
	}

	private void messageProductSku(MessageAttr attr, JsonNode result_path) {
		MessageProductSku messageProductSku;
		messageProductSku=new MessageProductSku();
		JsonNode skuId_path=result_path.path("skuId");
		JsonNode state_path=result_path.path("state");
		messageProductSku.setSkuId(skuId_path.asText());
		messageProductSku.setState(state_path.asInt());
		attr.setMessage(messageProductSku);
	}

	private void complete(MessageAttr attr, JsonNode result_path) {
		MessageOrderComplete complete;
		complete=new MessageOrderComplete();
		JsonNode orderId_path=result_path.path("orderId");
		JsonNode state_path=result_path.path("state");
		complete.setOrderId(orderId_path.asText());
		complete.setState(state_path.asInt());
		attr.setMessage(complete);
	}

	private void messageState(MessageAttr attr, JsonNode result_path) {
		MessageState messageState;
		messageState=new MessageState();
		JsonNode skuId_path=result_path.path("skuId");
		JsonNode state_path=result_path.path("state");
		messageState.setSkuId(skuId_path.asText());
		messageState.setState(state_path.asInt());
		attr.setMessage(messageState);
	}

	private void messagePrice(MessageAttr attr, JsonNode result_path) {
		MessagePrice messagePrice;
		messagePrice=new MessagePrice();
		JsonNode skuId_path=result_path.path("skuId");
		JsonNode state_path=result_path.path("state");
		JsonNode price_path=result_path.path("price");
		JsonNode market_price_path=result_path.path("market_price");
		messagePrice.setSkuId(skuId_path.asText());
		messagePrice.setState(state_path.asInt());
		messagePrice.setPrice(price_path.decimalValue());
		messagePrice.setMarket_price(market_price_path.decimalValue());
		attr.setMessage(messagePrice);
	}

}
