package www.tonghao.service.common.mapper;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.SystemSetting;

public interface SystemSettingMapper extends BaseMapper<SystemSetting>{

	SystemSetting selectByCode(String code);
	
	int updateByCode(SystemSetting systemSetting);
}
