package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.AddressGetReq;
import www.tonghao.mall.api.sn.resultwrap.AddressGetRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.FullAddressGetRequest;
import com.suning.api.entity.govbus.FullAddressGetResponse;
import com.suning.api.entity.govbus.FullAddressGetResponse.ResultInfo;
import com.suning.api.exception.SuningApiException;

/**
 * 获取全量地址
 */
public class AddressGetApi extends AbstractSnApi<AddressGetRes>{

	private static Log logger = LogFactory.getLog(AddressGetApi.class);
	
	public AddressGetApi(){
		super(new AddressGetReq());
	}
	
	@Override
	protected AddressGetRes resolver(DefaultSuningClient client) {
		String result = result(client);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		AddressGetRes res = new AddressGetRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取全量地址错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode fullAddressNode = bodyNode.path("getFullAddress");
			JsonNode resultInfoNode = fullAddressNode.path("resultInfo");
			List<ResultInfo> resultInfo = new ArrayList<ResultInfo>();
			ResultInfo fulladdress = null;
			for (JsonNode jsonNode : resultInfoNode) {
				fulladdress = new ResultInfo();
				JsonNode idNode = jsonNode.path("id");
				JsonNode pIdNode = jsonNode.path("pId");
				JsonNode levelNode = jsonNode.path("level");
				JsonNode nameNode = jsonNode.path("name");
				JsonNode snIdNode = jsonNode.path("snId");
				JsonNode secondPidNode = jsonNode.path("secondPid");
				fulladdress.setId(idNode.asText());
				fulladdress.setpId(pIdNode.asText());
				fulladdress.setLevel(levelNode.asText());
				fulladdress.setName(nameNode.asText());
				fulladdress.setSnId(snIdNode.asText());
				fulladdress.setSecondPid(secondPidNode.asText());
				resultInfo.add(fulladdress);
			}
			res.setResultInfo(resultInfo);
		}
		return res;
	}

	//获取商品目录
	@Override
	protected String result (DefaultSuningClient client){
		FullAddressGetRequest  request = new FullAddressGetRequest ();//api入参校验逻辑开关
		request.setCheckParam(true);
		FullAddressGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取品目池失败！");
		}
		return response.getBody();
	}
	
}
