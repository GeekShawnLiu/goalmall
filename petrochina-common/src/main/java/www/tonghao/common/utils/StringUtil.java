package www.tonghao.common.utils;

public class StringUtil {

	/**
	 * 
	 * Description: 判断字符串是否为空
	 * 
	 * @data 2019年6月3日
	 * @param 
	 * @return
	 */
	public static boolean strIsEmpty(String str){
		return str == null || "".equals(str.trim()) || "null".equals(str.trim()) || "undefined".equals(str.trim());
	}

	/**
	 * 
	 * Description: 判断字符串非空
	 * 
	 * @data 2019年6月3日
	 * @param 
	 * @return
	 */
	public static boolean strIsNotEmpty(String str){
		return !strIsEmpty(str);
	}
	
	/**
	 * 
	 * Description: 字符串去前后空格
	 * 
	 * @data 2019年8月30日
	 * @param 
	 * @return
	 */
	public static String strTrim(String str){
		if(strIsEmpty(str)){
			return null;
		}else{
			return str.trim();
		}
	}
}
