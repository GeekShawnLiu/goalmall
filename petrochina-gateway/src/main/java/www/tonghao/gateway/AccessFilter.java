package www.tonghao.gateway;

import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import www.tonghao.common.jwt.TokenAuthenticationService;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.ResultUtil;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AccessFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if (request.getMethod().equals("OPTIONS")) {
			return false;
		}
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String token = request.getHeader("token");
		String pathName = request.getServletPath();
		if (!StringUtils.isBlank(pathName) && !pathName.endsWith("login") && !pathName.contains("upload") && !pathName.contains("mallweb") 
				&& !pathName.contains("login/sendMobileCode")&& !pathName.contains("login/djApp") && !pathName.contains("login/ruixin") && !pathName.contains("/getActive") && !pathName.contains("searchweb/search") 
				&& !pathName.contains("/exportExcel") && !pathName.contains("/download") && !pathName.endsWith("ruixin") && !pathName.contains("userRegister")
				&& !pathName.contains("/supplierRegister")&& !pathName.contains("/orderPayPrice") && !pathName.contains("platform/portal") && !pathName.contains("platform/portalProduct")
				&& !pathName.contains("/api")) {
			if (StringUtils.isBlank(token)) {
				ctx.getResponse().setContentType("text/html;charset=UTF-8");
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JsonUtil.toJson(ResultUtil.resultMessage(
						"token", "请登陆后再访问")));
			} else {
				try {
					boolean authentication = TokenAuthenticationService
							.getAuthentication(request);
					if (authentication) {
						return null;
					}
				} catch (Exception e) {
					if (e instanceof ExpiredJwtException) {
						ctx.setSendZuulResponse(false);
						ctx.getResponse().setContentType(
								"text/html;charset=UTF-8");
						ctx.setResponseStatusCode(200);
						ctx.setResponseBody(JsonUtil.toJson(ResultUtil
								.resultMessage("token", "登录超时")));
					} else {
						ctx.setSendZuulResponse(false);
						ctx.getResponse().setContentType(
								"text/html;charset=UTF-8");
						ctx.setResponseStatusCode(200);
						ctx.setResponseBody(JsonUtil.toJson(ResultUtil
								.resultMessage("token", "登录错误")));
					}
				}
			}
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
