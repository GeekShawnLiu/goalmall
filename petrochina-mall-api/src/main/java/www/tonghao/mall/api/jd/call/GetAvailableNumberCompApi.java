package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.reqwrap.GetAvailableNumberCompReq;
import www.tonghao.mall.api.jd.resultwrap.GetAvailableNumberCompRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Description: 查询可售后商品Api
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetAvailableNumberCompApi extends AbstractBusinesApi<GetAvailableNumberCompRes>{

	private static Log logger = LogFactory.getLog(GetAvailableNumberCompApi.class);
	
	public GetAvailableNumberCompApi(String jdOrderId, String skuId){
		super(new GetAvailableNumberCompReq(jdOrderId, skuId));
	}
	
	@Override
	protected GetAvailableNumberCompRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		GetAvailableNumberCompRes res=new GetAvailableNumberCompRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultMessageNode = rootNode.path("result");
			res.setResult(resultMessageNode.asInt());
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
}
