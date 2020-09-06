package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.entity.AfsApply;
import www.tonghao.mall.api.jd.reqwrap.CreateAfsApplyReq;
import www.tonghao.mall.api.jd.resultwrap.CreateAfsApplyRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Description: 申请售后
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class CreateAfsApplyApi extends AbstractBusinesApi<CreateAfsApplyRes>{

	private static Log logger = LogFactory.getLog(CreateAfsApplyApi.class);
	
	public CreateAfsApplyApi(AfsApply afsApply){
		super(new CreateAfsApplyReq(afsApply));
	}
	
	@Override
	protected CreateAfsApplyRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		CreateAfsApplyRes res = new CreateAfsApplyRes();
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
