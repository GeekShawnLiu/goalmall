package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.MessageDelReq;
import www.tonghao.mall.api.jd.resultwrap.MessageDelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

public class MessageDelApi extends AbstractBusinesApi<MessageDelRes> {

	private static Log logger = LogFactory.getLog(MessageDelApi.class);
	public MessageDelApi(String id){
		super(new MessageDelReq(id));
	}
	
	@Override
	protected MessageDelRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		MessageDelRes res=new MessageDelRes();
		res.setSuccess(success);
		JsonNode resultMessageNode = rootNode.path("resultMessage");
		JsonNode resultCodeNode = rootNode.path("resultCode");
		res.setResultCode(resultCodeNode.asText());
		res.setResultMessage(resultMessageNode.asText());
		JsonNode result_CodeNode = rootNode.path("result");
		res.setResult(result_CodeNode.asBoolean());
		return res;
	}

}
