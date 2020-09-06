package www.tonghao.mall.api.jd.reqwrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

/**
 * 
 * Description: 查询服务单明细接口
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetServiceDetailInfoReq implements ReqWrap{

	/**
	 * 京东售后服务单号
	 * 必填
	 */
	private String afsServiceId;
	
	/**
	 * 获取信息模块：
	 * 不设置数据表示只获取服务单主信息、商品明细以及客户信息；
	 * 1、代表增加获取售后地址信息；
	 * 2、代表增加获取客户发货信息；
	 * 3、代表增加获取退款明细；
	 * 4、代表增加获取跟踪信息；
	 * 5、代表增加获取允许的操作信息
	 */
	private List<Integer> appendInfoSteps;
	
	public GetServiceDetailInfoReq(String afsServiceId, List<Integer> appendInfoSteps) {
		this.afsServiceId = afsServiceId;
		this.appendInfoSteps = appendInfoSteps;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getServiceDetailInfo", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = new HashMap<>();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("param", getParam());
		return param;
	}
	
	public String getParam(){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("afsServiceId", afsServiceId);
		paramMap.put("appendInfoSteps", appendInfoSteps);
		return JsonUtil.toJson(paramMap);
	}
}
