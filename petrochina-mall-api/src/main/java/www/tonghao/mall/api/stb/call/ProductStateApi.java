package www.tonghao.mall.api.stb.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.reqwarp.ProductStateReq;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductStateApi extends AbstractBusinesApi<ProductStateRes> {
	private static Log logger = LogFactory.getLog(ProductStateApi.class);
	//skus 多个中间用,隔开
    public ProductStateApi(String skus) {
    	super(new ProductStateReq(skus));
    }
	@Override
	protected ProductStateRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductStateRes productStateRes=new ProductStateRes();
		productStateRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			List<ProductStateAttr> attrs=new ArrayList<>();
			for (JsonNode node : resultNode) {
				ProductStateAttr attr=new ProductStateAttr();
				String sku = node.path("sku").asText();
				Integer state = node.path("state").asInt();
				attr.setSku(sku);
				attr.setState(state);
				attrs.add(attr);
			}
			productStateRes.setList(attrs);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			productStateRes.setDesc(descNode.asText());
		}
		return productStateRes;
	}

}
