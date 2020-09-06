package www.tonghao.common.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;

import www.tonghao.common.pay.utils.HttpProtocolHandler;
import www.tonghao.common.pay.utils.HttpRequest;
import www.tonghao.common.pay.utils.HttpResponse;
import www.tonghao.common.pay.utils.HttpResultType;
import www.tonghao.common.pay.utils.MD5;
import www.tonghao.common.pay.utils.RSA;

public class PayUtils {

	/**
	 * 畅捷支付平台公钥
	 */
	//正式环境
	//public static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";// 生产环境
	//测试环境
	public static String   MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";// 生产环境
	
	
	/**
	 * 商户私钥
	 */
	//生产环境测试商户号 200001220035 
	//正式环境
	//public static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALZfP51hvseDtK12kR7zBKquM7ZRZFzwj78DyOPDod8oE4GGNxZgbNq03eRBYQI3UA2iPnBTzvcvsgsVKhJIa2gGqtbO63RVrfHTiC/4gHLGGyWdDio0Rr+CeWTvDPG5VmLFYIfVFBN/1rrUrH2ac2zhR1J3pBJw1Qv41B7kgcm/AgMBAAECgYAXoYfwsLWzzJEg2Pg89mhoZWI2AZ3rnRBDRA5H1RWogNzmL6n7sErqm/0UkpQrYVdyLM/CZlmvFfMQFJH7BH9GJ109v6akuNO+rmfOCK2FTo76++t8ZD1fOw90fJPv9wiyoEO5OX2T3v0dYrlJKqWoorTqmKUvmi01wsBqMrqLQQJBAOWs20fS3hu+VPcVSydDEVu9HVBJlYppI2JHuazMw09i2Q4CX5rDMCi8zNgLa/p47UQ8RodYi8YrxtDRrMMFk+kCQQDLRmuF/7w57AKRu3jhSnbgh/L9WV9AyKTqVSJlFjOhR4j2uWT3L86VLCBlVhGgfwrBp2en834A+ZS+MRVOj69nAkBCtj7LUfip3lMXodu+f8CfAQNuurDxDzDz7xmDk8Z3XKLnatESRvfKHWzyo/6tNkrUsx1ZWuLeoQCSzxR7YTdhAkEAj6Urxn15ndTR2/oG6e2dI/3uEGpfjYA8SfvvWk/eHFJYz0EjwCngajnMis/PlQMxrreF4IGIwVedbOlyjRmscQJBAI0O6YEIE57dniVgY3OHKic0tS/cSAee7qcUurH+50SKdGlzcO0HKg1ypMmdQBIMJxO6ctzKyjUKy8lMPVJpn5s=";
	//测试环境
	public static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMP320M90c5qvzWTsK9/X+4G/n5LcQIBCBJ7ySoT7JyWvgVJQ2jnB7t4k105WVRCkdwtoKuchjT/FLVag+jdWdeG546PiJF7QxWQ88DDep+HLQg7dktre+8CmCIeOOyaCPWKatyVb7jBJV1q9YYLthBbmLq4r71BQB8hdJHm1AnHAgMBAAECgYBnR/37Vl15GLFln00DcXIxlOYT0dbhY4HJnAvNbWrKrHfBeXdZlklw+H9SJqN50QvmqvV+/KOO0tErM2VKGT77eAOSiFNZ1C0MlsvxWYT0CaPYZjweOstwQrV77SeW1ORiTQZdwVnJnW4bzp9ofydBcUx+Lf4lkoKW3+6tSacdgQJBAOmxGlTPeTjGxXJnZxqf8DbyFl0Z0UofSHC5vKnpTrGvdBmrz3+YS3da9RNUjQXf70s0gXLxcPxZbZyjcpa48ncCQQDWrN/CUD9tNwXTXCxRncdeC+jt85zp/76gh+0/KGyDM1svhLccmrfL4f9MXmwXtx08SLciX1AWtdNEj5BxTqcxAkEAzjtJLYdbR0vDe87dIpX6+8JxEgByvqMU3sZLRPdsR0q7ftws5kGUd+SBW2nGemTBXPEdPD7uowSYwx9LRNA6yQJBAMpspm2Ox9APEyIzBuNK2KRYrKI7lZmcer7ajR6B2ph4g6IyDYweY/MPPnFfk6NlGl8u3ei+YUGY4AcFLJl7JfECQAt5+7sqnVRsMZiDYVxUWqV0CoeKHx5y40vpKdErCaqju3ynEdGXk+hRTQK5LkXrOQXXHmLLqd6k6tp6SBq+uuY=";
	
	/**
	 * 编码类型
	 */
	public static String charset = "UTF-8";
	
	//正式站商户号
	//public static String USER_NO = "200005640029";
	//测试商户号
	public static String USER_NO = "200001220035";

	/**
	 * 获取SimpleDateFormat 
	 * 
	 * @param parttern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String parttern)
			throws RuntimeException {
		return new SimpleDateFormat(parttern);
	}

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 *
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 钱包处理结果
	 * @throws Exception
	 */
	public static String buildRequest(Map<String, String> sParaTemp,
			String signType, String key, String inputCharset, String gatewayUrl)
			throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key,
				inputCharset);
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(inputCharset);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setParameters(generatNameValuePair(
				createLinkRequestParas(sPara), inputCharset));
		request.setUrl(gatewayUrl);
		HttpResponse response = httpProtocolHandler
				.execute(request, null, null);
		if (response == null) {
			return null;
		}
		String strResult = response.getStringResult();
		return strResult;
	}
	
	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 *
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 钱包处理结果
	 * @throws Exception
	 */
	public static Object buildRequests(Map<String, String> sParaTemp,
			String signType, String key, String inputCharset, String gatewayUrl)
			throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key,
				inputCharset);
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(inputCharset);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setParameters(generatNameValuePair(
				createLinkRequestParas(sPara), inputCharset));
		request.setUrl(gatewayUrl);
		HttpResponse response = httpProtocolHandler
				.execute(request, null, null);
		if (response == null) {
			return null;
		}

		byte[] byteResult = response.getByteResult();
		if (byteResult.length > 1024) {
			return byteResult;
		} else {
			return response.getStringResult();
		}

	}


	/**
	 * MAP类型数组转换成NameValuePair类型
	 *
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(
			Map<String, String> properties, String charset) throws Exception {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			// nameValuePair[i++] = new NameValuePair(entry.getKey(),
			// URLEncoder.encode(entry.getValue(),charset));
			nameValuePair[i++] = new NameValuePair(entry.getKey(),
					entry.getValue());
		}
		return nameValuePair;
	}

	/**
	 * 生成要请求给钱包的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @param signType
	 *            RSA
	 * @param key
	 *            商户自己生成的商户私钥
	 * @param inputCharset
	 *            UTF-8
	 * @return 要请求的参数数组
	 * @throws Exception
	 */
	public static Map<String, String> buildRequestPara(
			Map<String, String> sParaTemp, String signType, String key,
			String inputCharset) throws Exception {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = "";
		if ("MD5".equalsIgnoreCase(signType)) {
			mysign = buildRequestByMD5(sPara, key, inputCharset);
		} else if ("RSA".equalsIgnoreCase(signType)) {
			mysign = buildRequestByRSA(sPara, key, inputCharset);
		}
		// 签名结果与签名方式加入请求提交参数组中
		System.out.println("sign:" + mysign);
		sPara.put("Sign", mysign);
		sPara.put("SignType", signType);

		return sPara;
	}

	/**
	 * 生成MD5签名结果
	 *
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestByMD5(Map<String, String> sPara,
			String key, String inputCharset) throws Exception {
		String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		mysign = MD5.sign(prestr, key, inputCharset);
		return mysign;
	}

	/**
	 * 生成RSA签名结果
	 *
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestByRSA(Map<String, String> sPara,
			String privateKey, String inputCharset) throws Exception {
		String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		mysign = RSA.sign(prestr, privateKey, inputCharset);
		return mysign;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @param encode
	 *            是否需要urlEncode
	 * @return 拼接后字符串
	 */
	public static Map<String, String> createLinkRequestParas(
			Map<String, String> params) {
		Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
		List<String> keys = new ArrayList<String>(params.keySet());
		String charset = params.get("Charset");
		if (StringUtils.isBlank(charset)) {
			charset = params.get("InputCharset");
		}
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value;
			try {
				value = URLEncoder.encode(params.get(key), charset);
				encodeParamsValueMap.put(key, value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return encodeParamsValueMap;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @param encode
	 *            是否需要urlEncode
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params,
			boolean encode) {

		// params = paraFilter(params);

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		String charset = params.get("Charset");
		if (StringUtils.isBlank(charset)) {
			charset = params.get("InputCharset");
		}

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (encode) {
				try {
					value = URLEncoder.encode(value, charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 除去数组中的空值和签名参数
	 *
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type") || key.equalsIgnoreCase("signtype")) {
				continue;
			}
			// try {
			// value = URLEncoder.encode(value,charset);
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 向测试服务器发送post请求
	 * 
	 * @param origMap
	 *            参数map
	 * @param charset
	 *            编码字符集
	 * @param MERCHANT_PRIVATE_KEY
	 *            私钥
	 */
	public static String gatewayPost(Map<String, String> origMap) {
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
			Map<String, String> sPara = buildRequestPara(origMap, "RSA",
					MERCHANT_PRIVATE_KEY, charset);
			System.out.println(urlStr + createLinkString(sPara, true));
			String resultString = buildRequest(origMap, "RSA",
					MERCHANT_PRIVATE_KEY, charset, urlStr);
			System.out.println(resultString);
			return resultString;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/**
	 * 向测试服务器发送post请求
	 * 
	 * @param origMap
	 *            参数map
	 * @param charset
	 *            编码字符集
	 * @param MERCHANT_PRIVATE_KEY
	 *            私钥
	 */
	public Object gatewayPosts(Map<String, String> origMap, String charset,
			String MERCHANT_PRIVATE_KEY) {
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
			// String urlStr =
			// "https://cpay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
			Map<String, String> sPara = buildRequestPara(origMap, "RSA",
					MERCHANT_PRIVATE_KEY, charset);
			System.out.println(urlStr + createLinkString(sPara, true));
			Object obj = buildRequests(origMap, "RSA", MERCHANT_PRIVATE_KEY,
					charset, urlStr);
			System.out.println(obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 加密，部分接口，有参数需要加密
	 * 
	 * @param src
	 *            原值
	 * @param publicKey
	 *            畅捷支付发送的平台公钥
	 * @param charset
	 *            UTF-8
	 * @return RSA加密后的密文
	 */
	private String encrypt(String src, String publicKey, String charset) {
		try {
			byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset),
					publicKey);
			return Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
