package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.ProductReq;
import www.tonghao.mall.api.sn.reqwrap.ProductSkuReq;
import www.tonghao.mall.api.sn.resultwrap.ProductSkuRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.ProdPoolQueryRequest;
import com.suning.api.entity.govbus.ProdPoolQueryResponse;
import com.suning.api.entity.govbus.ProdPoolQueryResponse.ResultInfo;
import com.suning.api.exception.SuningApiException;

public class ProductSkuApi extends AbstractSnApi<ProductSkuRes> {
	private static Log logger = LogFactory.getLog(CategoryGetApi.class);
	private String catalogId;
	public ProductSkuApi(String catalogId){
		super(new ProductSkuReq());
		this.catalogId=catalogId;
		
	}
	@Override
	protected ProductSkuRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductSkuApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		ProductSkuRes res = new ProductSkuRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode prodPoolNode = bodyNode.path("queryProdPool");
			JsonNode resultInfoNode = prodPoolNode.path("resultInfo");
			List<ResultInfo> resultInfo = new ArrayList<ResultInfo>();
			ResultInfo proPoolInfo = null;
			for (JsonNode jsonNode : resultInfoNode) {
				proPoolInfo = new ResultInfo();
				JsonNode skuIdNode = jsonNode.path("skuId");
				proPoolInfo.setSkuId(skuIdNode.asText());
				resultInfo.add(proPoolInfo);
			}
			res.setResultInfo(resultInfo);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		ProdPoolQueryRequest request = new ProdPoolQueryRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setCategoryId(catalogId);
		ProdPoolQueryResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取商品池失败！");
		}
		return response.getBody();
	}

}
