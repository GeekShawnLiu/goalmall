package www.tonghao.common.utils;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * token-工具类
 * @author developer001
 *
 */
public class TokenUtil {
	
	private static final String FROM_TOKEN = "token_";
	private static final String PARAM_TOKEN_FLAG = "reqToken";
	
	private TokenUtil(){}
	
	//生成请求token
	public static String  generate(HttpServletRequest request){
		String uuid = UUID.randomUUID().toString();
		request.getSession().setAttribute(PARAM_TOKEN_FLAG, uuid);
		return uuid;
	}
	
	//验证请求
	public static boolean  validation(HttpServletRequest request){
		return validation(request, true);
	}
	
	
	/**
	 * 验证请求
	 * @param request 
	 * @param remove 是否移除token
	 * @return
	 */
	public static boolean  validation(HttpServletRequest request,boolean remove){
		HttpSession session = request.getSession();
		Object sessionFlag = session.getAttribute(PARAM_TOKEN_FLAG);
		Object requestFlag = request.getParameter(FROM_TOKEN);
		if (requestFlag!=null&&sessionFlag != null && sessionFlag.equals(requestFlag)) {
			if(remove){
				remove(request);
			}
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 移除token
	 * @param request
	 */
	public static void remove(HttpServletRequest request){
		request.getSession().removeAttribute(PARAM_TOKEN_FLAG);
	}
	
	/**
	 * 得到session里的token
	 * @param request
	 * @return
	 */
	public static Object getSessionToken(HttpServletRequest request){
		return request.getSession().getAttribute(PARAM_TOKEN_FLAG);
	}
}