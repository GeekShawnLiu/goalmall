package www.tonghao.mall.api.stb.reqwarp;

import java.util.Map;

import com.google.common.collect.Maps;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.stb.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

public class MessageDelReq implements ReqWrap {

	private String messId;
	public MessageDelReq(String messId) {
		this.messId=messId;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.STB_CODE+"/delMessage", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("id", messId);
		param.put("token", AccessTokenApi.getCacheToken());
		return param;
	}

}
