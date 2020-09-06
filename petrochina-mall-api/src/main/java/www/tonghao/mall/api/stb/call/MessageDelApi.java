package www.tonghao.mall.api.stb.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.reqwarp.MessageDelReq;
import www.tonghao.mall.api.stb.resultwrap.MessageDelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class MessageDelApi extends AbstractBusinesApi<MessageDelRes> {
	private static Log logger = LogFactory.getLog(MessageDelApi.class);
	 public MessageDelApi(String messId) {
		 super(new MessageDelReq(messId));
	 }
	@Override
	protected MessageDelRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		MessageDelRes delRes=new MessageDelRes();
		delRes.setSuccess(success);
		if(success) {
			boolean resultNode = rootNode.path("result").asBoolean();
			delRes.setResult(resultNode);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			delRes.setDesc(descNode.asText());
		}
		return delRes;
	}

}
