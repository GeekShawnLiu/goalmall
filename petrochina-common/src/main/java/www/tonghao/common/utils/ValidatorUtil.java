package www.tonghao.common.utils;

import java.util.Date;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ValidatorUtil {
	
	private static Log logger = LogFactory.getLog(ValidatorUtil.class);
	
	/**
	 * 验证用户的输入字符是否在最大长度内
	 * @param str 
	 * 			 需要验证的字符串
	 * @param maxLength
	 * 			 最大长度
	 * @param allowBlank 是否允许为空
	 * @return 是否符合标准
	 */
	public static boolean isValidLength(String str, int maxLength,boolean allowBlank) {
		if (str == null  || str.length() > maxLength||(!allowBlank&& str.length() == 0)) {
			return false;
		}
		return true;
	}
	/**
	 * 验证用户的输入不为空的字符是否在最大长度内
	 * @param str
	 * 		 需要验证的字符串
	 * @param maxLength
	 * 		 最大长度
	 * @return 是否符合标准
	 */
	public static boolean isValidLength(String str, int maxLength) {
		return isValidLength(str, maxLength, false);
	}
	
	/**
	 * 验证用户的输入字符是否在长度范围内
	 * @param str
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isValidLengthRange(String str,int minLength,int maxLength) {
		if (str == null  || str.length()<minLength || str.length() > maxLength) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证字符串数组是否包含字符串
	 * @param array
	 * @param string
	 * @return
	 */
	public static boolean isContainStringFormArray(String[] array,String string){
		for(String str:array){
			if(str.equals(string)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 是否为金额,小数点最多为5位数
	 * 
	 * @param value
	 * 		需要验证的字符串
	 * 
	 * @return 是否符合标准
	 */
	private static Pattern moneyPattern = Pattern
			.compile("^(\\d+)(\\.\\d*)?$");
	public static boolean isMoneyValue(String value){
		if(value==null){
			return false;
		}
		return moneyPattern.matcher(value).matches();
	}
	
	/**
	 * 是否是数字
	 * 
	 * @param value
	 */
	private static Pattern numberPattern = Pattern
			.compile("^([+-]?)\\d*\\.?\\d+$");
	public static boolean isNumber(String value) {
		if(value==null){
			return false;
		}
		return numberPattern.matcher(value).matches();
	}
	
	/**
	 * 是否是Long型数字
	 * 
	 * @param value
	 *            需要验证的字符串
	 * @return 是否符合标准
	 */
	public static boolean isLong(String value) {
		if(value==null)return false;
		Long longValue = null;
		try{
			longValue = new Long(value);
		}catch(NumberFormatException e){
			logger.error(value+" convertor Long NumberFormatException");
		}
		return longValue!=null ;
	}
	
	/**
	 * 是否是Long型数字
	 * 
	 * @param value
	 *            需要验证的字符串
	 * @return 是否符合标准
	 */
	public static boolean isNotLong(String value) {
		return !isLong(value) ;
	}
	/**
	 * 是否是正确的ip地址
	 * 
	 * @param value
	 *            需要验证的字符串
	 * @return 是否符合标准
	 */
	private static Pattern ipPattern = Pattern
			.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
	public static boolean isValidIPv4Address(String str,boolean allowBlank) {
		if(str==null)return false;
		if(allowBlank&& str.length() == 0){
			return true;
		}
		return ipPattern.matcher(str).matches();
	}
	
	public static boolean isValidIPv4Address(String str) {
		return isValidIPv4Address(str, false);
	}
	
	/**
	 * 验证是否是正确的Email地址
	 * 
	 * @param value
	 * @return
	 */
	private static Pattern emailPattern = Pattern
	.compile(".+@.+\\.[a-z]+");
	public static boolean isEmail(String value) {
		if(value==null)return false;
		return emailPattern.matcher(value).matches();
	}
	
	/**
	 * 验证是否是正确的手机号码  匹配13，14，15，16, 17，18开头的手机号码
	 * @param value
	 * @return
	 */
	private static Pattern mobliePattern = Pattern.compile("^0?1[3|4|5|7|6|8][0-9]\\d{8}$");
	public static boolean isMoblie(String value){
		if(value==null)return false;
		return mobliePattern.matcher(value).matches();
	}
	
	/**
	 * 验证是否是正确的电话号码
	 * @param value
	 * @return
	 */
	private static Pattern phone1Pattern = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");// 验证带区号的
	private static Pattern phone2Pattern = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");// 验证没有区号的
	public static boolean isPhone(String value){
		if(value==null)return false;
		if(value.length() > 9){
			return phone1Pattern.matcher(value).matches();
		}else{
			return phone2Pattern.matcher(value).matches();
		}
	}
	
	/**
	 * 验证是否为正确的身份证号码
	 * @param value
	 * @return
	 */
	private final static int[] r = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2}; 
	private final static String[] k = {"1","0","x","9","8","7","6","5","4","3","2"};
	public static boolean isIdCard(String value){
		if(value==null)return false;
		if(value.length()!=18){
			return false;
		}
		
		if(!value.substring(0, 17).matches("^\\d{17}$"))
		{
			return false;
		}
		
		//1、将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7－9－10－5－8－4－2－1－6－3－7－9－10－5－8－4－2。
		//2、将这17位数字和系数相乘的结果相加。
		int s = 0;
		for (int i = 0; i < r.length; i++) {
			char vc = value.charAt(i);
			String vs = new String(new char[]{vc});
			int v = Integer.parseInt(vs);
			s = s + v*r[i];
		}
		
		//3、用加出来和除以11，看余数是多少？
		int sv = s%11;
		
		//4、余数只可能有0－1－2－3－4－5－6－7－8－9－10这11个数字。其分别对应的最后一位身份证的号码为1－0－X －9－8－7－6－5－4－3－2。
		String l = k[sv];
		boolean result = l.equalsIgnoreCase(value.substring(17));
		return result;
	}
	
	/**
	 * 判断是否已过期
	 * @param inputDate
	 * @param timeOut 过期时间 单位：秒
	 * @return true:超时 ； false：未超时
	 */
	public static boolean hasExpired(Date inputDate,int timeOut) {
		return new Date().after(DateUtils.addSeconds(inputDate, timeOut));
	}
	
	private static Pattern chinesePattern = Pattern.compile("^[\u4E00-\u9FA5]+");
	/**
	 * 判断字符串是否全中文
	 * @param s
	 * @return
	 */
	public static boolean isFullChinese(String s){
		if(StringUtils.isEmpty(s)){
			return false;
		}
		return chinesePattern.matcher(s).matches();
	}
	
	/**
	 * 是否包含中文
	 * @param s
	 * @return
	 */
	public static boolean isContainChinese(String s){
		if(StringUtils.isNotEmpty(s)){
			for(int i=0;i<s.length();i++){
				String v = Character.toString(s.charAt(i));
				if(chinesePattern.matcher(v).matches()){
					return true;
				}
			}
		}
		return false;
	}
	
}