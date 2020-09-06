package www.tonghao.mall.api.standard.reqwrap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Element;
import org.dom4j.Node;

import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class AccessTokenReq implements ReqWrap{
	
	private String apiUrlPrefix;
	
	public AccessTokenReq(String emallcode){
		apiUrlPrefix = "/api_config/standard/"+emallcode;
	}

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
		param.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		String sign = buildSgin(param);
		param.put("sign", sign);
		return param;
	}

	private String buildSgin(Map<String,String> param){
		StringBuffer sb = new StringBuffer();
		sb.append(param.get("username"));
		sb.append(param.get("password"));
		sb.append(param.get("timestamp"));
		sb.append(param.get("password"));
		String signText = sb.toString();
		return DigestUtils.md5Hex(signText).toLowerCase();
	}
}
