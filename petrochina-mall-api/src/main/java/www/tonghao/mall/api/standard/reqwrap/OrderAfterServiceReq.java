package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.api.standard.entity.Services;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class OrderAfterServiceReq implements ReqWrap{
	
	private String apiUrlPrefix;
	private String emallCode;
	private Services services;
	public OrderAfterServiceReq(String emallCode,Services services){
		this.emallCode = emallCode;
		this.services=services;
		apiUrlPrefix = "/api_config/standard/"+emallCode;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/order_after_service_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		//请求参数
		param.put("token", AccessTokenApi.getCacheToken(emallCode));
		param.put("order_service_id", services.getOrderServiceId());	//退换货的服务号id
		param.put("order_id", services.getOrderId());		//贵方订单号（当只传订单号时，则返回所有该订单的服务信息）
		param.put("page", services.getPage()+"");		//查询记录页码，默认第一页
		param.put("per", services.getPer()+"");	//查询记录每次获取数量，默认一次请求1000条记录
		return param;
	}

}
