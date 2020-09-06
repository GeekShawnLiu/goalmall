package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class ProductSkuReq implements ReqWrap{
	
	private String emallcode;
	private String poolId;
	public ProductSkuReq(String emallcode,String poolId){
		this.emallcode = emallcode;
		this.poolId = poolId;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		String apiUrlPrefix = "/api_config/standard/"+emallcode;
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/product_sku_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		param.put("catalog_id", poolId);
		return param;
	}

}
