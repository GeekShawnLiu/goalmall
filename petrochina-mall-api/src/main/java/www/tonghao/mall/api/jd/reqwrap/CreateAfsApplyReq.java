package www.tonghao.mall.api.jd.reqwrap;

import java.util.HashMap;
import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.jd.entity.AfsApply;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

/**
 * 
 * Description: 申请售后请求参数
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class CreateAfsApplyReq implements ReqWrap {

	private AfsApply afsApply;

	public CreateAfsApplyReq(AfsApply afsApply) {
		this.afsApply = afsApply;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/" + Constant.JD_CODE + "/createAfsApply", "value");
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
		paramMap.put("jdOrderId", afsApply.getJdOrderId());
		paramMap.put("customerExpect", afsApply.getCustomerExpect());
		paramMap.put("questionDesc", afsApply.getQuestionDesc());
		paramMap.put("isNeedDetectionReport", afsApply.getIsNeedDetectionReport());
		paramMap.put("questionPic", afsApply.getQuestionPic());
		paramMap.put("isHasPackage", afsApply.getIsHasPackage());
		paramMap.put("packageDesc", afsApply.getPackageDesc());
//		paramMap.put("asCustomerDto", afsApply.getAsCustomerDto() != null ? JsonUtil.toJson(afsApply.getAsCustomerDto()) : null);
//		paramMap.put("asPickwareDto", afsApply.getAsPickwareDto() != null ? JsonUtil.toJson(afsApply.getAsPickwareDto()) : null);
//		paramMap.put("asReturnwareDto", afsApply.getAsReturnwareDto() != null ? JsonUtil.toJson(afsApply.getAsReturnwareDto()) : null);
//		paramMap.put("asDetailDto", afsApply.getAsDetailDto() != null ? JsonUtil.toJson(afsApply.getAsDetailDto()) : null);
		paramMap.put("asCustomerDto", afsApply.getAsCustomerDto() );
		paramMap.put("asPickwareDto", afsApply.getAsPickwareDto());
		paramMap.put("asReturnwareDto", afsApply.getAsReturnwareDto());
		paramMap.put("asDetailDto", afsApply.getAsDetailDto());
		
		return JsonUtil.toJson(paramMap);
	}
}
