package www.tonghao.mall.core;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
/**
 * 工具类 - 读取xml配置
 *
 */
public class XmlConfig {

	static final String CONFIG_FILE_LOCATION = "/www/tonghao/mall/xml/mall_config.xml";
	static Document document = null;
	static{
		try {
			InputStream in = XmlConfig.class.getResourceAsStream(CONFIG_FILE_LOCATION);
			document = new SAXReader().read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public static String getSingleNoteString(String xpathExpression){
		return document.selectSingleNode(xpathExpression).getText();
	}
	public static String getAttributeValue(String xpathExpression,String attrName){
		String default_result=null;
		Element elements = getElements(xpathExpression);
		if(elements!=null){
			Attribute attribute = elements.attribute(attrName);
			if(attribute!=null){
				default_result=attribute.getValue();
			}
		}
		return default_result;
	}
	public static Element getElements(String xpathExpression){
		return (Element) document.selectSingleNode(xpathExpression);
	}
	
	public static List<Node> getNode(String xpathExpression){
		return  document.selectNodes(xpathExpression);
	}
	public static void main(String[] args) {
		List<Node> node = XmlConfig.getNode("api_config/stb/config");
		for (Node node2 : node) {
			Element element= (Element) node2;
			String key = element.attributeValue("key");
			String value=element.attributeValue("value");
			System.out.println(key);
		}
	}
}
