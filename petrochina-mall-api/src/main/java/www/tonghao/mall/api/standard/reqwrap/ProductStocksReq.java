package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class ProductStocksReq implements ReqWrap{
	private String sku;
	private String emallcode;
	private String area;
	private String apiUrlPrefix;
	public ProductStocksReq(String emallcode, String area, String sku){
		apiUrlPrefix = "/api_config/standard/"+emallcode;
		this.emallcode = emallcode;
		this.area = area;
		this.sku = sku;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/product_stocks_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("sku", sku);
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		param.put("area", area);
		return param;
	}

}
