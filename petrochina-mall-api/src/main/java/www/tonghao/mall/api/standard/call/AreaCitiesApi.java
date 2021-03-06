package www.tonghao.mall.api.standard.call;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.reqwrap.CitiesReq;
import www.tonghao.mall.api.standard.resultwrap.CityRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * 获取二级地址
 *
 */
public class AreaCitiesApi extends AbstractBusinesApi<CityRes>{
	private static Log logger = LogFactory.getLog(AreaCitiesApi.class);
	
	public AreaCitiesApi(String emallcode,String provinceId){
		super(new CitiesReq(emallcode, provinceId));
	}
	
	
	@Override
	protected CityRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		CityRes res = new CityRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			Map<String,String> resultMap = JsonUtil.toObject(resultNode.toString(), new TypeReference<Map<String,String>>(){});
			res.setResult(resultMap);
		}else{
			JsonNode resultMessageNode = rootNode.path("desc");
			res.setDesc(resultMessageNode.asText());
		}
		return res;
	}
	
}
