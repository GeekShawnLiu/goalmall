package www.tonghao.mall.api.stb.call;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.ProductStocksAttr;
import www.tonghao.mall.api.stb.reqwarp.ProductStocksReq;
import www.tonghao.mall.api.stb.resultwrap.ProductStocksRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductStocksApi extends AbstractBusinesApi<ProductStocksRes> {

	private static Log logger = LogFactory.getLog(ProductStocksApi.class);
	public ProductStocksApi(String area,List<Map<String, String>> skus) {
		super(new ProductStocksReq(area, skus));
	}
	
	@Override
	protected ProductStocksRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductStocksRes productStocksRes=new ProductStocksRes();
		productStocksRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			List<ProductStocksAttr> attrs=new ArrayList<>();
			for (JsonNode jsonNode : resultNode) {
				ProductStocksAttr attr=new ProductStocksAttr(); 
				String sku = jsonNode.path("sku").asText();
				String desc = jsonNode.path("desc").asText();
				String area = jsonNode.path("area").asText();
				int num = jsonNode.path("num").asInt();
				attr.setArea(area);
				attr.setSku(sku);
				attr.setDesc(desc);
				attr.setNum(num);
				attrs.add(attr);
			}
			productStocksRes.setProductStocksAttrs(attrs);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			productStocksRes.setDesc(descNode.asText());
		}
		return productStocksRes;
	}

}
