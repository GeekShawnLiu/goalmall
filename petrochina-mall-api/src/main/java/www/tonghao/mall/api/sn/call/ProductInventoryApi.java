package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.ProductInventoryReq;
import www.tonghao.mall.api.sn.resultwrap.ProductInventoryRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.InventoryGetRequest;
import com.suning.api.entity.govbus.InventoryGetRequest.SkuIds;
import com.suning.api.entity.govbus.InventoryGetResponse;
import com.suning.api.entity.govbus.InventoryGetResponse.ResultInfo;
import com.suning.api.exception.SuningApiException;

/**
 * 获取商品库存状态
 */
public class ProductInventoryApi extends AbstractSnApi<ProductInventoryRes>{

	private static Log logger = LogFactory.getLog(ProductInventoryApi.class);
	
	private String cityId;
	private String countyId;
	private List<SkuIds> skuIds;
	
	public ProductInventoryApi(String cityId,String countyId,List<SkuIds> skuIds){
		super(new ProductInventoryReq());
		this.cityId = cityId;
		this.countyId = countyId;
		this.skuIds = skuIds;
	}
	
	@Override
	protected ProductInventoryRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductInventoryApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		JsonNode errorNode = successNode.findPath("sn_error");
		ProductInventoryRes res = new ProductInventoryRes();
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取商品库存状态错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode InventoryNode = bodyNode.path("getInventory");
			JsonNode resultInfoNode = InventoryNode.path("resultInfo");
			ResultInfo prodInventory =null;
			List<ResultInfo> info=new ArrayList<InventoryGetResponse.ResultInfo>();
			for (JsonNode jsonNode : resultInfoNode) {
				prodInventory= new ResultInfo();
				prodInventory.setSkuId(jsonNode.path("skuId").asText());
				//库存状态.00：有货01：暂不销售 02：无货 03：库存不足
				prodInventory.setState(jsonNode.path("state").asText());
				//剩余库存值 （返回-1 代表库存未知）
				prodInventory.setRemainNum(jsonNode.path("remainNum").asText());
				info.add(prodInventory);
			}
			res.setResultInfo(info);
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		InventoryGetRequest request = new InventoryGetRequest();//api入参校验逻辑开关
		request.setCityId(cityId);
		request.setCountyId(countyId);
		request.setSkuIds(skuIds);
		request.setCheckParam(true);
		InventoryGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取商品库存失败！");
		}
		return response.getBody();
	}

}
