package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.reqwrap.UpdateSendSkuReq;
import www.tonghao.mall.api.jd.resultwrap.UpdateSendSkuRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Description: 填写发运信息Api
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class UpdateSendSkuApi extends AbstractBusinesApi<UpdateSendSkuRes>{

	private static Log logger = LogFactory.getLog(UpdateSendSkuApi.class);

	public UpdateSendSkuApi(UpdateSendSkuReq updateSendSkuReq){
		super(updateSendSkuReq);
	}
	
	@Override
	protected UpdateSendSkuRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		UpdateSendSkuRes res = new UpdateSendSkuRes();
		res.setSuccess(success);
		if(!success){
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
}
