package www.tonghao.mall.api.standard.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

/**  
* <p>Title: GetInvoiceListReq</p>  
* <p>Description: 获取电子发票请求封装</p>  
* @author YML  
* @date 2018年12月19日  
*/ 
public class GetInvoiceListReq implements ReqWrap{
	
	private String order_id;//用，分割的字符串,比如:'order_id1,order_id2'
	private String emallcode;
	private String apiUrlPrefix;
	public GetInvoiceListReq(String emallcode,String order_id){
		apiUrlPrefix = "/api_config/standard/"+emallcode;
		this.emallcode = emallcode;
		this.order_id = order_id;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/get_invoice_list", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		param.put("order_ids", order_id);
		return param;
	}

}
