package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class MessageDelReq implements ReqWrap{

	private String messageId;
	private String apiUrlPrefix;
	private String emallcode;
	public MessageDelReq(String messageId,String emallcode){
		apiUrlPrefix = "/api_config/standard/"+emallcode;
		this.messageId=messageId;
		this.emallcode=emallcode;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/del_message_pool", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("messageId", messageId);
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		return param;
	}

}
