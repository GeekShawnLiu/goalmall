package www.tonghao.platform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.service.common.entity.SystemSetting;
import www.tonghao.service.common.service.SystemSettingService;

@RestController
@RequestMapping("/systemSetting")
public class SystemSettingController {

	@Autowired
	private SystemSettingService systemSettingService;
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public Map<String, Object> view(){
		List<SystemSetting> list = systemSettingService.selectByExample(null);
		Map<String, Object> resultMap = new HashMap<>();
		if(CollectionUtils.isNotEmpty(list)){
			for (SystemSetting systemSetting : list) {
				resultMap.put(systemSetting.getCode(), systemSetting.getSetValue());
			}
		}
		return resultMap;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody SystemSetting systemSetting){
		return systemSettingService.saveEntity(systemSetting);
	}
}
