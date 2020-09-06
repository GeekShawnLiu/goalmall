package www.tonghao.sms.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.utils.SendSmsApi;

@RestController
@RequestMapping("/sms")
public class SmsController {
	
	private Logger log= LoggerFactory.getLogger(SmsController.class);
	
	@RequestMapping(value="/sendSms", method=RequestMethod.GET)
	public Map<String, Object> sendSms(String code, int time, String mobile){
		log.info(code, time, mobile);
		return SendSmsApi.SendCode(code, time, mobile);
	}
}
