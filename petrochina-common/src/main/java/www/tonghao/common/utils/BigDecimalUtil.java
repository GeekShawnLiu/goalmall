package www.tonghao.common.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal ZERO = new BigDecimal("0");

	/**
	 * BigDecimal 加法
	 * 
	 * @param a
	 * @param b
	 * @return a+b
	 */
	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			throw new NullPointerException();
		}
		return BigDecimalToDouble(a).add(BigDecimalToDouble(b));
	}

	/**
	 * BigDecimal 减法
	 * 
	 * @param a
	 * @param b
	 * @return a-b
	 */
	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			throw new NullPointerException();
		}
		return BigDecimalToDouble(a).subtract(BigDecimalToDouble(b));
	}

	/**
	 * 乘法
	 * 
	 * @param a
	 * @param b
	 * @return a*b
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			throw new NullPointerException();
		}
		return BigDecimalToDouble(a).multiply(BigDecimalToDouble(b));
	}

	/**
	 * 除法
	 * 
	 * @param a
	 * @param b
	 * @return a除以b
	 */
	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			throw new NullPointerException();
		}
		return BigDecimalToDouble(a).divide(BigDecimalToDouble(b));
	}
	
	/**
	 * 除法
	 * 
	 * @param a
	 * @param b
	 * @return a除以b
	 */
	public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
		if (a == null || b == null) {
			throw new NullPointerException();
		}
		return BigDecimalToDouble(a).divide(BigDecimalToDouble(b), scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 对比
	 * 
	 * @param a
	 * @param b
	 * @return a>b:1,a<b:-1, a=b:0
	 */
	public static int compareTo(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			throw new NullPointerException();
		}
		return BigDecimalToDouble(a).compareTo(BigDecimalToDouble(b));
	}

	public static BigDecimal BigDecimalToDouble(BigDecimal b) {
		return new BigDecimal(b.doubleValue() + "");
	}

}
