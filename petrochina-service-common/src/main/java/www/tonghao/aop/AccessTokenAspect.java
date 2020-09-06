package www.tonghao.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.util.CollectionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import www.tonghao.common.AccessToken;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

@Aspect
@Component
public class AccessTokenAspect {

	@Pointcut("@annotation(www.tonghao.common.AccessToken)")
	public void doAccessToken(){
		
	}
	
	@Around("doAccessToken()")
	public Object doBefore(ProceedingJoinPoint  joinPoint) throws LoginException{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Users user = UserUtil.getUser(request);
		if(user !=null && CollectionUtils.isNotEmpty(user.getRoles())){
	        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	        Method method = signature.getMethod();
	        AccessToken accessToken = method.getAnnotation(AccessToken.class);
	        String[] rolesCode = accessToken.rolesCode();
	        
	        if(rolesCode !=null && rolesCode.length >0){
	        	for(String code : rolesCode){
	        		for(Roles roles : user.getRoles()){
	        			if(code.equals(roles.getCode())){
	        				try {
	        					return joinPoint.proceed();
	        				} catch (Throwable e) {
	        					e.printStackTrace();
	        				}
	        			}
	        		}
	        	}
	        }
	        return ResultUtil.error("对不起，权限不足");
		}
		return "操作失败";
	}
}
