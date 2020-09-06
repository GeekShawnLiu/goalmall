package www.tonghao.mall.api.sn.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.MessageDeleteReq;
import www.tonghao.mall.api.sn.resultwrap.MessageDeleteRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.MessageDeleteRequest;
import com.suning.api.entity.govbus.MessageDeleteResponse;
import com.suning.api.exception.SuningApiException;

public class MessageDeleteApi extends AbstractSnApi<MessageDeleteRes>{
	private static Log logger = LogFactory.getLog(MessageDeleteApi.class);
	private String id;
	public MessageDeleteApi(String id){
		super(new MessageDeleteReq());
		this.id=id;
	}
	@Override
	protected MessageDeleteRes resolver(DefaultSuningClient client) {
		String result = result(client);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		MessageDeleteRes res = new MessageDeleteRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁删除消息错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode deleteMessageNode = bodyNode.path("deleteMessage");
			JsonNode isDelSuccessNode = deleteMessageNode.path("isDelSuccess");
			res.setIsDelSuccess(isDelSuccessNode.asText());
		}
		return res;
		
	}

	@Override
	protected String result(DefaultSuningClient client) {
		MessageDeleteRequest request = new MessageDeleteRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setId(id);
		MessageDeleteResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("删除消息失败！");
		}
		return response.getBody();
	}

}
