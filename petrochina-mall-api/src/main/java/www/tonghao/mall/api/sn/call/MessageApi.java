package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.attwrap.MessageAttr;
import www.tonghao.mall.api.sn.attwrap.MessageCatalogs;
import www.tonghao.mall.api.sn.attwrap.MessageOrder;
import www.tonghao.mall.api.sn.attwrap.MessageState;
import www.tonghao.mall.api.sn.attwrap.MessageTrack;
import www.tonghao.mall.api.sn.reqwrap.MessageReq;
import www.tonghao.mall.api.sn.resultwrap.MessageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.MessageGetRequest;
import com.suning.api.entity.govbus.MessageGetResponse;
import com.suning.api.entity.govbus.MessageGetResponse.ResultInfo;
import com.suning.api.exception.SuningApiException;

/**
 * 获取消息
 */
public class MessageApi extends AbstractSnApi<MessageRes>{

	private static Log logger = LogFactory.getLog(MessageApi.class);
	//消息类型：10-商品池 上架、下架、添加、删除、修改 
	//11-订单	实时创建、预占成功、确认预占、取消预占、异常订单取消　
	//12-物流 商品出库、商品妥投、商品拒收、商品退货 
	//13-目录 添加、修改、删除
	private int type;
	
	public MessageApi(int type){
		super(new MessageReq());
		this.type = type;
	}
	
	@Override
	protected MessageRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("MessageApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		MessageRes res = new MessageRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取消息错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode getMessageNode = bodyNode.path("getMessage");
			JsonNode resultInfoNode = getMessageNode.path("resultInfo");
			List<MessageAttr> mess_list=new ArrayList<MessageAttr>();
			MessageState mess=null;
			MessageOrder order=null;
			MessageTrack track=null;
			MessageCatalogs catalogs=null;
			for (JsonNode jsonNode : resultInfoNode) {
				if(type==10){
					messageState(mess_list, jsonNode);
				}else if(type==11){
					messageOrder(mess_list, jsonNode);
				}else if(type==12){
					messageTrack(mess_list, jsonNode);
				}else if(type==13){
					messageCatalogs(mess_list, jsonNode);
				}
			}
			res.setResult(mess_list);
		}
		return res;
	}

	private void messageCatalogs(List<MessageAttr> mess_list, JsonNode jsonNode) {
		MessageCatalogs catalogs;
		catalogs=new MessageCatalogs();
		MessageAttr attr=new MessageAttr();
		JsonNode oldcategoryIdNode = jsonNode.path("oldcategoryId");
		JsonNode oldcategoryNameNode = jsonNode.path("oldcategoryName");
		JsonNode categoryIdNode = jsonNode.path("categoryId");
		JsonNode categoryNameNode = jsonNode.path("categoryName");
		JsonNode timeNode = jsonNode.path("time");
		JsonNode idNode = jsonNode.path("id");
		JsonNode statusNode = jsonNode.path("status");
		JsonNode typeNode = jsonNode.path("type");
		catalogs.setOldcategoryId(oldcategoryIdNode.asText());
		catalogs.setOldcategoryName(oldcategoryNameNode.asText());
		catalogs.setId(idNode.asText());
		catalogs.setCategoryId(categoryIdNode.asText());
		catalogs.setCategoryName(categoryNameNode.asText());
		catalogs.setTime(timeNode.asText());
		catalogs.setStatus(statusNode.asInt());
		catalogs.setType(typeNode.asInt());
		attr.setMessage(catalogs);
		mess_list.add(attr);
	}

	private void messageTrack(List<MessageAttr> mess_list, JsonNode jsonNode) {
		MessageTrack track;
		MessageAttr attr=new MessageAttr();
		track=new MessageTrack();
		JsonNode orderNoNode = jsonNode.path("orderNo");
		JsonNode idNode = jsonNode.path("id");
		JsonNode statusNode = jsonNode.path("status");
		JsonNode orderItemNoNode = jsonNode.path("orderItemNo");
		JsonNode cmmdtyCodeNode = jsonNode.path("cmmdtyCode");
		JsonNode typeNode = jsonNode.path("type");
		track.setId(idNode.asText());
		track.setOrderItemNo(orderItemNoNode.asText());
		track.setOrderNo(orderNoNode.asText());
		track.setSku(cmmdtyCodeNode.asText());
		track.setStatus(statusNode.asInt());
		track.setType(typeNode.asInt());
		attr.setMessage(track);
		mess_list.add(attr);
	}

	private void messageOrder(List<MessageAttr> mess_list, JsonNode jsonNode) {
		MessageOrder order;
		MessageAttr attr=new MessageAttr();
		order=new MessageOrder();
		JsonNode idNode = jsonNode.path("id");
		JsonNode statusNode = jsonNode.path("status");
		JsonNode orderNoNode = jsonNode.path("orderNo");
		JsonNode typeNode = jsonNode.path("type");
		order.setId(idNode.asText());
		order.setOrderNo(orderNoNode.asText());
		order.setStatus(statusNode.asInt());
		order.setType(typeNode.asInt());
		attr.setMessage(order);
		mess_list.add(attr);
	}

	private void messageState(List<MessageAttr> mess_list, JsonNode jsonNode) {
		MessageState mess;
		MessageAttr attr=new MessageAttr();
		mess=new MessageState();
		JsonNode cmmdtyCodeNode = jsonNode.path("cmmdtyCode");
		JsonNode idNode = jsonNode.path("id");
		JsonNode statusNode = jsonNode.path("status");
		JsonNode categoryIdNode = jsonNode.path("categoryId");
		JsonNode timeNode = jsonNode.path("time");
		JsonNode typeNode = jsonNode.path("type");
		mess.setCategoryId(categoryIdNode.asText());
		mess.setSku(cmmdtyCodeNode.asText());
		mess.setStatus(statusNode.asInt());
		mess.setTime(timeNode.asText());
		mess.setType(typeNode.asInt());
		mess.setId(idNode.asText());
		attr.setMessage(mess);
		mess_list.add(attr);
	}


	@Override
	protected String result(DefaultSuningClient client) {
		MessageGetRequest request = new MessageGetRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setType(type+"");
		MessageGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取消息失败！");
		}
		return response.getBody();
	}

}
