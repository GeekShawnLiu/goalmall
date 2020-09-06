package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.ProductStockReq;
import www.tonghao.mall.api.sn.resultwrap.ProductStockRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.MpStockQueryRequest;
import com.suning.api.entity.govbus.MpStockQueryRequest.SkuIds;
import com.suning.api.entity.govbus.MpStockQueryResponse;
import com.suning.api.entity.govbus.MpStockQueryResponse.ResultInfo;
import com.suning.api.exception.SuningApiException;

/**
 * 获取商品库存
 */
public class ProductStockApi extends AbstractSnApi<ProductStockRes>{

	private static Log logger = LogFactory.getLog(ProductStockApi.class);
	
	private String cityId;
	private List<SkuIds> skuIds;
	
	public ProductStockApi(List<SkuIds> skuIds,String cityId){
		super(new ProductStockReq());
		this.cityId = cityId;
		this.skuIds = skuIds;
	}
	
	@Override
	protected ProductStockRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductSkuApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		ProductStockRes res = new ProductStockRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取商品库存错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode queryPriceNode = bodyNode.path("queryMpStock");
			JsonNode resultInfoNode = queryPriceNode.path("resultInfo");
			List<ResultInfo> resultInfo = new ArrayList<ResultInfo>();
			ResultInfo prodStock = null;
			for (JsonNode node : resultInfoNode) {
				prodStock = new ResultInfo();
				JsonNode skuIdNode = node.path("skuId");
				//字段类型：Int，库存状态，0:现货 1在途 2：无货
				JsonNode stateNode = node.path("state");
				prodStock.setSkuId(skuIdNode.asText());
				prodStock.setState(stateNode.asText());
				resultInfo.add(prodStock);
			}
			res.setResultInfo(resultInfo);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		MpStockQueryRequest request = new MpStockQueryRequest();//api入参校验逻辑开关
		request.setCityId(cityId);
		request.setSkuIds(skuIds);
		MpStockQueryResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取商品库存失败！");
		}
		return response.getBody();
	}

}
