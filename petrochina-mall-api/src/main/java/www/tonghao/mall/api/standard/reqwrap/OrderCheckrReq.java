package www.tonghao.mall.api.standard.reqwrap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Element;
import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class OrderCheckrReq implements ReqWrap{
	
	private String apiUrlPrefix;
	private String bdate;
	private String edate;
	private String emallcode;
	public OrderCheckrReq(String emallcode,String bdate,String edate){
		this.emallcode=emallcode;
		this.bdate=bdate;
		this.edate=edate;
		apiUrlPrefix = "/api_config/standard/"+emallcode;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/checkr", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		param.put("bdate", bdate);
		param.put("edate", edate);
		return param;
	}

}
