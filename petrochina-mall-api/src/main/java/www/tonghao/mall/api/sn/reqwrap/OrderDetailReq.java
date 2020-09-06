package www.tonghao.mall.api.sn.reqwrap;

import java.util.Map;

import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.SuNingParams;
import www.tonghao.mall.core.XmlConfig;


public class OrderDetailReq implements ReqWrap{

	private String apiUrlPrefix;
	
	public OrderDetailReq(){
		apiUrlPrefix = "/api_config/suning/";
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/common_url","value");
	}

	@Override
	public Map<String, String> getParams() {
		return SuNingParams.getParams(apiUrlPrefix);
	}

}
