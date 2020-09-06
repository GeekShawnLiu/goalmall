package www.tonghao.mall.core;

import java.util.Map;

/**
 * 请求封装
 *
 */
public interface ReqWrap {
	HttpMethod getHttpMethod();
	
	String getApiUrl();
	
	Map<String,String> getParams();
}
