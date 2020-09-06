package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class MessageDelReq implements ReqWrap {

	private String id;

	public MessageDelReq(String id){
		this.id=id;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/delMessage", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("id", id);
		return param;
	}
	
}
