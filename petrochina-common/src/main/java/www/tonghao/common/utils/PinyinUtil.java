package www.tonghao.common.utils;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉语拼音工具类
 * @author developer001
 *
 */
public class PinyinUtil {
	/**
	 * 获得首个大写字母
	 * @param inputString
	 * @return
	 */
	public static String firstUpperCase(String inputString){
		String v = HanyuToPinyin(inputString);
		String firstV = StringUtils.substring(v, 0, 1).toUpperCase();
		return firstV;
	}
	
	/**
	 * 获得首个小写字母
	 * @param inputString
	 * @return
	 */
	public static String firstLowerCase(String inputString){
		String v = HanyuToPinyin(inputString);
		String firstV = StringUtils.substring(v, 0, 1).toLowerCase();
		return firstV;
	}
	
	/**
	 * 获取汉语拼音
	 * @param inputString
	 * @return
	 */
	public static String HanyuToPinyin(String inputString){
		if(inputString==null){
			return null;
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE ); //设置不加声调 
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);  
  
        char[] input = inputString.trim().toCharArray();  
        StringBuffer output = new StringBuffer("");  
  
        try {  
            for (int i = 0; i < input.length; i++) {  
                if (Character.toString(input[i]).matches("[\u4E00-\u9FA5]+")) {  
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);  
                    if(null!=temp){
                    	output.append(temp[0]);
                    	
                    }
					 
                    output.append(" ");  
                } else  
                    output.append(Character.toString(input[i]));  
            }  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return output.toString();
	}
	
	public static void main(String[] args) {
        	String inputString="飚王";
            System.out.println(firstUpperCase(inputString));
    }
}
