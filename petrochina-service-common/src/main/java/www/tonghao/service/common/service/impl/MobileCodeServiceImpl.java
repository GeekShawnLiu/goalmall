package www.tonghao.service.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.common.constant.SmSConstant;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.HttpClient;
import www.tonghao.common.utils.NumUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.service.common.service.MobileCodeService;

@Service("mobileCodeService")
public class MobileCodeServiceImpl implements MobileCodeService{
	
	@Autowired
	private RedisDao redisDao;
	
	//验证码过期时间毫秒
	public static final long MOBILE_CODE_TIMEOUT_MILLISECONDS = 2 * 60 * 1000;
	
	//验证码过期时间分钟
	public static final int MOBILE_CODE_TIMEOUT_MINUTES = 2;

	@Override
	public Map<String, Object> getMobileCode(String mobile, String smsurl) {
		if(StringUtils.isBlank(smsurl)){
			return ResultUtil.success("短信发送失败");
		}
		
		if(StringUtils.isBlank(mobile)){
			return ResultUtil.error("请填写手机号");
		}
		
		if(redisDao.isNotKey(mobile)){
			return ResultUtil.error("验证码已发送，请稍后再试");
		}
		
		//获取验证码
		String code = NumUtil.createIdentifyCode(6);
		
		//发送短信
		//SendSmsApi.SendCode(code, MOBILE_CODE_TIMEOUT_MINUTES, mobile);
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		map.put("time", MOBILE_CODE_TIMEOUT_MINUTES + "");
		map.put("phone", mobile);
		
		String doGet = HttpClient.doGet(smsurl, map);
		System.out.println(doGet);
		JsonNode rootNode = JsonUtil.readTree(doGet);
		String result_code = rootNode.path("code").asText();
		if(!"85002".equals(result_code)) {
			//验证码缓存
			redisDao.setKey(mobile, code, MOBILE_CODE_TIMEOUT_MILLISECONDS);
			return ResultUtil.success("短信已发送");
		}else {
			return ResultUtil.success("短信发送失败");
		}
	}

	@Override
	public Map<String, Object> sendMobile(String mobile, String smsurl) {
		if(StringUtils.isBlank(smsurl)){
			return ResultUtil.success("短信发送失败");
		}
		
		if(StringUtils.isBlank(mobile)){
			return ResultUtil.error("请填写手机号");
		}
		
		if(redisDao.isNotKey(mobile)){
			return ResultUtil.error("验证码已发送，请稍后再试");
		}
		
		//获取验证码
		String code = NumUtil.createIdentifyCode(6);
		
		//发送短信
		Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", mobile);
		map.put("params", code + "," + MOBILE_CODE_TIMEOUT_MINUTES);
		map.put("template", SmSConstant.LOGIN_TEMPLATE_ID);
	   
		//验证码缓存
		String doGet = HttpClient.doGet(SmSConstant.SMSURL, map);
		System.out.println(doGet);
		JsonNode rootNode = JsonUtil.readTree(doGet);
		String result_code = rootNode.path("code").asText();
		if(!"85002".equals(result_code)) {
			redisDao.setKey(mobile, code, MOBILE_CODE_TIMEOUT_MILLISECONDS);
			return ResultUtil.success("短信已发送");
		}else {
			return ResultUtil.error("短信发送失败");
		}
	}
}
