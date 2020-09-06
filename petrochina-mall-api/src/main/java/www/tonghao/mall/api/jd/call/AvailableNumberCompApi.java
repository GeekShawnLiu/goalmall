package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.AvailableNumberCompReq;
import www.tonghao.mall.api.jd.resultwrap.AvailableNumberCompRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
/**
 * 
 *校验某订单中某商品是否可以提交售后服务 
 */
public class AvailableNumberCompApi extends AbstractBusinesApi<AvailableNumberCompRes> {
	private static Log logger = LogFactory.getLog(AvailableNumberCompApi.class);
	public AvailableNumberCompApi(String orderId,String sku){
		super(new AvailableNumberCompReq(orderId, sku));
	}
	
	@Override
	protected AvailableNumberCompRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		AvailableNumberCompRes res=new AvailableNumberCompRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			res.setResult(resultNode.asInt());
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}

}
