package www.tonghao.mall.api.stb.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.reqwarp.ConfirmOrderReq;
import www.tonghao.mall.api.stb.resultwrap.ConfirmOrderRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ConfirmOrderApi extends AbstractBusinesApi<ConfirmOrderRes> {
	private static Log logger = LogFactory.getLog(ConfirmOrderApi.class);
	public ConfirmOrderApi(String supplierOrderId) {
		super(new ConfirmOrderReq(supplierOrderId));
	}
	
	
	@Override
	protected ConfirmOrderRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ConfirmOrderRes confirmOrderRes=new ConfirmOrderRes();
		confirmOrderRes.setSuccess(success);
		if(success) {
			boolean resultNode = rootNode.path("result").asBoolean();
			confirmOrderRes.setResult(resultNode);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			confirmOrderRes.setDesc(descNode.asText());
		}
		return confirmOrderRes;
	}
	
}
