package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.attwrap.PriceSkus;
import www.tonghao.mall.api.sn.reqwrap.ProductPriceReq;
import www.tonghao.mall.api.sn.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.PriceQueryRequest;
import com.suning.api.entity.govbus.PriceQueryRequest.Skus;
import com.suning.api.entity.govbus.PriceQueryResponse;
import com.suning.api.exception.SuningApiException;

/**
 * 获取商品价格
 */
public class ProductPriceApi extends AbstractSnApi<ProductPriceRes>{

	private static Log logger = LogFactory.getLog(ProductPriceApi.class);
	
	private String cityId;
	private List<Skus> skus;
	
	public ProductPriceApi(List<Skus> skus,String cityId){
		super(new ProductPriceReq());
		this.cityId = cityId;
		this.skus = skus;
	}
	
	@Override
	protected ProductPriceRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductPriceApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		ProductPriceRes res = new ProductPriceRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取商品价格错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode queryPriceNode = bodyNode.path("queryPrice");
			JsonNode resultInfoNode = queryPriceNode.path("skus");
			List<PriceSkus> list = new ArrayList<PriceSkus>();
			PriceSkus prodPrice = null;
			for (JsonNode node : resultInfoNode) {
				prodPrice = new PriceSkus();
				JsonNode skuIdNode = node.path("skuId");		//商品编码
				JsonNode nakedpriceNode = node.path("nakedprice");	//裸价
				JsonNode priceNode = node.path("price");		//价格
				JsonNode discountRateNode = node.path("discountRate");		//折扣率
				JsonNode snPriceNode = node.path("snPrice");		//易购价
				prodPrice.setSkuId(skuIdNode.asText());
				prodPrice.setNakedprice(nakedpriceNode.asText());
				prodPrice.setPrice(priceNode.asText());
				prodPrice.setDiscountRate(discountRateNode.asText());
				prodPrice.setSnPrice(snPriceNode.asText());
				list.add(prodPrice);
			}
			res.setResultInfo(list);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		PriceQueryRequest request = new PriceQueryRequest();//api入参校验逻辑开关
		request.setCityId(cityId);
		request.setSkus(skus);;
		PriceQueryResponse  response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取商品价格失败！");
		}
		return response.getBody();
	}

}
