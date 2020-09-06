package www.tonghao.mall.api.stb.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.MessageAttr;
import www.tonghao.mall.api.stb.attwrap.MessageOrder;
import www.tonghao.mall.api.stb.attwrap.MessageSku;
import www.tonghao.mall.api.stb.reqwarp.MessageReq;
import www.tonghao.mall.api.stb.resultwrap.MessageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class GetMessageApi extends AbstractBusinesApi<MessageRes> {

	private static Log logger = LogFactory.getLog(GetMessageApi.class);
	
	public GetMessageApi(int type) {
		super(new MessageReq(type));
	}
	
	@Override
	protected MessageRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		MessageRes messageRes=new MessageRes();
		messageRes.setSuccess(success);
		if(success) {
			//1代表订单拆分变更
			//2代表商品协议价格、史泰博价格变更
			//3代表商品库存变更
			//4代表商品上下架变更
			//5代表该订单已妥投
			//6代表添加、删除、更新商品池内商品
			//7 代表发票推送接口 ，success为true时，有invoiceId发票号，如果为false时，代表开发票失败，resultMessage有具体的失败原因
			//8 代表挂起订单状态变更
			//13 取消订单通知消息
			JsonNode resultNode = rootNode.path("result");
			List<MessageAttr> attrs=new ArrayList<>();
			for (JsonNode jsonNode : resultNode) {
				MessageAttr attr=new MessageAttr();
				String id = jsonNode.path("id").asText();
				String time = jsonNode.path("time").asText();
				String type = jsonNode.path("type").asText();
				attr.setId(id);
				attr.setTime(time);
				attr.setType(Integer.parseInt(type));
				JsonNode path = jsonNode.path("result");
				//type==6 代表添加、删除、更新商品池内商品 "state":"1添加，2删除3 更新"
				//type==4代表商品上下架变更 "state":0是下架，1是上架
				if(Integer.parseInt(type)==6||Integer.parseInt(type)==4) {
					MessageSku messageSku=new MessageSku();
					String state = path.path("state").asText();
					String skuId = path.path("skuId").asText();
					messageSku.setSkuId(skuId);
					messageSku.setState(Integer.parseInt(state));
					attr.setMessage(messageSku);
					attrs.add(attr);
				}
				//2代表商品协议价格、史泰博价格变更
				if(Integer.parseInt(type)==2) {
					MessageSku messageSku=new MessageSku();
					String skuId = path.path("skuId").asText();
					messageSku.setSkuId(skuId);
					attr.setMessage(messageSku);
					attrs.add(attr);
				}
				//1 拆单消息
				if(Integer.parseInt(type)==1) {
					MessageOrder messageOrder=new MessageOrder(); 
					String pOrderId = path.path("pOrderId").asText();
					messageOrder.setPorderId(pOrderId);
					attr.setMessage(messageOrder);
					attrs.add(attr);
				}
				//5 妥投
				if(Integer.parseInt(type)==5) {
					MessageOrder messageOrder=new MessageOrder(); 
					String orderId = path.path("orderId").asText();
					int state = path.path("state").asInt();
					messageOrder.setOrderId(orderId);
					messageOrder.setState(state);
					attr.setMessage(messageOrder);
					attrs.add(attr);
				}
				//5 取消
				if(Integer.parseInt(type)==13) {
					MessageOrder messageOrder=new MessageOrder(); 
					String orderId = path.path("orderId").asText();
					messageOrder.setOrderId(orderId);
					attr.setMessage(messageOrder);
					attrs.add(attr);
				}
			}
			messageRes.setAttr(attrs);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			messageRes.setDesc(descNode.asText());
		}
		return messageRes;
	}

}
