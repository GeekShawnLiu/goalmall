package www.tonghao.mall.api.stb.reqwarp;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.stb.call.AccessTokenApi;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

public class ProductStocksReq implements ReqWrap {

	private String area;
	private List<Map<String, String>> skus;
	
	public ProductStocksReq(String area,List<Map<String, String>> skus) {
		this.area=area;
		this.skus=skus;
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.STB_CODE+"/getStockById", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken());
		param.put("Area", area);
		param.put("skus", JsonUtil.toJson(skus));
		return param;
	}

}
