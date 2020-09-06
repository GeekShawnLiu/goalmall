package www.tonghao.platform.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.IntegritySetting;
import www.tonghao.platform.service.IntegritySettingService;
import www.tonghao.service.common.service.SuppliersService;

@RestController
@RequestMapping("/integritySetting")
public class IntegritySettingController {
   @Autowired
   private IntegritySettingService integritySettingService;
   
   @Autowired
   private SuppliersService suppliersService;
   
   
   @RequestMapping(value="/getOneEntity",method=RequestMethod.GET)
   public IntegritySetting getById(){
	   List<IntegritySetting> selectByExample = integritySettingService.selectByExample(null);
	   IntegritySetting integritySetting=null;
	   if(!CollectionUtils.isEmpty(selectByExample)){
		   integritySetting=selectByExample.get(0);
	   }
	   return integritySetting;
   }
   
   @RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
   public Map<String, Object>  saveOrUpdate(@RequestBody IntegritySetting integritySetting ){
	   int saveOrUpdate=0;
	   if(integritySetting.getId()!=null){
		   integritySetting.setUpdateAt(DateUtilEx.timeFormat(new Date()));
		   saveOrUpdate=integritySettingService.updateNotNull(integritySetting);
	   }else{
		   integritySetting.setUpdateAt(DateUtilEx.timeFormat(new Date()));
		   integritySetting.setCreateAt(DateUtilEx.timeFormat(new Date()));
		   saveOrUpdate=integritySettingService.save(integritySetting);
	   }
	   return ResultUtil.result(saveOrUpdate, 0);
   }
   
}
