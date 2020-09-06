package www.tonghao.mall.api.stb.call;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.reqwarp.ProductPriceReq;
import www.tonghao.mall.api.stb.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductPriceApi extends AbstractBusinesApi<ProductPriceRes> {
	private static Log logger = LogFactory.getLog(ProductPriceApi.class);
	public ProductPriceApi(String skus) {
		super(new ProductPriceReq(skus));
	}
	@Override
	protected ProductPriceRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductPriceRes priceRes=new ProductPriceRes();
		priceRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			List<ProductPriceAttr> attrs=new ArrayList<>();
			for (JsonNode jsonNode : resultNode) {
				ProductPriceAttr attr=new ProductPriceAttr();
				String sku = jsonNode.path("skuId").asText();//sku
				BigDecimal bizPrice = new BigDecimal(jsonNode.path("bizPrice").asText());//协议价
				BigDecimal supplierPrice = new BigDecimal(jsonNode.path("supplierPrice").asText());//市场价
				attr.setSku(sku);
				attr.setPrice(bizPrice);
				attr.setMall_price(supplierPrice);
				attrs.add(attr);
			}
			priceRes.setAttrs(attrs);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			priceRes.setDesc(descNode.asText());
		}
		return priceRes;
	}

}
