package www.tonghao.mall.api.standard.call;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.CatalogPoolAttr;
import www.tonghao.mall.api.standard.reqwrap.CatalogPoolReq;
import www.tonghao.mall.api.standard.resultwrap.CatalogPoolRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import java.util.ArrayList;
import java.util.Set;


/**
 * 获取品目池接口
 * @author developer001
 *
 */
public class CatalogPoolApi extends AbstractBusinesApi<CatalogPoolRes>{

	private static Log logger = LogFactory.getLog(CatalogPoolApi.class);
	
	public CatalogPoolApi(String emallcode){
		super(new CatalogPoolReq(emallcode));
	}
	@Override
	protected CatalogPoolRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		CatalogPoolRes res = new CatalogPoolRes();
		res.setSuccess(success);
		if(success){
			String resultData = rootNode.path("result").toString();
			Set<CatalogPoolAttr> resultAttr = JsonUtil.toObject(resultData, new TypeReference<Set<CatalogPoolAttr>>(){});
			res.setResult(new ArrayList<>(resultAttr));
		}else{
			JsonNode desc = rootNode.path("desc");
			res.setDesc(desc.asText());
		}
		return res;
	}
	
}
