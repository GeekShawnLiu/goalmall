package www.tonghao.common.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * 工具类-中文金额
 * @author developer001
 *
 */
/**
 * 工具类-中文金额
 * @author developer001
 *
 */
public class ChineseMoneyUtil {
	
	private static String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万",
			"拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

	public static String getChineseMoney(BigDecimal money) {
		if(money==null){
			return "";
		}
	    String moneyStr = "";
	    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	    moneyStr = df.format(money);
	    String[] moneys = moneyStr.split("\\.");
	    moneyStr = transChineseMoney0(moneyStr)+transChineseMoney1(moneys[0])+"圆"
	            + transChineseMoney2(moneys[1]);
	    
	    return moneyStr;
	}
	public static String getChineseMoney(String money) {
	    if (money.trim().isEmpty()) {
	        return "";
	    }
	    Double dMoney = 0.00;
	    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	    dMoney += Double.parseDouble(money.trim());
	    money = df.format(dMoney);
	    String[] moneys = money.split("\\.");
	    money = transChineseMoney0(money)+transChineseMoney1(moneys[0])+"圆"
	            + transChineseMoney2(moneys[1]);
	    return money;
	}
	
	/**
	 * 处理正负
	 * @param NumStr
	 * @return
	 */
	public static String transChineseMoney0(String NumStr) {
	    double val = Double.parseDouble(NumStr);
	    String SignStr = "";
	    if (val < 0) {
			SignStr = "负";
		}
 		return SignStr;
	}
	
	/**
	 * 处理整数位的金额
	 * 
	 * @param money
	 * @return
	 */
	public static String transChineseMoney1(String money) {
	    String NumStr = money;
	    if(NumStr.startsWith("-")){
	    	NumStr = StringUtils.substringAfter(NumStr, "-");
	    }
	    // 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
 		String RMBStr = "";
 		boolean lastzero = false;
 		boolean hasvalue = false; // 亿、万进位前有数值标记
 		int len, n;
 		len = NumStr.length();
 		if (len > 15) {
 			return "数值过大!";
 		}
 		for (int i = len - 1; i >= 0; i--) {
 			if (NumStr.charAt(len - i - 1) == ' ') {
 				continue;
 			}
 			n = NumStr.charAt(len - i - 1) - '0';
 			if (n < 0 || n > 9) {
 				return "输入含非数字字符!";
 			}
 			if (n != 0) {
 				if (lastzero) {
 					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
 				}
 				// 除了亿万前的零不带到后面
 				// 如十进位前有零也不发壹音用此行
 				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) { // 十进位处于第一位不发壹音
 					RMBStr += HanDigiStr[n];
 				}
 				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
 				hasvalue = true; // 置万进位前有值标记
 			} else {
 				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
 					RMBStr += HanDiviStr[i]; // “亿”或“万”
 			}
 			if (i % 8 == 0) {
 				hasvalue = false; // 万进位前有值标记逢亿复位
 			}
 			lastzero = (n == 0) && (i % 4 != 0);
 		}
 		if (RMBStr.length() == 0) {
 			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
 		}
 		return RMBStr;
	}
	/**
	 * 处理小数的金额
	 * 
	 * @param money
	 * @return
	 */
	public static String transChineseMoney2(String money) {
	    if (money.isEmpty()) {
	        return "";
	    }
	    double val = Double.parseDouble("0."+money);
	    String TailStr = "";
	    long fraction, integer;
		int jiao, fen;
		if (val < 0) { 
			val = -val;
		}
		if (val > 99999999999999.999 || val < -99999999999999.999) {
			return "数值位数过大!";
		}
		// 四舍五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0) {
				TailStr += "角";
			}
			// 零元后不写零几分
			if (integer == 0 && jiao == 0) {
				TailStr = "";
			}
			if (fen != 0) {
				TailStr += HanDigiStr[fen] + "分";
			}
		}
	    return TailStr;
	}
	public static void main(String[] args) {
		String money = "100000.00";
	    String value = getChineseMoney(money);
	    System.out.println("money = "+value);
	    
	    System.out.println("money = "+getChineseMoney("1210002000.01"));
	    System.out.println("money = "+getChineseMoney("0.00"));
	   
	}
}

