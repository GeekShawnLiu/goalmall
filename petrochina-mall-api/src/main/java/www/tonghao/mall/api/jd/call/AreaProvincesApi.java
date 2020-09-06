package www.tonghao.mall.api.jd.call;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.ProvincesReq;
import www.tonghao.mall.api.jd.resultwrap.ProvincesRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
/**
 * 获取一级地址
 *
 */
public class AreaProvincesApi extends AbstractBusinesApi<ProvincesRes>{
	
	private static Log logger = LogFactory.getLog(AreaProvincesApi.class);
	
	public AreaProvincesApi(){
		super(new ProvincesReq());
	}
	
	@Override
	protected ProvincesRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProvincesRes res = new ProvincesRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			Map<String,String> resultMap = JsonUtil.toObject(resultNode.toString(), new TypeReference<Map<String,String>>(){});
			res.setResult(resultMap);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
	
}
