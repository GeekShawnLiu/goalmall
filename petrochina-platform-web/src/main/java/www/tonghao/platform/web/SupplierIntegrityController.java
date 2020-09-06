package www.tonghao.platform.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.IntegritySetting;
import www.tonghao.platform.entity.SupplierIntegrity;
import www.tonghao.platform.entity.SupplierIntegrityVo;
import www.tonghao.platform.service.IntegritySettingService;
import www.tonghao.platform.service.SupplierIntegrityService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/supplierIntegrity")
public class SupplierIntegrityController {
	@Autowired
	private IntegritySettingService integritySettingService;
	@Autowired
	private SupplierIntegrityService supplierIntegrityService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="供应商id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<SupplierIntegrity> test(@ModelAttribute PageBean page,Long supplierId){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(SupplierIntegrity.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("supplierId", supplierId);
		List<SupplierIntegrity> list = supplierIntegrityService.selectByExample(example);
		return new PageInfo<SupplierIntegrity>(list);
	}
	
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody SupplierIntegrity integrity,HttpServletRequest request) throws ParseException{
		integrity.setCreateAt(DateUtilEx.timeFormat(new Date()));
		integrity.setAddressIp(getIpAddr(request));
		List<IntegritySetting> selectByExample = integritySettingService.selectByExample(null);
		if(!CollectionUtils.isEmpty(selectByExample)){
			IntegritySetting integritySetting = selectByExample.get(0);
			if(integrity.getIsTransaction()==1){
				String createAt = integrity.getCreateAt();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = simpleDateFormat.parse(createAt);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(parse);
				calendar.add(calendar.MONTH, integritySetting.getCycle());
				integrity.setRecoverAt(simpleDateFormat.format(calendar.getTime()));
			}
		}
		int save = supplierIntegrityService.save(integrity);
		return ResultUtil.result(save, 0);
	}
	
	@ApiOperation(value="查询供应商",notes="分页查询查询供应商api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getSupplierPage",method=RequestMethod.GET)
	public PageInfo<SupplierIntegrityVo> getSupplierPage(@ModelAttribute PageBean page,String supplierName,String isTransaction,String minScore,String maxScore){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supplierName", supplierName);
		map.put("isTransaction", isTransaction);
		map.put("minScore", minScore);
		map.put("maxScore", maxScore);
		List<SupplierIntegrityVo> selectSupplier = supplierIntegrityService.selectSupplier(map);
		return new PageInfo<SupplierIntegrityVo>(selectSupplier);
	}
	private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            System.out.println("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            System.out.println("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            System.out.println("getRemoteAddr ip: " + ip);
        } 
        System.out.println("获取客户端ip: " + ip);
        return ip;  
    }
}
