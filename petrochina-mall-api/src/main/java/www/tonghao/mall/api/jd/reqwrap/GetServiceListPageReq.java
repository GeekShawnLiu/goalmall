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
 * Description: 查询服务单概要接口请求参数
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetServiceListPageReq implements ReqWrap {

	/**
	 * 京东订单号 必填
	 */
	private String jdOrderId;

	/**
	 * 页码，1代表第一页 必填
	 */
	private Integer pageIndex;

	/**
	 * 每页记录数, 大小取值范围[1,100] 必填
	 */
	private Integer pageSize;

	public GetServiceListPageReq(String jdOrderId, Integer pageIndex, Integer pageSize) {
		this.jdOrderId = jdOrderId;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/" + Constant.JD_CODE + "/getServiceListPage", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> param = new HashMap<>();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("param", getParam());
		return param;
	}

	public String getParam() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("jdOrderId", jdOrderId);
		paramMap.put("pageIndex", pageIndex);
		paramMap.put("pageSize", pageSize);
		return JsonUtil.toJson(paramMap);
	}
}
