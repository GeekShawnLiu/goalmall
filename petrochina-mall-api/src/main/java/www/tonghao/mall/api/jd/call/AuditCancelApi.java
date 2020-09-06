package www.tonghao.mall.api.jd.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.reqwrap.AuditCancelReq;
import www.tonghao.mall.api.jd.resultwrap.AuditCancelRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

public class AuditCancelApi extends AbstractBusinesApi<AuditCancelRes>{

	private static Log logger = LogFactory.getLog(AuditCancelApi.class);
	
	public AuditCancelApi(List<Integer> serviceIdList, String approveNotes){
		super(new AuditCancelReq(serviceIdList, approveNotes));
	}
	
	@Override
	protected AuditCancelRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		AuditCancelRes res = new AuditCancelRes();
		res.setSuccess(success);
		JsonNode resultNode = rootNode.path("result");
		if(resultNode != null){
			res.setResult(resultNode.asBoolean());
		}
		JsonNode resultMessageNode = rootNode.path("resultMessage");
		if(resultMessageNode != null){
			res.setResultMessage(resultMessageNode.asText());
		}
		JsonNode resultCodeNode = rootNode.path("resultCode");
		if(resultCodeNode != null){
			res.setResultCode(resultCodeNode.asText());
		}
		return res;
	}
}
