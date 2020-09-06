package www.tonghao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import www.tonghao.utils.SendSmsApi;

/*@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)*/
public class SmsApplication {
	public static void main(String[] args) {
		/*SpringApplication.run(SmsApplication.class, args);*/
		//SendSmsApi.SendCodePrice("李万林", "13223123", "2019-09-09", "积分支付", "2019-09-08", "15510505500");
		/*Map<String, Object> sendCode = SendSmsApi.SendUser("2019", "积分活动", "2019-07-19","15254566709");*/
		/*Map<String, Object> sendCode = SendSmsApi.SendCode("1234", 1, "15510505500");*/
	
		
	}
}
