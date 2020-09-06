package www.tonghao.mall.api.jd.call;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.CountyReq;
import www.tonghao.mall.api.jd.resultwrap.CountyRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * 地址三级接口
 *
 */
public class AreaCountyApi extends AbstractBusinesApi<CountyRes>{

	private static Log logger = LogFactory.getLog(AreaCountyApi.class);
	
	public AreaCountyApi(String cityId){
		super(new CountyReq(cityId));
	}
	
	@Override
	protected CountyRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		CountyRes res = new CountyRes();
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
