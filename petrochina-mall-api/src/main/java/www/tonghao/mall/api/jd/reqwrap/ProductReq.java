package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class ProductReq implements ReqWrap{

	private String sku;
	public ProductReq(String sku){
		this.sku=sku;
	}
	
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getDetail", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		String token = AccessTokenApi.getCacheToken(Constant.JD_CODE);
		param.put("token", token);
		param.put("sku", sku);
		param.put("queryExts", "appintroduce,wxintroduction,isFactoryShip");
		return param;
	}

}
