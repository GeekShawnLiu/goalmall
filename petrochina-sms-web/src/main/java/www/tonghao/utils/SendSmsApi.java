package www.tonghao.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;


public class SendSmsApi {

	// 短信应用SDK AppID
	private static int APP_ID = 1400207267;

	// 短信应用SDK AppKey
	private static String APP_Key = "8eb1da7b29daeda5068245ed1c59e9c9";

	// 短信模板ID
	private static int templateId = 323436;
	
	private static int price_templateId = 386989;
	
	private static int user_templateId = 386972;
	
	private static int beginuser_templateId = 386971;

	// 签名
	private static String smsSign = "中油惠服";

	
	/**
	 * 
	 * Description: 
	 * 
	 * @data 2019年4月30日
	 * @param code 验证码
	 * @param time 时间 分钟
	 * @return
	 */
	public static Map<String, Object> beginSendUser(String year,String activityName,String begintime,String endtime,String mobile) {

		try {
			String[] params = { year,activityName,begintime,endtime};
			SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_Key);
			SmsSingleSenderResult result = ssender.sendWithParam("86", mobile, beginuser_templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
			HashMap object = JsonUtil.toObject(result.toString(), HashMap.class);
			return object;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 
	 * 
	 * @data 2019年4月30日
	 * @param code 验证码
	 * @param time 时间 分钟
	 * @return
	 */
	public static Map<String, Object> SendUser(String year,String activityName,String time,String mobile) {

		try {
			String[] params = { year,activityName,time};
			SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_Key);
			SmsSingleSenderResult result = ssender.sendWithParam("86", mobile, user_templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
			HashMap object = JsonUtil.toObject(result.toString(), HashMap.class);
			return object;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 
	 * 
	 * @data 2019年4月30日
	 * @param code 验证码
	 * @param time 时间 分钟
	 * @return
	 */
	public static Map<String, Object> SendCodePrice(String userName,String orderNo, String refundTime,String orderType, String CreateTime,String mobile) {

		try {
			String[] params = { userName,refundTime,orderNo,orderType,CreateTime };
			SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_Key);
			SmsSingleSenderResult result = ssender.sendWithParam("86", mobile, price_templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
			HashMap object = JsonUtil.toObject(result.toString(), HashMap.class);
			return object;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 
	 * 
	 * @data 2019年4月30日
	 * @param code 验证码
	 * @param time 时间 分钟
	 * @return
	 */
	public static Map<String, Object> SendCode(String code, int time, String mobile) {

		try {
			String[] params = { code, time + "" };
			SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_Key);
			SmsSingleSenderResult result = ssender.sendWithParam("86", mobile, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
			HashMap object = JsonUtil.toObject(result.toString(), HashMap.class);
			return object;
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
		return null;
	}
}
