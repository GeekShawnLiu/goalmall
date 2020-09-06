package www.tonghao.service.common.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.SystemSetting;
import www.tonghao.service.common.mapper.SystemSettingMapper;
import www.tonghao.service.common.service.SystemSettingService;

@Service("systemSettingService")
public class SystemSettingServiceImpl extends BaseServiceImpl<SystemSetting> implements SystemSettingService{

	@Autowired
	private SystemSettingMapper systemSettingMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	@Override
	public Map<String, Object> saveEntity(SystemSetting systemSetting) {
		int save = 0;
		if(systemSetting != null && systemSetting.getCode() != null){
			SystemSetting selectByCode = systemSettingMapper.selectByCode(systemSetting.getCode());
			if(selectByCode == null){
				save = systemSettingMapper.insertSelective(systemSetting);
			}else{
				save = systemSettingMapper.updateByCode(systemSetting);
			}
		}
		if(save > 0){
			//更新redis缓存
			redisDao.setKey("sys_" + systemSetting.getCode(), systemSetting.getSetValue());
			return ResultUtil.success("");
		}else{
			return ResultUtil.error("保存失败");
		}
	}

	@Override
	public boolean openMixturePay() {
		SystemSetting systemSetting = systemSettingMapper.selectByCode("open_mixture_pay");
		return systemSetting != null && "1".equals(systemSetting.getSetValue());
	}
}
