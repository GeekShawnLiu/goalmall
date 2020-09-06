package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class CatalogPoolReq implements ReqWrap{
	
	private String emallcode;
	public CatalogPoolReq(String emallcode){
		this.emallcode = emallcode;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		String apiUrlPrefix = "/api_config/standard/"+emallcode;
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/sku_pool_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		return param;
	}
}
