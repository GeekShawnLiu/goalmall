package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.ProductImageReq;
import www.tonghao.mall.api.sn.resultwrap.ProductImageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.ProdImageQueryRequest;
import com.suning.api.entity.govbus.ProdImageQueryRequest.SkuIds;
import com.suning.api.entity.govbus.ProdImageQueryResponse;
import com.suning.api.entity.govbus.ProdImageQueryResponse.ResultInfo;
import com.suning.api.entity.govbus.ProdImageQueryResponse.Urls;
import com.suning.api.exception.SuningApiException;

/**
 * 获取商品图片
 */
public class ProductImageApi extends AbstractSnApi<ProductImageRes>{

	private static Log logger = LogFactory.getLog(ProductImageApi.class);
	
	private List<SkuIds> skuIds;
	
	public ProductImageApi(List<SkuIds> skuIds){
		super(new ProductImageReq());
		this.skuIds = skuIds;
	}
	
	@Override
	protected ProductImageRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductImageApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		ProductImageRes res = new ProductImageRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁商品图片错误,错误码["+errorCodeNode.asText()+"],错误信息:"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode prodImageNode = bodyNode.path("queryProdImage");
			JsonNode errorMsgNode = prodImageNode.path("errorMsg");
			if (!errorMsgNode.isMissingNode()) {
				res.setError_msg(errorMsgNode.asText());
				logger.error("苏宁商品图片错误,错误信息:"+errorMsgNode.asText());
				return res;
			}
			JsonNode resultInfoNode = prodImageNode.path("resultInfo");
			List<ResultInfo> prodImageInfoList = new ArrayList<ResultInfo>();
			ResultInfo prodImageInfo =null;
				for(JsonNode infoNode : resultInfoNode){
					prodImageInfo = new ResultInfo();
					JsonNode skuIdNode = infoNode.path("skuId");
					prodImageInfo.setSkuId(skuIdNode.asText());
					JsonNode urlsNode = infoNode.path("urls");
					List<Urls> urls = new ArrayList<Urls>();
					Urls url = null;
					for(JsonNode urlNode : urlsNode){
						url = new Urls();
						JsonNode pathNode = urlNode.path("path");
						JsonNode primaryNode = urlNode.path("primary");
						url.setPath(pathNode.asText());
						url.setPrimary(primaryNode.asText());
						urls.add(url);
					}
					prodImageInfo.setUrls(urls);
					prodImageInfoList.add(prodImageInfo);
				}
				res.setResultInfo(prodImageInfoList);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		ProdImageQueryRequest request = new ProdImageQueryRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setSkuIds(skuIds);
		ProdImageQueryResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			logger.error("获取商品图片失败！");
		}
		return response.getBody();
	}

}
