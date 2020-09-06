package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import www.tonghao.mall.api.sn.reqwrap.MessagePriceReq;
import www.tonghao.mall.api.sn.resultwrap.MessagePriceRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.PricemessageQueryRequest;
import com.suning.api.entity.govbus.PricemessageQueryResponse;
import com.suning.api.entity.govbus.PricemessageQueryResponse.Result;
import com.suning.api.exception.SuningApiException;

public class MessagePriceApi extends AbstractSnApi<MessagePriceRes> {

	public MessagePriceApi(){
		super(new MessagePriceReq());
	}
	@Override
	protected MessagePriceRes resolver(DefaultSuningClient client) {
		String result = result(client);
		System.out.println(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		MessagePriceRes res = new MessagePriceRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode getMessageNode = bodyNode.path("queryPricemessage");
			JsonNode resultInfoNode = getMessageNode.path("result");
			List<Result> results = new ArrayList<Result>();
			Result resultInfo = null;
			for (JsonNode jsonNode : resultInfoNode) {
				resultInfo = new Result();
				JsonNode cmmdtyCodeNode = jsonNode.path("cmmdtyCode");	//商品编码
				JsonNode cityIdNode = jsonNode.path("cityId");			//城市编码
				JsonNode timeNode = jsonNode.path("time");				//变动时间
				resultInfo.setCmmdtyCode(cmmdtyCodeNode.asText());
				resultInfo.setCityId(cityIdNode.asText());
				resultInfo.setTime(timeNode.asText());
				results.add(resultInfo);
			}
			res.setResult(results);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		PricemessageQueryRequest request = new PricemessageQueryRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		PricemessageQueryResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
		}
		return response.getBody();
	}

}
