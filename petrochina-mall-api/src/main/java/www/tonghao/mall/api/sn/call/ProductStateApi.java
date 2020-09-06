package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.ProductStateReq;
import www.tonghao.mall.api.sn.resultwrap.ProductStateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest;
import com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds;
import com.suning.api.entity.govbus.BatchProdSaleStatusGetResponse;
import com.suning.api.entity.govbus.BatchProdSaleStatusGetResponse.OnShelvesList;
import com.suning.api.exception.SuningApiException;

/**
 * 获取商品上下架状态
 */
public class ProductStateApi extends AbstractSnApi<ProductStateRes>{

	private static Log logger = LogFactory.getLog(ProductStateApi.class);
	
	private List<SkuIds> skuIds;
	
	public ProductStateApi(List<SkuIds> skuIds){
		super(new ProductStateReq());
		this.skuIds = skuIds;
	}
	
	@Override
	protected ProductStateRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductStateApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		ProductStateRes res = new ProductStateRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取商品上下架错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode prodSaleStatusNode = bodyNode.path("getBatchProdSaleStatus");
			JsonNode onShelvesNode = prodSaleStatusNode.path("onShelvesList");
			List<OnShelvesList> list = new ArrayList<OnShelvesList>();
			OnShelvesList onShelves = null;
				for(JsonNode node : onShelvesNode){
					//state:上下架状态1：在售0：下架
					onShelves = new OnShelvesList();
					JsonNode skuIdNode = node.path("skuId");
					JsonNode stateNode = node.path("state");
					onShelves.setSkuId(skuIdNode.asText());
					onShelves.setState(stateNode.asText());
					list.add(onShelves);
				}
				res.setOnShelvesList(list);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		BatchProdSaleStatusGetRequest request = new BatchProdSaleStatusGetRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setSkuIds(skuIds);
		BatchProdSaleStatusGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取商品上下架失败！");
		}
		return response.getBody();
	}

}
