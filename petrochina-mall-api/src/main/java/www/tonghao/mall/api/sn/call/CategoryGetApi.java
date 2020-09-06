package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.CategoryGetReq;
import www.tonghao.mall.api.sn.resultwrap.CategoryGetRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.CategoryGetRequest;
import com.suning.api.entity.govbus.CategoryGetResponse;
import com.suning.api.entity.govbus.CategoryGetResponse.ResultInfo;
import com.suning.api.exception.SuningApiException;

/**
 * 获取品目池
 */
public class CategoryGetApi extends AbstractSnApi<CategoryGetRes>{

	private static Log logger = LogFactory.getLog(CategoryGetApi.class);
	
	public CategoryGetApi(){
		super(new CategoryGetReq());
	}
	
	@Override
	protected CategoryGetRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("CategoryGetApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		CategoryGetRes res = new CategoryGetRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取品目池错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode categoryNode = bodyNode.path("getCategory");
			JsonNode resultInfoNode = categoryNode.path("resultInfo");
			List<ResultInfo> resultInfo = new ArrayList<ResultInfo>();
			ResultInfo categoryGetInfo = null;
			for (JsonNode jsonNode : resultInfoNode) {
				categoryGetInfo = new ResultInfo();
				JsonNode categoryIdNode = jsonNode.path("categoryId");
				JsonNode categoryNameNode = jsonNode.path("categoryName");
				categoryGetInfo.setCategoryId(categoryIdNode.asText());
				categoryGetInfo.setCategoryName(categoryNameNode.asText());
				resultInfo.add(categoryGetInfo);
			}
			res.setResultInfo(resultInfo);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		CategoryGetRequest request = new CategoryGetRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		CategoryGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取品目池失败！");
		}
		return response.getBody();
	}
	
}
