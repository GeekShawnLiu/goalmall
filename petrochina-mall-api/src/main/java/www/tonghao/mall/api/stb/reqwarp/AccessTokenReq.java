package www.tonghao.mall.api.stb.reqwarp;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Node;

import com.google.common.collect.Maps;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

public class AccessTokenReq implements ReqWrap{

	
	private String apiUrlPrefix="/api_config/"+Constant.STB_CODE;
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/access_token_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		List<Node> node = XmlConfig.getNode(apiUrlPrefix+"/config");
		for (Node node2 : node) {
			Element element= (Element) node2;
			String key = element.attributeValue("key");
			String value=element.attributeValue("value");
			param.put(key, value);
		}
		return param;
	}

}
