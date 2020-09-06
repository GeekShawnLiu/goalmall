package www.tonghao.mall.api.jd.reqwrap;

import java.util.HashMap;
import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

/**
 * 
 * Description: 查询商品售后类型接口请求参数
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetCustomerExpectCompReq implements ReqWrap{

	//京东订单号
	private String jdOrderId;
	
	//京东商品编号
	private String skuId;
	
	public GetCustomerExpectCompReq(String jdOrderId, String skuId) {
		this.jdOrderId = jdOrderId;
		this.skuId = skuId;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getCustomerExpectComp", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = new HashMap<>();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("param", getParam());
		return param;
	}
	
	public String getParam(){
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("jdOrderId", jdOrderId);
		paramMap.put("skuId", skuId);
		return JsonUtil.toJson(paramMap);
	}
}
