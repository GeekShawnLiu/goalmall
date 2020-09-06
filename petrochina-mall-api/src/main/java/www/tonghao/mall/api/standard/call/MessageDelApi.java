package www.tonghao.mall.api.standard.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.reqwrap.MessageDelReq;
import www.tonghao.mall.api.standard.resultwrap.MessageDelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
/**
 * 消息删除接口
 *
 */
public class MessageDelApi extends AbstractBusinesApi<MessageDelRes> {
	private static Log logger = LogFactory.getLog(MessageDelApi.class);
	
	public MessageDelApi(String messageId,String emallCode){
		super(new MessageDelReq(messageId, emallCode));
	}
	@Override
	protected MessageDelRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		MessageDelRes delRes=new MessageDelRes();
		delRes.setSuccess(success);
		delRes.setDesc(rootNode.path("desc").asText());
		return delRes;
	}

}
