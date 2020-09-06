package www.tonghao.service.common.service;

import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.SystemSetting;

public interface SystemSettingService extends BaseService<SystemSetting>{

	Map<String, Object> saveEntity(SystemSetting systemSetting);
	
	/**
	 * 
	 * Description: 是否开启混合支付
	 * 
	 * @data 2019年8月7日
	 * @param 
	 * @return
	 */
	boolean openMixturePay();
}
