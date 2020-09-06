package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class CustomerExpectCompReq implements ReqWrap {
	private String orderId;
	private String sku;
	
	public CustomerExpectCompReq(String orderId,String sku){
		this.orderId=orderId;
		this.sku=sku;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getCustomerExpectComp", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("param", getMap());
		return param;
	}
	public String getMap(){
		Map<String,String> param = Maps.newHashMap();
		param.put("jdOrderId", orderId);
		param.put("skuId", sku);
		return JsonUtil.toJson(param);
	}
}
