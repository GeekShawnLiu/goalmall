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
 * Description: 取消服务单接口请求参数
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class AuditCancelReq implements ReqWrap{

	/**
	 * 京东售后服务单集合
	 */
	private List<Integer> serviceIdList;
	
	/**
	 * 审核意见
	 * 必填
	 */
	private String approveNotes;
	
	public AuditCancelReq(List<Integer> serviceIdList, String approveNotes) {
		this.serviceIdList = serviceIdList;
		this.approveNotes = approveNotes;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/auditCancel", "value");
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
		paramMap.put("serviceIdList", serviceIdList);
		paramMap.put("approveNotes", approveNotes);
		return JsonUtil.toJson(paramMap);
	}
}
