package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.ProductCheckAttr;
import www.tonghao.mall.api.jd.reqwrap.ProductCheckReq;
import www.tonghao.mall.api.jd.resultwrap.ProductCheckRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductCheckApi extends AbstractBusinesApi<ProductCheckRes> {
	private static Log logger = LogFactory.getLog(ProductCheckApi.class);
	public ProductCheckApi(String sku){
		super(new ProductCheckReq(sku));
	}
	@Override
	protected ProductCheckRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductCheckRes checkRes=new ProductCheckRes();
		checkRes.setSuccess(success);
		if(success){
			List<ProductCheckAttr> attrs=new ArrayList<ProductCheckAttr>();
			JsonNode resultNode = rootNode.path("result");
			for (JsonNode jsonNode : resultNode) {
				ProductCheckAttr attr=new ProductCheckAttr();
				JsonNode is7ToReturn = jsonNode.path("is7ToReturn");
				JsonNode name = jsonNode.path("name");
				JsonNode skuId = jsonNode.path("skuId");
				JsonNode saleState = jsonNode.path("saleState");
				JsonNode isCanVAT = jsonNode.path("isCanVAT");
				attr.setIs7ToReturn(is7ToReturn.asInt());
				attr.setName(name.asText());
				attr.setSkuId(skuId.asText());
				attr.setSaleState(saleState.asInt());
				attr.setIsCanVAT(isCanVAT.asInt());
				attrs.add(attr);
			}
			checkRes.setResult(attrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			checkRes.setResultCode(resultCodeNode.asText());
			checkRes.setResultMessage(resultMessageNode.asText());
		}
		return checkRes;
	}

}
