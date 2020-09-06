package www.tonghao.service.common.service;

import java.util.Map;

public interface MobileCodeService {
	
	public Map<String, Object> getMobileCode(String mobile, String smsurl);
	
	public Map<String, Object> sendMobile(String mobile, String smsurl);
}
