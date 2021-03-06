package www.tonghao.common.pay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.common.pay.utils.HttpProtocolHandler;
import www.tonghao.common.pay.utils.HttpRequest;
import www.tonghao.common.pay.utils.HttpResponse;
import www.tonghao.common.pay.utils.HttpResultType;
import www.tonghao.common.pay.utils.MD5;
import www.tonghao.common.pay.utils.RSA;
import www.tonghao.common.utils.JsonUtil;

public class ChanpaySCPDemo {

	/**
	 * 畅捷支付平台公钥
	 * 测试环境
	 */
	private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";// 生产环境
		
	
	
	/**
	 * 商户私钥
	 */
	//生产环境测试商户号 200001220035 
	private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMP320M90c5qvzWTsK9/X+4G/n5LcQIBCBJ7ySoT7JyWvgVJQ2jnB7t4k105WVRCkdwtoKuchjT/FLVag+jdWdeG546PiJF7QxWQ88DDep+HLQg7dktre+8CmCIeOOyaCPWKatyVb7jBJV1q9YYLthBbmLq4r71BQB8hdJHm1AnHAgMBAAECgYBnR/37Vl15GLFln00DcXIxlOYT0dbhY4HJnAvNbWrKrHfBeXdZlklw+H9SJqN50QvmqvV+/KOO0tErM2VKGT77eAOSiFNZ1C0MlsvxWYT0CaPYZjweOstwQrV77SeW1ORiTQZdwVnJnW4bzp9ofydBcUx+Lf4lkoKW3+6tSacdgQJBAOmxGlTPeTjGxXJnZxqf8DbyFl0Z0UofSHC5vKnpTrGvdBmrz3+YS3da9RNUjQXf70s0gXLxcPxZbZyjcpa48ncCQQDWrN/CUD9tNwXTXCxRncdeC+jt85zp/76gh+0/KGyDM1svhLccmrfL4f9MXmwXtx08SLciX1AWtdNEj5BxTqcxAkEAzjtJLYdbR0vDe87dIpX6+8JxEgByvqMU3sZLRPdsR0q7ftws5kGUd+SBW2nGemTBXPEdPD7uowSYwx9LRNA6yQJBAMpspm2Ox9APEyIzBuNK2KRYrKI7lZmcer7ajR6B2ph4g6IyDYweY/MPPnFfk6NlGl8u3ei+YUGY4AcFLJl7JfECQAt5+7sqnVRsMZiDYVxUWqV0CoeKHx5y40vpKdErCaqju3ynEdGXk+hRTQK5LkXrOQXXHmLLqd6k6tp6SBq+uuY=";
	
	/**
	 * 编码类型
	 */
	private static String charset = "UTF-8";

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
		System.out.println("预下订单参数:"+sPara);
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
		System.out.println("预下订单参数："+sPara);
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
					|| key.equalsIgnoreCase("sign_type")||key.equalsIgnoreCase("signtype")) {
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
	public String gatewayPost(Map<String, String> origMap, String charset,
			String MERCHANT_PRIVATE_KEY) {
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
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

	/**
	 * 异步通知验签仅供参考
	 */
	public void notifyVerify() {

		String sign = "uERyn9W/b8I88bAVyaXUXXpyw0Ir5D3da6WiO5qrpDrvpgBmDzrYWt2wjZsu6CZdgxZ3+VSdRszrCKJM0UxUGqqKkf0gg90DFlGPMqloZHBzemXSoU2Zz/XYc7/CXWoi3+ZYk43dMhbh/S++RQFBOq+abkiGeD6QNlm4TUiJ7os=";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("notify_id", "448b7e8b93694e958eae50e295617033");
		paramMap.put("notify_type", "trade_status_sync");
		paramMap.put("notify_time", "20170705104121");
		paramMap.put("_input_charset", "UTF-8");
		paramMap.put("version", "1.0");
		paramMap.put("outer_trade_no", "0001149922237380501132458");
		paramMap.put("inner_trade_no", "101149922237409129479");
		paramMap.put("trade_status", "TRADE_SUCCESS");
		paramMap.put("trade_amount", "0.01");
		paramMap.put("gmt_create", "20170705104121");
		paramMap.put("gmt_payment", "20170705104121");
		// paramMap.put("gmt_close", "");
		paramMap.put("extension", "{}");
		String text = createLinkString(paramMap, false);
		System.out.println("ori_text:" + text);
		try {
			System.out.println(RSA.verify(text, sign, MERCHANT_PUBLIC_KEY,
					charset));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 4.2.1.1. api mag_pub_com_pay 公众号/服务窗确认支付
	 */
	public void mag_pub_com_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_pub_com_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");//生产环境测试商户号
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		origMap.put("BankCode", "WXPAY");
		origMap.put("DeviceInfo", "wx90192dels817xla0");
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "0.01");
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "11");
		origMap.put("TradeMemo", "1111");
		origMap.put("Subject", "0153");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("OrderEndTime", "");
		origMap.put("NotifyUrl", "http://www.baidu.com");
		origMap.put("SpbillCreateIp", "127.0.0.1");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 4.2.1.2. api mag_pub_sinm_pay 微信公众号预下单
	 */
	public void mag_pub_sinm_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_pub_sinm_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035"); //生产环境的测试商户号
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		origMap.put("AppId", "wx5fe8ca9dca7bbbce");
		origMap.put("BuyerPayCode", "ozFSO1u0v7mwILyKlP2EzdSsdueA");
		origMap.put("DeviceInfo", "013467007045764");
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "1.00");
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "11");
		origMap.put("TradeMemo", "1111");
		origMap.put("Subject", "0153");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("OrderEndTime", "");
		origMap.put("NotifyUrl", "http://www.baidu.com");
		origMap.put("SpbillCreateIp", "127.0.0.1");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 4.2.1.4. api mag_init_code_pay 主扫预下单
	 */
	public void mag_init_code_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_init_code_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");//生产环境测试商户号
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		//origMap.put("MchId", "200000140001");
		origMap.put("MchId", "200001220035");
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		//origMap.put("BankCode", "UNIONPAY");
		origMap.put("BankCode", "ALIPAY");
		//origMap.put("BankCode", "WXPAY");
		origMap.put("AppId", "wx90192dels817xla0");
		origMap.put("DeviceInfo", "wx90192dels817xla0");
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "0.02");
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "11");
		origMap.put("TradeMemo", "1111");
		origMap.put("Subject", "0153");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("OrderEndTime", "");
		origMap.put("NotifyUrl", "http://www.baidu.com");
		origMap.put("SpbillCreateIp", "127.0.0.1");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 4.2.1.5. api mag_pass_code_pay 被扫
	 */
	public void mag_pass_code_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_pass_code_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		origMap.put("BankCode", "ALIPAY");
		origMap.put("AppId", "");
		origMap.put("BuyerPayCode", "282848082187302444");
		origMap.put("DeviceInfo", "");
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "0.01");
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "11");
		origMap.put("TradeMemo", "1111");
		origMap.put("Subject", "0153");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("OrderEndTime", "");
		origMap.put("NotifyUrl", "http://www.baidu.com");
		origMap.put("SpbillCreateIp", "127.0.0.1");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	


	/**
	 * 4.2.1.6. api mag_one_code_pay 一码付
	 */
	public String mag_one_code_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_one_code_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");	//生产环境测试商户号
		
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");	//生产环境测试商户号
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		origMap.put("BusiType", "2");	//1动码，2固码
		origMap.put("DeviceInfo", "wx90192dels817xla0");
		origMap.put("Currency", "CNY");
		//origMap.put("TradeAmount", "1.00");	// 动码时金额为必输
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "测试订单");
		origMap.put("TradeMemo", "测试订单");
		origMap.put("Subject", "测试订单");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("OrderEndTime", "");
		origMap.put("NotifyUrl", "http://nbtest.sinopr.org/test");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		return this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 支付宝服务窗   mag_ali_pub_sinm_pay
	 */
	public void mag_ali_pub_sinm_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_ali_pub_sinm_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");//生产环境测试商户号
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		origMap.put("BuyerId", "");//买家支付宝用户ID
		origMap.put("BuyerLogonId", "15070921673");//买家支付宝账号,与BuyerId二者至少选一
		origMap.put("DeviceInfo", "013467007045764");
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "1.00");
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "11");
		origMap.put("TradeMemo", "1111");
		origMap.put("Subject", "0153");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("OrderEndTime", "");
		origMap.put("LimitCreditPay", "");	//NoCredit
		origMap.put("NotifyUrl", "http://www.baidu.com");
		origMap.put("SpbillCreateIp", "127.0.0.1");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	/**
	 * 支付宝WAP支付   mag_ali_wap_pay
	 */
	public void mag_ali_wap_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_ali_wap_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");
		origMap.put("TradeType", "11");
		
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "1.00");
		origMap.put("GoodsName", "测试订单");
		origMap.put("TradeMemo", "测试订单");
		origMap.put("Subject", "测试订单");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		origMap.put("SpbillCreateIp", "192.168.1.1");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	/**
	 * 
	 * 4.2.1.7. 商户订单状态查询 api nmg_api_query_trade
	 */
	private void nmg_api_query_trade() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put("Service", "nmg_api_query_trade");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");// 畅捷支付分配的商户号
		
		origMap.put("InputCharset", charset);// 字符集
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "");// 前台跳转url
		origMap.put("Memo", "备注");

		// 2.2 业务参数
		origMap.put("TrxId", Long.toString(System.currentTimeMillis()));// 订单号
		origMap.put("OrderTrxId", "1496822592397");// 原业务请求订单号
		origMap.put("TradeType", "pay_order");// 原业务订单类型
		origMap.put("Extension", "");// 原业务订单类型
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 
	 * 4.2.1.8. 商户退款请求 api nmg_api_refund
	 */

	private void nmg_api_refund() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put("Service", "nmg_api_refund");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");// 畅捷支付分配的商户号
		origMap.put("InputCharset", charset);// 字符集
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		origMap.put("Memo", "备注");
		// 2.2 业务参数
		origMap.put("TrxId", "101156334484758762149");// 订单号
		origMap.put("OriTrxId", "1563344842194");// 原有支付请求订单号
		origMap.put("TrxAmt", "0.88");// 退款金额

		origMap.put("NotifyUrl", "www.baidu.com");// 异步通知地址
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 2.10 商户日交易对账单文件
	 */
	public void nmg_api_everyday_trade_file() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put("Service", "nmg_api_everyday_trade_file");// 支付的接口名
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");// 畅捷支付分配的商户号
		origMap.put("InputCharset", charset);// 字符集
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "");// 前台跳转url
		origMap.put("Memo", "备注");

		// 2.11 日支付对账文件
//		origMap.put("TransDate", "20170601");// 交易日期
//		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		origMap.put("TransDate", "20170602");// 交易日期
		Object obj = this.gatewayPosts(origMap, charset, MERCHANT_PRIVATE_KEY);
		if (obj instanceof String) {
			System.out.println((String) obj);
		} else {
			this.downloadFile((byte[]) obj);
		}

		
	}
	
	public void downloadFile(byte[] bt) {
		// 确定写出文件的位置
		File file = new File("C:\\Test.xls");
		// 建立输出字节流
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			// 用FileOutputStream 的write方法写入字节数组
			fos.write(bt);
			System.out.println("文件下载成功");
			// 为了节省IO流的开销，需要关闭
			if (fos != null) {
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * nmg_api_refund_trade_file
	 */
	public void nmg_api_refund_trade_file() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put("Service", "nmg_api_refund_trade_file");// 支付的接口名
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");// 畅捷支付分配的商户号
		origMap.put("InputCharset", charset);// 字符集
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "");// 前台跳转url
		origMap.put("Memo", "备注");

		// 2.11 日支付对账文件
		origMap.put("TransDate", "20160728");// 交易日期

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	/**
	 * 4.2.1.4. mag_wx_app_pay 微信小程序支付
	 */
	public void mag_wx_mini_program_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_wx_mini_program_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200001220035");//生产环境测试商户号
		origMap.put("InputCharset", charset);
		origMap.put("TradeDate", getDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", getDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		origMap.put("ReturnUrl", "www.baidu.com");// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
		origMap.put("MchId", "200001220035");
		origMap.put("SubMchId", "");
		origMap.put("TradeType", "11");
		origMap.put("AppId", "wxf8e96529a5c95f8e");//wx90192dels817xla0
		origMap.put("BuyerPayCode", "ozFSO1u0v7mwILyKlP2EzdSsdueA");//wx90192dels817xla0
		origMap.put("DeviceType", "ANDROID");
		origMap.put("DeviceInfo", "wx90192dels817xla0");
		origMap.put("Currency", "CNY");
		origMap.put("TradeAmount", "0.01");
		origMap.put("EnsureAmount", "");
		origMap.put("GoodsName", "11");
		origMap.put("TradeMemo", "1111");
		origMap.put("Subject", "0153");
		origMap.put("OrderStartTime",
				getDateFormat("yyyyMMddHHmmss").format(date));
		//origMap.put("OrderEndTime", "20190323194038");
		origMap.put("NotifyUrl", "http://www.baidu.com");
		origMap.put("SpbillCreateIp", "127.0.0.1");
		origMap.put("SplitList", "");
		origMap.put("Ext", "{'ext':'ext1'}");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	
	/**
	 * 4.2.10.微信APP支付
	 */
	public void mag_wx_app_pay() {

		Date date = new Date();
		Map<String, String> origMap = new HashMap<String, String>();
		// 基本参数
		origMap.put("Service", "mag_wx_wap_pay");
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", PayUtils.USER_NO);	//生产环境测试商户号
		origMap.put("InputCharset", "UTF-8");
		origMap.put("TradeDate", new SimpleDateFormat("yyyyMMdd").format(date));
		origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(date));
		// origMap.put("SignType","RSA");
		/*origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");*/// 前台跳转url
		origMap.put("Memo", "备注");

		// 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
		origMap.put("OutTradeNo", "34234234234");//商户订单号
		origMap.put("MchId", PayUtils.USER_NO);	//生产环境测试商户号
		origMap.put("TradeType", "11");//交易类型（即时 11 担保 12）
		
		origMap.put("AppId", "wx90192dels817xla0");//微信/支付宝给商户开通的标识
		
		origMap.put("DeviceInfo", "AND_SDK");//"iOS_SDK",AND_SDK
		//应用名 如果是用于苹果或安卓app应用中，传分别对应在AppStore和安卓市场中的应用名（如：王者荣耀）；如果是用于手机网站，传对应的网站名称（如：京东商城）
		origMap.put("MchAppName", "中油福瑞商城");
		//如果是用于苹果或安卓app应用中，传分别对应在AppStore应用唯一标识（如：com.tencent.wzryISO）和安卓传包名（如：com.tencent.tmgp.sgame）；如果是用于手机网站，传对应的网站首页URL必须是公网能正常访问的（如：https://m.jd.com）
		origMap.put("MchAppId", "https://freemall.richfit.com/html/");//应用标识
		origMap.put("Currency", "CNY");//默认CNY，暂只支持CNY
		origMap.put("TradeAmount", "0.9");	// 金额为必输
		origMap.put("GoodsName", "中油福瑞商城订单");
		origMap.put("Subject", "中油福瑞商城订单");
		origMap.put("TradeMemo", "中油福瑞商城订单");
		origMap.put("OrderStartTime",
				new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(date);
		nowTime.add(Calendar.MINUTE, 15);
		origMap.put("OrderEndTime", new SimpleDateFormat("yyyyMMddHHmmss").format(nowTime.getTime()));//订单失效时间
		origMap.put("NotifyUrl", "");
		origMap.put("SpbillCreateIp", "192.168.1.1");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	

	public static void main(String[] args) throws Exception {
		ChanpaySCPDemo test = new ChanpaySCPDemo();
		test.mag_init_code_pay();
		/*test.mag_ali_wap_pay();*/
		/*test.mag_ali_wap_pay();*/
		//test.mag_wx_mini_program_pay(); 
		
		//test.mag_one_code_pay();// 一码付
		/*test.mag_one_code_pay();*/
		/*test.mag_pub_sinm_pay();*/
		 /*test.mag_ali_wap_pay();*/
	//	 test.mag_init_code_pay();// 主扫预下单
//		 test.mag_pass_code_pay();// 被扫
//		 test.mag_one_code_pay();// 一码付
//		 test.mag_pub_com_pay();// 公众号/服务窗确认支付	大商户 下线
		 /*test.mag_pub_sinm_pay();*/// 微信公众号预下单             一户一报
//		 test.mag_ali_pub_sinm_pay(); //支付宝服务窗		一户一报
//		 test.mag_ali_wap_pay();  //支付宝WAP		
//		test.mag_wx_mini_program_pay(); //小程序

//		test.nmg_api_query_trade();// 商户订单状态查询
//		test.nmg_api_refund();//商户退款请求
//		test.nmg_api_everyday_trade_file();//商户日交易对账单文件
//		test.nmg_api_refund_trade_file();//商户日退款对账单文件
		/*test.nmg_api_refund();*/
	}
}
