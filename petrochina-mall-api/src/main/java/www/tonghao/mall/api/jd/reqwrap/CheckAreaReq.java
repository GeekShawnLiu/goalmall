package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import com.google.common.collect.Maps;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

public class CheckAreaReq implements ReqWrap {

	private String skuIds;
	
	private String province;
	
	private String city;
	
	private String county;
	
	public CheckAreaReq(String skuIds,String province,String city,String county){
		this.skuIds=skuIds;
		this.province=province;
		this.city=city;
		this.county=county;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/checkArea", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("skuIds", skuIds);
		param.put("province", province);
		param.put("city", city);
		param.put("county", county);
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		return param;
	}

}
