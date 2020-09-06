package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.JsonUtil;

@RestController
@RequestMapping("/sys_setting")
public class SystemSettingController {

	@Autowired
	private RedisDao redisDao;
	
	/**
	 * 
	 * Description: 获取系统配置
	 * 
	 * @data 2019年7月24日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="获取系统配置")
	@ApiImplicitParams({
		@ApiImplicitParam(name="code",value="code",required=true,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/getSetting", method=RequestMethod.GET)
	public String getSetting(String code){
		Object value = redisDao.getValue("sys_"+code);
		return value == null ? null : JsonUtil.toJson(value);
	}
	
}
