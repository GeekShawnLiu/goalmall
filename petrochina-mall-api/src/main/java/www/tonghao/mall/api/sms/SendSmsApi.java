package www.tonghao.mall.api.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import www.tonghao.mall.api.utils.HttpClient;
import www.tonghao.mall.api.utils.JsonUtil;

public class SendSmsApi {

	// 短信应用SDK AppID
	private static int APP_ID = 1400207267;

	// 短信应用SDK AppKey
	private static String APP_Key = "8eb1da7b29daeda5068245ed1c59e9c9";

	// 短信模板ID
	private static int templateId = 323436;

	// 签名
	private static String smsSign = "中油福瑞商城";

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
			return null;
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
	
	public static void main(String[] args) {
		Map<String, String> params=new HashMap<>();
		params.put("phone", "155105055");
		params.put("code", "0192");
		params.put("time", "1");
		String doGet = HttpClient.doGet("https://www.jianbuzou.xyz/jbzprd/sendcode", params);
		JsonNode rootNode = JsonUtil.readTree(doGet);
		String code = rootNode.path("code").asText();
		//"85002"
		System.out.println(code);
		if(!"85002".equals(code)) {
			System.out.println("发送成功");
		}else {
			System.out.println("发送失败");
		}
		
		
		System.out.println(doGet);
	}
}
