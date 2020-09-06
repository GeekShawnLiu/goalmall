package www.tonghao.mall.api.jd.call;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.TownReq;
import www.tonghao.mall.api.jd.resultwrap.TownRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 *四级地区接口
 */
public class AreaTownApi extends AbstractBusinesApi<TownRes>{
	private static Log logger = LogFactory.getLog(AreaTownApi.class);
	public AreaTownApi(String id){
		super(new TownReq(id));
	}
	@Override
	protected TownRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		TownRes res=new TownRes();
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
