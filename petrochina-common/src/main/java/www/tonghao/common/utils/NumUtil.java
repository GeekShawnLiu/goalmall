package www.tonghao.common.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class NumUtil {

	public static final String ALLNUM = "0123456789";
	
	/**
	 * 
	 * Description: 获取指定位数的纯数字随机数
	 * 
	 * @data 2019年5月2日
	 * @param 
	 * @return
	 */
	public static String createIdentifyCode(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLNUM.charAt(random.nextInt(ALLNUM.length())));
        }
        return sb.toString();
    }
	
	/**
	 * 
	 * Description: 判断是否为纯数字
	 * 
	 * @data 2019年8月22日
	 * @param 
	 * @return
	 */
	public static boolean isNum(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
}
