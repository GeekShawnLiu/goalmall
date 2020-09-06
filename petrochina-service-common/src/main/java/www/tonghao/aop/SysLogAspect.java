package www.tonghao.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UrlPathHelper;

import www.tonghao.common.SysLog;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.IpAddressUtil;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.SysOperateLogs;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PermissionsService;
import www.tonghao.service.common.service.SysOperateLogsService;
import www.tonghao.utils.UserUtil;

@Aspect
@Component
public class SysLogAspect {

	@Autowired
	private SysOperateLogsService sysOperateLogsService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Pointcut("@annotation(www.tonghao.common.SysLog)")
	public void operationLog(){
		
	}
	
	@Around("operationLog()")
	public Object saveLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		String methodName = proceedingJoinPoint.getSignature().getName();
		Method[] methods = proceedingJoinPoint.getTarget().getClass().getMethods();
		Method method = null;
		for (Method m : methods) {
			if(methodName.equals(m.getName())){
				method = m;
				break;
			}
		}
		SysLog sysLog = method.getAnnotation(SysLog.class);
		String opt = sysLog.opt();
		saveSysOperateLogs(opt);
		return proceedingJoinPoint.proceed();
	}
	
	private void saveSysOperateLogs(String operate){
		SysOperateLogs sysOperateLogs = new SysOperateLogs();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UrlPathHelper helper = new UrlPathHelper();
		String contextpath = helper.getContextPath(request);
		String requestUri = helper.getOriginatingRequestUri(request);
		String url = StringUtils.isEmpty(contextpath)?requestUri:StringUtils.substringAfter(requestUri, contextpath);
		String urlPattern = StringUtils.substringBeforeLast(url, "/");
		if(StringUtils.isNotEmpty(urlPattern)){
			url = urlPattern;
		}
		sysOperateLogs.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		sysOperateLogs.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		sysOperateLogs.setLogType((byte)1);
		sysOperateLogs.setIp(IpAddressUtil.getIpAddress(request));
		sysOperateLogs.setDription(operate);
		Permissions permission = permissionsService.findByUrl(url);
		String sysName = null;
		String moduleName = null;
		if(permission!=null){
			List<Permissions> list = permission.getNodeList();
			if(list!=null){
				if(list.size()>0){
					sysName = list.get(0).getName();
				}
				if(list.size()>1){
					moduleName = list.get(1).getName();
				}
			}
		}
		sysOperateLogs.setSysName(sysName);
		sysOperateLogs.setModuleName(moduleName);
		
		Users user = UserUtil.getUser(request);
		if(user!=null){
			sysOperateLogs.setUserId(user.getId());
			sysOperateLogs.setLoginName(user.getLoginName());
			sysOperateLogs.setOperator(user.getRealName());
			sysOperateLogs.setOperatorDep(user.getDepName());
		}
		sysOperateLogsService.save(sysOperateLogs);
	}
}
