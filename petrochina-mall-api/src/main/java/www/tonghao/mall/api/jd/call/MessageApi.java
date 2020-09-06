package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.MessageAddOrder;
import www.tonghao.mall.api.jd.attwrap.MessageAfterService;
import www.tonghao.mall.api.jd.attwrap.MessageAttr;
import www.tonghao.mall.api.jd.attwrap.MessageNewOrder;
import www.tonghao.mall.api.jd.attwrap.MessageOrder;
import www.tonghao.mall.api.jd.attwrap.MessageOrderFinished;
import www.tonghao.mall.api.jd.attwrap.MessagePOrder;
import www.tonghao.mall.api.jd.attwrap.MessagePrice;
import www.tonghao.mall.api.jd.attwrap.MessageProduct;
import www.tonghao.mall.api.jd.attwrap.MessageSku;
import www.tonghao.mall.api.jd.attwrap.MessageState;
import www.tonghao.mall.api.jd.reqwrap.MessageReq;
import www.tonghao.mall.api.jd.resultwrap.MessageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
/**
 * 1 代表订单拆分变更 
 *   2 代表商品价格变更
 * 4 商品上下架变更消息 
 * 5 代表该订单已妥投 
 * 6 代表添加、删除商品池内商品 
 *   10 代表订单取消（不区分取消原因）
 * 12 代表配送单生成 
 * 13 换新订单生成 
 *   16 商品介绍及规格参数变更消息 
 * 18 订单等待确认收货 
 * 23 订单配送退货 
 * 25 新订单消息 
 *   26 预定订单
 * 28 售后服务单状态变更 
 *
 */
public class MessageApi extends AbstractBusinesApi<MessageRes> {

	private int type;
	private static Log logger = LogFactory.getLog(MessageApi.class);
	public MessageApi(int type){
		super(new MessageReq(type));
		this.type=type;
	}
	@Override
	protected MessageRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		MessageRes res=new MessageRes();
		res.setSuccess(success);
		if(success){
			JsonNode result_CodeNode = rootNode.path("result");
			List<MessageAttr> attrs=new ArrayList<MessageAttr>();
			MessageAttr attr=null;
			if(type==1){//1 代表订单拆分变更 
				messagePOrder(result_CodeNode, attrs);
			}else if(type==2){//2 代表商品价格变更
				messagePrice(result_CodeNode, attrs);
			}else if(type==4){//4 商品上下架变更消息
				messageState(result_CodeNode, attrs);
			}else if(type==5){//5 代表该订单已妥投 
				messageOrderFinished(result_CodeNode, attrs);
			}else if(type==6){//6 代表添加、删除商品池内商品 
				messageSku(result_CodeNode, attrs);
			}else if(type==10||type==12||type==18||type==23||type==26){//10 代表订单取消（不区分取消原因）
				//12 代表配送单生成  18 订单等待确认收货 23 订单配送退货 26 预定订单
				messageOrder(result_CodeNode, attrs);
			}else if(type==13){//13 换新订单生成 
				messageAddOrder(result_CodeNode, attrs);
			}else if(type==16){//16 商品介绍及规格参数变更消息 
				messageProduct(result_CodeNode, attrs);
			}else if(type==25){//25 新订单消息 
				messageNewOrder(result_CodeNode, attrs);
			}else if(type==28){//28 售后服务单状态变更 
				messageAfterService(result_CodeNode, attrs);
			}
			res.setResult(attrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		
		
		return res;
	}
	private void messageAfterService(JsonNode result_CodeNode,
			List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageAfterService afterService=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			afterService=new MessageAfterService();
			afterService.setAfsServiceId(result_path.path("afsServiceId").asText());
			afterService.setOrderId(result_path.path("orderId").asText());
			afterService.setPin(result_path.path("pin").asText());
			afterService.setSkuId(result_path.path("skuId").asText());
			afterService.setState(result_path.path("state").asInt());
			attr.setMessage(afterService);
			attrs.add(attr);
		}
	}
	private void messageNewOrder(JsonNode result_CodeNode,
			List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageNewOrder messageNewOrder=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			messageNewOrder=new MessageNewOrder();
			messageNewOrder.setOrderId(result_path.path("orderId").asText());
			messageNewOrder.setPin(result_path.path("pin").asText());
			attr.setMessage(messageNewOrder);
			attrs.add(attr);
		}
	}
	private void messageProduct(JsonNode result_CodeNode,
			List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageProduct messageProduct=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			messageProduct=new MessageProduct();
			messageProduct.setSkuId(result_path.path("skuId").asText());
			attr.setMessage(messageProduct);
			attrs.add(attr);
		}
	}
	private void messageAddOrder(JsonNode result_CodeNode,
			List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageAddOrder addOrder=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			addOrder=new MessageAddOrder();
			addOrder.setAfsServiceId(result_path.path("afsServiceId").asText());
			addOrder.setOrderId(result_path.path("orderId").asText());
			attr.setMessage(addOrder);
			attrs.add(attr);
		}
	}
	private void messageOrder(JsonNode result_CodeNode, List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageOrder messageOrder=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			messageOrder=new MessageOrder();
			messageOrder.setOrderId(result_path.path("orderId").asText());
			attr.setMessage(messageOrder);
			attrs.add(attr);
		}
	}
	private void messageSku(JsonNode result_CodeNode, List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageSku messageSku=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			messageSku=new MessageSku();
			messageSku.setPage_num(result_path.path("page_num").asText());
			messageSku.setSkuId(result_path.path("skuId").asText());
			messageSku.setState(result_path.path("state").asInt());
			attr.setMessage(messageSku);
			attrs.add(attr);
		}
	}
	private void messageOrderFinished(JsonNode result_CodeNode,
			List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageOrderFinished finished=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			finished=new MessageOrderFinished();
			finished.setOrderId(result_path.path("orderId").asText());
			finished.setState(result_path.path("state").asInt());
			attr.setMessage(finished);
			attrs.add(attr);
		}
	}
	private void messageState(JsonNode result_CodeNode, List<MessageAttr> attrs) {
		MessageAttr attr;
		MessageState messageState=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			messageState=new MessageState();
			messageState.setSkuId(result_path.path("skuId").asText());
			messageState.setState(result_path.path("state").asInt());
			attr.setMessage(messageState);
			attrs.add(attr);
		}
	}
	private void messagePrice(JsonNode result_CodeNode, List<MessageAttr> attrs) {
		MessageAttr attr;
		MessagePrice messagePrice=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			messagePrice=new MessagePrice();
			messagePrice.setSkuId(result_path.path("skuId").asText());
			attr.setMessage(messagePrice);
			attrs.add(attr);
		}
	}
	private void messagePOrder(JsonNode result_CodeNode, List<MessageAttr> attrs) {
		MessageAttr attr;
		MessagePOrder message=null;
		for (JsonNode resultNode : result_CodeNode) {
			attr=new MessageAttr();
			attr.setId(resultNode.path("id").asText());
			attr.setTime(resultNode.path("time").asText());
			attr.setType(resultNode.path("type").asInt());
			JsonNode result_path = resultNode.path("result");
			message=new MessagePOrder();
			message.setpOrder(result_path.path("pOrder").asText());
			attr.setMessage(message);
			attrs.add(attr);
		}
	}

}
