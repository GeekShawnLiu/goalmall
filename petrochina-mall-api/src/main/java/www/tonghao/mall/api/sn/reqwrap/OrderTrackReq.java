package www.tonghao.mall.api.sn.reqwrap;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.SuNingParams;
import www.tonghao.mall.core.XmlConfig;

public class OrderTrackReq implements ReqWrap{

	private String apiUrlPrefix;
	
	public OrderTrackReq(){
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
