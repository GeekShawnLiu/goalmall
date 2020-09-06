package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class ProductPriceReq implements ReqWrap{
	private String sku;//多个以逗号隔开
	private String emallcode;
	private String apiUrlPrefix;
	public ProductPriceReq(String emallcode, String sku){
		apiUrlPrefix = "/api_config/standard/"+emallcode;
		this.emallcode = emallcode;
		this.sku = sku;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/product_price_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("sku", sku);
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		return param;
	}

}
