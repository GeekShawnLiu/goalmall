package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class ProductPoolReq implements ReqWrap {
	
	
    
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getPageNum", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		String token = AccessTokenApi.getCacheToken(Constant.JD_CODE);
		param.put("token", token );
		return param;
	}

}
