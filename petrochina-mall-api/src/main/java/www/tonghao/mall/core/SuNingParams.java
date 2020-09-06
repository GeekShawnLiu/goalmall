package www.tonghao.mall.core;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Node;

import com.google.common.collect.Maps;

public class SuNingParams {
	
	public static Map<String, String> getParams(String apiUrlPrefix) {
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
