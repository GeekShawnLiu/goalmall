package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class MessageReq implements ReqWrap {

	private String emallcode;
	private int type;
	private String apiUrlPrefix;
	public MessageReq(String emallcode,int type){
		this.emallcode = emallcode;
		this.type=type;
		apiUrlPrefix = "/api_config/standard/"+emallcode;
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/get_message_pool", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("type", type+"");
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		return param;
	}

}
