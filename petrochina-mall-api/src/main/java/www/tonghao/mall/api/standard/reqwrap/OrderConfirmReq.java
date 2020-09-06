package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class OrderConfirmReq implements ReqWrap{
	
	private String emallcode;
	private String orderId;
	public OrderConfirmReq(String emallcode,String orderId){
		this.emallcode = emallcode;
		this.orderId = orderId;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		String apiUrlPrefix = "/api_config/standard/"+emallcode;
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/order_confirm_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		param.put("order_id", orderId);
		return param;
	}

}
