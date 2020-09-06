package www.tonghao.mall.core;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;

/**
 * 电商接口公共类
 *
 * @author developer001
 */
public class ApiCommon {

	/**
	 * 电商code
	 */
	private static Map<String,String> codeMap = Maps.newHashMap();
	
	static{
		List<Node> node = XmlConfig.getNode("/api_config/common/emall_mapping/mall");
		for (Node node2 : node) {
			Element element = (Element)node2;
			String emallcode = element.attributeValue("code");
			codeMap.put(emallcode, emallcode);
		}
	}
	
	/**
	 * 获取电商code
	 * @param key
	 * @return
	 */
	public static String getCode(String key){
		if(key==null) return null;
		return codeMap.get(key);
	}
	
	/**
	 * 检查code是否电商code
	 * @param code
	 * @return
	 */
	public static boolean checkCode(String code){
		if(code==null) return false;
		return StringUtils.isNotEmpty(codeMap.get(code))?true:false;
	}
	
	/**
	 * 得到所有电商code
	 * @return
	 */
	public List<String> getAllCodes(){
		List<String> list = Lists.newArrayList();
		codeMap.forEach((k,v) ->{
			list.add(v);
		});
		return list;
	}
}
